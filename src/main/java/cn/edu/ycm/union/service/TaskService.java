package cn.edu.ycm.union.service;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import cn.edu.ycm.union.database.DatabaseOper;
import cn.edu.ycm.union.dto.TaskData;
import cn.edu.ycm.union.dto.TaskObject;
import cn.edu.ycm.union.dto.TaskTemplate;
import cn.edu.ycm.union.dto.UserInfo;
import cn.edu.ycm.union.utils.GenerateID;
import cn.edu.ycm.union.utils.JsonTool;

public class TaskService {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
	
	private DatabaseOper databaseOper = new DatabaseOper();
	private UserService userService = new UserService();
	
	//增
	public void createTask(TaskObject taskObject,String creater){
		logger.info("新建一个统计任务");
		String taskID = GenerateID.getID();
		//在tasks中插入一条数据
		TaskTemplate taskTemplate = new TaskTemplate();
		taskTemplate.setTaskID(taskID);
		taskTemplate.setValidFlag("true");
		taskTemplate.setCreaterName(creater);
		taskTemplate.setTaskObject(taskObject);
		databaseOper.insertDocument("tasks", new Gson().toJson(taskTemplate));
		//再新建一个collection
		String collectionName = "task"+taskID;
		databaseOper.createCollection(collectionName);
	}
	
	
	//删
	public void delTask(String taskID){
		logger.info("删除一个统计任务");
		//删除tasks中的任务
		Bson bson = Filters.eq("taskID",taskID);
		databaseOper.deleteDocument("tasks", bson);
		//删除task+taskID Document
		databaseOper.deleteCollection("task"+taskID);
	}
	
	//改
	public void updateTask(String taskID, TaskTemplate taskTemplate){
		logger.info("更改一个统计任务");
		Bson bson = Filters.eq("taskID",taskID);
		String json = JsonTool.Json2String(taskTemplate);
		databaseOper.updateDocument("tasks", bson, json);
	}
	
	//查
	public TaskTemplate getTaskTemplateByTaskID(String taskID){
		logger.info("查询一个统计任务");
		//删除tasks中的任务
		Bson bson = Filters.eq("taskID",taskID);
		MongoCursor<Document> result = databaseOper.getDocumentByBson("tasks", bson).iterator();
		TaskTemplate ret = null;
		while(result.hasNext()){
			ret = new Gson().fromJson(result.next().toJson(),TaskTemplate.class);
		}
		return ret;
	}

	//全查
	public List<TaskTemplate> getTasks(){
		logger.info("查询所有统计任务");
		MongoCursor<Document> result = databaseOper.getDocument("tasks").iterator();
		List<TaskTemplate> ret = new ArrayList<>();
		while (result.hasNext()){
			TaskTemplate tmp = new Gson().fromJson(result.next().toJson(),TaskTemplate.class);
			ret.add(tmp);
		}
		return ret;
	}
	
	/************************** 针对用户的task **********************************/
	
	//用户请求填写
	public TaskObject getUserTaskByTaskIDAndUserID(String taskID,String userID){
		logger.info("用户"+userID+"请求填写"+taskID+"任务");
		TaskObject result = getTaskByTaskIDAndUserID(taskID,userID);
		if (result == null){//如果不存在则插入一条新的
			createTaskByTaskIDAndUserID(taskID,userID);
		}
		TaskObject ret = getTaskByTaskIDAndUserID(taskID,userID);
		logger.info(JsonTool.Json2String(ret));
		return ret;
	}
	
	
	//查
	public TaskObject getTaskByTaskIDAndUserID(String taskID,String userID){
		logger.info("用户"+userID+"查询"+taskID+"任务");
		String collectionName = "task"+taskID;
		Bson bson = Filters.eq("userID",userID);
		MongoCursor<Document> result = databaseOper.getDocumentByBson(collectionName, bson).iterator();
		TaskData ret = null;
		while(result.hasNext()){
			String tmp = result.next().toJson();
			ret = new Gson().fromJson(tmp,TaskData.class);
			return ret.getTaskObject();
		}
		return null;
	}
	
	//为某个用户新建某个任务-注：个人任务不支持删除
	public void createTaskByTaskIDAndUserID(String taskID,String userID){
		logger.info("为用户"+userID+"创建"+taskID+"任务");
		String collectionName = "task"+taskID;
		TaskData taskData = new TaskData();
		taskData.setUserID(userID);
		TaskObject taskObject = getTaskTemplateByTaskID(taskID).getTaskObject();
		taskData.setTaskObject(taskObject);
		databaseOper.insertDocument(collectionName, new Gson().toJson(taskData));
	}
	
	//为某个用户更新某个任务
	public void updateTaskByTaskIDAndUserID(String taskID,String userID,TaskObject taskObject){
		logger.info("为用户"+userID+"更新"+taskID+"任务");
		String collectionName = "task"+taskID;
		TaskData taskData = new TaskData();
		taskData.setUserID(userID);
		taskData.setTaskObject(taskObject);
		Bson bson = Filters.eq("userID",userID);
		databaseOper.updateDocument(collectionName, bson, new Gson().toJson(taskData));
	}
	
	//查某个任务下所有用户的填写记录-按工号排序
	public Map<String,TaskObject> getUserDatasByTaskID(String taskID){
		Map<String,TaskObject> ret = new TreeMap<String,TaskObject>();
		String collectionName = "task"+taskID;
		MongoCursor<Document> result = databaseOper.getDocument(collectionName).iterator();
		TaskData tmp;
		while(result.hasNext()){
			tmp = new Gson().fromJson(result.next().toJson(),TaskData.class);
			TaskObject taskObject = tmp.getTaskObject();
			ret.put(tmp.getUserID(), taskObject);
		}
		return ret;
	}
	
	public String getExcelFileUserTaskByTaskID(String taskID, String fileName){
		TaskTemplate taskTemplate = getTaskTemplateByTaskID(taskID);
		Map<String,TaskObject> result = getUserDatasByTaskID(taskID);
		//打开excel文件
		try( FileOutputStream fos = new FileOutputStream(fileName)){
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(taskTemplate.getTaskObject().getTaskTitle());
			
			int rowCnt = 0;
			Iterator<Entry<String, TaskObject>> iter = result.entrySet().iterator();  
			while (iter.hasNext()){
				Entry<String, TaskObject> element = iter.next();
				String userID = element.getKey();
				TaskObject taskObject = element.getValue();
				
				if (rowCnt == 0){//首行加入标题
					//插入一行
					HSSFRow row = sheet.createRow(rowCnt++);
					int cellCnt = 0;
					row.createCell(cellCnt++).setCellValue("工号");
					row.createCell(cellCnt++).setCellValue("姓名");
					row.createCell(cellCnt++).setCellValue("团队");
					for (int j=0; j < taskObject.getTable().getLines().size(); j++){
						row.createCell(cellCnt++).setCellValue(taskObject.getTable().getLines().get(j).getKey());
					}
				}
				
				//取用户详情，拼EXCEL
				UserInfo userInfo = userService.getUserInfoByID(userID);
				if (userInfo != null){//如果该用户不存在则不生成，如已被删除
					//新建一行
					HSSFRow row = sheet.createRow(rowCnt++);
					int cellCnt = 0;
					row.createCell(cellCnt++).setCellValue(userInfo.getId());
					row.createCell(cellCnt++).setCellValue(userInfo.getName());
					row.createCell(cellCnt++).setCellValue(userInfo.getTeam());
					for (int j=0; j < taskObject.getTable().getLines().size(); j++){
						row.createCell(cellCnt++).setCellValue(taskObject.getTable().getLines().get(j).getValue());
					}
				}
			}
			wb.write(fos);
		} catch (Exception e) {
			System.out.print("excel生成出错:");
			e.printStackTrace();
			return null;
		}
		
		return "../task-result.xlsx";
	}
	
}

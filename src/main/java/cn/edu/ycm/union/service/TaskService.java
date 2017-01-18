package cn.edu.ycm.union.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class TaskService {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
	
	private DatabaseOper databaseOper = new DatabaseOper();
	private UserService userService = new UserService();
	
	//增
	public void createTask(TaskObject taskObject){
		logger.info("新建一个统计任务");
		String taskID = GenerateID.getID();
		//在tasks中插入一条数据
		TaskTemplate taskTemplate = new TaskTemplate();
		taskTemplate.setTaskID(taskID);
		taskTemplate.setTaskObject(taskObject);
		databaseOper.insertDocument("users", new Gson().toJson(taskTemplate));
		//再新建一个collection
		String collectionName = "task"+taskID;
		databaseOper.createCollection(collectionName);
	}
	
	
	//删
	public void delTask(String taskID){
		logger.info("删除一个统计任务");
		//删除tasks中的任务
		Bson bson = Filters.eq("taskID",taskID);
		databaseOper.deleteDocument("users", bson);
		//删除task+taskID Document
		databaseOper.deleteCollection("task"+taskID);
	}
	
	//查
	public TaskObject getTaskByTaskID(String taskID){
		logger.info("查询一个统计任务");
		//删除tasks中的任务
		Bson bson = Filters.eq("taskID",taskID);
		MongoCursor<Document> result = databaseOper.getDocumentByBson("tasks", bson).iterator();
		TaskObject ret = null;
		while(result.hasNext()){
			ret = new Gson().fromJson(result.next().toJson(),TaskObject.class);
		}
		return ret;
	}

	//全查
	public List<TaskObject> getTasks(){
		logger.info("查询所有统计任务");
		MongoCursor<Document> result = databaseOper.getDocument("tasks").iterator();
		List<TaskObject> ret = new ArrayList<>();
		while (result.hasNext()){
			TaskObject tmp = new Gson().fromJson(result.next().toJson(),TaskObject.class);
			ret.add(tmp);
		}
		return ret;
	}
	
	/************************** 针对用户的task **********************************/
	
	//查
	public TaskObject getTaskByTaskIDAndUserID(String taskID,String userID){
		logger.info("用户“+userID+”查询“+taskID+”任务");
		String collectionName = "task"+taskID;
		Bson bson = Filters.eq("userID",userID);
		MongoCursor<Document> result = databaseOper.getDocumentByBson(collectionName, bson).iterator();
		TaskObject ret = null;
		while(result.hasNext()){
			ret = new Gson().fromJson(result.next().toJson(),TaskObject.class);
		}
		return ret;
	}
	
	//为某个用户新建某个任务-注：个人任务不支持删除
	public void createTaskByTaskIDAndUserID(String taskID,String userID){
		logger.info("为用户“+userID+”创建“+taskID+”任务");
		String collectionName = "task"+taskID;
		TaskData taskData = new TaskData();
		taskData.setUserID(userID);
		TaskObject taskObject = getTaskByTaskID(taskID);
		taskData.setTaskObject(taskObject);
		databaseOper.insertDocument(collectionName, new Gson().toJson(taskData));
	}
	
	//为某个用户更新某个任务
	public void updateTaskByTaskIDAndUserID(String taskID,String userID,TaskObject taskObject){
		logger.info("为用户“+userID+”更新“+taskID+”任务");
		String collectionName = "task"+taskID;
		TaskData taskData = new TaskData();
		taskData.setUserID(userID);
		taskData.setTaskObject(taskObject);
		Bson bson = Filters.eq("userID",userID);
		databaseOper.updateDocument(collectionName, bson, new Gson().toJson(taskData));
	}
	
	//查某个任务下所有用户的填写记录
	public List<Map<UserInfo,TaskObject>> getUserDatasByTaskID(String taskID){
		List<Map<UserInfo,TaskObject>> ret = new ArrayList<Map<UserInfo,TaskObject>>();
		String collectionName = "task"+taskID;
		MongoCursor<Document> result = databaseOper.getDocument(collectionName).iterator();
		TaskData tmp;
		while(result.hasNext()){
			tmp = new Gson().fromJson(result.next().toJson(),TaskData.class);
			UserInfo user = userService.getUserInfoByID(tmp.getUserID());
			TaskObject taskObject = tmp.getTaskObject();
			Map<UserInfo,TaskObject> addTmp = new HashMap<>();
			addTmp.put(user, taskObject);
			ret.add(addTmp);
		}
		return ret;
	}
	
}

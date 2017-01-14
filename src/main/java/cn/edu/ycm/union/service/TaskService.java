package cn.edu.ycm.union.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.ycm.union.database.DatabaseOper;
import cn.edu.ycm.union.dto.TaskObject;
import cn.edu.ycm.union.dto.TaskRow;
import cn.edu.ycm.union.dto.TaskTable;
import cn.edu.ycm.union.dto.UserInfo;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;
import jodd.petite.scope.ProtoScope;

@PetiteBean(scope=ProtoScope.class)
public class TaskService {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
	
	@PetiteInject
	private DatabaseOper databaseOper;
	
	//新建一个统计任务-注：任务不支持修改
	public void createTask(TaskObject taskObject){
		//databaseOper.createCollection(collectionName);
	}
	
	//删除一个统计任务
	public void createTask(String taskID){
		databaseOper.deleteCollection("task"+taskID);
	}
	
	//为某个用户新建某个任务-注：个人任务不支持删除
	
	//为某个用户更新某个任务
	
	//
	
	//输出某个任务的结果，按人排列
	public Map<UserInfo,TaskTable> outResult(String taskId){
		Map<UserInfo,TaskTable> ret = new TreeMap<>();
		return ret;
	}
	
	//将输出结果变为表格
	public List<List<String>> outResultInTable(String taskId){
		List<List<String>> ret = new ArrayList<List<String>>();
		List<String> tmp = new ArrayList<>();
		Map<UserInfo,TaskTable> data = outResult(taskId);
		for (Map.Entry<UserInfo, TaskTable> m:data.entrySet()){
			tmp.clear();
			//取id
			tmp.add(m.getKey().getId());
			//取name
			tmp.add(m.getKey().getName());
			//取team
			tmp.add(m.getKey().getTeam());
			//取cellphone
			tmp.add(m.getKey().getCellphone());
			//取table
			for (TaskRow row: m.getValue().getLines()){
				//取填写项
				tmp.add(row.getKey());
				//取填写值
				tmp.add(row.getValue());
			}
			ret.add(tmp);
		}
		return ret;
	}
	
	//Excel形式输出单个任务的所有统计结果
	public void outrResultInExcel(String taskId){
		
	}
	
	
}

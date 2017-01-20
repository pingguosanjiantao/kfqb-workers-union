package cn.edu.ycm.union.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.ycm.union.dto.ReturnMsg;
import cn.edu.ycm.union.dto.TaskObject;
import cn.edu.ycm.union.dto.TaskTemplate;
import cn.edu.ycm.union.service.TaskService;
import cn.edu.ycm.union.utils.JsonTool;
import cn.edu.ycm.union.utils.ReturnTool;
import cn.edu.ycm.union.utils.UserTool;

@Path("/action")
public class TaskController {

	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
	
	private TaskService taskService = new TaskService();
	
	@GET
	@Path("tasks")
	public String getTasks(@Context HttpServletRequest request){
		logger.info("查询所有任务");
		if (!UserTool.isAdmin(request)){
			return ReturnTool.getFailedStringReturn("没有权限");
		}
		List<TaskTemplate> result = taskService.getTasks();
		ReturnMsg ret = ReturnTool.getSuccReturn();
		ret.setTasks(result);
		return JsonTool.Json2String(ret);
	}
	
	@POST
	@Path("task")
	public String createTask(@FormParam("newtask") String newTaskJson,
		      			     @Context HttpServletRequest request){
		logger.info("");
		//反序列化为对象
		TaskObject taskObject = (TaskObject) JsonTool.String2Json(newTaskJson, TaskObject.class);
		logger.info("新增任务:"+JsonTool.Json2String(taskObject));
		if (!UserTool.isAdmin(request)){
			return ReturnTool.getFailedStringReturn("没有权限");
		}
		taskService.createTask(taskObject,UserTool.getUserNameBySession(request));
		return ReturnTool.getSuccStringReturn();
	}
	
	@DELETE
	@Path("task/{id}")
	public String delateTask(@PathParam("id") String id){
		System.out.println("request 删");
		String result  = "删";
		return result;
	}
	
	@PUT
	@Path("task/{id}")
	public String updateTask(@PathParam("id") String id){
		System.out.println("request 改");
		String result  = "改";
		return result;
	}
	
	@GET
	@Path("task/{id}")
	public String RetrieveTask(@PathParam("id") String id){
		System.out.println("request 查");
		String result  = "查";
		
		
		//从数据库找到对应的task，然后反馈给前台
		
		
		
		
		
		
		
		
		
		return result;
	}
}

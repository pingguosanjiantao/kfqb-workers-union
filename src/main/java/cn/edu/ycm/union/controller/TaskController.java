package cn.edu.ycm.union.controller;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;

import cn.edu.ycm.union.database.DatabaseOper;
import cn.edu.ycm.union.utils.TaskTemplate;

@Path("/action")
public class TaskController {

	private DatabaseOper dbOper = new DatabaseOper();
	
	@GET
	@Path("tasks")
	public String getTasks(){
		System.out.println("request getTasks");
		List<TaskTemplate> tempaltes = dbOper.getTaskTemplates();
		//String result  = "列表";
		return new Gson().toJson(tempaltes);
	}
	
	@POST
	@Path("task")
	public String createTask(){
		System.out.println("request 增");
		String result  = "增";
		return result;
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

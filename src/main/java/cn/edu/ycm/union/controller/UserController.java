package cn.edu.ycm.union.controller;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/action")
public class UserController {
	@GET
	@Path("users")
	public String getTasks(){
		String result  = "列表";
		return result;
	}
	
	@POST
	@Path("user")
	public String createTask(){
		String result  = "增";
		return result;
	}
	
	@DELETE
	@Path("user/{id}")
	public String delateTask(@PathParam("id") String id){
		String result  = "删";
		return result;
	}
	
	@PUT
	@Path("user/{id}")
	public String updateTask(@PathParam("id") String id){
		String result  = "改";
		return result;
	}
	
	@GET
	@Path("user/{id}")
	public String RetrieveTask(@PathParam("id") String id){
		String result  = "查";
		return result;
	}
}

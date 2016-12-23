package cn.edu.ycm.union.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/action")
public class TestRest {

	@GET
	@Produces("application/json;charset=utf-8")
	@Path("subpath/{id}")
	public String get(@PathParam("id") String id){
		String result  = "杨成敏成功打出了"+id;
		return result;
	}
		
}

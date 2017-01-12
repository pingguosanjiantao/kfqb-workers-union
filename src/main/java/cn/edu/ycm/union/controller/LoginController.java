package cn.edu.ycm.union.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/login")
public class LoginController {
	
	static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@POST
	@Path("/")
	public void getTasks(@FormParam("username") String first,
									@FormParam("password") String second,
									@Context HttpServletResponse response) throws IOException{
		
		logger.info("工号为"+first+"-"+"名字是"+second+"的用户登录");
		//String result  = "列表";
		
		//成功进入主页面
		response.sendRedirect("./pages/main.html");
		//失败进入登录页面
		
	}
}

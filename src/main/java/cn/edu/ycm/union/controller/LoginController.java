package cn.edu.ycm.union.controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/login")
public class LoginController {
	static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@POST
	@Path("/")
	public void getTasks(){
		logger.info("denglu");
		//String result  = "列表";
	}
}

package cn.edu.ycm.union.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import cn.edu.ycm.union.dto.ReturnMsg;
import cn.edu.ycm.union.dto.UserInfo;
import cn.edu.ycm.union.service.UserService;
import cn.edu.ycm.union.userbuffer.UserMap;

@Path("/login")
public class LoginController {
	
	static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	private UserMap userMap = new UserMap();
	
	private UserService userService = new UserService();
	
	@POST
	@Path("/")
	public String getTasks(@FormParam("username") String userID,
						   @FormParam("password") String cellphone,
						   @Context HttpServletResponse response) throws IOException{
		
		logger.info("工号为"+userID+"的用户登录");
		
		ReturnMsg ret = new ReturnMsg();
		
		//查询是否已登录
		if (userMap.isLogin(userID)){
			logger.info("工号为"+userID+"的用户已经登录");
			response.sendRedirect("./pages/main.html");
		}else{
			UserInfo result = userService.getUserInfoByID(userID);
			if (result == null){
				logger.info("工号为"+userID+"的用户不存在");
				ret.setMsg("工号为"+userID+"的用户不存在");
				return new Gson().toJson(ret);
			}
			if (result.getCellphone().equals(cellphone)){
				logger.info("工号为"+userID+"的用户密码校验通过");
				//response.sendRedirect("./pages/main.html");
				ret.setMsg("ok");
				return new Gson().toJson(ret);
			}
			logger.info("工号为"+userID+"的用户登录密码错误");
		}
		logger.info("工号为"+userID+"的用户登录失败");
		ret.setMsg("密码错误");
		return new Gson().toJson(ret);
	}
}

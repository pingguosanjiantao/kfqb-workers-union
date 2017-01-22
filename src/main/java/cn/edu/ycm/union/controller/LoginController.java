package cn.edu.ycm.union.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import cn.edu.ycm.union.utils.ReturnTool;
import cn.edu.ycm.union.utils.UserTool;

@Path("/login")
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	private UserMap userMap = new UserMap();
	
	private UserService userService = new UserService();
	
	@POST
	@Path("/login")
	public String login(@FormParam("username") String userID,
						   @FormParam("password") String cellphone,
						   @Context HttpServletRequest request) throws IOException{
		
		logger.info("工号为"+userID+"的用户登录");
		
		UserInfo result = userService.getUserInfoByID(userID);
		
		if (result == null){
			logger.info("工号为"+userID+"的用户不存在");
			return doUndefinedUser(userID);
		}

		if (result.getCellphone().equals(cellphone)){
			logger.info("工号为"+userID+"的用户密码校验通过");
			return doPassValidate(result, request);
		}
		
		logger.info("工号为"+userID+"的用户密码错误");
		return doWrongPassword();
	}
	
	@POST
	@Path("/logout")
	public String logout(@Context HttpServletRequest request) throws IOException{
		
		UserInfo userInfo = UserTool.getUserBySession(request);
		logger.info("工号为"+userInfo.getId()+"的用户退出");
		doLogout(userInfo);
		return ReturnTool.getSuccStringReturn();
	}
	
	
	private void doLogout(UserInfo userInfo) {
		userMap.delUser(userInfo);
	}

	private String doUndefinedUser(String userID){
		ReturnMsg ret = new ReturnMsg();
		ret.setStatus("1");
		ret.setMsg("工号为"+userID+"的用户不存在");
		return new Gson().toJson(ret);
	}
	
	private String doWrongPassword(){
		ReturnMsg ret = new ReturnMsg();
		ret.setStatus("1");
		ret.setMsg("密码错误");
		return new Gson().toJson(ret);
	}
	
	private String doPassValidate(UserInfo result, HttpServletRequest request){
		ReturnMsg ret = new ReturnMsg();
		//放入userMap
		userMap.insertUser(result);
		//将用户信息放入session
		HttpSession session = request.getSession();
		session.setAttribute("userId",result );
		ret.setStatus("0");
		ret.setJumpurl("./pages/main.html");
		return new Gson().toJson(ret);
	}
	
}

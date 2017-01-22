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
import cn.edu.ycm.union.dto.UserInfo;
import cn.edu.ycm.union.service.UserService;
import cn.edu.ycm.union.utils.JsonTool;
import cn.edu.ycm.union.utils.ReturnTool;
import cn.edu.ycm.union.utils.UserTool;

@Path("/action")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private UserService userService = new UserService();
	
	@GET
	@Path("users")
	public String getUsers(@Context HttpServletRequest request){
		logger.info("查询所有用户");
		if (!UserTool.isAdmin(request)){
			return ReturnTool.getFailedStringReturn("没有权限");
		}
		List<UserInfo> result = userService.getUsers();
		ReturnMsg ret = ReturnTool.getSuccReturn();
		ret.setUsers(result);
		return JsonTool.Json2String(ret);
	}
	
	@POST
	@Path("user")
	public String createUser(@FormParam("newuser") String newUserJson,
							 @Context HttpServletRequest request){
		//反序列化为对象
		UserInfo newUser = (UserInfo) JsonTool.String2Json(newUserJson, UserInfo.class);
		//防止前台被人篡改，强制新用户为非管理员
		newUser.setAdminFlag("false");
		logger.info("新增id为"+newUser.getId()+"的用户");
		if (!UserTool.isAdmin(request)){
			return ReturnTool.getFailedStringReturn("没有权限");
		}
		boolean succFlag = userService.insertUser(newUser);
		if (succFlag == false){//插入成功
			return ReturnTool.getFailedStringReturn("已存在该用户");
		}
		return ReturnTool.getSuccStringReturn();
	}
	
	@DELETE
	@Path("user/{id}")
	public String delateTask(@PathParam("id") String id,
							 @Context HttpServletRequest request){
		logger.info("删除id为"+id+"的用户");
		if (!UserTool.isAdmin(request)){
			return ReturnTool.getFailedStringReturn("没有权限");
		}
		userService.deleteUser(id);
		return ReturnTool.getSuccStringReturn();
	}
	
	@PUT
	@Path("user/{id}")
	public String updateTask(@FormParam("newuser") String newUserJson,
							 @Context HttpServletRequest request){
		//反序列化为对象
		UserInfo newUser = (UserInfo) JsonTool.String2Json(newUserJson, UserInfo.class);
		logger.info("修改id为"+newUser.getId()+"的用户");
		if (!UserTool.isAdmin(request)){
			return ReturnTool.getFailedStringReturn("没有权限");
		}
		userService.updateUserInfo(newUser);
		return ReturnTool.getSuccStringReturn();
	}
	
	@GET
	@Path("user/{id}")
	public String RetrieveTask(@PathParam("id") String id,
							   @Context HttpServletRequest request){
		if (id.equals("self")){
			id= UserTool.getUserIDBySession(request);
		}
		logger.info("查询id为"+id+"的用户");
		if (!UserTool.isAdminOrSelf(id,request)){
			return ReturnTool.getFailedStringReturn("没有权限");
		}
		ReturnMsg ret = ReturnTool.getSuccReturn();
		UserInfo result = userService.getUserInfoByID(id);
		ret.setUser(result);
		return JsonTool.Json2String(ret);
	}
	
}

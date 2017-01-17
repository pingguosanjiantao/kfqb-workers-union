package cn.edu.ycm.union.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

import com.google.gson.Gson;

import cn.edu.ycm.union.dto.ReturnMsg;
import cn.edu.ycm.union.dto.UserInfo;
import cn.edu.ycm.union.service.UserService;
import cn.edu.ycm.union.userbuffer.UserMap;

@Path("/action")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private UserService userService = new UserService();
	private UserMap userMap = new UserMap();
	
	@GET
	@Path("users")
	public String getUsers(@Context HttpServletRequest request){
		logger.info("查询所有用户");
		ReturnMsg ret;
		if (isAdmin(request)){//如果是管理员
			List<UserInfo> result = userService.getUsers();
			ret = succReturn();
			ret.setUsers(result);
		}else{//如果不是管理员
			ret = failedReturn("没有权限");
		}
		return jsoner(ret);
	}
	
	@POST
	@Path("user")
	public String createUser(@FormParam("newuser") String newUserJson,
											 @Context HttpServletRequest request){
		//反序列化为对象
		UserInfo newUser = new Gson().fromJson(newUserJson, UserInfo.class);
		//防止前台被人篡改，强制新用户为非管理员
		newUser.setAdminFlag("false");
		logger.info("新增id为"+newUser.getId()+"的用户");
		ReturnMsg ret;
		if (isAdmin(request)){//如果是管理员
			boolean succFlag = userService.insertUser(newUser);
			if (succFlag == true){//插入成功
				ret = succReturn();
			}else{
				ret = failedReturn("已存在该用户");
			}
		}else{//如果不是管理员
			ret = failedReturn("没有权限");
		}
		return jsoner(ret);
	}
	
	@DELETE
	@Path("user/{id}")
	public String delateTask(@PathParam("id") String id,
											@Context HttpServletRequest request){
		logger.info("删除id为"+id+"的用户");
		ReturnMsg ret;
		if (isAdmin(request)){//如果是管理员
			ret = succReturn();
			userService.deleteUser(id);
		}else{//如果不是管理员
			ret = failedReturn("没有权限");
		}
		return jsoner(ret);
	}
	
	@PUT
	@Path("user/{id}")
	public String updateTask(@FormParam("newuser") String newUserJson,
											  @Context HttpServletRequest request){
		//反序列化为对象
		UserInfo newUser = new Gson().fromJson(newUserJson, UserInfo.class);
		logger.info("修改id为"+newUser.getId()+"的用户");
		ReturnMsg ret;
		if (isAdmin(request)){//如果是管理员
			userService.updateUserInfo(newUser);
			ret = succReturn();
		}else{//如果不是管理员
			ret = failedReturn("没有权限");
		}
		return jsoner(ret);
	}
	
	@GET
	@Path("user/{id}")
	public String RetrieveTask(@PathParam("id") String id,
												@Context HttpServletRequest request){
		logger.info("查询id为"+id+"的用户");
		ReturnMsg ret;
		if (isAdminOrSelf(id,request)){//如果是管理员
			ret = succReturn();
			UserInfo result = userService.getUserInfoByID(id);
			ret.setUser(result);
		}else{//如果不是管理员
			ret = failedReturn("没有权限");
		}
		return jsoner(ret);
	}
	
	//校验是否为管理员
	private boolean isAdmin(HttpServletRequest request){
		UserInfo userInfo = getUserBySession(request);
		if (userInfo.getAdminFlag().equals("true")){
			return true;
		}
		return false;
	}
	
	private ReturnMsg failedReturn(String msg){
		ReturnMsg ret = new ReturnMsg();
		ret.setStatus("1");
		ret.setMsg(msg);
		return ret;
	}
	
	private ReturnMsg succReturn(){
		ReturnMsg ret = new ReturnMsg();
		ret.setStatus("0");
		return ret;
	}
	
	private String jsoner(Object ob){
		return new Gson().toJson(ob);
	}
	
	private UserInfo getUserBySession(HttpServletRequest request){
		HttpSession session = request.getSession();
		UserInfo userInfoSession = (UserInfo) session.getAttribute("userId");
		UserInfo userInfo = userMap.getUserInfo(userInfoSession.getId());
		return userInfo;
	}
	
	//查询个人信息只能是管理员或者用户自己
	private boolean isAdminOrSelf(String id, HttpServletRequest request){
		UserInfo userInfo = getUserBySession(request);
		if (userInfo.getId().equals(id) || userInfo.getAdminFlag().equals("true")){
			return true;
		}
		return false;
	}
	
}

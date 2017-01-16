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
		ReturnMsg ret = new ReturnMsg();
		if (isAdmin(request)){//如果是管理员
			ret.setStatus("0");
			List<UserInfo> result = userService.getUsers();
			ret.setUsers(result);
		}else{//如果不是管理员
			ret.setStatus("1");
			ret.setMsg("没有权限");
		}
		return new Gson().toJson(ret);
	}
	
	@POST
	@Path("user")
	public String createUser(@FormParam("newuser") String newUserJson){
		UserInfo newUser = new Gson().fromJson(newUserJson, UserInfo.class);
		//防止前台被人篡改
		newUser.setAdminFlag("false");
		logger.info("新增id为"+newUser.getId()+"的用户");
		userService.insertUser(newUser);
		ReturnMsg ret = new ReturnMsg();
		ret.setStatus("0");
		return new Gson().toJson(ret);
	}
	
	@DELETE
	@Path("user/{id}")
	public String delateTask(@PathParam("id") String id,
											@Context HttpServletRequest request){
		logger.info("删除id为"+id+"的用户");
		ReturnMsg ret = new ReturnMsg();
		if (isAdmin(request)){//如果是管理员
			ret.setStatus("0");
			userService.deleteUser(id);
		}else{//如果不是管理员
			ret.setStatus("1");
			ret.setMsg("没有权限");
		}
		return new Gson().toJson(ret);
	}
	
	@PUT
	@Path("user/{id}")
	public String updateTask(@PathParam("id") String id){
		String result  = "改";
		return result;
	}
	
	@GET
	@Path("user/{id}")
	public String RetrieveTask(@PathParam("id") String id,
												@Context HttpServletRequest request){
		logger.info("查询id为"+id+"的用户");
		ReturnMsg ret = new ReturnMsg();
		if (isAdmin(request)){//如果是管理员
			ret.setStatus("0");
			UserInfo result = userService.getUserInfoByID(id);
			ret.setUser(result);
		}else{//如果不是管理员
			ret.setStatus("1");
			ret.setMsg("没有权限");
		}
		return new Gson().toJson(ret);
	}
	
	//校验是否为管理员
	private boolean isAdmin(HttpServletRequest request){
		HttpSession session = request.getSession();
		UserInfo userInfoSession = (UserInfo) session.getAttribute("userId");
		UserInfo userInfo = userMap.getUserInfo(userInfoSession.getId());
		if (userInfo.getAdminFlag().equals("true")){
			return true;
		}
		return false;
	}
	
}

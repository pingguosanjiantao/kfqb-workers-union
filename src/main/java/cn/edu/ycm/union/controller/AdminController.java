package cn.edu.ycm.union.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import cn.edu.ycm.union.dto.ReturnMsg;
import cn.edu.ycm.union.dto.UserInfo;
import cn.edu.ycm.union.userbuffer.UserMap;

@Path("/admin")
public class AdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	private UserMap userMap = new UserMap();
	
	@POST
	@Path("/")
	public String getTasks(@Context HttpServletRequest request) throws IOException{
		HttpSession session = request.getSession();
		UserInfo userInfoSession = (UserInfo) session.getAttribute("userId");
		//强制每次从缓存中取用户详情，防止前台篡改
		UserInfo userInfo = userMap.getUserInfo(userInfoSession.getId());
		logger.info("用户"+userInfo.getId()+"请求管理员链接");
		if (userInfo.getAdminFlag().equals("true")){
			return doIsAdmin(userInfo);
		}
		return doNotAdmin(userInfo);
	}

	private String doIsAdmin(UserInfo userInfo) {
		logger.info("用户"+userInfo.getId()+"是管理员，返回链接地址");
		ReturnMsg ret = new ReturnMsg();
		ret.setStatus("0");
		ret.setJumpurl("../pages/admin.html");
		return new Gson().toJson(ret);
	}

	private String doNotAdmin(UserInfo userInfo) {
		logger.info("用户"+userInfo.getId()+"不是管理员");
		ReturnMsg ret = new ReturnMsg();
		ret.setStatus("1");
		ret.setMsg("(,,Ծ‸Ծ,,) ，还不是管理员，加油吧！！！");
		return new Gson().toJson(ret);
	}

}

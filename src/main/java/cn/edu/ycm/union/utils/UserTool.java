package cn.edu.ycm.union.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.edu.ycm.union.dto.UserInfo;
import cn.edu.ycm.union.userbuffer.UserMap;

public class UserTool {
	
	/**
	 * 从request获取用户id
	 * @param request
	 * @return
	 */
	public static String getUserNameBySession(HttpServletRequest request){
		return getUserBySession(request).getName();
	}
	
	/**
	 * 从request获取用户id
	 * @param request
	 * @return
	 */
	public static String getUserIDBySession(HttpServletRequest request){
		return getUserBySession(request).getId();
	}
	
	/**
	 * 从request session获取用户信息
	 * @param request
	 * @return
	 */
	public static UserInfo getUserBySession(HttpServletRequest request){
		UserMap userMap = new UserMap();
		HttpSession session = request.getSession();
		UserInfo userInfoSession = (UserInfo) session.getAttribute("userId");
		UserInfo userInfo = userMap.getUserInfo(userInfoSession.getId());
		return userInfo;
	}
	
	/**
	 * 查询个人信息只能是管理员或者用户自己
	 * @param id
	 * @param request
	 * @return
	 */
	public static boolean isAdminOrSelf(String id, HttpServletRequest request){
		UserInfo userInfo = getUserBySession(request);
		if (userInfo.getId().equals(id) || userInfo.getAdminFlag().equals("true")){
			return true;
		}
		return false;
	}
	
	/**
	 * 校验是否为管理员
	 * @param request
	 * @return
	 */
	public static boolean isAdmin(HttpServletRequest request){
		UserInfo userInfo = getUserBySession(request);
		if (userInfo.getAdminFlag().equals("true")){
			return true;
		}
		return false;
	}
}

package cn.edu.ycm.union.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import cn.edu.ycm.union.dto.UserInfo;
import cn.edu.ycm.union.service.UserService;
import cn.edu.ycm.union.userbuffer.UserMap;
import jodd.petite.meta.PetiteInject;

@Path("/login")
public class LoginController {
	
	//static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@PetiteInject
	private UserMap userMap;
	
	@PetiteInject
	private UserService userService;
	
	@POST
	@Path("/")
	public String getTasks(@FormParam("username") String userID,
									@FormParam("password") String cellphone,
									@Context HttpServletResponse response) throws IOException{
		
		//logger.info("工号为"+userID+"的用户登录");
		
		//查询是否已登录
		if (userMap.isLogin(userID)){
			//logger.info("工号为"+userID+"的用户已经登录");
			response.sendRedirect("./pages/main.html");
			return "ok";
		}else{
			UserInfo result = userService.getUserInfoByID(userID);
			if (result.getCellphone().equals(cellphone)){
				//logger.info("工号为"+userID+"的用户密码校验通过");
				response.sendRedirect("./pages/main.html");
				return "ok";
			}
			//logger.info("工号为"+userID+"的用户登录密码错误");
		}
		//logger.info("工号为"+userID+"的用户登录失败");
		return "密码错误";
	}
}

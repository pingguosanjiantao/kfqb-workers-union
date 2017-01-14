package cn.edu.ycm.union.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.ycm.union.dto.UserInfo;
import cn.edu.ycm.union.userbuffer.UserMap;
import jodd.petite.meta.PetiteInject;

public class LoginFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);
	
	@PetiteInject
	private UserMap userMap;
	
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("userId");
		if (userInfo == null || ! userMap.isLogin(userInfo.getId())){
			logger.info("取不到用户信息或用户未登录，返回登录页面");
			//res.sendRedirect("./");
		}
		//如果该用户未登录则返回欢迎登录页面
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

}

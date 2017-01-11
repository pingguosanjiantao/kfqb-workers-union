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
import cn.edu.ycm.union.usercache.UserMap;

public class LoginFilter implements Filter {

	static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);
	
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
		if (userInfo != null ){
			System.out.println("get the person");
		}else{
			System.out.println("can not get the person");
		}
		//如果该用户未登录则返回欢迎登录页面
		//res.sendRedirect("./");
		logger.info("经过了filter");
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

}

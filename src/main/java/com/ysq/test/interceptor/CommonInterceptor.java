package com.ysq.test.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ysq.test.entity.User;
import com.ysq.test.service.UserService;
import com.ysq.test.util.SessionContext;

public class CommonInterceptor extends HandlerInterceptorAdapter {

	@Resource(name = "userService")
	private UserService service;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getParameter("token");
		HttpSession session = null;
		if (token != null && token.length() > 1) {
			session = SessionContext.getInstance().getSession(token);
		}
		if (session != null && session.getAttribute("userId") != null) {// session存在,返回true
			session.setMaxInactiveInterval(14400);
			return true;
		} else {// 不存在去数据库查找
			User user = service.findUserByAccessToken(token);
			if (user != null) {
				session = request.getSession();
				session.setMaxInactiveInterval(14400);
				session.setAttribute("userId", user.getId());
				session.setAttribute("token", token);
				SessionContext.getInstance().AddSession(token, session);
				return true;
			}
		}
		return false;
	}

}

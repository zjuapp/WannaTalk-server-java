package com.wannatalk.server.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wannatalk.server.web.common.CommonHttpServlet;

public class LoginServlet extends CommonHttpServlet{

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(userName + " " + password);
		response.getWriter().print(qs.judge(userName, password));	
	}
}

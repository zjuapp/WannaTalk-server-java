package com.wannatalk.server.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wannatalk.server.web.common.CommomHttpServlet;

public class RegisterServlet extends CommomHttpServlet{

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		if(userName == null) {
			System.out.println("no username!");
		}
			
		if(qs.registerUser(userName, password)) {
			responseSucc(response, "");
		} else {
			responseError(response, "");
		}
	}


}

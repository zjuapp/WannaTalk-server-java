package com.wannatalk.server.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.wannatalk.server.web.common.CommonHttpServlet;

public class RegisterServlet extends CommonHttpServlet{
	public static final String TAG = "RegisterServlet";
	private Logger log = Logger.getLogger(RegisterServlet.class);
	public RegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug(TAG + "is invoked");
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		if(userName == null) {
			System.out.println("no username!");
		}
		if(qs.registerUser(userName, password, sex)) {
			response.getWriter().print("1");
		} else {
			response.getWriter().print("0");
		}
	}
}
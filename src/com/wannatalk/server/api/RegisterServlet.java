package com.wannatalk.server.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.wannatalk.server.model.User;
import com.wannatalk.server.web.common.CommonHttpServlet;

public class RegisterServlet extends CommonHttpServlet{
	Logger log = Logger.getLogger(RegisterServlet.class);
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("RegisterServlet is invoked");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int sex = Integer.parseInt(request.getParameter("sex"));
		int motion = Integer.parseInt(request.getParameter("motion"));
		if(username == null) {
			responseError(response, "user name is required");
			return;
		}
		if(sex != 0 && sex != 1) {
			responseError(response, "sex should be 0 or 1");
			return;
		}
		/*
		if(motion ...)    motionµÄÈ¡Öµ·¶Î§
		*/
		User user = new User(username, password, sex, motion);
		if(qs.registerUser(user)) {
			responseSucc(response, "");
		} else {
			responseError(response, "");
		}
	}


}

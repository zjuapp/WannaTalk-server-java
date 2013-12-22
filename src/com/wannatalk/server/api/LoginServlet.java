package com.wannatalk.server.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.spdy.api.Settings.Flag;

import com.wannatalk.server.model.User;
import com.wannatalk.server.web.common.CommonHttpServlet;

public class LoginServlet extends CommonHttpServlet{

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(userName + " " + password);
		List <User> res =  qs.judge(userName, password);
		if(res == null)
			response.getWriter().print("false");
		else {
			Boolean flag = qs.updatestate(res.get(0).uid, 1);
			System.out.println("update state " + res.get(0).uid + " 1 " + flag);
			outputuser(res, response);
		}
	}
}

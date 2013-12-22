package com.wannatalk.server.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.wannatalk.server.web.common.CommonHttpServlet;

public class UpdateStateServlet extends CommonHttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.valueOf(request.getParameter("id"));
		int state = Integer.valueOf(request.getParameter("state"));
		if(state != 0 && state != 1){
			System.out.println("change num error");
			response.getWriter().print("false");
		}
		else{
			Boolean  flag = qs.updatestate(id, state);
			response.getWriter().print(flag);
			System.out.println("update state" + id + " " + state + " " + flag);
		}
		
	}
}

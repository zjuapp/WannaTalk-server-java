package com.wannatalk.server.web.common;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.wannatalk.server.data.QueryService;
import com.wannatalk.server.data.h2.QueryServiceImpl;

public class CommonHttpServlet extends HttpServlet{
	protected QueryService qs = null;
	static Logger log = Logger.getLogger(CommonHttpServlet.class);
	protected static Gson gson = new Gson();
	
	@Override
	public void init() {
		try {
			if (qs == null) {
				qs = new QueryServiceImpl();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected void responseError(HttpServletResponse response ,String mesg) throws IOException {
		response.setContentType("text/plain");
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		response.getWriter().println("false: " + mesg );
		
	}
	
	protected void responseSucc(HttpServletResponse response, String mesg) throws IOException {
		response.setContentType("text/plain");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("true: " + mesg);
	}

	
}

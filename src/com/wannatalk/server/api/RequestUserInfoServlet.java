package com.wannatalk.server.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.wannatalk.server.model.User;
import com.wannatalk.server.web.common.CommonHttpServlet;

public class RequestUserInfoServlet  extends CommonHttpServlet{
	public static final String TAG = "RequestUserInfo";
	private static Logger log = Logger.getLogger(RequestUserInfoServlet.class); 
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("JServer - " + TAG + " is invoked");
		log.debug(TAG + " receiveed : " + request.getContentLength() + " bytes");
		String uid = request.getParameter("uid");
		if(uid == null) {
			log.error("Error : uid is required");
			responseError(response, "Error : uid is required");
			return;
		}
		User user = qs.getUser(uid);
		if(user == null) {
			log.error("get user error, user == null!");
			return;
		}
		log.debug("get user ok ! username : " + user.username );
		Gson gson = new Gson();
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", user.username);
		String info = gson.toJson(params);
		response.getOutputStream().write(info.getBytes());
	}
}

package com.wannatalk.server.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;

import com.wannatalk.server.ServerConfig;
import com.wannatalk.server.web.common.CommonHttpServlet;

public class PushMessageServlet extends CommonHttpServlet{
	private static Logger log = Logger.getLogger(PushMessageServlet.class);
	public static final String TAG = "PushMessageServlet";
	private static int sendId = getRandomSendNo();
	private static final JPushClient jPushClient = new JPushClient(ServerConfig.MASTER_SECRET, ServerConfig.JPUSH_APPKEY);
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug(TAG + " is invoked");
		String mesg     = request.getParameter("message");
		String friend   = request.getParameter("friend");
		String uid      = request.getParameter("uid");
		String username = qs.getUser(uid).username;
		if(uid == null) {
			log.error("Error : uid is required");
			responseError(response, "Error : uid is required");
			return;
		}
		if(mesg == null) {
			log.error("Error : message is required");
			responseError(response, "Error : uid is required");
			return;
		}
		if(friend == null) {
			log.error("Error : friend is required");
			responseError(response, "Error : friend is required");
			return;
		}
		MessageResult mr = null;
		Map<String , Object> extra = new HashMap<String, Object>();
		sendId++;
		log.debug("send id = " + sendId);
		extra.put("sendNo", sendId);
		mr = jPushClient.sendCustomMessageWithAlias(sendId, friend, username, mesg, null, extra);
		if(mr == null) {
			log.error("Error : unexcepted null result");
			responseError(response, "Error : unexcepted null result");
			return;
		}else if (mr.getErrcode() != 0) {
			String info = "message result error code : " + mr.getErrcode() +
					" error message : " + mr.getErrmsg() + 
					" sendNo : " + mr.getSendno();
			log.error("Error : " + info);
			responseError(response, info);
			return;
		} else {
			responseSucc(response, "send ok");
		}
	}
	public static final int MIN = Integer.MIN_VALUE / 4;
	public static final int MAX = Integer.MAX_VALUE / 2;
	
	public static int getRandomSendNo() {
		return (int) (MIN + Math.random() * (MAX - MIN));
	}
}

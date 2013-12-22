package com.wannatalk.server;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.wannatalk.server.api.LoginServlet;
import com.wannatalk.server.api.PushMessageServlet;
import com.wannatalk.server.api.RegisterServlet;
import com.wannatalk.server.api.RequestUserInfoServlet;
import com.wannatalk.server.api.SearchServlet;
import com.wannatalk.server.api.UpdatePosServlet;
import com.wannatalk.server.api.UpdateSignatureAndMotionServlet;
import com.wannatalk.server.api.UpdateStateServlet;
import com.wannatalk.server.api.UploadImageServlet;
import com.wannatalk.server.web.HelloServlet;
public class JServer {
	static Logger log = Logger.getLogger(JServer.class);
	
	public static void main(String[] args) throws Exception {
		Server server = new Server(8081);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new HelloServlet()), "/hello");
        context.addServlet(new ServletHolder(new RegisterServlet()),"/api/register");
        context.addServlet(new ServletHolder(new LoginServlet()),"/api/login");
        context.addServlet(new ServletHolder(new RequestUserInfoServlet()), "/api/request_user_info");
        context.addServlet(new ServletHolder(new PushMessageServlet()), "/api/push_message");
        context.addServlet(new ServletHolder(new SearchServlet()), "/api/search");
        context.addServlet(new ServletHolder(new UpdatePosServlet()), "/api/updatepos");
        context.addServlet(new ServletHolder(new UpdateSignatureAndMotionServlet()), "/api/updatemotionandsig");
        context.addServlet(new ServletHolder(new UploadImageServlet()), "/api/push_img");
        context.addServlet(new ServletHolder(new UpdateStateServlet()), "/api/updatestate");
          server.start();
		server.join();
	}
}
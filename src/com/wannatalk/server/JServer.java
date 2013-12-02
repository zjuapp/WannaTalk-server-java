package com.wannatalk.server;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.wannatalk.server.web.HelloServlet;
public class JServer {
	static Logger log = Logger.getLogger(JServer.class);
	
	public static void main(String[] args) throws Exception {
		Server server = new Server(8081);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new HelloServlet()), "/hello");
        
		server.start();
		server.join();
	}
}
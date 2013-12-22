package com.wannatalk.server.web.common;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import com.google.gson.Gson;
import com.wannatalk.server.data.QueryService;
import com.wannatalk.server.data.h2.QueryServiceImpl;
import com.wannatalk.server.model.User;

public class CommonHttpServlet extends HttpServlet{
	protected QueryService qs = null;
	static Logger log = Logger.getLogger(CommonHttpServlet.class);
	protected static Gson gson = new Gson();
	
	@Override
	public void init() {
		if (qs == null) {
			qs = new QueryServiceImpl();
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
	protected void outputuser(List <User> res, HttpServletResponse response) throws IOException{
		Element root = new Element("users");
		Document document = new Document(root);
		root = document.getRootElement();
		for(int i = 0; i < res.size(); ++i){
			Element usr = new Element("user");
			Element id = new Element("uid");
			usr.addContent(id.setText(Integer.toString(res.get(i).uid)));
			Element signature = new Element("signature");
			usr.addContent(signature.setText(res.get(i).signature));
			Element sex = new Element("sex");
			usr.addContent(sex.setText(Integer.toString(res.get(i).sex)));
			Element motion = new Element("motion");
			usr.addContent(motion.setText(Integer.toString(res.get(i).motion)));
			Element motionlevel = new Element("motionlevel");
			usr.addContent(motionlevel.setText(Integer.toString(res.get(i).motionlevel)));
			root.addContent(usr);
			System.out.println(res.get(i).username + " " + res.get(i).password);
		}
		System.out.println("down");
		response.getWriter().print(res.size());
		XMLOutputter XMLOut = new XMLOutputter();
		XMLOut.output(document, response.getWriter());
	}
	
}

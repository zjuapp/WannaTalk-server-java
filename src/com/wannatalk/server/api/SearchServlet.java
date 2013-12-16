package com.wannatalk.server.api;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.*;
import com.wannatalk.server.model.MapPoint;
import com.wannatalk.server.model.User;
import com.wannatalk.server.web.common.*;
import javax.xml.soap.*;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

public class SearchServlet extends CommonHttpServlet{
	public SearchServlet(){
		super();
	}
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String lat = request.getParameter("lat");
		String lon = request.getParameter("lon");
		String r = request.getParameter("r");
		List <User> res = qs.search_user(new MapPoint(Integer.valueOf(lat), Integer.valueOf(lon)), Integer.valueOf(r));
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

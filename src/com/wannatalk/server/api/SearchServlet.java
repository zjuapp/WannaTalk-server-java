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
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String lat = request.getParameter("lat");
		String lon = request.getParameter("lon");
		String r = request.getParameter("r");
		List <User> res = qs.search_user(new MapPoint(Integer.valueOf(lat), Integer.valueOf(lon)), Integer.valueOf(r));
		try{
			outputuser(res, response);
		}
		catch(Exception e){
			System.out.println("internal error");
		}
	}
}

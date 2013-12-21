package com.wannatalk.server.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wannatalk.server.web.common.CommonHttpServlet;

public class UpdatePosServlet extends CommonHttpServlet{
	protected void service(HttpServletRequest request, HttpServletResponse response)
			 {
		try{
			int id = Integer.valueOf(request.getParameter("id"));
			int lat = Integer.valueOf(request.getParameter("lat"));
			int lon = Integer.valueOf(request.getParameter("lon"));
			Boolean res = qs.updatepos(id, lat, lon);
			response.getWriter().print(res);
			System.out.println("update pos : " + id + " " + lon + " " + lat + " " + res);
		}
		catch(Exception e){
			e.printStackTrace();
			try {
				response.getWriter().print("param error");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}

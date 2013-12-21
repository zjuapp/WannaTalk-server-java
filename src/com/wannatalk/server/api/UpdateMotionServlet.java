package com.wannatalk.server.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wannatalk.server.web.common.CommonHttpServlet;

public class UpdateMotionServlet extends CommonHttpServlet{
	protected void service(HttpServletRequest request, HttpServletResponse response)
			 {
		try{
			int motionid = Integer.valueOf(request.getParameter("motion"));
			int motionlevel = Integer.valueOf(request.getParameter("motionlevel"));
			int id = Integer.valueOf(request.getParameter("id"));
			Boolean res = qs.updatemotion(id, motionid, motionlevel);
			response.getWriter().print(res);
			System.out.println("update motion : " + id + " " + motionid + " " + motionlevel + " " + res);
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

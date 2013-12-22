
package com.wannatalk.server.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wannatalk.server.web.common.CommonHttpServlet;

public class UpdateSignatureAndMotionServlet extends CommonHttpServlet{
	protected void service(HttpServletRequest request, HttpServletResponse response)
			 {
		try{
			int id = Integer.valueOf(request.getParameter("id"));
			String signature = request.getParameter("signature");
			int motionid = Integer.valueOf(request.getParameter("motion"));
			int motionlevel = Integer.valueOf(request.getParameter("motionlevel"));
			Boolean res = qs.updatesignatureandmotion(id, signature, motionid, motionlevel);
			response.getWriter().print(res);
			System.out.println("update signature and motion : " + id + " " + signature + " " + motionid + " " + motionlevel);
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

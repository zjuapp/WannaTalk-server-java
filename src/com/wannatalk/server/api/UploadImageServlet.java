package com.wannatalk.server.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.wannatalk.server.web.common.CommonHttpServlet;

public class UploadImageServlet extends CommonHttpServlet{
	public static final String TAG = "UploadImageServlet";
	Logger logger = Logger.getLogger(UploadImageServlet.class);
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug(TAG+" is invoked");
		String uid = request.getParameter("uid");
		String fileName = request.getParameter("filename");
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		String serverRealPath = context.getRealPath("/");
		try{
			InputStream is = request.getInputStream();
			String imgDir = uid + "img";
			String imgPath = imgDir + "/head." + fileName.substring(fileName.lastIndexOf(".") + 1);
			File fileDir = new File(imgDir);
			logger.debug(TAG + " dir is " + fileDir.getAbsolutePath());
			if(!fileDir.exists()){
				logger.debug(TAG + " make dir " + fileDir);	
				fileDir.mkdirs();
			}
			File[] fs = fileDir.listFiles();
			if(fs != null){
				for(File f : fs){
					String str = f.getName().substring(0, f.getName().lastIndexOf("."));
					if(str.equals("head")){
						f.delete();
					}
				}
			}
			OutputStream os = new FileOutputStream(imgPath);
			byte[] buffer = new byte[1024];
			int length = -1;
			//以MultipartEntity的方式接收上传文件，流中会包含描述信息，  
            //去掉第一次读取的内容，即可除去，保证只接收文件内容  
			boolean first = true;
			while((length = is.read(buffer)) != -1){
				if(!first) {
					os.write(buffer, 0, length);
				}
				first = false;
			}
			os.flush();
			is.close();
			os.close();
			logger.debug(TAG + " upload image ok!");
			responseSucc(response, "upload ok");
		} catch(Exception e) {
			logger.debug(TAG + "Exception : ");
			e.printStackTrace();
			this.responseError(response, "upload error");
			logger.debug(TAG + " upload image failed!");
			
		}
		
	}
	

}

package com.leetai.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.File;
import com.jspsmart.upload.SmartUpload;

public class UpLoadServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 
		  response.setContentType("text/html;charset=UTF-8");
		//request.setCharacterEncoding("utf-8");// post方法直接utf-8 不用再转字符
		System.out.println(request.getContentType());
		PrintWriter out = response.getWriter();
  
        SmartUpload smartUpload = new SmartUpload();  
  
        try {  
            smartUpload.initialize(this.getServletConfig(), request, response);  
            smartUpload.upload();  
             File smartFile = smartUpload.getFiles().getFile(0);  
         String fileName =   smartFile.getFieldName() ;
         System.out.println("fileName="+fileName);
         System.out.println("fileExt="+smartFile.getFileExt()); 
         System.out.println("FilePathName="+smartFile.getFilePathName());
            if (!smartFile.isMissing()) {  
                String saveFileName = "d:/temp/" + "fileName.png" ;//smartFile.getFileName();  
                smartFile.saveAs(saveFileName, smartUpload.SAVE_PHYSICAL);  
                out.print("ok:" + saveFileName);// + ", msg:" + smartUpload.getRequest().getParameter("msg"));  
            } else {  
            	out.print("missing...");  
            }  
        } catch (Exception e) {  
        	out.print(e);  
        }  
  
		String result = "success";
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=utf-8");
	
		out.print(result);
		out.flush();
		out.close();
	}

}

package com.leetai.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownLoad extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			 String downFilename=request.getParameter("curfile");
			 String path ="D:/";
			 String filepath=request.getParameter("path");
				// response.setContentType("text/plain");
				response.setContentType("application/vnd.android.package-archive");
			 response.setHeader("Location",downFilename);
			 response.setHeader("Content-Disposition", "attachment; filename="
			 + "pa.apk");
			OutputStream outputStream = response.getOutputStream();
			InputStream inputStream = new FileInputStream("d:/pa.apk");
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, i);
			}
			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException e1) {
			System.out.println("没有找到您要的文件");
		} catch (Exception e) {
			System.out.println("系统错误，请及时与管理员联系");
		}

	}

}

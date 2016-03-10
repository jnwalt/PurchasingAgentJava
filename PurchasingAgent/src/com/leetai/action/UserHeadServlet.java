package com.leetai.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.leetai.tools.UpLoadTool;

public class UserHeadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean isMultipart = true;
		boolean isSaved = true;
		List<FileItem> items = null;
		FileItem fi = null;
		int id;
		String result = "";
		String param1 = ""; // request.getParameter("param1");
		if (request.getContentType()
				.equals("application/x-www-form-urlencoded")) {
			isMultipart = false;
		} else {
			items = UpLoadTool.initUpload(request, response);
			isMultipart = true;
		}

		if (!isMultipart) {
			request.setCharacterEncoding("utf-8");// post方法直接utf-8 不用再转字符
			param1 = request.getParameter("param1");

		} else {
			try {
				Iterator<FileItem> i = items.iterator();
				while (i.hasNext()) {
					fi = (FileItem) i.next();
					// 判断是字段还是文件
					if (fi.isFormField()) {
						if (fi.getFieldName().equals("param1")) {
							param1 = fi.getString();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			if (param1.equals("add")) {

				if (isMultipart) {
					isSaved = UpLoadTool.savedUserHead(items, request);
				}

				if (isSaved) {
					result = "success";
				} else {
					result = "图片上传失败！";
				}

			} else {
				System.out.println("UserHeadServlet参数错误无法解析;");
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
	}

}

package com.leetai.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.leetai.mapping.VersionMapper;
import com.leetai.modle.Version;
import com.leetai.tools.MyBATISSqlSessionFactory;

public class VersionServlet extends HttpServlet {

 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("GB2312");
		String result = "";

		Version version = new Version();
		try {
			
			version = MyBATISSqlSessionFactory.getSession().getMapper(
					VersionMapper.class).select();
			//System.out.println("getVersionName="+version.getVersionName());
		} catch (Exception e) {
			e.printStackTrace();
			 
			MyBATISSqlSessionFactory.getSession().rollback();
		} finally {
			MyBATISSqlSessionFactory.closeSession();
		}
		
		
		Gson gson = new Gson();
		result = gson.toJson(version);
		//System.out.println("result="+result);
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();

	}

	 

}

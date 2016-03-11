package com.leetai.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.leetai.mapping.BidMapper;
import com.leetai.modle.Bid;
import com.leetai.modle.Bid;
import com.leetai.tools.MyBATISSqlSessionFactory;
import com.leetai.tools.UpLoadTool;

public class BidServlet extends HttpServlet {

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
		String result = "";
		Gson gson = new Gson();
		Bid bid = new Bid();
		int id = 0;
		request.setCharacterEncoding("utf-8");
		String param1 = request.getParameter("param1");
		String param2 = request.getParameter("param2");
//		System.out.println("param1:" + param1);
//		System.out.println("param2:" + param2);
		if (param1 == null) {
			param1 = "";
		}
		if (param2 == null) {
			param2 = "";
		}
		try {
			if (param1.equals("add")) {

				bid = gson.fromJson(param2, Bid.class);
				id = MyBATISSqlSessionFactory.getSession()
						.getMapper(BidMapper.class).insert(bid);
				MyBATISSqlSessionFactory.getSession().commit();
				id = bid.getsId();
				//System.out.println("id = bid.getBidId();" + id);
				if (id != 0) {
					result = "success";
				} else {
					result = "数据保存失败";
				}

			} else if (param1.equals("modify")) {
				bid = gson.fromJson(param2, Bid.class);
				id = MyBATISSqlSessionFactory.getSession()
						.getMapper(BidMapper.class).updateByPrimaryKey(bid);
				MyBATISSqlSessionFactory.getSession().commit();
				id = bid.getsId();
				if (id != 0) {
					result = "success";
				} else {
					result = "数据保存失败";
				}
			} else {
				System.out.println("BidServlet参数错误无法解析;");
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

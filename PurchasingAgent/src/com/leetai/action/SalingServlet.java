package com.leetai.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.leetai.mapping.AddressMapper;
import com.leetai.mapping.PublishMapper;
import com.leetai.modle.Address;
import com.leetai.modle.Publish;
import com.leetai.tools.MyBATISSqlSessionFactory;

public class SalingServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("GB2312");
		List<Publish> list = new ArrayList<Publish>();
		String param1 = request.getParameter("param1");
		String param2 = request.getParameter("param2");
		String result = "";
		Gson gson = new Gson();
		Address address = new Address();

		if (param1 == null) {
			param1 = "";
		} else {
			param1 = new String(param1.getBytes("iso8859-1"), "utf-8");
		}
		if (param2 == null) {
			param2 = "";
		} else {
			param2 = new String(param2.getBytes("iso8859-1"), "utf-8");
		}
		 System.out.println("param1:" + param1);
		 System.out.println("param2:" + param2);

		// System.out.println("publish.getAddress()" + publish.getAddress());
		try {
			if (param1.equals("query")) {
				list = MyBATISSqlSessionFactory.getSession()
						.getMapper(PublishMapper.class).findAll();
				result = gson.toJson(list);
				System.out.println("SalingServlet:result=" + result);
				// for (int i = 0; i < list.size(); i++) {
				// System.out.println("list.get(i).getTitle()="
				// + list.get(i).getTitle());
				// }
				MyBATISSqlSessionFactory.getSession().clearCache();
			}
			// else if (param1.equals("add")) {
			//
			// Publish = gson.fromJson(param2, Publish.class);
			// int a = MyBATISSqlSessionFactory.getSession()
			// .getMapper(AddressMapper.class).insert(address);
			// MyBATISSqlSessionFactory.getSession().commit();
			// // System.out.println("publish.getId()=" + publish.getId());
			// result = address.getId() + "";
			// } else if (param1.equals("delete")) {
			// int a = MyBATISSqlSessionFactory.getSession()
			// .getMapper(AddressMapper.class).deleteByPrimaryKey(Integer.parseInt(param2));
			// MyBATISSqlSessionFactory.getSession().commit();
			// result = a+"";
			// } else if (param1.equals("modify")) {
			// address = gson.fromJson(param2, Address.class);
			// // System.out.println("address.getTitle()="+publish.getTitle());
			// int a = MyBATISSqlSessionFactory.getSession()
			// .getMapper(AddressMapper.class).updateByPrimaryKey(address);
			// MyBATISSqlSessionFactory.getSession().commit();
			// result = a+"";
			// }
			else {
				System.out.println("SalingServlet参数错误无法解析;");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "fail";
			MyBATISSqlSessionFactory.getSession().rollback();
		} finally {
			MyBATISSqlSessionFactory.closeSession();
		}

		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}

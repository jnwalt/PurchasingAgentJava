package com.leetai.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.leetai.mapping.OrderMapper;
import com.leetai.modle.Order;
import com.leetai.modle.Type;
import com.leetai.tools.MyBATISSqlSessionFactory;

public class OrderServlet extends HttpServlet {

	String param1;
	String param2;
	String param3;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("GB2312");
		List<Order> list = new ArrayList<Order>();
		Order order = new Order();
		Gson gson = new Gson();
		String result = "";
		param1 = request.getParameter("param1");
		param2 = request.getParameter("param2");
		param3 = request.getParameter("param3");
		initParam();

		try {
			if (param1.equals(Type.ACTION_TYPE_QUERY)) {
				if (param3.equals("all")) {
					list = MyBATISSqlSessionFactory.getSession()
							.getMapper(OrderMapper.class)
							.selectAllByUserId(Integer.parseInt(param2));
				} else {
					list = MyBATISSqlSessionFactory
							.getSession()
							.getMapper(OrderMapper.class)
							.selectAllByStatus(Integer.parseInt(param2),
									Integer.parseInt(param3));
				}
				result = gson.toJson(list);
				//System.out.println("result:"+result);
				MyBATISSqlSessionFactory.getSession().clearCache();
			} else if (param1.equals(Type.ACTION_TYPE_QUERY_DETAIL)) {
				order = MyBATISSqlSessionFactory.getSession()
						.getMapper(OrderMapper.class)
						.selectBySId(Integer.parseInt(param2));
				result = gson.toJson(order);
				// System.out.println("OrderServlet:result="+result);
				// for (int i = 0; i < list.size(); i++) {
				// System.out.println("list.get(i).getTitle()="
				// + list.get(i).getTitle());
				// }
				MyBATISSqlSessionFactory.getSession().clearCache();
			} else if (param1.equals("add")) {

				order = gson.fromJson(param2, Order.class);
				int a = MyBATISSqlSessionFactory.getSession()
						.getMapper(OrderMapper.class).insert(order);
				MyBATISSqlSessionFactory.getSession().commit();
				// System.out.println("publish.getId()=" + publish.getId());
				// result = address.getId() + "";
			} else if (param1.equals("delete")) {
				int a = MyBATISSqlSessionFactory.getSession()
						.getMapper(OrderMapper.class)
						.deleteByPrimaryKey(Integer.parseInt(param2));
				MyBATISSqlSessionFactory.getSession().commit();
				result = a + "";
			} else if (param1.equals("modify")) {
				order = gson.fromJson(param2, Order.class);
				// System.out.println("address.getTitle()="+publish.getTitle());
				int a = MyBATISSqlSessionFactory.getSession()
						.getMapper(OrderMapper.class).updateByPrimaryKey(order);
				MyBATISSqlSessionFactory.getSession().commit();
				result = a + "";
			} else {
				System.out.println("OrderServlet参数错误无法解析;");
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

	private void initParam() {
		try {
			
			
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
			if (param3 == null) {
				param3 = "";
			} else {
				param3 = new String(param3.getBytes("iso8859-1"), "utf-8");
			}
			System.out.println("param1:" + param1);
			System.out.println("param2:" + param2);
			System.out.println("param3:" + param3);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

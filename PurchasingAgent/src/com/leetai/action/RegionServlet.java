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
import com.leetai.mapping.RegionMapper;
import com.leetai.modle.Address;
import com.leetai.modle.Region;
import com.leetai.tools.MyBATISSqlSessionFactory;

public class RegionServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		request.setCharacterEncoding("GB2312");
		List<Region> list = new ArrayList<Region>();
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
		// System.out.println("param1:" + param1);
		// System.out.println("param2:" + param2);

		// System.out.println("publish.getAddress()" + publish.getAddress());
		try {
			if (param1.equals("query")) {
				list = MyBATISSqlSessionFactory.getSession()
						.getMapper(RegionMapper.class)
						.findAll(Double.parseDouble((param2)));
				result = gson.toJson(list);
				System.out.println("RegionServlet:result="+result);
				 
				MyBATISSqlSessionFactory.getSession().clearCache();
			}  else {
				System.out.println("RegionServlet参数错误无法解析;");
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
}

package com.leetai.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.leetai.mapping.BidMapper;
import com.leetai.mapping.OrderMapper;
import com.leetai.mapping.PublishMapper;
import com.leetai.modle.Bid;
import com.leetai.modle.Order;
import com.leetai.modle.Publish;
import com.leetai.tools.MyBATISSqlSessionFactory;

public class MatchingServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int s_id, p_id, ps_id;
		Order order = new Order();
		request.setCharacterEncoding("GB2312");
		List<Bid> list = new ArrayList<Bid>();
		// request.setCharacterEncoding("utf-8");//post方法直接utf-8 不用再转字符
		String param1 = request.getParameter("param1");
		// String param2 = request.getParameter("param2");
		String result = "";
		if (param1 == null) {
			param1 = "";
		} else {
			param1 = new String(param1.getBytes("iso8859-1"), "utf-8");
		}
		 // System.out.println("param1:" + param1);
		// System.out.println("param2:" + param2);
		s_id = Integer.parseInt(param1);
		Gson gson = new Gson();

		// System.out.println("publish.getAddress()" + publish.getAddress());
		try {
			Bid bid = MyBATISSqlSessionFactory.getSession()
					.getMapper(BidMapper.class).selectByPrimaryKey(s_id);

			order = getOrder(bid);

			ps_id = MyBATISSqlSessionFactory.getSession()
					.getMapper(OrderMapper.class).insert(order);
///更新publish标志位
			MyBATISSqlSessionFactory.getSession()
			.getMapper(PublishMapper.class).updatePFlag(bid.getPublish().getpId());
 		
			if (ps_id != 0) {
				result = "success";
			} else {
				result = "false";
			}
			MyBATISSqlSessionFactory.getSession().commit();
			MyBATISSqlSessionFactory.getSession().clearCache();
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

	private Order getOrder(Bid bid) {
		Order orderNew = new Order();
		Date date = new Date();
		// orderNew.setpId(publish.getId());
		orderNew.setBid(bid);
		orderNew.setPsAddTime(date);
		orderNew.setPsStatus(0);

		return orderNew;
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
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

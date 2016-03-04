package com.leetai.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.leetai.mapping.UserMapper;
import com.leetai.mapping.VersionMapper;
import com.leetai.modle.User;
import com.leetai.modle.Version;
import com.leetai.tools.MyBATISSqlSessionFactory;

public class UserinfoServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UserinfoServlet() {
		super();
	}

	/**
	 * The doDelete method of the servlet. <br>
	 * 
	 * This method is called when a HTTP delete request is received.
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
	public void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
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
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
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
		request.setCharacterEncoding("utf-8");
		String result = "";//
		String param1 = request.getParameter("param1").trim(); // 获取用户名
		String param2 = request.getParameter("param2").trim(); // 获取操作类型
		System.out.println(param1);
		System.out.println(param2);
		if (param1 == null) {
			param1 = "123";
		} else {
			// param1 = new String(param1.getBytes("iso8859-1"), "utf-8");
		}
		User user = new User();
		if(param2.equals("doQuery"))
		{
			try{
//				Version version = new Version();
//				  version = MyBATISSqlSessionFactory.getSession()
//				.getMapper(VersionMapper.class).select();
//				  System.out.println(version.getVersionCode());  
				  
				  
				user = MyBATISSqlSessionFactory.getSession()
				.getMapper(UserMapper.class).selectByUsername(param1);
				// String param1 = request.getParameter("param1");
				// String param2 = request.getParameter("param2");
				System.out.println(user.getUsername());
				if (user.getUsername() != null) {
					Gson gson = new Gson();
					result = gson.toJson(user);
				} else {
					result = "没有该用户！";
				}
			}catch (Exception e) {
				e.printStackTrace();
				result=e.getMessage();
				
			} finally {
				MyBATISSqlSessionFactory.closeSession();
			}
			
		}else if(param2.equals("doUpdate"))
		{
			String param3 = request.getParameter("param3").trim(); 
//			user = MyBATISSqlSessionFactory.getSession()
//			.getMapper(UserMapper.class).selectByUsername(param1);
			//user.setTelphone(param3);
			Gson gson=new Gson();
			
			user=gson.fromJson(param3, User.class);
			
			try {

				int a = MyBATISSqlSessionFactory.getSession()
				.getMapper(UserMapper.class).updateByPrimaryKey(user);
				
				MyBATISSqlSessionFactory.getSession().commit();
	 
				result=a+"";
			} catch (Exception e) {
				e.printStackTrace();
				result="更新失败！";
				MyBATISSqlSessionFactory.getSession().rollback();
			} finally {
				MyBATISSqlSessionFactory.closeSession();
			}

			
		}
		
		System.out.println(result);
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result);
		
		out.flush();
		out.close();

	}

}

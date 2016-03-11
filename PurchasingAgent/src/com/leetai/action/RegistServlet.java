package com.leetai.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leetai.mapping.UserMapper;
import com.leetai.modle.User;
import com.leetai.tools.MyBATISSqlSessionFactory;

public class RegistServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		String param1 = request.getParameter("param1");
		String param2 = request.getParameter("param2");
//		System.out.println("param1:" + param1);
//		System.out.println("param2:" + param2);
		if (param1 == null) {
			param1 = "";
		} else {
			// param1 = new String(param1.getBytes("iso8859-1"), "utf-8");
		}

		if (param2 == null) {
			param2 = "";
		} else {
			// param2 = new String(param2.getBytes("iso8859-1"), "utf-8");
		}

		User user = new User();
		user.setPassword(param2);
		user.setUsername(param1);
	 

		String result = "胜多负少";

		// String resource = "configuration.xml";
		// InputStream inputStream = Resources.getResourceAsStream(resource);
		// SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
		// .build(inputStream);
		// SqlSession session = sqlSessionFactory.openSession();
		//
		// try {
		// session.getMapper(UserMapper.class).insert(user);
		// session.commit();
		// } catch (Exception e) {
		// session.rollback();
		// e.printStackTrace();
		// } finally {
		// session.close();
		// }

		try {

			int a = MyBATISSqlSessionFactory.getSession()
					.getMapper(UserMapper.class).insert(user);
			MyBATISSqlSessionFactory.getSession().commit();
			//System.out.println("user.getUserId()=" + user.getUserId());
			result=a+"";
		} catch (Exception e) {
			e.printStackTrace();
			result="该用户名已被使用！";
			MyBATISSqlSessionFactory.getSession().rollback();
		} finally {
			MyBATISSqlSessionFactory.closeSession();
		}

		// System.out.println("执行完毕");

		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
	}
}

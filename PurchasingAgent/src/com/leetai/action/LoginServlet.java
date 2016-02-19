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
import com.leetai.mapping.UserMapper;
import com.leetai.mapping.VersionMapper;
import com.leetai.modle.User;
import com.leetai.modle.Version;
import com.leetai.tools.MyBATISSqlSessionFactory;

public class LoginServlet extends HttpServlet {

	//
	// public void doGet(HttpServletRequest request, HttpServletResponse
	// response)
	// throws ServletException, IOException {
	// request.setCharacterEncoding("GB2312");
	// // request.setCharacterEncoding("utf-8");//post方法直接utf-8 不用再转字符
	// String param1 = request.getParameter("param1");
	// String param2 = request.getParameter("param2");
	//
	// if (param1 == null) {
	// param1 = "";
	// } else {
	// param1 = new String(param1.getBytes("iso8859-1"), "utf-8");
	// }
	//
	// if (param2 == null) {
	// param2 = "";
	// } else {
	// param2 = new String(param2.getBytes("iso8859-1"), "utf-8");
	// }
	// System.out.println("param1:"+param1);
	// System.out.println("param2:"+param2);
	// String result = "";
	// List<User> list = new ArrayList<User>();
	// User user = new User();
	// user = MyBATISSqlSessionFactory.getSession().getMapper(
	// UserMapper.class).selectByPrimaryKey("1");
	// //
	// // if (list != null&&list.size()==1) {
	// // if (list.get(0).getPassword().toString().equals(param2)) {
	// // result = "success";
	// // } else {
	// // result = "faile";
	// // }
	// // } else {
	// // result = "faile";
	// // }
	//
	// if (user.getUsername() != null ) {
	// if (user.getPassword().toString().equals(param2)) {
	// result = "登陆成功";
	// } else {
	// result = "密码错误";
	// }
	// } else {
	// result = "没有该用户！";
	// }
	//
	// response.setCharacterEncoding("utf-8");
	// response.setHeader("Content-type", "text/html;charset=utf-8");
	// PrintWriter out = response.getWriter();
	// out.print(result);
	// out.flush();
	// out.close();
	// }
	//
	//

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");// post方法直接utf-8 不用再转字符
		String param1 = request.getParameter("param1");
		String param2 = request.getParameter("param2");

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
		// System.out.println("param1:"+param1);
		// System.out.println("param2:"+param2);
		String result = "";
		List<User> list = new ArrayList<User>();
		User user = new User();
		user = MyBATISSqlSessionFactory.getSession()
				.getMapper(UserMapper.class).selectByUsername(param1);
		//System.out.println("user.getUserId()=" + user.getUserId());
		// if (list != null&&list.size()==1) {
		// if (list.get(0).getPassword().toString().equals(param2)) {
		// result = "success";
		// } else {
		// result = "faile";
		// }
		// } else {
		// result = "faile";
		// }

		if (user.getUsername() != null) {
			if (user.getPassword().toString().equals(param2)) {
				result = user.getUserId()+"";
			} else {
				result = "000";
			}
		} else {
			result = "111";
		}

		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
	}

}

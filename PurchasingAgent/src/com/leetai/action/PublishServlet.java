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

import com.google.gson.Gson;
import com.leetai.mapping.PublishMapper;
import com.leetai.modle.Publish;
import com.leetai.tools.MyBATISSqlSessionFactory;
import com.leetai.tools.UpLoadTool;

public class PublishServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("GB2312");
		// request.setCharacterEncoding("utf-8");//post方法直接utf-8 不用再转字符
		String param1 = request.getParameter("param1");
		String param2 = request.getParameter("param2");
		String result = "";
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
//		System.out.println("param1:" + param1);
//		System.out.println("param2:" + param2);

		// System.out.println("publish.getAddress()" + publish.getAddress());
		Gson gson = new Gson();
		Publish publish = new Publish();
		try {
			if (param1.equals("delete")) {
				int a = MyBATISSqlSessionFactory.getSession()
						.getMapper(PublishMapper.class)
						.deleteByPrimaryKey(Integer.parseInt(param2));
				MyBATISSqlSessionFactory.getSession().commit();
				result = a + "";
			} else if (param1.equals("modify")) {
				publish = gson.fromJson(param2, Publish.class);
				// System.out.println("publish.getTitle()=" +
				// publish.getTitle());
				int a = MyBATISSqlSessionFactory.getSession()
						.getMapper(PublishMapper.class)
						.updateByPrimaryKey(publish);
				MyBATISSqlSessionFactory.getSession().commit();
				result = a + "";
			} else {
				System.out.println("PublishServlet参数错误无法解析;");
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
		response.setHeader("Content-param1", "text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Publish publish = new Publish();
		// System.out.println("request.getContentType()="
		// + request.getContentType());
		Gson gson = new Gson();
		String result = "";
		String param1 = ""; // request.getParameter("param1");
		String param2 = "";// request.getParameter("param2");
		boolean isMultipart = true;
		boolean isSaved = true;
		List<FileItem> items = null;
		FileItem fi = null;
		int id;
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
			param2 = request.getParameter("param2");
		} else {
			try {
				Iterator<FileItem> i = items.iterator();
				while (i.hasNext()) {
					fi = (FileItem) i.next();
					// 判断是字段还是文件
					if (fi.isFormField()) {
						if (fi.getFieldName().equals("param1")) {
							param1 = fi.getString();
						} else if (fi.getFieldName().equals("param2")) {
							param2 = fi.getString();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//
//		System.out.println("param1=" + param1);
//		System.out.println("param2=" + param2);
		try {
			if (param1.equals("add")) {

				publish = gson.fromJson(param2, Publish.class);
				id = MyBATISSqlSessionFactory.getSession()
						.getMapper(PublishMapper.class).insert(publish);
				MyBATISSqlSessionFactory.getSession().commit();
				id = publish.getpId();
				if (isMultipart) {
					isSaved = UpLoadTool.savedFile(items, id, publish
							.getpUser().getUserId(), request);
				}
				// publish.setId(id);
				// publish.setImg(path);
				// id = MyBATISSqlSessionFactory.getSession()
				// .getMapper(PublishMapper.class)
				// .updateByPrimaryKey(publish);
				// MyBATISSqlSessionFactory.getSession().commit();
				if (isSaved) {
					if (id != 0) {
						result = "success";
					} else {
						result = "数据保存失败";
					}
				} else {
					result = "图片上传失败！";
				}

			} else if (param1.equals("modify")) {
				publish = gson.fromJson(param2, Publish.class);
				id = MyBATISSqlSessionFactory.getSession()
						.getMapper(PublishMapper.class)
						.updateByPrimaryKey(publish);
				MyBATISSqlSessionFactory.getSession().commit();

				id = publish.getpId();
				if (isMultipart) {
					isSaved = UpLoadTool.savedFile(items, id, publish
							.getpUser().getUserId(), request);
				}

				if (isSaved) {
					if (id != 0) {
						result = "success";
					} else {
						result = "数据保存失败";
					}
				} else {
					result = "图片上传失败！";
				}

			} else {
				System.out.println("PublishServlet参数错误无法解析;");
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// result = "fail";
		// MyBATISSqlSessionFactory.getSession().rollback();
		// } finally {
		// MyBATISSqlSessionFactory.closeSession();
		// }

		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
	}

}

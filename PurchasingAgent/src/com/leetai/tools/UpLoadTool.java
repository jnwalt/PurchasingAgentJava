package com.leetai.tools;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UpLoadTool {
	public static List<FileItem> initUpload(HttpServletRequest request,
			HttpServletResponse response) {
		//String uploadPath = "D:\\temp"; // 上传文件的目录
		 String tempPath = "D:\\temp"; // 临时文件目录
		//String tempPath = "d:\\temp\\buffer\\"; // 临时文件目录
		File tempPathFile = new File(tempPath);
		if  (!tempPathFile.exists()  && !tempPathFile.isDirectory())      
		{       
			tempPathFile.mkdir();
		}
		// 建工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设参数
		factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
		factory.setRepository(tempPathFile);// 设置缓冲区目录
		// 建实例
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(4194304); // 设置最大文件尺寸，这里是4MB

		List<FileItem> items;

		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return items;
	}


	public static boolean savedFile(List<FileItem> items , int id,int userId,HttpServletRequest request) {
		
		String path = request.getSession().getServletContext().getRealPath("/");
		System.out.println("path=" + path);
		path += "\\Pic\\ps\\p\\" + id+"\\"+userId;
		File file = new File(path);
		if  (!file.exists()  && !file.isDirectory())      
		{       
			file.mkdirs();
		}
		
		for (int i = 0; i < items.size();i++) {
			FileItem fi = items.get(i);
			
			if (!fi.isFormField()) {
				String tempname= fi.getName();
				String fileType = fi.getName().substring(fi.getName().indexOf(".")+1,fi.getName().length());
				String name =   fi.getFieldName()+"."+fileType;
				if (name != null) {
					File savedFile = new File(path, name);
					try {
						fi.write(savedFile);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("UpLoadTool.savedFile保存失败");
						return false;
					}
				}
			}
		}
		
		return true;
		
	}
}

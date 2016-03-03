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
		//String uploadPath = "D:\\temp"; // �ϴ��ļ���Ŀ¼
		 String tempPath = "D:\\temp"; // ��ʱ�ļ�Ŀ¼
		//String tempPath = "d:\\temp\\buffer\\"; // ��ʱ�ļ�Ŀ¼
		File tempPathFile = new File(tempPath);
		if  (!tempPathFile.exists()  && !tempPathFile.isDirectory())      
		{       
			tempPathFile.mkdir();
		}
		// ������
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// �����
		factory.setSizeThreshold(4096); // ���û�������С��������4kb
		factory.setRepository(tempPathFile);// ���û�����Ŀ¼
		// ��ʵ��
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(4194304); // ��������ļ��ߴ磬������4MB

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
						System.out.println("UpLoadTool.savedFile����ʧ��");
						return false;
					}
				}
			}
		}
		
		return true;
		
	}
}

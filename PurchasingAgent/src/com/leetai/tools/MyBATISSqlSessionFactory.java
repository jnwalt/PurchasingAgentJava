package com.leetai.tools;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBATISSqlSessionFactory {
	 // �����ļ�������λ�ú�����
	 private static String CONFIG_FILE_LOCATION = "configuration.xml";

	 // ����ʵ�����ӳصģ���������Map���ϡ�
	 private static final ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();
	 // Hibernate������ȡ�����ļ�����
	 private static InputStream is;
	 // �����������ӵģ�����������ӳأ�ʹ�õ������ģʽ
	 private static SqlSessionFactory sqlsessionFactory;
	 // ���õ������ļ�λ��
	 private static String configFile = CONFIG_FILE_LOCATION;

	 // ��̬�飬�����ʱ����ִ��
	 static {
	  try {
	   // ���������ļ����ڴ���
	   is = Resources.getResourceAsStream(configFile);
	   // �������ӳ��Լ����������
	   sqlsessionFactory = new SqlSessionFactoryBuilder().build(is);
	  } catch (Exception e) {
	   System.err.println("%%%% Error Creating SessionFactory %%%%");
	   e.printStackTrace();
	  }
	 }

	 private MyBATISSqlSessionFactory() {
	 }

	 /**
	  * ȡ�����ݿ����Ӷ���
	  * 
	  * @return Session
	  * @throws HibernateException
	  */
	 public static SqlSession getSession() {
	  // �ȴ�ThreadLocal��ȡ�����ӡ�
	  SqlSession session = (SqlSession) threadLocal.get();

	  // �����ͷû�����ӣ���ȡ��һ���µ�����
	  if (session == null) {
	   session = sqlsessionFactory.openSession();
	   // ��ȡ�ó������Ӽ�¼��ThreadLocal�У��Ա��´�ʹ�á�
	   threadLocal.set(session);
	  }
	  return session;
	 }

	 /**
	  * ���ӹرյķ���
	  * 
	  * @throws HibernateException
	  */
	 public static void closeSession() {
	  SqlSession session = (SqlSession) threadLocal.get();
	  // ��ThreadLocal��գ���ʾ��ǰ�߳��Ѿ�û�����ӡ�
	  threadLocal.set(null);
	  // ���ӷŻص����ӳ�
	  if (session != null) {
	   session.close();
	  }
	 }
}

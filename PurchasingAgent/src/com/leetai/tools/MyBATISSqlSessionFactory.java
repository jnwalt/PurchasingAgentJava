package com.leetai.tools;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBATISSqlSessionFactory {
	 // 配置文件的所在位置和名称
	 private static String CONFIG_FILE_LOCATION = "configuration.xml";

	 // 用来实现连接池的，该类类似Map集合。
	 private static final ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();
	 // Hibernate用来读取配置文件的类
	 private static InputStream is;
	 // 用来建立连接的，该类就是连接池，使用单例设计模式
	 private static SqlSessionFactory sqlsessionFactory;
	 // 备用的配置文件位置
	 private static String configFile = CONFIG_FILE_LOCATION;

	 // 静态块，类加载时最先执行
	 static {
	  try {
	   // 加载配置文件到内存中
	   is = Resources.getResourceAsStream(configFile);
	   // 建立连接池以及里面的连接
	   sqlsessionFactory = new SqlSessionFactoryBuilder().build(is);
	  } catch (Exception e) {
	   System.err.println("%%%% Error Creating SessionFactory %%%%");
	   e.printStackTrace();
	  }
	 }

	 private MyBATISSqlSessionFactory() {
	 }

	 /**
	  * 取得数据库连接对象
	  * 
	  * @return Session
	  * @throws HibernateException
	  */
	 public static SqlSession getSession() {
	  // 先从ThreadLocal中取得连接。
	  SqlSession session = (SqlSession) threadLocal.get();

	  // 如果手头没有连接，则取得一个新的连接
	  if (session == null) {
	   session = sqlsessionFactory.openSession();
	   // 把取得出的连接记录到ThreadLocal中，以便下次使用。
	   threadLocal.set(session);
	  }
	  return session;
	 }

	 /**
	  * 连接关闭的方法
	  * 
	  * @throws HibernateException
	  */
	 public static void closeSession() {
	  SqlSession session = (SqlSession) threadLocal.get();
	  // 将ThreadLocal清空，表示当前线程已经没有连接。
	  threadLocal.set(null);
	  // 连接放回到连接池
	  if (session != null) {
	   session.close();
	  }
	 }
}

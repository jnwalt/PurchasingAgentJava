package com.leetai.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnection {
public static Connection getConnection (){
	Connection  conn = null;
	try {
//		自动化部项目管理
//		Class.forName("oracle.jdbc.driver.OracleDriver");
//		String url = "jdbc:oracle:thin:@172.16.1.105:1521:orcl";
//		String user = "pmdba";
//		String password = "pmdba";
		
		//荣钢测试数据库
//		Class.forName("oracle.jdbc.driver.OracleDriver");
//		String url = "jdbc:oracle:thin:@172.16.69.145:1521:rcmes";
//		String user = "rcmes";
//		String password = "rcmes";
		
//		//济钢MES正式数据库
//		Class.forName("oracle.jdbc.driver.OracleDriver");
//		String url = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=172.16.4.21)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=172.16.4.23)(PORT=1521))(LOAD_BALANCE =yes)(ENABLE=broken)(CONNECT_DATA=(SERVICE_NAME=fmesdb)(FAILOVER_MODE=(TYPE=SELECT)(METHOD=BASIC)(RETRIES=80)(DELAY=5))))";
//		String user = "jisco";
//		String password = "asdf987";
		
		// 合同管理
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://172.16.69.49:3306/pa";
		String user = "pa";
		String password = "pa";
		
		conn = DriverManager.getConnection(url, user, password);
	} catch (ClassNotFoundException e) {
		
		e.printStackTrace();
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	return conn;
}
}

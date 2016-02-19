package com.leetai.tools;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * 
 * @author
 * 
 */
public class DBUtils {

	private Connection connection;

	private PreparedStatement pstmt;

	private ResultSet resultSet;

	public <T> List<T> query(String sql, Class<T> type) {
		Connection conn = GetConnection.getConnection();
		List<T> list = new ArrayList<T>();
		QueryRunner queryRunner = new QueryRunner();
		try {

			list = queryRunner.query(conn, sql, new BeanListHandler<T>(type));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public <T> List<T> query(String sql, Class<T> type, Object params) {
		Connection conn = GetConnection.getConnection();
		List<T> list = new ArrayList<T>();
		QueryRunner queryRunner = new QueryRunner();
		try {

			list = queryRunner.query(conn, sql, new BeanListHandler<T>(type),
					params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public <T> List<T> query(String sql, Class<T> type, Object[] params) {
		Connection conn = GetConnection.getConnection();
		List<T> list = new ArrayList<T>();
		QueryRunner queryRunner = new QueryRunner();
		try {

			list = queryRunner.query(conn, sql, new BeanListHandler<T>(type),
					params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<Map<String, Object>> query(String sql, List<Object> params)
			throws SQLException {
		Connection conn = GetConnection.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int index = 1;
		pstmt = conn.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
			list.add(map);
		}
		return list;
	}

	public boolean update(String sql) {
		Connection conn = GetConnection.getConnection();
		boolean flag = false;
		int result = -1;//
		QueryRunner queryRunner = new QueryRunner();
		try {

			result = queryRunner.update(conn, sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		flag = result > 0 ? true : false;
		return flag;

	}

	public boolean update(String sql, Object param) {
		Connection conn = GetConnection.getConnection();
		boolean flag = false;
		int result = -1;//
		QueryRunner queryRunner = new QueryRunner();
		try {

			result = queryRunner.update(conn, sql, param);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		flag = result > 0 ? true : false;
		return flag;

	}

	public boolean update(String sql, Object[] params) {
		Connection conn = GetConnection.getConnection();
		boolean flag = false;
		int result = -1;//
		QueryRunner queryRunner = new QueryRunner();
		try {

			result = queryRunner.update(conn, sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		flag = result > 0 ? true : false;
		return flag;

	}

	public boolean update(String sql, List<Object> params) {
		Connection conn = GetConnection.getConnection();
		boolean flag = false;
		int result = -1;//
		QueryRunner queryRunner = new QueryRunner();
		Object[] str_params;
		try {
			if (params != null && !params.isEmpty()) {
				str_params = new Object[params.size()];
				for (int i = 0; i < params.size(); i++) {
					str_params[i] = params.get(i);
				}
			} else {
				str_params = null;
			}
			result = queryRunner.update(conn, sql, str_params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		flag = result > 0 ? true : false;
		return flag;

	}

	/***********
	 * 
	 * 无参数无返回值
	 * 
	 * @param conn
	 * @param proName
	 */
	public void exePro(String proName) {
		Connection conn = GetConnection.getConnection();
		CallableStatement callst = null;
		try {

			callst = conn.prepareCall("{ call " + proName + " }");

			callst.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/****
	 * 有一个参数无返回值
	 * 
	 * @param conn
	 * @param proName
	 * @param params
	 */
	public void exePro(String proName, String params) {
		Connection conn = GetConnection.getConnection();

		CallableStatement callst = null;
		try {

			callst = conn.prepareCall("{ call " + proName + "(?) }");

			callst.setString(1, params);

			callst.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 有一个参数一个返回值
	 * 
	 * @param conn
	 * @param proName
	 * @param params
	 * @param outParams
	 */
	public void exePro(String proName, String params, String outParams) {
		Connection conn = GetConnection.getConnection();

		CallableStatement callst = null;
		try {

			callst = conn.prepareCall("{ call " + proName + "(?,?) }");

			callst.setString(1, params);
			callst.registerOutParameter(2, Types.VARCHAR);
			callst.execute();
			outParams = callst.getString(2);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 有多个参数无返回值
	 * 
	 * @param conn
	 * @param proName
	 * @param params
	 */
	public void exePro(String proName, String[] params) {
		Connection conn = GetConnection.getConnection();

		CallableStatement callst = null;
		String paramscount = "";
		try {
			paramscount = "(";
			for (int i = 0; i < params.length; i++) {

				paramscount += "?";
				if (i != params.length - 1) {

					paramscount += ",";
				} else {
					paramscount += ")";

				}
			}

			callst = conn.prepareCall("{ call " + proName + paramscount + " }");
			for (int i = 0; i < params.length; i++) {
				callst.setString(i + 1, params[i]);
			}
			callst.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 多个参数一个输出
	 * 
	 * @param conn
	 * @param proName
	 * @param params
	 * @param outParams
	 */
	public void exePro(String proName, String[] params, String outParams) {
		Connection conn = GetConnection.getConnection();

		CallableStatement callst = null;
		String paramscount = "";
		try {
			paramscount = "(";
			for (int i = 0; i < params.length; i++) {

				paramscount += "?";
				if (i != params.length - 1) {

					paramscount += ",";
				} else {
					paramscount += ")";

				}
			}

			callst = conn.prepareCall("{ call " + proName + paramscount + " }");
			for (int i = 0; i < params.length; i++) {
				callst.setString(i + 1, params[i]);
			}
			callst.registerOutParameter(params.length + 1, Types.VARCHAR);
			callst.execute();
			outParams = callst.getString(params.length + 1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 多个参数一个输出
	 * 
	 * @param conn
	 * @param proName
	 * @param params
	 * @param outParams
	 */
	public void exePro(String proName, String[] params,
			StringBuffer outParams1, StringBuffer outParams2) {
		Connection conn = GetConnection.getConnection();

		CallableStatement callst = null;
		String paramscount = "";
		try {
			paramscount = "(";
			for (int i = 0; i < params.length + 2; i++) {

				paramscount += "?";
				if (i != params.length + 1) {

					paramscount += ",";
				} else {
					paramscount += ")";

				}
			}

			callst = conn.prepareCall("{ call " + proName + paramscount + " }");
			System.out
					.println("调用过程语句为：{ call " + proName + paramscount + " }");
			// System.out.println(params.toString());
			for (int i = 0; i < params.length; i++) {
				callst.setString(i + 1, params[i]);
			}
			callst.registerOutParameter(params.length + 1, Types.VARCHAR);
			callst.registerOutParameter(params.length + 2, Types.VARCHAR);
			callst.execute();

			int a = callst.getInt(params.length + 1);
			String aa = callst.getString(params.length + 2);
			// System.out.println(a);
			// System.out.println(aa);
			outParams1.append(a);
			outParams2.append(aa);
			// System.out.println(callst.getString(4).toString());
			// System.out.println(callst.getString(5).toString());
			// System.out.println(outParams1.toString());
			// System.out.println(outParams2.toString());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

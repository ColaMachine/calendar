package com.dozenx.util.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MysqlUtil {

	List<String> tables_a = new ArrayList<String>();
	HashMap<String, ArrayList> map_a = new HashMap();
	HashMap<String, ArrayList> map_b = new HashMap();
	List<String> tables_b = new ArrayList<String>();
	String FIELD = "field";
	String TYPE = "type";
	String url_a, url_b;
	Connection con_a, con_b;
	Statement stmt_a, stmt_b;

	public void run() {
		System.out.println("compare start ");
		getData();
		compare();
		System.out.println("compare end ");
	}
	public Connection getConnection(String user,String password,String url){
		Connection conn;
		try {

			// 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
			// 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
			Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
			// or:
			// com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
			// or：
			// new com.mysql.jdbc.Driver();

			System.out.println("成功加载MySQL驱动程序");
			// 一个Connection代表一个数据库连接
			conn = DriverManager.getConnection(url+"?user="+user+"&password="+password);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	public void executeQuery(String user,String password,String url,String sql){
		Connection conn ;
		try{
			conn=this.getConnection(user,password,url);


			Statement stmt	= conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				HashMap map =new HashMap();
				rs.getRow();
				// System.out.println(rs.getString(1));
				tables_a.add(rs.getString(1));
				map_a.put(rs.getString(1), new ArrayList());
			}
			rs.close();
		}catch (Exception e ){
			e.printStackTrace();
		}

	}
	public void compare() {
		for (int i = 0; i < tables_a.size(); i++) {
			String tablename = tables_a.get(i);
			// System.out.println("begin check table "+tablename);
			if (map_b.get(tablename) == null) {
				System.out.println("b 库  " + tablename + "不存在");
			} else {
				List cols_a = map_a.get(tablename);
				List cols_b = map_b.get(tablename);

				for (int j = 0; j < cols_a.size(); j++) {
					boolean found = false;
					HashMap map = (HashMap) cols_a.get(j);
					String field_a = (String) map.get(FIELD);
					String type_a = (String) map.get(TYPE);
					for (int k = 0; k < cols_b.size(); k++) {

						HashMap map_b = (HashMap) cols_b.get(k);
						String field_b = (String) map_b.get(FIELD);
						String type_b = (String) map_b.get(TYPE);
						if (field_a.equals(field_b)) {
							found = true;
							if (type_b.equals(type_a)) {

							} else {
								System.out.println("table " + tablename + " col :" + field_a + " 's type " + type_a
										+ "==>" + type_b);
							}
							break;
						}

					}
					if (!found) {
						System.out.println("table " + tablename + " col :" + field_a + " miss");
					}
				}
			}

		}

	}

	public void getData() {
		try {
			url_a = "jdbc:mysql://192.168.10.138:3306/test?user=twifi&password=twifi123$";
			url_b = "jdbc:mysql://192.168.10.183:3306/test?user=twifi&password=twifi123$";
			con_a = DriverManager.getConnection(url_a);
			con_b = DriverManager.getConnection(url_b);
			stmt_a = con_a.createStatement();
			stmt_b = con_b.createStatement();
			String query = "select table_name from information_schema.tables where table_schema='test'";

			ResultSet rs = stmt_a.executeQuery(query);
			while (rs.next()) {
				// System.out.println(rs.getString(1));
				tables_a.add(rs.getString(1));
				map_a.put(rs.getString(1), new ArrayList());
			}
			rs.close();
			rs = stmt_b.executeQuery(query);
			while (rs.next()) {
				// System.out.println(rs.getString(1));
				tables_b.add(rs.getString(1));
				map_b.put(rs.getString(1), new ArrayList());
			}

			rs.close();

			for (int i = 0; i < tables_a.size(); i++) {
				String tablename = tables_a.get(i);
				query = "show columns from " + tablename;
				List cols_a = map_a.get(tablename);
				rs = stmt_a.executeQuery(query);
				while (rs.next()) {
					HashMap col = new HashMap();
					String field = rs.getString(1);
					String type = rs.getString(2);
					col.put(FIELD, field);
					col.put(TYPE, type);
					cols_a.add(col);
				}
				rs.close();

			}

			for (int i = 0; i < tables_b.size(); i++) {
				String tablename = tables_b.get(i);
				query = "show columns from " + tablename;
				List cols_b = map_b.get(tablename);
				rs = stmt_b.executeQuery(query);
				while (rs.next()) {
					HashMap col = new HashMap();
					String field = rs.getString(1);
					String type = rs.getString(2);
					col.put(FIELD, field);
					col.put(TYPE, type);
					cols_b.add(col);
				}
				rs.close();

			}
			stmt_b.close();
			stmt_a.close();
			con_a.close();
			con_b.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		MysqlUtil compareMysql = new MysqlUtil();
		compareMysql.run();
	}
}

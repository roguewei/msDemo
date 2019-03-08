package com.rogue.gbf.gbfdemo.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {
	
	private static Properties props;
	
	static {
		try {
			InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("application.yml");
			props = new Properties();
			props.load(in);
			in.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConn() throws Exception{
		String url = "jdbc:mysql://192.168.0.118:3306/miaosha?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8";
		String username = "root";
		String password = "123456";
		String driver = "com.mysql.jdbc.Driver";
		Class.forName(driver);
		return DriverManager.getConnection(url,username, password);
	}
}

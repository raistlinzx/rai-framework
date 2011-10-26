package com.rai.framework.util.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import com.rai.framework.util.interfaces.DatabaseUtil;

public abstract class AbstractDatabaseUtil implements DatabaseUtil {

	protected Logger log = Logger.getLogger(this.getClass().getName());
	protected Connection conn;
	protected String database;

	protected Connection initConn(Properties properties)
			throws ClassNotFoundException, SQLException {
		// 驱动程序名
		String driver = properties.getProperty("driver");
		// URL指向要访问的数据库名
		String url = properties.getProperty("url");
		// MySQL配置时的用户名
		String user = properties.getProperty("user");
		// Java连接MySQL配置时的密码
		String password = properties.getProperty("password");
		// 加载驱动程序
		Class.forName(driver);
		// 连续数据库
		Connection conn = DriverManager.getConnection(url, user, password);

		if (!conn.isClosed())
			log.info("Successed for connecting to the Database!");

		return conn;
	}

	public AbstractDatabaseUtil(Properties properties) {
		try {
			conn = initConn(properties);
			database = properties.getProperty("database");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

package kr.or.ddit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Factory Object[Method] Pattern
 *
 */
public class ConnectionFactory {
	private static String driverClassName;
	private static String url;
	private static String user;
	private static String password;
	private static DataSource dataSource;
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("kr.or.ddit.db.dbInfo");
		driverClassName = bundle.getString("driverClassName");
		url = bundle.getString("url");
		user = bundle.getString("user");
		password = bundle.getString("password");
		
//		try {
//			Class.forName(driverClassName);
//		} catch (ClassNotFoundException e1) {
//			e1.printStackTrace();
//		}
		
		BasicDataSource ds = new BasicDataSource();
		dataSource = ds;
		ds.setDriverClassName(driverClassName);
		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(password);
		ds.setInitialSize(5);
		ds.setMaxTotal(10);
		ds.setMaxWaitMillis(2000);
//		성능(performance) : memory + response time
//		String-> StringBuffer
//		response time  = process time + latency time(network)
		
	}
	
	public static Connection getConnection() throws SQLException{
//		Connection conn = DriverManager.getConnection(url, user, password);
//		OracleDataSource dataSource = new OracleDataSource();
//		dataSource.setURL(url);
		Connection conn = dataSource.getConnection();
		return conn;
	}
}










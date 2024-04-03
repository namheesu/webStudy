package kr.or.ddit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Factory Object[Method] Pattern
 * 	: 객체의 생성을 전담하는 객체를 별도로 운영하는 구조.
 *
 */
public class ConnectionFactory {
	private static String url;
	private static String user;
	private static String password;
	private static DataSource dataSource;

	static {	// static 코드블럭 = 생성자 / ConnectionFactory가 메모리에 로딩될 때 한번 실행됨
		ResourceBundle dbInfo = ResourceBundle.getBundle("kr.or.ddit.db.dbInfo");
		url = dbInfo.getString("url");
		user = dbInfo.getString("user");
		password = dbInfo.getString("password");
		
		BasicDataSource bds = new BasicDataSource();
		dataSource = bds;
		bds.setDriverClassName(dbInfo.getString("driverClassName"));
		bds.setUrl(url);
		bds.setUsername(user);
		bds.setPassword(password);
		bds.setInitialSize(Integer.parseInt(dbInfo.getString("initialSize"))); 	// 몇개의 칼국수를 미리 만들어놓을것인가
		bds.setMaxWaitMillis(Long.parseLong(dbInfo.getString("maxWait"))); // 2초동안 기다린 후  반납된 커넥션 사용
		bds.setMaxTotal(Integer.parseInt(dbInfo.getString("maxTotal"))); 	// max를 넘으면 sqlException
		bds.setMaxIdle(Integer.parseInt(dbInfo.getString("maxIdle")));	// 7-5=2개 줄임
		
		
//		try {
//			Class.forName(dbInfo.getString("driverClassName"));	// 2번째 단계
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException(e);
//		}	
	}
	
	public static Connection getConnection() throws SQLException{
//		Connection conn = DriverManager.getConnection(url,user,password);
		Connection conn = dataSource.getConnection();
		return conn;
	}
}

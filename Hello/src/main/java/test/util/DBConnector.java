package test.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {
	// Connection 객체를 리턴해주는 메소드 정의하기
	public Connection getConn() {
		Connection conn=null;

		try {
			//오라클 드라이버 로딩 
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//접속할 DB 의 정보 @아이피주소:port번호:db이름
			String url="jdbc:oracle:thin:@localhost:1521:xe";
			//계정 비밀번호를 이용해서 Connection 객체의 참조값 얻어오기
			conn=DriverManager.getConnection(url, "scott", "TIGER");
			//예외가 발생하지 않고 여기까지 실행순서가 내려오면 접속 성공이다.
			System.out.println("Oracle DB 접속 성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// conn 타입 변수를 리턴해주면 conn 에 . 만 찍어서 사용할 수 있다.
		return conn;
	}
}

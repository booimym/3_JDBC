package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	
	
	/* DB연결하는 Connection 생성, 자동 커밋 off하기, 트랜잭션 제어,JDBC 객체 자원 반환(close)
	 * 
	 * 이러한 JDBC에서 반복 사용되는 코드를 모아두는 클래스
	 *
	 * * [특징] 모든 필드, 메서드가 static이다 - 어디서든지 클래스명.필드명 / 클래스명.메서드명 으로 호출이 가능하다.
	 * 									 (별도로 객체 생성을 하지 않아도...)
	 * 									[ex] new JDBCTemplete.Connection 이러지 않고  JDBCTemplete.Connection만 써도 됨.
	 *
	 * */
	
	private static Connection conn = null;
	// -> static 메서드에서 필드를 사용하기 위해서는 필드도 static 필드여야만 한다.
	
	
	
	
	/** DB연결 정보를 담고 있는 Connection 객체 생성 및 반환 메서드
	 * @return
	 */
	public static Connection getConnection() {
		
		try {
			
			//현재 connection 객체가 없을 경우 -> 새 커넥션 객체를 생성
			if(conn == null || conn.isClosed() ) {
								//conn.isClosed() : 커넥션이 close 상태이면 true 반환
			
				Properties prop = new Properties();
				//properties : MAP<String,String> 형태의 객체, XML 입출력에 특화되어 있다.
				
				//driver.xml파일을 읽어오기.
				//driver가져오는 스트림이 만들어지자마자 xml로 읽어오기
//				FileInputStream fis = new FileInputStream("driver.xml");
//				prop.loadFromXML(fis); // 이 두 줄을 한번에 쓰기! 더 간편!
				prop.loadFromXML(new FileInputStream("driver.xml"));
				//-> xml 파일에 작성된 내용이 properties 객체에 모두 저장됨...
				
				
				//XML에서 읽어온 값들을 모두 String 변수에 저장.
				String driver = prop.getProperty("driver");
				String url = prop.getProperty("url");
				String user = prop.getProperty("user");
				String password = prop.getProperty("password");
				
				//커넥션 생성하기.
				Class.forName(driver); //Oracle JDBC Driver 객체를 메모리에 로드한다.
				conn =DriverManager.getConnection(url,user,password); // 만들어진 Driver객체를 DriverManager가 잡아서 Connection 객체에다가 넣는다.
				
				//개발자가 직접 트랜잭션을 제어할 수 있도록
				//자동 커밋 비활성화
				conn.setAutoCommit(false);
			}
			
			
		} catch(Exception e) {
			System.out.println("[Connection 생성 중 예외 발생]");
			e.getStackTrace();
		}
		
		
		return conn;
		
	}
	

	/** Connection 객체 자원 반환 메서드
	 * @param conn
	 */
	public static void close(Connection conn) {
	
		
		try {
			   // 전달받은 conn이 
			   // 참조하는 Connection 객체가 있고
			   // 그 Connection 객체가 close 상태가 아니라면
			if(conn != null && !conn.isClosed()) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/** Statement, PreparedStatement 객체 자원 반환 메서드 //preparedStatement는  (부모:statement를 )상속받고 있는 자식이니까 다형성이 적용되어서 부모타입의 참조변수로 자식객체를 참조할 수 있다.
	 * 											     //자식 객체로 연결되어 실행되는 동적 바인딩이 적용이 된다.
	 * @param stmt
	 */
	public static void close(Statement stmt) {
		//오버로딩:하나의 이름으로 
	
		try {
			   // 전달받은 stmt이 
			   // 참조하는 Statement 객체가 있고
			   // 그 Statement 객체가 close 상태가 아니라면
			if(stmt != null && !stmt.isClosed()) stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}


	/** ResultSet 객체 자원 반환 메서드
	 * @param rs
	 */
	public static void close(ResultSet rs) {
						//오버로딩:하나의 이름으로 
	
		try {
			   // 전달받은 rs가 
			   // 참조하는 ResultSet 객체가 있고
			   // 그 ResultSet 객체가 close 상태가 아니라면
			if(rs != null && !rs.isClosed()) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	/** 트랜잭션 Commit 메서드
	 * @param conn
	 */
	public static void commit(Connection conn) {
		
		try {
				//연결되어 Connection객체일 경우에만/ 정상이라고 판단해서 (연결된 db를) commit을 실행하겠다.
			if(conn != null && !conn.isClosed()) conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/** 트랜잭션 RollBack 메서드
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		
		try {
				//연결되어 Connection객체일 경우에만/ 정상이라고 판단해서 (연결된 db를) rollback을 실행하겠다.
			if(conn != null && !conn.isClosed()) conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}

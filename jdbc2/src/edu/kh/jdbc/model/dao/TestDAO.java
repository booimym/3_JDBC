package edu.kh.jdbc.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.model.vo.TestVO;

//DAO(Data Access Object) : 데이터가 저장된 DB에 접근하는 객체
//							-> SQL 수행, 결과 반환 받는 기능을 수행

public class TestDAO {

	
	// JDBC 객체를 참조하기 위한 참조변수 선언
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// XML로 SQL을 다룰 예정 -> Properties 객체 사용
	private Properties prop;
	
	//기본 생성자
	public TestDAO() {
		
		// TestDAO 객체 생성 시
		// test-query.xml 파일의 내용을 읽어와
		// Properties 객체에 저장
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("test-query.xml"));
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/** 1행 삽입 DAO
	 * @param conn
	 * @param vo1
	 * @return result
	 */
	public int insert(Connection conn, TestVO vo1) throws SQLException {
												//throws SQLException
												// -> 호출한 곳으로 발생한 SQLException을 던진다.
												// 이유) 예외 처리를 모아서 진행하기 위해서!
		// 1. 결과 저장용 변수를 선언한다.
		int result = 0;
		
			try {
				//2. SQL 작성(test-query.xml에 작성된 SQL을 얻어오는 거임. prop이 얻어온 걸 저장하는 중임!)
				
				String sql = prop.getProperty("insert");
				
				//3. PreparedStatement 객체를 생성
				//service클래스에서 온 connecton을 이용한다.
				pstmt = conn.prepareStatement(sql);
				// -> 예외처리 해줘야 되는데, throw 이용하기
				
				//4. ?(위치홀더)에 알맞은 값을 세팅하는 코드 
				pstmt.setInt(1, vo1.getTestNo());
				pstmt.setString(2, vo1.getTestTitle());
				pstmt.setString(3, vo1.getTestContent());
				
				//5. SQL( INSERT ) 수행 후 결과를 반환 받기
				//pstmt.executeQuery(); -> SELECT 수행, ResultSet 반환
				//pstmt.executeUpdate(); //-> DML 수행, 반환된 행의 개수를 반환
				
				result = pstmt.executeUpdate();
				
			}finally {
				
				//6. 사용한 JDBC 객체 자원을 반환하는 메소드( close() );
				close(pstmt);
				
				//catch안하는데 try,finally구문을 써야 되는 이유?
				//에러나면 close 못하고 넘어가니까 그러면 안 되니까 에러나든 말든 close를 실행하게 하는 finally문이 필요하댜.
			}
		
		return result;
	}
	public int update(Connection conn, TestVO vo3) throws SQLException {
		
		String sql = prop.getProperty("update");
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(3, vo3.getTestNo());
			pstmt.setString(1, vo3.getTestTitle());
			pstmt.setString(2, vo3.getTestContent());
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		
		return result ;
	}

}

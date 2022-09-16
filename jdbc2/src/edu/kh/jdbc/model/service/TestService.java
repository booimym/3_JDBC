package edu.kh.jdbc.model.service;

// import static 구문
// -> static이 붙은 필드,메서드를 호출할 때
//    클래스명을 생략할 수 있게 하는 구문

import static edu.kh.jdbc.common.JDBCTemplate.*;



import java.sql.Connection;
import java.sql.SQLException;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.model.dao.TestDAO;
import edu.kh.jdbc.model.vo.TestVO;

// Service : 비즈니스 로직(데이터 가공, 트랜잭션 제어) 처리
// -> 실제 프로그램이 제공하는 기능을 모아놓은 클래스


// 하나의 Service 메서드에서 n개의 DAO 메서드(지정된 SQL을 수행하는 애)를 호출하여
// 이를 하나의 트랜잭션 단위로 취급하여
// 한번에 Commit, Rollback을 실행할 수 있다.


//  * 여러 DML을 수행하지 않는 경우(단일 DML이나 SELECT구문의 경우 등...)에도 
// 		코드의 통일성을 지키기 위해서 Service에서 Connection 객체를 생성한다.

// 	  -> Connection 객체가 commit, rollback 메서드를 제공해서 그럽니다.


public class TestService {
	// 아메리카노 제조 -> 이 메소드를 호출하면 아메리카노 제조해서 주는 거임.
	// 콜드브루 제조 -> 이 메소드를 호출하면 콜드브루 제조 해서 주는 거임.
	// 카페라떼 제조 -> 이 메소드를 호출하면  카페라떼 제조해서 주는 거임.
	
	
	private TestDAO dao = new TestDAO();
	
	
	/** 1행 삽입 서비스
	 * @param vo1
	 * @return
	 */
	public int insert(TestVO vo1) throws SQLException {
		
		//1. 커넥션 생성
		
		//클래스명.메소드명 적으면 바로 호출 가능해
		Connection conn = getConnection();
		
		
		//INSERT DAO 메서드를 호출하여 수행 후 결과를 반환받기
		//service에서 생성한 Connection객체를 반드시 DAO에 전달해야 한다. statement만들 때 필요해.
		int result = dao.insert(conn, vo1); 
		//result == SQL 수행 후 반영된 결과 행의 개수임.
		
		//트랜잭션 제어 처리
		if(result > 0) commit(conn);
		else rollback(conn);
		
		//2. 커넥션 반환(close)
		close(conn);
		
		//결과 반환
		return result;
	}


	/**
	 * @param vo1
	 * @param vo2
	 * @param vo3
	 * @return result
	 * @throws SQLException 
	 */
	public int insert(TestVO vo1, TestVO vo2, TestVO vo3) throws Exception {
													//아래 catch문에서 강제 발생된 예외를
													//호출부로 던진다는 구문
		
													//왜 예외를 강제 발생 시켰는가?
													//-> Run2에서 예외 상황에 대한 다른 결과를 출력하기 위해서...
		//1. Connection 생성(무조건 첫 번째에 하기!!)
		Connection conn = getConnection();
		
		int res = 0; //insert 3회 모두 성공 시 1을, 아니면 0을 저장할 변수.
		
		try {
			// insert 중 오류가 발생하면 모든 insert내용을 rollback하는 거 만들거임.
			// -> try-catch로 예외가 발생했다는 것을 인지함.
			
			int result1 = dao.insert(conn, vo1);
			int result2 = dao.insert(conn, vo2);
			int result3 = dao.insert(conn, vo3);
			
			//트랜잭션 제어
			if (result1 + result2 + result3 == 3) { //모두 insert 성공한 경우
				commit(conn);
				res = 1;
				
			} else {
				rollback(conn);
			}
			
		} catch (SQLException e) { //dao 수행 중 예외 발생 시 
			rollback(conn);
			//-> 실패된 데이터를 DB에 삽입하지 않음
			//-> DB에는 성공된 데이터만 저장이 된다
			//-> ==DB에 저장된 데이터의 신뢰도가 상승한다.
			e.printStackTrace();
			
			// Run2클래스로 예외를 전달할 수 있도록 예외 강제 발생시킬거야 
			throw new Exception("DAO 수행 중 예외 발생"); //예외객체를 만들었어 -> 그리고 이걸 던져줘
			
		} finally { //무조건 conn 반환하기
			
			close(conn);
		}
		
		
		
		
		return res; //insert 3회의 결과를 반환한다.
	}
	
}

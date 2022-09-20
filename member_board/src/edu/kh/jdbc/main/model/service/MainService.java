package edu.kh.jdbc.main.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.main.model.dao.MainDAO;
import edu.kh.jdbc.member.vo.Member;


// Service : 데이터 가공, 트랜젝션 제어 처리를 하는 역할 담당
public class MainService {
	
	private MainDAO dao = new MainDAO();
	//service 안에 dao도 같이 불러오는데, 기본생성자인 객체를 불러옴( 안에 properties 있음)

	/**아이디 중복 검사 서비스
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public int idDupCheck(String memberId) throws Exception {
		
		//1. Connection 생성하기
		Connection conn = getConnection();
		
		//2. DAO 메서드 호출 후 결과 반환 받기
		int result = dao.idDupCheck(conn,memberId);
		
		// 3. connection 반환하기만 하기 (SELECT 구문은 트랜잭션 제어가 필요 X)
		close(conn);
		
		
		//4. 조회 결과 반환
		return result;
	}

	/** 회원 가입 서비스
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int signUp(Member member) throws Exception{
		
		
		//1. Connection 생성하기
		Connection conn = getConnection();
		
		
		//2. DAO 메서드 호출 후 결과 반환 받기
		int result = dao.signUp(conn,member); //member를 삽입해달라고 넘겨준다.
		
		//3. 트랜잭션 제어 처리
		if(result > 0 ) commit(conn);
		else 			rollback(conn);
		
		//4.Connection 반환
		close(conn);
		
		//5. 삽입 결과 반환
		return result;
	}

	/** 1. 로그인 서비스
	 * @param memberId
	 * @param memberPw
	 * @return
	 */
	public Member login(String memberId, String memberPw) throws Exception {
		
		//1. 커넥션 생성
		//2. DAO 메소드 호출 후 결과를 반환받기
		//3. 커넥션 반환
		//4. 결과 반환
		
		Connection conn = getConnection();
		
		Member loginMember  =dao.login(conn,memberId,memberPw);
		
		close(conn);
		
		return loginMember;
		
		
		
	
	}

}

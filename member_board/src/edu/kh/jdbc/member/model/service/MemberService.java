package edu.kh.jdbc.member.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.member.model.dao.MemberDAO;
import edu.kh.jdbc.member.vo.Member;

public class MemberService {

	
	private MemberDAO dao = new MemberDAO();
	
	
	public int updateMine(String memberId,String memberName, String memberGender) throws Exception {
		
		//1. Connection 생성하기
		Connection conn = getConnection();
		
		//2. DAO 메서드 호출 후 결과 반환 받기
		int result = dao.updateMine(conn,memberId,memberName,memberGender); 
		
		//3. 트랜잭션 제어 처리
		if(result > 0 ) commit(conn);
		else 			rollback(conn);
		//4. 커넥션 반환
		close(conn);
		//5. 수정 결과 반환
		return result;
		
	}
	
	//[수업 ver.]
	// int result = dao.updateMine(conn,member);
	
	public int updatePw(String currentPw, String newPw1, String memberId) throws Exception{
	
		//1. Connection 생성하기
		Connection conn = getConnection();
		
		//2. DAO 메서드 호출 후 결과 반환 받기
		int result = dao.updatePw(conn,currentPw, newPw1, memberId); 
		
		//3. 트랜잭션 제어 처리
		if(result > 0 ) commit(conn);
		else 			rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 회원 목록 조회 서비스
	 * @return
	 * @throws Exception
	 */
	public List<Member> selectAll() throws Exception {
		
		//1. Connection 생성하기
		//JDBCTemplate에 저장해놨음...
		Connection conn = getConnection();
		
		//2. dao 메소드 호출 후 결과 받아옴...
		List<Member> memberList = dao.selectAll(conn);
		
		//3.커넥션 반환
		close(conn);
	
		
		
		//4. 결과 반환(view에)
		return memberList;
	}

	
	public int secession(String memberPw, int memberNo) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.session(conn,memberPw,memberNo);
		
		if (result > 0) commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return result;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

package lsy_project.jdbc.main.model;

import static lsy_project.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;

import lsy_project.jdbc.member.VO.MemberVO;

public class MainService {

	private MainDAO dao = new MainDAO();
	
	
	
	public MemberVO login(String memberId, String memberPw) throws Exception {
		
		Connection conn = getConnection();
		
		MemberVO loginMember  = dao.login(conn,memberId,memberPw);
		
		close(conn);
		
		return loginMember;
	}

	
	public int idDupCheck(String memberId) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.idDupCheck(conn, memberId);
		
		close(conn);
		
		return result;
	}


	public int signUp(MemberVO memberVo) throws Exception{
		
		Connection conn =getConnection();
		
		int result = dao.signUp(conn,memberVo);
		
		if(result>0) commit(conn);
		else		rollback(conn);
		
		close(conn);
		
		return result;
		
		
	}
	
}

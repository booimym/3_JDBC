package lsy_project.jdbc.main.model;


import static lsy_project.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import lsy_project.jdbc.member.VO.MemberVO;

public class MainDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private Properties prop = null;
	
	public MainDAO() {
		
		try {
			
			prop = new Properties();
			
			prop.loadFromXML(new FileInputStream("main-query.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public MemberVO login(Connection conn, String memberId, String memberPw) throws Exception {
		
		MemberVO loginMember = null;
		
		try {
			
			String sql = prop.getProperty("login");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,memberId);
			pstmt.setString(2,memberPw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				loginMember = new MemberVO();
				

				
				loginMember.setMemberNo(rs.getInt("MEMBER_NO"));
				loginMember.setMemberId(memberId);
				loginMember.setMemberPw(memberPw);
				loginMember.setEnrollDate(rs.getString("ENROLL_DATE"));
				
				
			}
			
		} finally {
			
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
		
		return loginMember;
	}


	public int idDupCheck(Connection conn, String memberId) throws Exception {
		
		int result = 0;
		try {
			
			String sql = prop.getProperty("idDupCheck");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,memberId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				
				result = rs.getInt(1); //컬럼순서
			}
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}


	public int signUp(Connection conn, MemberVO memberVo) throws Exception{
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("signUp");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberVo.getMemberId());
			pstmt.setString(2, memberVo.getMemberPw());
			pstmt.setString(3, memberVo.getMemberNm());
			
			result = pstmt.executeUpdate();
			
		}finally {
			
			close(pstmt);
			
		}
		
		
		return result;
	}

}

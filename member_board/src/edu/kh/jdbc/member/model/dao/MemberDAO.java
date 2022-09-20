package edu.kh.jdbc.member.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.member.vo.Member;



public class MemberDAO {
	
	//필드(멤버변수)
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	
	//생성자: 객체 생성될 때 만들어지는 메소드...
	//기본생성자일 때, 
	public MemberDAO() {
		
		try {
			
			prop = new Properties(); //properties 객체 생성하기.
			prop.loadFromXML(new FileInputStream("member-query.xml"));
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	/**
	 * @param conn
	 * @param memberId
	 * @param memberName
	 * @param memberGender
	 * @return
	 * @throws Exception
	 */
	public int updateMine(Connection conn, String memberId,String memberName, String memberGender) throws Exception {
		
		//1. 결과 저장용 변수 생성(UPDATE 반영 결과 행의 개수(정수형)을 저장하기 위한 변수)
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateMine");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberName);
			pstmt.setString(2, memberGender);
			pstmt.setString(3, memberId);
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			close(pstmt);
		}
		
		// 수정 결과 반환
		return result;
	}
	
	//[수업 ver.]
	//pstmt.setString(1, member.getMemberName());
	
	
	public int updatePw(Connection conn, String currentPw, String newPw1, String memberId) throws Exception{
		int result = 0;
		
		try {
			String sql = prop.getProperty("updatePw");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(2, memberId);
			pstmt.setString(1, newPw1);
			pstmt.setString(3, currentPw);
			
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			close(pstmt);
		}
		
		return result;
	}

	
	
	/**회원 목록 조회 DAO
	 * @param conn
	 * @return memberList
	 * @throws Exception
	 */
	public List<Member> selectAll(Connection conn) throws Exception {

		
		//1. 결과 저장용 변수를 만들기
		//부모타입으로 참조해서 자식객체 생성
		List<Member> memberList = new ArrayList<>();
		
		try {
			//2. SQL을 Properties에서 얻어오기
			String sql = prop.getProperty("selectAll");
			//3. Stmt 객체를 생성하기
			stmt = conn.createStatement();
			//4. sql(select)수행 후에 결과(rs)를 반환받기. 
			rs = stmt.executeQuery(sql);
			//5. (while)을 이용해서 결과의 각 행에 접근 후 컬럼 값을 얻어와 멤버 객체에 저장 후 List에 추가하는 작업
			while(rs.next()) {
				//아이디,이름,성별
//				String memberId = rs.getString("MEMBER_ID");
//				String memberName = rs.getString("MEMBER_NM");
//				String memberGender = rs.getString("MEMBER_GENDER");
				
				Member member = new Member();
				member.setMemberId(rs.getString("MEMBER_ID"));
				member.setMemberName(rs.getString("MEMBER_NM"));
				member.setMemberGender(rs.getString("MEMBER_GENDER"));
				
				memberList.add(member);
			}
			
			
		}finally {
			//6.JDBC 객체 자원을 반환하기
			close(stmt);
			close(rs);
		}
		
		
		
		//7.조회 결과를 담은 LIST를 반환하기...
		return memberList;
	}

	
	
	public int session(Connection conn, String memberPw, int memberNo) throws Exception {
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("secession");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(2, memberPw);
			pstmt.setInt(1, memberNo);
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		
		
		
		return result;
	}
	
	

	
}

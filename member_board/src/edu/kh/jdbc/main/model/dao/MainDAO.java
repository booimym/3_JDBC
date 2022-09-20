package edu.kh.jdbc.main.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.jdbc.member.vo.Member;


//DAO(Data Access Object) : DB 연결용 객체
public class MainDAO {

	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private Properties prop = null;
	//Properties : Map<String,String>으로 제한되어있는 애 & XML 파일을 읽고 /쓰고 특화되어있는 애
	
	//기본생성자
	public MainDAO() {
		
		try {
			
			prop = new Properties(); // Priperties 객체를 생성
			
			prop.loadFromXML(new FileInputStream("main-query.xml"));
			//main-query.xml 파일의 내용을 읽어와  Properties 객체에 저장하기!
		
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	/** 아이디 중복 검사 DAO
	 * @param conn
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public int idDupCheck(Connection conn, String memberId) throws Exception {
		
		//1. 결과 저장용 변수 
		int result = 0;
		
		try {
			//2. SQL 얻어오기
			String sql = prop.getProperty("idDupCheck");
			
			//3. PreparedStratement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//4. ?에 알맞은 값을 세팅한다.
			pstmt.setString(1,memberId);
			
			//5. SQL 수행 후 결과 반환받기
			rs = pstmt.executeQuery();
			
			// 6. 조회 결과를 옮겨 담기
			// 1행 조회 -> if
			// n행 조회 -> while
			while(rs.next()) {
				//result = rs.getInt("COUNT(*)"); //컬럼명
				result = rs.getInt(1); //컬럼순서
			}
			
		} finally {
			//7. 사용한 JDBC 객체 자원 반환
			close(rs);
			close(pstmt);
			
		}
		
		
		//8.결과 반환
		return result;
	}

	public int signUp(Connection conn, Member member) throws Exception {
		
		int result = 0;
		
			try {
				
				String sql = prop.getProperty("signUp");
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, member.getMemberId());
				pstmt.setString(2, member.getMemberPw());
				pstmt.setString(3, member.getMemberName());
				pstmt.setString(4, member.getMemberGender());
				
				result = pstmt.executeUpdate();
				
			}finally {
				
				close(pstmt);
				
			}
		
		
		
		return result;
	}

	/** 로그인 DAO
	 * @param conn
	 * @param memberId
	 * @param memberPw
	 * @return loginMember
	 * @throws Exception
	 */
	public Member login(Connection conn, String memberId, String memberPw) throws Exception {
		
		//1. 결과 저장용 변수 선언
		Member loginMember = null;
		
		try {
			
			//2. SQL 얻어오기(main-query.xml 파일에 작성된 SQL)
			String sql = prop.getProperty("login");
			
			//3. PreparedStatement 객체 행성
			//4. ?에 알맞은 값 대입
			//5. SQL(SELECT) 수행 후 결과(ResultSet) 반환 받기
			//6. 조회 결과가 있을 경우 컬럼 값을 모두 얻어와
			//   Member 객체를 생성해서 loginMember에 대입한다.
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,memberId);
			pstmt.setString(2,memberPw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				int memberNo = rs.getInt("MEMBER_NO");
				//String memberId = rs.getString("MEMBER_ID");
				String memberNm = rs.getString("MEMBER_NM");
				String memberGender = rs.getString("MEMBER_GENDER");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				loginMember = new Member(memberNo,memberId,memberNm,memberGender,enrollDate);
				
				//2번째 방법
				//먼저 loginMember객체를 생성한 후에 그 객체에다가 하나씩 담아도 됨.
				//loginMember.setMemberNo(rs.getInt("MEMBER_NO"))
				//loginMember.setMemberId(memberId);
				//loginMember.setMemberName(rs.getString("MEMBER_NM"))
				//loginMember.set....
				
				//3번째 방법
				//loginMember = new Member(rs.getInt("MEMBER_NO"),
				//memberId, rs.getString("MEMBER_NM"),
				//rs.getString("MEMBER_GENDER"),
				//rs.getString("ENROLL_DATE")
				
				
			}
			
			
			
		}finally {
			//7. 사용한 JDBC 객체 자원을 반환하고
			
			try {
				
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		//8. 조회 결과를 반환한다.
		return loginMember;
	}

	
	
	
	
}

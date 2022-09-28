package lsy_project.jdbc.book.model;

import static lsy_project.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import lsy_project.jdbc.book.VO.BookVO;
import lsy_project.jdbc.member.view.MemberView;

public class BookDAO {
	
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public BookDAO() {
		
		try {
			
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("book-query.xml"));
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public List<BookVO> selectByBranch(Connection conn, String input) throws Exception{
		
		
		List<BookVO> bookList = new ArrayList<>();
		
		try {
			
			String sql = prop.getProperty("selectByBranch");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, input);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				BookVO book = new BookVO();
				book.setAllMoviesNo(rs.getInt("ALL_MOVIES_NO"));
				book.setMovieTitle(rs.getString("MOVIE_TITLE"));
				book.setScreenNm(rs.getString("SCREEN_NAME"));
				book.setStartTime(rs.getString("상영시간"));
				book.setCountSeat(rs.getInt("남은 좌석 개수"));
				
				bookList.add(book);
			}
			
		}finally {
			close(pstmt);
			close(rs);
		}
		
		return bookList;
	}
	
	public List<BookVO> selectByMovie(Connection conn, String input) throws Exception {
		
		List<BookVO> bookList = new ArrayList<>();
		
		try {
			
			String sql = prop.getProperty("selectByMovie");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, input);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				BookVO book = new BookVO();
				book.setAllMoviesNo(rs.getInt("ALL_MOVIES_NO"));
				book.setTheaterNm(rs.getString("THEATER_NM"));;
				book.setScreenNm(rs.getString("SCREEN_NAME"));
				book.setStartTime(rs.getString("상영시간"));
				book.setCountSeat(rs.getInt("남은 좌석 개수"));
				
				bookList.add(book);
			}
			
		}finally {
			close(pstmt);
			close(rs);
		}
		
		return bookList;
		
	}

	public int checkRating(Connection conn, int input1) throws Exception{
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("checkRating");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, input1);
			rs = pstmt.executeQuery();
		
			while(rs.next()) {
				result = rs.getInt(1);
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
	}
	
	
	

	public int leftSeat(Connection conn, int input1) throws Exception{
		
		int result = 0;
		
		try {
		
			String sql = prop.getProperty("leftSeat");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, input1);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
			result = rs.getInt(1);
			}
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
		
	}

	public int checkSeat(Connection conn, int input1, int seatNum)throws Exception {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("checkSeat");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, input1);
			pstmt.setInt(2, seatNum);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				result = rs.getInt(1);
			}
		
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public int booking(Connection conn, BookVO book)throws Exception {
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("booking");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,book.getMemberNo());
			pstmt.setInt(2, book.getAllMoviesNo());
			pstmt.setInt(3, book.getSeatNo());
			
			result = pstmt.executeUpdate();
			
		}finally {
			
			close(pstmt);
		}
		
		
		return result;
	}

	public List<BookVO> checkBooking(Connection conn) throws Exception {
		
		
		List<BookVO> bookList = new ArrayList<>();
		
		try {
			
			
			
			String sql = prop.getProperty("checkBooking");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, MemberView.loginMember.getMemberId());
			pstmt.setString(2, MemberView.loginMember.getMemberPw());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				BookVO book = new BookVO();
				book.setReservNo(rs.getInt("RESERV_NO"));
				book.setAllMoviesNo(rs.getInt("ALL_MOVIES_NO"));
				book.setTheaterNm(rs.getString("THEATER_NM"));;
				book.setScreenNm(rs.getString("SCREEN_NAME"));
				book.setMovieTitle(rs.getString("MOVIE_TITLE"));
				book.setStartTime(rs.getString("상영시간"));
				book.setSeatNm(rs.getString("SEAT_NAME"));
				
				bookList.add(book);
				
				
			}
		}finally {
			
			close(pstmt);
		}
		
		return bookList;
	}

	public int cancleBooking(Connection conn, int input2) throws Exception {
		
		int result = -1;
		
		try {
		String sql = prop.getProperty("deleteBooking");
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, MemberView.loginMember.getMemberId());
		pstmt.setString(2, MemberView.loginMember.getMemberPw());
		pstmt.setInt(3, input2);
		
		result = pstmt.executeUpdate();
		
		}finally {
			
			close(pstmt);
		}
		return result;
	}

	

	
	
	
	
	
	
	
	
	
	
	
}

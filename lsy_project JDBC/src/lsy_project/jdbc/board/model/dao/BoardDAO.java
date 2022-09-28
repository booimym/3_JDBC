package lsy_project.jdbc.board.model.dao;

import static lsy_project.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import lsy_project.jdbc.board.model.vo.BoardVO;

public class BoardDAO {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public BoardDAO() {
		
		try {
			
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("board-query.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int insertBoard(Connection conn, int num, int memberNo, String content) throws Exception {
		
		int result = 0 ;
		
		try {
			
			String sql = prop.getProperty("insertBoard");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			pstmt.setString(2, content);
			pstmt.setInt(3, memberNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			close(pstmt);
			
		}
		
		
		return result;
	}

	public List<BoardVO> selectAllBoard(Connection conn) throws Exception{
		
		List<BoardVO> boardList = new ArrayList<>();
		
		try {
			
			String sql = prop.getProperty("selectAllBoard");
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				BoardVO board = new BoardVO();
				board.setBoardNo(rs.getInt("BOARD_NO"));
				board.setMovieName(rs.getString("MOVIE_TITLE"));
				board.setBoardContent(rs.getString("BOARD_CONTENT"));
				board.setMemberId(rs.getString("MEMBER_ID"));
				
				boardList.add(board);
				
			}
			
		}finally {
			
			close(rs);
			close(stmt);
			
		}
		
		return boardList;
	}

	public int insertScore(Connection conn, int allMovieNum, Double score) throws Exception {
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("insertScore");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setDouble(1, score);
			
			pstmt.setInt(2, allMovieNum);
			
			result = pstmt.executeUpdate();
			
		}finally {
			
			close(pstmt);
			
		}
		
		return result;
	}
	
}

package edu.kh.jdbc.board.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.board.model.vo.Board;

public class BoardDAO {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	//기본생성자(객체가 초기화할 때 기능을 수행)
	public BoardDAO() {
		
		try {
		prop = new Properties();
		prop.loadFromXML(new FileInputStream("board-query.xml"));
		//inputstream으로 불러온 것을 prop에 적재하겠다(load)
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**1. 게시글 목록 조회
	 * @param conn
	 * @return boardList
	 */
	public List<Board> selectAllBoard(Connection conn) throws Exception{
		
		List<Board>  boardList = new ArrayList<>();
		//비어있는 list객체가 생성된다.
		
		try {
			
			String sql = prop.getProperty("selectAllBoard");
			//위치홀더 없으니까 statment쓰자.
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			
			
			//rs에 담긴 값을 List에 옮겨담기
			while(rs.next()) {
			
				Board board = new Board();
				board.setBoardNo(rs.getInt("BOARD_NO"));
				board.setBoardTitle(rs.getString("BOARD_TITLE"));
				board.setMemberName(rs.getString("MEMBER_NM"));
				board.setCreateDate(rs.getString("CREATE_DT"));
				board.setReadCount(rs.getInt("READ_COUNT"));
				board.setCommentCount(rs.getInt("COMMENT_COUNT"));
			
				boardList.add(board);
			
			}
			
			
			
			
			
		}finally {
		
			close(rs);
			close(stmt);
		}
		return boardList;
	}

	/** 게시글 상세 조회 DAO
	 * @param conn
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board selectBoard(Connection conn, int boardNo) throws Exception {
		//결과 저장용 변수 선언
		Board board = null; //if문으로 조회 결과 없으면 null, 있으면 아니니까 첨에 null로 해줘야 해
		
		try {
			
			String sql = prop.getProperty("selectBoard");
			pstmt = conn.prepareStatement(sql); //PreparedStatement 생성
			pstmt.setInt(1, boardNo); //?에 알맞은 값 대입
			rs = pstmt.executeQuery(); //select니까 결과resultset을 반환받아서 rs에 넣기.
			
			if(rs.next()) { //조회 결과가 있을 경우
				
				board = new Board(); //BOARD 객체 생성 == Board는 null 아님
				board.setBoardNo(rs.getInt("BOARD_NO"));
				board.setBoardTitle(rs.getString("BOARD_TITLE"));
				board.setBoardContent(rs.getString("BOARD_CONTENT"));
				board.setMemberNo(rs.getInt("MEMBER_NO"));
				board.setMemberName(rs.getString("MEMBER_NM"));
				board.setReadCount(rs.getInt("READ_COUNT"));
				board.setCreateDate(rs.getString("CREATE_DT"));
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		return board;
	}

	/** 조회수 증가하는 DAO
	 * @param conn
	 * @param boardNo
	 * @return
	 * @throws Exception
	 */
	public int increaseReadCount(Connection conn, int boardNo)throws Exception  {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("increaseReadCount");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		
		
		return result;
	}

	/** 게시글 수정 DAO
	 * @param conn
	 * @param board
	 * @return result
	 * @throws Exception
	 */
	public int updateBoard(Connection conn, Board board)throws Exception {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateBoard");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,board.getBoardTitle());
			pstmt.setString(2,board.getBoardContent());
			pstmt.setInt(3,board.getBoardNo());
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		
		
		return result;
	}

	public int deleteBoard(Connection conn, int boardNo) throws Exception{
		
int result = 0;
		
		try {
			String sql = prop.getProperty("deleteBoard");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,boardNo);
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		
		
		return result;
	}
	
	
	
	
	
}

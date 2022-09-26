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

	/**
	 * @param conn
	 * @param board
	 * @return
	 * @throws Exception
	 */
	public int insertBoard(Connection conn, Board board) throws Exception {
		int result = 0;
		// 제일 위에다가 결과를 저장하는 변수를 선언하는 이유는
		// {}안에다가 변수를 선언하면 return 에서 result를 사용 못함.
		try {		
		String sql = prop.getProperty("insertBoard");	
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1,board.getBoardNo()); // 추가
		pstmt.setString(2,board.getBoardTitle());
		pstmt.setString(3,board.getBoardContent());
		pstmt.setInt(4,board.getMemberNo());
		
		result = pstmt.executeUpdate();
		
		}finally {
			//무조건 실행되는 부분이니까 반환하는 걸 여기다가 쓴다.
		
			close(pstmt);
			
		
		
		}
		
		return result;
	}

	/** 다음 게시글 번호를 생성하는 DA0
	 * @param conn
	 * @return boardNo
	 * @throws Exception
	 */
	public int nextBoardNo(Connection conn) throws Exception{
		
		int boardNo = 0;
		
		
		try {
		
			String sql = prop.getProperty("nextBoardNo");
			
			pstmt=conn.prepareStatement(sql);
			//?없어도 prepareStatement쓸 수있음. 걍 set안 쓰면 됨.
			
			rs = pstmt.executeQuery(); // select니까 query //결과는 1행임.
			
			if(rs.next()) {
				boardNo = rs.getInt(1); //첫번째 컬럼값을 얻어와 boardNo에 저장함.
			}
			
		}finally {
		 close(rs);
		 close(pstmt);
		}
		
		
		return boardNo;
	}

	/** 게시글 검색 DAO
	 * @param conn
	 * @param condition
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public List<Board> searchBoard(Connection conn, int condition, String query) throws Exception {
		
		List<Board> boardList = new ArrayList<>();
		//board 안서도 앞에 써져있으니까 타입을 자동으로 추론한다....
		
		try {
			
			String sql = prop.getProperty("searchBoard1")
						+ prop.getProperty("searchBoard2_"+condition)
						+prop.getProperty("searchBoard3");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, query);
			
			//3번 (제목+내용)은  ?가 2개 존재하기 때문에 추가 세팅 구문을 작성해야 된다...
			if (condition ==3)
				pstmt.setString(2, query);
			
			rs = pstmt.executeQuery();
			
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
			close(pstmt);
		}
		
		
		
		return boardList;
	}
	
	
	
	
	
}

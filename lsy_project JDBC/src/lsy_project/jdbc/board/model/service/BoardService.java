package lsy_project.jdbc.board.model.service;

import static lsy_project.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import lsy_project.jdbc.board.model.dao.BoardDAO;
import lsy_project.jdbc.board.model.vo.BoardVO;

public class BoardService {
	
	private BoardDAO dao = new BoardDAO();
	
	
	public int insertBoard(int num, int memberNo, String content) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.insertBoard(conn, num,memberNo,content);
		
		if (result>0) commit(conn);
		else 		 rollback(conn);
		
		return result;
	}


	public List<BoardVO> selectAllBoard() throws Exception{
		
		Connection conn = getConnection();
		
		List<BoardVO> boardList = dao.selectAllBoard(conn);
		
		close(conn);
		
		return boardList;
	}


	public int insertScore(int allMovieNum, Double score) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.insertScore(conn, allMovieNum,score);
		
		if (result>0) commit(conn);
		else 		 rollback(conn);
		
		return result;
	}

}

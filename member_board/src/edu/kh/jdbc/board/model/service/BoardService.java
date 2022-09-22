package edu.kh.jdbc.board.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.board.model.dao.BoardDAO;
import edu.kh.jdbc.board.model.dao.CommentDAO;
import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.board.model.vo.Comment;

public class BoardService {
	
	//BoardDAO객체 생성
	private BoardDAO dao = new BoardDAO();
	
	//CommentDAO객체 생성
	//상세 조회 시 댓글 목록 조회 용도로 그 때 sql 쓰기 위해 필요함...
	private CommentDAO cDao = new CommentDAO();

	/** 1. 게시글 목록 조회 서비스
	 * @return boardList
	 * @throws Exception
	 */
	public List<Board> selectAllBoard() throws Exception{
		
		Connection conn = getConnection();
		
		List<Board> boardList = dao.selectAllBoard(conn);
		
		close(conn);
		
		return boardList;
	}

	/**2. 게시글 상세 조회
	 * @param boardNo
	 * @param memberNo
	 * @return board
	 * @throws Exception
	 */
	public Board selectBoard(int boardNo, int memberNo) throws Exception {
		
		Connection conn = getConnection();
		
		//1. 게시글 상세 조회하는 DAO를 호출한다.
		Board board = dao.selectBoard(conn,boardNo);
		// -> boardNo만 필요하니깐 얘만 던져준다
		// -> 조회 결과가 없으면 null,  있으면 null 아님
		
		if(board != null) { //게시글이 존재한다면,
			//2. 댓글 목록 조회하는 DAO 호출
			List<Comment> commentList = cDao.selectCommentList(conn,boardNo);
			//조회된 댓글 목록을 board에 저장한다.
			board.setCommentList(commentList);
			
			
			//3. 조회수 증가시키겠다.
			//단, 로그인한 회원과 게시글 작성자가 다를 경우에만 증가한다.
			if(memberNo != board.getMemberNo()) {
				int result = dao.increaseReadCount(conn,boardNo);
			
				//트랜잭션 제어
				if(result>0) {commit(conn);
				
				//미리 조회된 board의 조회수를 
				//증가된 DB의 조회 수와 동일한 값을 가지도록 동기화
				board.setReadCount(board.getReadCount()+ 1);
				
				}
				else		 rollback(conn);
				
			}
		
		}
		
		
		
		close(conn);
		
		return board;
	}
	
}

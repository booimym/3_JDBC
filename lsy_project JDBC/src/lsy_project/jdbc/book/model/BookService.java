package lsy_project.jdbc.book.model;

import static lsy_project.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import lsy_project.jdbc.book.VO.BookVO;

public class BookService {
	
	private BookDAO dao = new BookDAO();

	/**
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public List<BookVO> selectByBranch(String input) throws Exception{
		
		Connection conn = getConnection();
		
		List<BookVO> movieList = dao.selectByBranch(conn,input);
		
		close(conn);
		
		return movieList;
	}

	public int leftSeat(int input1) throws Exception{
		
		Connection conn = getConnection();
		int result = dao.leftSeat(conn,input1);
		close(conn);
		
		return result;
	}
	
	/** 청불 여부 count한다(숫자반환)
	 * @param input1
	 * @return
	 */
	public int checkRating(int input1) throws Exception {
		
		Connection conn = getConnection();
		int result = dao.checkRating(conn,input1);
		close(conn);
		
		return result;
	}

	

	public int checkSeat(int input1, int seatNum) throws Exception{
		
		Connection conn = getConnection();
		int result = dao.checkSeat(conn,input1,seatNum);
		close(conn);
		
		return result;
	}

	public int booking(BookVO book) throws Exception{
		
		//얘는 insert니까 트랜잭션 해줘야됨...
		Connection conn = getConnection();
		
		int result = dao.booking(conn,book);
		
		if(result>0) commit(conn);
		else		rollback(conn);
		
		close(conn);
		
		return result;
	}

	

	

	
}

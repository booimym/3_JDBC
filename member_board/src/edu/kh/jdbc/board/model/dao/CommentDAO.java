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
import edu.kh.jdbc.board.model.vo.Comment;

public class CommentDAO {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	//기본생성자(객체가 초기화할 때 기능을 수행)
		public CommentDAO() {
			
			try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("comment-query.xml"));
			//inputstream으로 불러온 것을 prop에 적재하겠다(load)
			}catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**댓글 목록 조회 DAO
		 * @param conn
		 * @param boardNo
		 * @return
		 * @throws Exception
		 */
		public List<Comment> selectCommentList(Connection conn, int boardNo)  throws Exception {
			
			List<Comment> commentList = new ArrayList<>();
			
			try {
				
				String sql = prop.getProperty("selectCommentList");
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, boardNo);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
			
				Comment comment = new Comment();
				comment.setCommentNo(rs.getInt(1)); //이렇게 컬럼순서로 얻어오는 것도 가능하다.
				comment.setCommentContent(rs.getString(2));
				comment.setMemberNumber(rs.getInt("MEMBER_NO"));
				comment.setMemberName(rs.getString("MEMBER_NM"));
				comment.setCreateDate(rs.getString("CREATE_DT"));
				
				commentList.add(comment);	
				}
				
				
			}finally {
				close(rs);
				close(pstmt);
			}
			
			
			
			return commentList;
		}
}

package boo.jdbc.dao;

import static boo.jdbc.common.JDBCTemplate.*;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import boo.jdbc.vo.VO;

public class DAO {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	//기본생성자(객체가 초기화할 때 기능을 수행)
	public DAO() {
		
		try {
		prop = new Properties();
		prop.loadFromXML(new FileInputStream("main-query.xml"));
		//inputstream으로 불러온 것을 prop에 적재하겠다(load)
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public VO select(Connection conn, String input) throws Exception {
		
		VO vo = null;
		
		try {
			
			String sql = prop.getProperty("select");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, input);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				vo = new VO();
				vo.setMovieContent(rs.getString("MOVIE_CONTENT"));
				
				
			}
			
		}finally {
			 close(rs);
			 close(stmt);
			
		}
		
		
		return vo;
	}
	
	
	
	
}

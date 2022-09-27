package lsy_project.jdbc.movie.model;

import static lsy_project.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import lsy_project.jdbc.movie.VO.MovieVO;

public class MovieDAO {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public MovieDAO() {
		
		try {
			
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("movie-query.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<MovieVO> selectAllMovie(Connection conn) throws Exception {
		
		
		List<MovieVO> movieList = new ArrayList<>();
		
		try {
			
			String sql = prop.getProperty("selectAllMovie");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				
				MovieVO movieVo = new MovieVO();
				movieVo.setMovieNo(rs.getInt("MOVIE_NO"));
				movieVo.setMovieTitle(rs.getString("MOVIE_TITLE"));
				movieVo.setMovieContent(rs.getString("MOVIE_CONTENT"));
				movieVo.setRunningTime(rs.getString("RUNNING_TIME"));
				movieVo.setMovieYear(rs.getInt("MOVIE_YEAR"));
				movieVo.setRating(rs.getString("RATING"));
				movieVo.setCountry(rs.getString("COUNTRY"));
				
				movieList.add(movieVo);
			}
			
		}finally {
			
			close(stmt);
			close(rs);
			
		}
		
		return movieList;
	}


	public MovieVO selectMovie(Connection conn, String input) throws Exception {
		
		MovieVO movieVo = null;
		
		try {
			
			String sql = prop.getProperty("selectMovie");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, input);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				movieVo = new MovieVO();
				
				//movieVo.setMovieNo(rs.getInt("MOVIE_NO"));
				movieVo.setMovieTitle(rs.getString("MOVIE_TITLE"));
				movieVo.setMovieContent(rs.getString("MOVIE_CONTENT"));
				movieVo.setRunningTime(rs.getString("RUNNING_TIME"));
				movieVo.setMovieYear(rs.getInt("MOVIE_YEAR"));
				movieVo.setRating(rs.getString("RATING"));
				movieVo.setCountry(rs.getString("COUNTRY"));
				
			}
			
		}finally {
			
			close(pstmt);
			close(rs);
		}
		
		return movieVo;
	}

	public List<MovieVO> selectTop3(Connection conn) throws Exception {
	
		List<MovieVO> movieList = new ArrayList<>();
		
		try {
			
			String sql = prop.getProperty("selectTop3");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				MovieVO movie = new MovieVO();
				movie.setMovieTitle(rs.getString(1));
				movie.setCount(rs.getInt(2));
				
				movieList.add(movie);
			}
					
					
			
		}finally {
			
		}
		
		return movieList;
	}

}

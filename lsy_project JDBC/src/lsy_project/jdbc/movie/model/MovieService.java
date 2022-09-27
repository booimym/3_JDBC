package lsy_project.jdbc.movie.model;

import static lsy_project.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import lsy_project.jdbc.movie.VO.MovieVO;

	
public class MovieService {

	private MovieDAO dao = new MovieDAO();
	
	
	public List<MovieVO> selectAllMovie() throws Exception {
		
		Connection conn = getConnection();
		
		List<MovieVO> movieList = dao.selectAllMovie(conn);
		
		close(conn);
		
		return movieList;
	}


	public MovieVO selectMovie(String input) throws Exception {
		
		Connection conn = getConnection();
		
		MovieVO movieVo = dao.selectMovie(conn, input);
		
		close(conn);
		
		return movieVo;
	}


	public List<MovieVO> selectTop3() throws Exception{
		
	
		Connection conn = getConnection();
		
		List<MovieVO> movieList = dao.selectTop3(conn);
		
		close(conn);
		
		return movieList;
	}

}

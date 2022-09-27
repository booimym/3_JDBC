package lsy_project.jdbc.movie.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import lsy_project.jdbc.movie.VO.MovieVO;
import lsy_project.jdbc.movie.model.MovieService;

public class MovieView {

	private Scanner sc = new Scanner(System.in);
	private MovieService service = new MovieService();
	
	/*
	 *1. 현재 상영중인 영화 전체 목록 조회
	 *2. 영화 상세 조회(게시글 상세 조회 처럼...)
	 *3. 인기 TOP3 조회
	 * 
	 * 
	 * 
	 * 
	 **/
	
	public void movieMenu() {
		
		int input = -1 ;
		
		do {
			
			try {
				
				System.out.println("[영화 조회하기]");
				System.out.println("1. 영화 전체 목록 조회");
				System.out.println("2. 영화 상세 조회");
				System.out.println("3. 예매순 TOP3 조회");
				System.out.println("4. 상영예정작 조회");
				System.out.println("0. 메인 메뉴로 돌아기기");
				
				System.out.print("\n [메뉴 선택] :");
				input = sc.nextInt();
				sc.nextLine();
				System.out.println();
				
				switch(input) {
				case 1 : selectAllMovie();break;
				case 2 : selectMovie(); break;
				case 3 : selectTop3(); break;
				case 4 : selectCommingSoon(); break;
				case 0 : System.out.println("메인 메뉴로 돌아갑니다...");break;
				default :System.out.println("메뉴에 작성된 번호만 입력해주세요");
				}
				
			}catch(InputMismatchException e) {
				System.out.println("\n<<입력형식이 올바르지 않습니다.>>");
				sc.nextLine(); //입력버퍼에 남아 있는 잘못된 문자열 제거...
			}
		} while(input != 0);
		
		
		
	}


	private void selectAllMovie() {
		
		try {
			
		List<MovieVO> movieList = service.selectAllMovie();
		
		for(MovieVO movie : movieList) {
			
			System.out.println("-------------------------------------------------------------");
			System.out.printf("제목 : %4s | 개봉연도 : %d | 상영시간 : %2s | 국가 : %1s | 등급 : %10s",
					movie.getMovieTitle(),movie.getMovieYear(),movie.getRunningTime(),movie.getRating());
		}
		
		
		}catch(Exception e) {
			System.out.println("\n<영화 목록 조회 중 예외 발생>\n");
			e.printStackTrace();
		}
	}
	

	private void selectMovie() {
		
		try {
			
			System.out.println("[상세 조회를 하고 싶은 영화를 검색해주세요.]");
			String input = sc.nextLine();
			
			MovieVO movieVo = service.selectMovie(input);
			System.out.println("===================================");
			System.out.println("영화제목 : " + movieVo.getMovieTitle());
			System.out.println("===================================");
			System.out.println("개봉 연도 : " + movieVo.getMovieYear()+"| 국가 : " + movieVo.getCountry());
			System.out.println("===================================");
			System.out.println("상영 시간 : " + movieVo.getRunningTime()+"| 등급 : " + movieVo.getRating());
			System.out.println("===================================");
			
			System.out.println("줄거리");
			System.out.println();
			System.out.println(movieVo.getMovieContent());
			System.out.println();
			
		}catch(Exception e) {
			System.out.println("\n<영화 상세 조회 중 예외 발생>\n");
			e.printStackTrace();
		}
	}
	

	private void selectTop3() {
		
		try {
			
			
			
		}catch(Exception e) {
			System.out.println("\n<영화 TOP3 조회 중 예외 발생>\n");
			e.printStackTrace();
		}
	}
	

	private void selectCommingSoon() {
		
		try {
			
		}catch(Exception e) {
			System.out.println("\n<상영예정작 조회 중 예외 발생>\n");
			e.printStackTrace();
		}
	}
}

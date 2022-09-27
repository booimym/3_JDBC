package lsy_project.jdbc.main.view;


import java.util.InputMismatchException;
import java.util.Scanner;

import lsy_project.jdbc.book.view.BookView;
import lsy_project.jdbc.main.model.MainService;
import lsy_project.jdbc.member.VO.MemberVO;
import lsy_project.jdbc.member.view.MemberView;
import lsy_project.jdbc.movie.view.MovieView;

public class MainView {

	private Scanner sc = new Scanner(System.in);
	private MainService service = new MainService();
	
	private MovieView movieView = new MovieView();
	
	private BookView bookView = new BookView();
	
	private MemberView memberView = new MemberView();
	
	
	
	public void mainMenu() {
		
		int input = -1;
		
		do {
			
			try {
				System.out.println("\n[공포/스릴러 영화관에 오신 것을 환영합니다]\n");
				
				System.out.println("1. 영화 조회");
				System.out.println("2. 예매하기");
				System.out.println("3. 호러 커뮤니티로 고고!");
				System.out.println("4. 마이페이지");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("\n [메뉴를 선택해주세요] :");
				input = sc.nextInt();
				sc.nextLine();
				System.out.println();
				
				switch(input) {
				case 1 : movieView.movieMenu();break;
				case 2 : bookView.bookMenu(); break;
				case 3 :
				case 4 : memberView.login(); break;
				case 0 : System.out.println("프로그램 종료"); break;
				default : System.out.println("메뉴에 작성된 번호만 입력해주세요");
				}
				
			}catch(InputMismatchException e) {
				System.out.println("<입력 형식이 올바르지 않습니다.>");
				sc.nextLine();
			}
			
			
		}while(input != 0);
	}
	
	
}

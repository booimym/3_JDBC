package lsy_project.jdbc.book.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import lsy_project.jdbc.book.model.BookService;
import lsy_project.jdbc.main.view.MainView;

public class BookView {
	
	private Scanner sc = new Scanner(System.in);
	private BookService service = new BookService();
	private MainView mainView = new MainView();		
	
	public void bookMenu() {
		
		int input = -1;
		
		do {
		
			try {
				
				System.out.println("\n[예매 화면]\n");
				System.out.println("1. 로그인 하기");
				System.out.println("2. 예매 하기");
				System.out.println("2. 내 예매 확인");
				System.out.println("3. 내 예매 수정");
				System.out.println("0. 메인 메뉴로 돌아가기");
				
				System.out.print("\n[메뉴를 선택해주세요]");
				input = sc.nextInt();
				
				switch(input) {
				case 1 : mainView.login(); break;
				case 2 : selectRunningMovie(); break;
				case 3 : checkBooking();break;
				case 4 : updateBooking();break;
				case 0 : System.out.println("메인메뉴로 돌아갑니다...");
				}
				
			}catch(InputMismatchException e) {
				System.out.println("\n<<입력형식이 올바르지 않습니다.>>");
				sc.nextLine(); //입력버퍼에 남아 있는 잘못된 문자열 제거...
			}
		
		} while(input != 0);
		
		
	}

	private void selectRunningMovie() {
		
		
		//1. 상영시간표 보기
		//2. 로그인하기 
		try {
			
			
			
			
			
			
		}catch(Exception e) {
			System.out.println("\n<상영시간표 조회 중 예외 발생>\n");
			e.printStackTrace();
		}
	}
	
	private void Booking() {
		
		try {
			
			
			
			
			
			
		}catch(Exception e) {
			System.out.println("\n<예매 중 예외 발생>\n");
			e.printStackTrace();
		}
	
	}
	
	private void checkBooking() {
		
		try {
			
		}catch(Exception e) {
			System.out.println("\n<예매 확인 중 예외 발생>\n");
			e.printStackTrace();
		}
		
	}
	
	
	
	private void updateBooking() {
		
		try {
			
		}catch(Exception e) {
			System.out.println("\n<예매 수정 중 예외 발생>\n");
			e.printStackTrace();
		}
		
	}



}

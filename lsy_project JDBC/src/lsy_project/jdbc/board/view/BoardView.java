package lsy_project.jdbc.board.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import lsy_project.jdbc.board.model.service.BoardService;
import lsy_project.jdbc.board.model.vo.BoardVO;
import lsy_project.jdbc.book.VO.BookVO;
import lsy_project.jdbc.book.model.BookService;
import lsy_project.jdbc.book.view.BookView;
import lsy_project.jdbc.member.view.MemberView;

public class BoardView {
	
	Scanner sc = new Scanner(System.in);
	
	private BoardService bService = new BoardService();
	
	private BookView bookView = new BookView();
	private BookService bookService = new BookService();
	private MemberView memberView = new MemberView();
	
	
	public void BoardMenu() {
		
		int input = -1;
		do {
			try {
				System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++");
				System.out.println("+++++++++[WELCOME TO HORRO COMMUNITY]+++++++++++");
				System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++\n");
				System.out.println();
				System.out.println("1. 한 줄 후기 작성");
				System.out.println("2. 한 줄 후기 조회");
				System.out.println("3. 이벤트 당첨자 확인");
				System.out.println("0. 로그인 메뉴로 이동");
				
				System.out.println();
				System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++");
				System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
				System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++\n");
				
				
				System.out.print("\n[메뉴선택] :");
				
				input =sc.nextInt();
				sc.nextLine();
				switch(input) {
					case 1 : insertBoard(); break; 
					case 2 : selectAllBoard(); break; 
					case 3 : ;break;
					case 4 : ; break;
					//검색
					//제목,내용,제목+내용,작성자
					case 0 :System.out.println("[로그인 메뉴로 돌아갑니다]"); break;
					default : System.out.println("메뉴에 작성된 번호만 입력해주세요");
				}
				
				System.out.println();
				
			}catch(InputMismatchException e) {
				System.out.println("\n입력 형식이 올바르지 않습니다\n");
				sc.nextLine(); // 입력버퍼에 남아있는 잘못된 문자열 제거용...
			}
		}while(input != 0);
		
		
	}


	private void insertBoard() {
		
		try{ 
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("++++++++++++++++++[한줄평 작성]++++++++++++++++++++");
			
			if(MemberView.loginMember == null) {
				memberView.login();
			}
			
			List<BookVO> bookList = bookService.checkBooking();
			
			for (BookVO book : bookList) {
				
				System.out.printf("예매 번호 : %d | 영화 번호 : %d | 영화 제목 : %s | %s | %s | 시간 : %s  | 좌석 : %s ",
						book.getReservNo(),
						book.getAllMoviesNo(),book.getMovieTitle(),book.getTheaterNm(),book.getScreenNm(),
						book.getStartTime(),book.getSeatNm());
						System.out.println();
				}
				
				if (bookList.isEmpty()) {
					System.out.println("\n[예매 내역이 존재하지 않습니다.]\n");
				}else {	
					int num = 0;
					System.out.print("\n[후기를 쓰고 싶은 영화의 제목을 입력하세요.]\n");
					String input = sc.nextLine();
					switch(input) {
					case "샤이닝" : num = 1 ;break;
					case "스크림" : num = 2 ;break;
					case "떼시스" : num = 3 ;break;
					case "링" : num = 4 ;break;
					case "착신아리" : num = 5 ; break;
					case "장화홍련" : num = 6 ;break;
					case "기담" : num = 7 ;break;
					case "불신지옥" : num = 8 ;break;
					case "화차" : num = 9 ;break;
					case "유전" : num = 10 ;break;
					case "곡성" : num = 11 ;break;
					case "겟아웃": num = 12 ; break;
					default : System.out.println("다시 입력해주세요.");
					}
					
					System.out.println("한줄평을 작성해주세요 : ");
					String content = sc.nextLine();
					
					int result = bService.insertBoard(num,MemberView.loginMember.getMemberNo(),content);
					
					
					if (result > 0) {
						System.out.println("[입력 성공!]");
					}
					
						System.out.println("원하시는 영화의 별점을 입력해주세요");
						
						System.out.print("예매 번호 :");
						int AllMovieNum = sc.nextInt();
						System.out.print("별점 :");
						Double score = sc.nextDouble();
						
					int result2 = bService.insertScore();
					
				}
	
		} catch (Exception e) {
			System.out.println("<한줄평 작성 중 예외 발생>");
			e.printStackTrace();
		}
		
	}
	

	private void selectAllBoard() {
		
		System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("++++++++++++++++++[한줄평 조회]++++++++++++++++++++");
		
		try {
		
			List<BoardVO> boardList = bService.selectAllBoard();
			
			if(boardList.isEmpty()) {
				System.out.println("\n[게시글이 존재하지 않습니다]\n");
			}else {
				
				for(BoardVO b : boardList) {
					
					System.out.println("|  영화 제목  |                    한줄평                        | ");
				}
				
			}
			
			
			
			
			
		}catch(Exception e) {
			System.out.println("<한줄평 조회 중 예외 발생>");
			e.printStackTrace();
		}
		
	}
	
	private void updateBoard() {	
		
		
		
	}	
}
	


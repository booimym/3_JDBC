package lsy_project.jdbc.book.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import lsy_project.jdbc.book.VO.BookVO;
import lsy_project.jdbc.book.model.BookService;
import lsy_project.jdbc.main.view.MainView;
import lsy_project.jdbc.member.view.MemberView;
import lsy_project.jdbc.movie.VO.MovieVO;

public class BookView {
	
	private Scanner sc = new Scanner(System.in);
	private BookService service = new BookService();
	private MemberView memberView = new MemberView();	
	
	public void bookMenu() {
		
		int input = -1;
		
		do {
		
			try {
				
				System.out.println("\n[예매 화면]\n");
				System.out.println("1. 로그인 하기");
				System.out.println("2. 예매 하기");
				System.out.println("3. 내 예매 확인");
				System.out.println("4. 내 예매 취소");
				System.out.println("0. 메인 메뉴로 돌아가기");
				
				System.out.print("\n[메뉴를 선택해주세요] :");
				input = sc.nextInt();
				
				switch(input) {
				case 1 : memberView.login(); break;
				case 2 : selectRunningMovie(); break;
				case 3 : checkBooking();break;
				case 4 : cancelBooking();break;
				case 0 : System.out.println("메인메뉴로 돌아갑니다...");
				default : System.out.println("올바른 번호를 입력해주세요.");
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
		int input = -1;
		
		do {
			try {
				
				System.out.println("[상영시간표]");
				System.out.println("1. 영화별로 조회");
				System.out.println("2. 지점별로 조회");
				System.out.println("0. 예매 메뉴로 돌아갑니다");
				System.out.print("\n[메뉴를 선택해주세요] :");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1 : selectByMovie();
				input = 0;break;
				case 2 : selectByBranch();
				input = 0;break;
				case 0 : System.out.println("예매 메뉴로 돌아갑니다...");
				default : System.out.println("올바른 번호를 입력해주세요."); 
				}
				System.out.println("\n========================================================================================\n");
				
				
				
				
				
			}catch(InputMismatchException e) {
				System.out.println("\n<<입력형식이 올바르지 않습니다.>>");
				sc.nextLine(); //입력버퍼에 남아 있는 잘못된 문자열 제거...
			}
		}while(input != 0);
	}
	
	private void selectByBranch() {
		
		int input2 = -1;
		try {
			
				System.out.print("지점 검색 : ");
				String input = sc.next();
				
				List<BookVO> bookList = service.selectByBranch(input);
				
				for(BookVO book : bookList) {
					
					System.out.println("\n----------------------------------------------------------------------------------------\n");
					System.out.printf("영화번호 : %2d | 영화제목 : %10s | 상영관 : %s | 시작시간 : %s ( %d석 남음)",
							book.getAllMoviesNo(),
							book.getMovieTitle(),
							book.getScreenNm(),
							book.getStartTime(),
							book.getCountSeat());
				}
				
				do {
					System.out.print("\n예매하시겠습니까?(Y/N) :");
					String yesno = sc.next().toUpperCase();
					if(yesno.equals("Y")){
						if(MemberView.loginMember == null) {
							memberView.login();
						}
						booking();
						input2 = 0;
					} else if (yesno.equals("N")) {
						System.out.println("예매 메뉴로 돌아갑니다...");
						input2 = 0;
					} else {
						System.out.println("다시 입력해주세요");
					}
				}while(input2 != 0);
			
			}catch(Exception e) {
				System.out.println("\n<영화 목록 조회 중 예외 발생>\n");
				e.printStackTrace();
			}
		
	}

	private void selectByMovie() {
		
		int input2 = -1;
		try {
			
				System.out.print("제목 검색 : ");
				String input = sc.next();
				
				List<BookVO> bookList = service.selectByMovie(input);
				
				for(BookVO book : bookList) {
					
					System.out.println("\n----------------------------------------------------------------------------------------\n");
					System.out.printf("영화번호 : %2d | 지점 : %10s | 상영관 : %s | 시작시간 : %s ( %d석 남음)",
							book.getAllMoviesNo(),
							book.getTheaterNm(),
							book.getScreenNm(),
							book.getStartTime(),
							book.getCountSeat());
				}
				
				do {
					System.out.print("\n예매하시겠습니까?(Y/N) :");
					String yesno = sc.next().toUpperCase();
					if(yesno.equals("Y")){
						if(MemberView.loginMember == null) {
							memberView.login();
						}
						booking();
						input2 = 0;
					} else if (yesno.equals("N")) {
						System.out.println("예매 메뉴로 돌아갑니다...");
						input2 = 0;
					} else {
						System.out.println("다시 입력해주세요");
					}
				}while(input2 != 0);
			
			}catch(Exception e) {
				System.out.println("\n<영화 목록 조회 중 예외 발생>\n");
				e.printStackTrace();
			}
		
	}

	/**
	 * 예매하기
	 */
	private void booking() {
		
		int num = -1;
		
		try {
			
			do {
				System.out.println("\n[예매하기]\n");
				
				System.out.print("영화 번호 선택 :");
				int allMoviesNo = sc.nextInt();
				System.out.println("인원을 선택해주세요(어른/청소년/노약자)");
				System.out.print("1.어른 : ");
				int adultCount = sc.nextInt();
				System.out.print("2.청소년 : ");
				int teenCount = sc.nextInt();
				System.out.print("3.노약자: ");
				int oldCount = sc.nextInt();
				sc.nextLine();
				
				int sum = adultCount + teenCount + oldCount;
				int leftSeat = service.leftSeat(allMoviesNo); //남은 좌석의 count(*)
				int checkRating = service.checkRating(allMoviesNo); //청불의 count(*)
				//int result = 0;//같은 좌석의 count(*)
				if(sum > leftSeat) {
					System.out.println("남은 좌석이 부족하여 예매가 불가능합니다.");
					System.out.println("남은 좌석 :" + leftSeat + "개");
				} else {
						if(teenCount > 0 && checkRating > 0) {//청불의 count(*)
						System.out.println("청소년은 다음 영화의 예매가 불가능합니다.");
						
						} else {
						
							System.out.println("좌석 선택하기(1~8 중에 선택해주세요)");
							
							for(int i = 0; i < sum ; i++) {
								
								int input = -1;
								do {
									System.out.print("좌석 : ");
									int seatNum = sc.nextInt();
									sc.nextLine();
									int result = service.checkSeat(allMoviesNo,seatNum); //같은 좌석의 count(*)
									
									if(result >0) {
										System.out.println("이미 예매된 좌석입니다.");
									}else {
										BookVO book = new BookVO();
										book.setMemberNo(MemberView.loginMember.getMemberNo());
										book.setAllMoviesNo(allMoviesNo);
										book.setSeatNo(seatNum);
										
										int result2 = service.booking(book);
										
										if (result2 > 0) {
										System.out.println("예매되었습니다.");
													
										
										
										} else {
											System.out.println("예매 실패");
										}
										
										input = 0;
									}
								}while(input != 0);
							}	
							
							int input2 = -1;
							do {
								System.out.print("\n예매내역을 확인하시겠습니까?(Y/N) :");
								String yesno = sc.next().toUpperCase();
								sc.nextLine();
								if(yesno.equals("Y")){
									checkBooking();
									input2 = 0;
								} else if (yesno.equals("N")) {
									System.out.println("예매 메뉴로 돌아갑니다...");
									input2 = 0;
								} else {
									System.out.println("다시 입력해주세요");
								}
							}while(input2 != 0);
							
							
							num = 0;
						
					}
				}
				
			} while(num != 0);
			
			
			
		}catch(Exception e) {
			System.out.println("\n<예매 중 예외 발생>\n");
			e.printStackTrace();
		}
	
	}
	
	/**
	 * 예매확인
	 */
	public void  checkBooking() {
		
		try {
			
			System.out.println("[예매 확인]");
			if(MemberView.loginMember == null) {
				memberView.login();
			}
			
			List<BookVO> bookList = service.checkBooking();
			
			for (BookVO book : bookList) {
			
			System.out.printf("예매 번호 : %d | 영화 번호 : %d | 영화 제목 : %s | %s | %s | 시간 : %s  | 좌석 : %s ",
					book.getReservNo(),
					book.getMemberNo(),book.getMovieTitle(),book.getTheaterNm(),book.getScreenNm(),
					book.getStartTime(),book.getSeatNm());
					System.out.println();
			}
			
			if (bookList.isEmpty()) {
				System.out.println("예매 내역이 존재하지 않습니다.");
			}
			
		}catch(Exception e) {
			System.out.println("\n<예매 확인 중 예외 발생>\n");
			e.printStackTrace();
		}
		
		
		
	}
	
	/**
	 * 예매취소
	 */
	private void cancelBooking() {
		
		
		try {
			
			System.out.println("[예매 취소]");
			if(MemberView.loginMember == null) {
				memberView.login();
			}
			
			List<BookVO> bookList = service.checkBooking();
			
			
				for (BookVO book : bookList) {
				
				System.out.printf("예매 번호 : %d | 영화 번호 : %d | 영화 제목 : %s | %s | %s | 시간 : %s  | 좌석 : %s ",
						book.getReservNo(),
						book.getMemberNo(),book.getMovieTitle(),book.getTheaterNm(),book.getScreenNm(),
						book.getStartTime(),book.getSeatNm());
						System.out.println();
				}
				
				if (bookList.isEmpty()) {
					System.out.println("\n[예매 내역이 존재하지 않습니다...]\n");
				}else {
			
					int input = -1;
					
					do {
						System.out.print("\n [취소할 예매 번호 입력] :");
						int input2 = sc.nextInt();
						sc.nextLine();
						int result = service.cancelBooking(input2);
						if (result > 0) {
							System.out.println("\n[예매 취소가 완료되었습니다.]\n");
							System.out.println("\n[계속 취소를 진행하시겠습니까?(Y/N)]\n");
							String yesno = sc.next().toUpperCase();
							sc.nextLine();
							if(yesno.endsWith("N")) {
								input = 0;
							}
							
							
						} else { System.out.println("[취소할 예매 번호를 다시 입력해주세요]");}
						
					
					}while(input != 0);
				}	
		}catch(Exception e) {
			System.out.println("\n<예매 취소 중 예외 발생>\n");
			e.printStackTrace();
		}
			
			
			
			
			
		
	}



}

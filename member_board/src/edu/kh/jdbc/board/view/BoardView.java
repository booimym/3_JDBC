package edu.kh.jdbc.board.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.board.model.service.BoardService;
import edu.kh.jdbc.board.model.service.CommentService;
import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.board.model.vo.Comment;
import edu.kh.jdbc.main.view.MainView;

public class BoardView {

	private Scanner sc = new Scanner(System.in);
	
	//게시판 관련된 기능 호출
	private BoardService bService = new BoardService();
	
	//댓글 관련 기능 호출
	private CommentService cService = new CommentService();
	
	
	/**
	 * 게시판 기능 메뉴 화면
	 */
	public void boardMenu() {
		
		int input = -1;
		
		do {
			try {
				System.out.println("\n*****게시판 기능*****\n");
				System.out.println("1.게시글 목록 조회");
				System.out.println("2.게시글 상세 조회(+댓글기능)");//
				System.out.println("3. 게시글 작성");
				System.out.println("4.게시글 검색");
				System.out.println("0. 로그인 메뉴로 이동");
					//??어케하더라..아 이거 0번누르면 끝나서 mainview로 이동해서 메인메뉴의 반복while다시 돌아간다.
				
				System.out.print("\n메뉴선택 :");
				input =sc.nextInt();
				sc.nextLine(); //입력버퍼 개행문자를 제거한다.
				
				System.out.println();
				
				switch(input) {
					case 1 : selectAllBoard(); break; //게시글 목록 조회
					case 2 : selectBoard(); break; // 게시글 상세 조회
					case 3 : break;
					case 4 : break;
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

	/**
	 * 1. 게시글 목록 조회
	 */
	private void selectAllBoard() {
		
		System.out.println("\n[게시글 목록 조회]\n");
		
		try {
			
			//목록 조회니까 List로 옮겨 담아 가져올 것입니당.
			
			List<Board> boardList = bService.selectAllBoard();
			// DAO에서 
			// new ArrayList<>(); 구문으로 인해
			// 반환되는 조회 결과는 null이 될 수 없다!
			// 보통 List, Board, int, Map이 반환되는데,
			// List는 null인 경우 없대...
			//그래서 List == null은 dead code래
			
			if(boardList.isEmpty()) {//조회 결과가 없을 경우
				System.out.println("게시글이 존재하지 않습니다.");
				
			} else {
				
				//리스트에서 board로 하나씩 옮겨닮겠다...
				for(Board b : boardList) {
					
				//3 | 샘플제목3[댓글수]|이름|작성일|조회수
				System.out.println(" --------------------------------------------------------");	
					
					
					System.out.printf("글 번호 :%d|제목 :%s[댓글수: %d]|작성자: %s|작성일 : %s|조회수 : %d\n",
							b.getBoardNo(),
							b.getBoardTitle(),
							b.getCommentCount(),
							b.getMemberName(),
							b.getCreateDate(),
							b.getReadCount());
				}
			}
			
			
			
		}catch(Exception e) {
			System.out.println("\n[게시글 목록 조회 중 예외 발생]\n");
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 2. 게시글 상세 조회
	 */
	private void selectBoard() {
		
		System.out.println("\n[게시글 상세 조회]\n");
		
		try {
			System.out.print("게시글 번호 입력 :");
			int boardNo = sc.nextInt();
			sc.nextLine();
			
			//게시글 상세 조회 서비스 호출 후 결과 반환 받기
			Board board = bService.selectBoard(boardNo,MainView.loginMember.getMemberNo());
												//게시글 번호, 로그인한 회원의 회원번호
												//내가 쓴 글을 조회할 때는 조회수가 안 올라감.
												//그 기능을 수행하기 위해 [로그인 한 회원번호]가 필요
			
			

	         if (board != null) {
	            System.out.println(" --------------------------------------------------------");
	            System.out.printf("글번호 : %d | 제목 : %s\n", board.getBoardNo(), board.getBoardTitle());
	            System.out.printf("작성자ID : %s | 작성일 : %s | 조회수 : %d\n", 
	                  board.getMemberName(), board.getCreateDate().toString(), board.getReadCount());
	            System.out.println(" --------------------------------------------------------");
	            System.out.println(board.getBoardContent());
	            System.out.println(" --------------------------------------------------------");

	         
	            // 댓글 목록
	            if(!board.getCommentList().isEmpty()) {
	               for(Comment c : board.getCommentList()) {
	                  System.out.printf("댓글번호: %d   작성자: %s  작성일: %s\n%s\n",
	                        c.getCommentNo(), c.getMemberName(), c.getCreateDate(), c.getCommentContent());
	                  System.out.println(" --------------------------------------------------------");
	               }
	            }
	            
	            // 댓글 등록, 수정, 삭제
	            // 게시글 수정/삭제 메뉴
	            subBoardMenu(board);
	            
	            
	         } else {
	            System.out.println("해당 번호의 게시글이 존재하지 않습니다.");
	         }
				
			
			
			
			
		} catch(Exception e) {
			System.out.println("\n[게시글 상세 조회 중 예외 발생]\n");
			e.printStackTrace();
		}
		
	}


	/**게시글 상세 조회 시 출력되는 서브 메뉴
	 * @param board(상세 조회된 게시글 + 작성자번호+댓글 목록)
	 */
	private void subBoardMenu(Board board) {
		
		//댓글 등록
		//댓글 수정
		//댓글 삭제

		
		try {
			System.out.println("1) 댓글 등록");
			System.out.println("2) 댓글 수정");
			System.out.println("3) 댓글 삭제");
			
			//로그인한 회원과 게시글 작성자가 같은 경우에만 출력되는 메뉴
													//board에 membernumber 넣어왓엇음 ㅋ
			if(MainView.loginMember.getMemberNo() == board.getMemberNo()) {
				
				System.out.println("4) 게시글 수정");
				System.out.println("5) 게시글 삭제");
				
				
			}
			
			
			System.out.println("0) 게시판 메뉴로 돌아가기");
			
			System.out.print("서브 메뉴 선택 :");
			int input = sc.nextInt();
			sc.nextLine();
			
			//로그인한 회원의 회원 번호
			int memberNo = MainView.loginMember.getMemberNo();
			
			switch(input) {
			case 1 : insertComment(board.getBoardNo(),memberNo) ;break; //몇번게시글인지,누가 댓글남기는 건지 알아야 됨.(로그인한 회원의 번호를 알아야 함...)
			case 2 : updateComment(board.getCommentList(),memberNo) ; break;
			
			case 3 : deleteComment(board.getCommentList(),memberNo); break;
			case 0 : System.out.println("\n[게시판 메뉴로 돌아갑니다..]\n");break;
			
			
			case 4 : case 5 : //4 또는 5 입력 시
				
				//4 또는 5를 입력한 회원이 게시글 작성자인 경우
				if(MainView.loginMember.getMemberNo() == board.getMemberNo()) {
					
					//단일if가 if-else보다 유지보수가 편해서 많이 쓴대. if문만 싹 지우먄 되니까
					if(input == 4 ) {
						//게시글 수정 호출
						updateBoard(board.getBoardNo()); //이제 회원번호는 인증되었으니까 회원번호 필요 없구, 몇번 게시글인지만 필요해
					}
					if(input == 5 ) {
						//게시글 삭체 호출
						deleteBoard(board.getBoardNo());
						//input = 0; //재귀호출 때문에 deleteBoard메소드 실행한 이후에 오류남...그걸 방지해주는 코드!
						//아니면,,, 방법2
					}
					
					break; //SWITCH 종료 -> DEFAULT 안나옴.
				}
			
				
			
			
			
			default:System.out.println("\n[메뉴에 작성된 번호만 입력해주세요]\n");
			
			}
			
			//댓글 등록, 수정, 삭제 선택 시
			//각각의 서비스 메서드 종료 후 다시 서브메뉴 메서드 호출
			if(input > 0 && input <5) {
				
				try {
		               board = bService.selectBoard(board.getBoardNo(), MainView.loginMember.getMemberNo());
		   
		               System.out.println(" --------------------------------------------------------");
		               System.out.printf("글번호 : %d | 제목 : %s\n", board.getBoardNo(), board.getBoardTitle());
		               System.out.printf("작성자ID : %s | 작성일 : %s | 조회수 : %d\n", 
		                     board.getMemberName(), board.getCreateDate().toString(), board.getReadCount());
		               System.out.println(" --------------------------------------------------------");
		               System.out.println(board.getBoardContent());
		               System.out.println(" --------------------------------------------------------");
		   
		            
		               // 댓글 목록
		               if(!board.getCommentList().isEmpty()) {
		                  for(Comment c : board.getCommentList()) {
		                     System.out.printf("댓글번호: %d   작성자: %s  작성일: %s\n%s\n",
		                           c.getCommentNo(), c.getMemberName(), c.getCreateDate(), c.getCommentContent());
		                     System.out.println(" --------------------------------------------------------");
		                  }
		               }
		            }catch (Exception e) {
		               e.printStackTrace();
		            }
				
				
				subBoardMenu(board); //자기가 자기를 무한하게 부르게 됨 ..... 재귀 호출
									 //효율이 좋대. ㅋ 
									 //무한루프에 안빠지는 방법으로 input>0 설정해놔서, 0누르면 종료되게 해놓음.
			}
			
			
			
		}catch(InputMismatchException e) {
				System.out.println("\n[잘못 입력되었습니다.]\n");
				sc.nextLine();//잘못된 문자열 제거
		}
	}

							
	/** (1) 댓글 등록
	 * @param bNo
	 * @param mNo
	 */
	private void insertComment(int bNo, int mNo) {
								//변수의 이름은 아무 효력이 없고, 어떤 타입이 넘어오는지, 순서가 어떻게 되는지만 중요함.
								//변수 이름 다르게 넘어와도 됨.	
		
		try {
			System.out.println("\n[댓글 등록]\n");
			
			//[내용 입력 메소드]를 이용해서 내용 입력 받기
			//재사용되는 것을 고려해서 inputConent()메소드에 분리함.
			String content = inputContent();
			
			
			
			//DB INSERT시 필요한 값을 하나ㅏ의 COMMENT객체에 저장한다.
			Comment comment = new Comment();
			comment.setCommentContent(content);
			comment.setBoardNo(bNo);
			comment.setMemberNumber(mNo);
			
			//댓글 삽입 서비스 호출 후 결과 반환 받기
			int result = cService.insertComment(comment);
			
			if(result > 0) {
				System.out.println("\n[댓글 등록 성공]\n");
			} else {
				System.out.println("\n[댓글 등록 실패]\n");
			}
			
			
		}catch(Exception e) {
			System.out.println("\n[댓글 등록 중 예외 발생]\n");
			e.printStackTrace();
		}
	}
	
	/**
	 * 내용 입력
	 * return content(내용이 반환되게 만들거임)
	 */
	private String inputContent() {
		
		
		String content = ""; //참조하는 게 o , 내용 x //빈 문자열임.
		
		String input = null; //참조하는 객체가 없음.
							//왜 ""이 아니고 null을 썼나면
							//
		
		System.out.println("입력 종료 시 ($exit) 입력");
		
		while(true) {
			
			input = sc.nextLine();
			
			if(input.equals("$exit")) {
				break;
			} //if가 실행되면 바로 종료됨.
			
			//입력된 내용을 content에 누적시키는데, 한번 누적시킬때마다 줄바꿈시킴.
			content += input + "\n";
		}
			return content;
		
	}
	
	/** (2) 댓글 수정
	 * @param commentList
	 * @param memberNo
	 */
	private void updateComment(List<Comment> commentList, int memberNo) {
								//댓글수정하는데 왜 commentList필요함? ㅠ
		
		
		//댓글 번호를 입력 받아
		//1) 해당 댓글이 commentList에 있는지 검사를 할 거임.
		//2) 있다면 해당 댓글이 로그인한 회원이 작성한 글인지 검사할 것임.
		
		try {
			
			System.out.println("\n[댓글 수정]\n");
			System.out.println("수정할 댓글 번호를 입력하세요 : ");
			int commentNo = sc.nextInt();
			sc.nextLine(); //개행문자 제거하기.
			
			
			boolean flag = true;
			
			for( Comment c : commentList ) {
				
				if(c.getCommentNo() == commentNo ) { //댓글 번호 일치
					flag = false; //일치하는 거니까, 바꿔주기
					
					if(c.getMemberNumber() == memberNo) { //회원 번호 일치
						
						//수정할 댓글 내용 입력받기
						String content = inputContent();
						
						
						//댓글 수정 서비스 호출
						int result = cService.updateComment(commentNo,content);
						
						if(result > 0 ) {
							System.out.println("\n[댓글 수정 성공]\n");
						}else {
							System.out.println("\n[댓글 수정 실패...]\n");
						}
						
					} else {
						System.out.println("\n[자신의 댓글만 수정할 수 있습니다]\n");
					}
					
					break; //더이상의 검사 불필요
				}
				
			} // for문 end
			
			if(flag) {
				System.out.println("\n[번호가 일치하는 댓글이 없습니다]\n");
			}
			
			
			
		}catch(Exception e) {
			System.out.println("\n<<댓글 수정 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	/** (3) 댓글 삭제
	 * @param commentList
	 * @param memberNo
	 */
	private void deleteComment(List<Comment> commentList, int memberNo) {
		
try {
			
			System.out.println("\n[댓글 삭제]\n");
			System.out.println("삭제할 댓글 번호를 입력하세요 : ");
			int commentNo = sc.nextInt();
			sc.nextLine(); //개행문자 제거하기.
			
			
			boolean flag = true;
			
			for( Comment c : commentList ) {
				
				if(c.getCommentNo() == commentNo ) { //댓글 번호 일치
					flag = false; //일치하는 거니까, 바꿔주기
					
					if(c.getMemberNumber() == memberNo) { //회원 번호 일치
						
						//정말 삭제할거야? y/n
						//y인 경우 댓글 삭제 서비스 호출
						
						System.out.print("정말 삭제하시겠습니까? (y/n) :");
						char ch = sc.next().toUpperCase().charAt(0);
						
						if(ch == 'Y') {
							int result = cService.deleteComment(commentNo);
							
							if(result > 0 ) {
								System.out.println("\n[댓글 삭제 성공]\n");
							}else {
								System.out.println("\n[댓글 삭제 실패...]\n");
							}
							
							
						} else {
							
							System.out.println("\n[취소되었습니다.]\n");
						}
						
						
					} else {
						System.out.println("\n[자신의 댓글만 삭제할 수 있습니다]\n");
					}
					
					break; //더이상의 검사 불필요
				}
				
			} // for문 end
			
			if(flag) {
				System.out.println("\n[번호가 일치하는 댓글이 없습니다]\n");
			}
			
			
			
		}catch(Exception e) {
			System.out.println("\n<<댓글 수정 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}

	/**(4)게시글 수정
	 * @param boardNo
	 */
	private void updateBoard(int boardNo) {
		
		try {
			
			System.out.println("\n[게시글 수정]\n");
			
			System.out.println("수정할 제목 :");
			String boardTitle = sc.nextLine();
		
			System.out.println("수정할 내용 :");
			String boardContent = inputContent();
			
			// 수정된 제목,내용 + 게시글 번호를 한번에 전달하기 위한 Board 객체 생성
			Board board = new Board();
			board.setBoardNo(boardNo);
			board.setBoardContent(boardContent);
			board.setBoardTitle(boardTitle);
			
			// 수정 서비스 호출
			int result = bService.updateBoard(board);
			
			if (result > 0) {
				System.out.println("\n[게시글 수정 성공]\n");
			} else {
				System.out.println("\n[게시글 수정 실패...]\n");
			}
			
		}catch(Exception e) {
			System.out.println("\n[게시글 수정 중 예외 발생]\n");
			e.printStackTrace();
		}
		
	}

	/**(5)게시글 삭제
	 * @param boardNo
	 */
	private void deleteBoard(int boardNo) {
		
		try {
			
			System.out.println("\n[게시글 삭제]\n");
			
			System.out.println("삭제할 제목 :");
			String boardTitle = sc.nextLine();
		
			System.out.println("정말 삭제하시겠습니까 (Y/N)");
			char ch = sc.next().toLowerCase().charAt(0);
			//소문자로 변환하는 코드 _ 왜?
			
			if(ch == 'y') {
				
				// 수정 서비스 호출
				int result = bService.deleteBoard(boardNo);
				
				if (result > 0) {
					System.out.println("\n[게시글 삭제 성공]\n");
				} else {
					System.out.println("\n[게시글 삭제 실패...]\n");
				}
				
			}else {
				System.out.println("\n[삭제 취소]\n");
			}
		
			
			
			
		}catch(Exception e) {
			System.out.println("\n[게시글 수정 중 예외 발생]\n");
			e.printStackTrace();
		}
		
	}
	
}

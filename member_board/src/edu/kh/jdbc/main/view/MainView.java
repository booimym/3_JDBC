package edu.kh.jdbc.main.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.board.view.BoardView;
import edu.kh.jdbc.main.model.service.MainService;
import edu.kh.jdbc.member.view.MemberView;
import edu.kh.jdbc.member.vo.Member;

//메인 화면
public class MainView {
	//필드//
	private Scanner sc = new Scanner(System.in);
	private MainService service = new MainService();
	
	//게시판 기능 메뉴 객체를 생성한다.
	private BoardView boardView = new BoardView();
	
	//로그인 된 회원 정보를 저장한 객체를 참조하는 참조변수
	public static Member loginMember = null;
	// -> 로그인x == null
	// -> 로그인o != null
	
	//회원 기능을 담당하는 메뉴 객체를 생성하기.
	private MemberView memberview = new MemberView();
	
	
	/**
	 * 메인 메뉴 출력 메소드
	 */
	public void mainMenu() {
		
		int input = -1 ; //(초기값을 -1로 하기 - 
		
		do {
			
			try {
				
				if(loginMember == null) {
					System.out.println("\n *****회원제 게시판 프로그램 *****\n");
					System.out.println("1. 로그인");
					System.out.println("2. 회원가입");
					System.out.println("0. 프로그램 종료");
				
					System.out.print("\n메뉴선택 :");
					input = sc.nextInt();
					sc.nextLine(); //입력버퍼에 남아있는 개헹문자 제거하기.
					System.out.println();
					
					switch(input) {
					case 1  : login(); break;
					case 2  : signUp(); break;
					case 0  : System.out.println("프로그램 종료");break;
					default : System.out.println("메뉴에 작성된 번호만 입력해주세요");
					}
				} else { //로그인 되었을 때의 화면
					
					System.out.println("***로그인 메뉴***");
					System.out.println("1. 회원 기능");
					//1. 내정보조회
					//2. 회원목록조회(아이디,이름,성별)
					//3. 내정보수정(이름,성별)
					//4. 비번변경(현재비번,새비번,새비번확인)
					//5. 회원탈퇴
					System.out.println("2. 게시판 기능");
					 /* 게시판 기능 (Board View, Service, DAO, board-query.xml)
					    * 
					    * 1. 게시글 목록 조회(작성일 내림차순)
					    *      (게시글 번호, 제목, 작성자명, 작성일, 조회수, 댓글 수)
					    * 
					    * 2. 게시글 상세 조회(게시글 번호 입력 받음)
					    *    (게시글 번호, 제목, 내용, 작성자명, 작성일, 조회수, 
					    *     댓글 목록(작성일 오름차순 )
					    *     
					    *     2-1. 댓글 작성
					    *     2-2. 댓글 수정 (자신의 댓글만)
					    *     2-3. 댓글 삭제 (자신의 댓글만)
					    *     
					    *     //자신이 작성한 글일 때만 메뉴 노출
					    *     2-4. 게시글 수정 
					    *     2-5. 게시글 삭제 
					    *     
					    *    
					    * 
					    * 3. 게시글 작성(제목, 내용 INSERT) 
					    *    -> 작성 성공 시 상세 조회 수행
					    * 
					    * 4. 게시글 검색(제목, 내용, 제목+내용, 작성자)
					    * 
					    * */
					System.out.println("0. 로그 아웃"); //로그아웃하면 메인메뉴로 간다.
					System.out.println("99. 프로그램 종료");
					
					System.out.print("\n메뉴선택 :");
					input = sc.nextInt();
					sc.nextLine(); //입력버퍼에 남아있는 개헹문자 제거하기.
					System.out.println();
					
					switch(input) {
					case 1  : memberview.memberMenu(loginMember);break;
							//여기 로그인된 객체를 회원기능으로 전달해줘야 되니까 매개변수로 전달해준다.
					case 2  : boardView.boardMenu(); break;
							//loginMember는 static으로 바꿨으니까
							//회원 정보가 필요한 경우 static으로 사용할 예정임.
					case 0  :  //<참고> 로그인 : loginMember가 참조하는 객체가 존재하면 됨.
						//		로그아웃 : loginMember가 참조하는 객체를 없애면 됨. null로 만들기.
						loginMember = null;
						System.out.println("\n로그아웃되었습니다\n");
						input = -1; //바깥에 있는 do-while문이 종료되지 않도록 0이 아닌 값으로 변경해주기
						break;
					case 99 : System.out.println("프로그램 종료");
							//방법1) input = 0; -> do-while 조건식을 false로 만듦.
							//방법2) System.exit(0); -> JVM을 종료시킨다.매개변수는 0, 아니면 오류를 의미.
							System.exit(0);
							break;
					default : System.out.println("메뉴에 작성된 번호만 입력해주세요");
					}
					
				}
				
			}catch(InputMismatchException e) {
				System.out.println("\n<<입력형식이 올바르지 않습니다.>>");
				sc.nextLine(); //입력버퍼에 남아 있는 잘못된 문자열 제거...
			}
			
			
			
			
			
		}while(input != 0); // 0이 아닐 때 true -> 반복
		
		
		
		
	}
	
	/** 1. 로그인 화면
	 * 
	 */
	private void login() {
		
		
		
		System.out.print("아이디 입력 :");
		String memberId = sc.next();
		System.out.print("비밀번호 : ");
		String memberPw = sc.next();
		
		try {
			//로그인 서비스 호출 후 조회 결과를 loginMember에 저장
			loginMember = service.login(memberId,memberPw);
			// 담고 서비스에 던지기
			//멤버 객체가 반환된다...
			
			if(loginMember != null) { //로그인 성공 시
				
				System.out.println(loginMember.getMemberName() + "님 환영합니다! ");
				//객체에서 이름을 찾아와서 인사해주기
				
			} else { //로그인 실패 시
				System.out.println("[님은 로긴이 안되셨네염...아이디/비번 확인해주세여]");
			}
		
		} catch(Exception e) {
			System.out.println("\n<로그인 중 에러 발생>\n");
		}
		
	}
	
	
	

	/**
	 *  2. 회원 가입 화면
	 */
	//이 안에서만 실행되는 메소드니까 private해도 무방하다!
	private void signUp(){
		System.out.println("[회원 가입]");
		String memberId = null;
		String memberPw1 = null;
		String memberPw2 = null;
		String memberName = null;
		String memberGender = null;
		
		
		try {
			while(true) {
				
				// 1. 아이디를 입력 받아 중복이 아닐 때까지 반복할 것임.
				System.out.print("아이디 입력 :");
				memberId = sc.next();
				
				// 입력 받은 아이디를 매개변수로 전달하여
				// 중복여부를 검사하는 서비스(idDupCheck) 호출 후 결과(1아니면 0으로 나옴)를 반환 받기!
				
				int result = service.idDupCheck(memberId);
				
				//중복이 아닌 경우
				if(result == 0) {
					System.out.println("[사용 가능한 아이디입니다.]");
					break;
				} else { //중복인 경우
					System.out.println("[이미 사용중인 아이디입니다.]");
				}
			}	
				// 2. 비밀번호 입력
				//비밀번호/비밀번호 확인이 일치할 때까지 무한 반복
			while(true) {
					
					System.out.print("비밀번호 : ");
					memberPw1 = sc.next();
					
					System.out.print("비밀번호 확인 :");
					memberPw2 = sc.next();
					
					//객체(String도 객체임)끼리 비교는 비교연산자 못 쓰고
					//equals 써야 됨.
					if(memberPw1.equals(memberPw2) ) { //일치할 경우
						System.out.println("[일치합니다.]");
						break; //일치할 때만 반복 끝.
					} else { //일치하지 않을 경우
						System.out.println("[비밀번호가 일치하지 않습니다.다시 입력해주세요]");
						
					}
					System.out.println();
			}	
			// 3. 이름 입력
			System.out.print("이름 입력 :");
			memberName = sc.next();
					
			// 4. 성별
			// M 또는 F가 입력될 때까지 무한 반복
			while(true) {
						
				System.out.print("성별 입력 :");
				memberGender = sc.next().toUpperCase(); //입력받자마자 대문자로 변경
						
				if (memberGender.equals("M")||memberGender.equals("F") ) {
					break; //일치할 때만 반복 끝.
				} else {
					System.out.println("[M또는 F로 입력해주세요]");
				}
				System.out.println();
			}
			
			// --아이디,비밀번호,이름,성별 입력 완료 --
			// --> 하나의 VO에 담아서 서비스 호출 후 결과 반환 받기
			Member member = new Member(memberId, memberPw2, memberName, memberGender);
			
			int result = service.signUp(member); 
			//이제 service가 dao에서 결과값 가지고 오고 일로 또 가지고 오겠지.
			
			//서비스 처리 결과에 따른 출력 화면 제어
			if(result > 0) {
				System.out.println("<회원가입 성공>");
			} else {
				System.out.println("<회원가입 실패>");
			}
			System.out.println();
				
				
			
		} catch(Exception e) {
			System.out.println("\n<회원 가입 중 예외 발생>");
			e.printStackTrace();
		}
	}
	
	
}

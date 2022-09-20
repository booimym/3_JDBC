package edu.kh.jdbc.member.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.main.view.MainView;
import edu.kh.jdbc.member.model.service.MemberService;
import edu.kh.jdbc.member.vo.Member;

/** 회원 메뉴 입/출력 클래스
 * @author user1
 *
 */
public class MemberView {

	//필드(공통적으로 쓰일 객체들 여기다가 생성)
	//필드에 스캐너 넣으면 여러번 안 써도 됨.
	private Scanner sc = new Scanner(System.in);
	
	//회원 관련 서비스를 제공하는 객체 생성
	private MemberService service = new MemberService();
	
	//로그인 회원 정보 저장용 변수
	private Member loginMember = null;
	
	//메뉴 번호를 입력받기 위한 변수
	private int input = -1; 
	// 왜 input을 필드로 올리나면, 5.회원탈퇴 할 때, 
	//input변수에다가 
	
	
	/**
	 *  회원 기능 메뉴
	 @param(parameter:전달인자) loginMember(로그인된 회원 정보)
	 */
	public void memberMenu(Member loginMember) {
		
		//전달 받은 로그인 회원 정보를 필드에 저장
		this.loginMember =loginMember;
		
		
		
		do {
			try {
				//1. 내정보조회
				//2. 회원목록조회(아이디,이름,성별)
				//3. 내정보수정(이름,성별)
				//4. 비번변경(현재비번,새비번,새비번확인)
				//5. 회원탈퇴
				System.out.println("\n****회원기능***\n");
				System.out.println("1. 내정보조회"); //완료
				System.out.println("2. 회원목록조회(아이디,이름,성별)"); 
				System.out.println("3. 내정보수정(이름,성별)"); //완료
				System.out.println("4. 비번변경(현재비번,새비번,새비번확인)");  //완료
				System.out.println("5. 회원탈퇴"); //delete구문인가?
				System.out.println("0. 메인메뉴로 이동");
				System.out.print("\n메뉴선택 :");
				
				input = sc.nextInt();
				sc.nextLine();//입력버퍼에 남아있는 개행문자 제거
				
				System.out.println();//줄바꿈
				
				switch(input) {
				case 1  : showMine(); break;
				case 2  : selectAll(); break;
				case 3  : updateMine(); break;
				case 4 : updatePw(); break;
				case 5 :  secession(); break;
				case 0 : System.out.println("[메인 메뉴로 이동합니다.]"); break;
				default : System.out.println("메뉴에 작성된 번호만 입력해주세요");
				}
				
				
			}catch(InputMismatchException e) {
				System.out.println("\n<<입력형식이 올바르지 않습니다.>>");
			}
		} while(input != 0);		
	}

	

	/**1. 내 정보 저장
	 * 
	 */
	private void showMine() {
		
		//System.out.println(loginMember.toString());
		//난 이렇게 했는뎁 ㅋ
		
		System.out.println("\n[내 정보 조회]\n");
		System.out.println("회원 번호 :" + loginMember.getMemberNo());
		System.out.println("아이디 :" + loginMember.getMemberId());
		System.out.println("이름 :" + loginMember.getMemberName());
		
			
		System.out.println("성별 : " + (loginMember.getMemberGender() == "M"? "남" : "여"));	
		System.out.println("가입일:"+ loginMember.getEnrollDate());
	}
	
	/**
	 * 2. 회원 목록 조회
	 */
	private void selectAll() {
		System.out.println("\n[회원 목록 조회]\n");
		
		// DB에서 회원 목록 조회(탈퇴 회원 미포함)
		// +가입일 내림차순
		
		//2. 회원 목록 조회(아이디,이름,성별)
		//조건1. 탈퇴 회원 미포함
		//조건2. 가입일 내림차순
		//[참고] 날짜보다 숫자 비교를 하는 게 더 빠름 -ENROLL_DATE보다 MEMBER_NO로 하는 게 더 빠름
		//(나중에 가입한 회원의 번호가 더 크니까 MEMBER_NO로 해도 상관 없음...)
		
		try {
			
			// 회원 목록 조회 서비스 호출 후 결과를 반환 받기
			//Member객체로만 타입을 제한하는 리스트 만들기...
			List<Member> memberList = service.selectAll();
			
			//memberList는 절대로 null이 아님.
			//안에 저장된 값이 있다,없다(비어있다.비어있지않다)로 구분하는 거임...
			if(memberList.isEmpty()) {
				System.out.println("조회된 결과가 없습니다.");
			}else {
				for(Member member : memberList) {
					
					System.out.printf("아이디: %10s / 이름 : %5s / 성별 : %3s\n", 
					member.getMemberId(),member.getMemberName(),member.getMemberGender());
				}
			
			}
			//조회 결과가 있으면 모두 출력
			//없으면 조회 결과가 없습니다 ....
			
			
		} catch(Exception e) {
			System.out.println("\n<<회원 목록 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
		
		
	}
	
	/**
	 * 3. 회원 정보 수정
	 */
	private void updateMine() {
		
		String memberName = null;
		String memberGender = null;
		
		//System.out.println(loginMember.getMemberId());
		
		try {
			
			System.out.println("\n[회원 정보 수정]\n");
			
			
			//1. 로그인한 애의
			
			//2. 이름 수정
			System.out.print("변경할 이름 입력 :");
			memberName = sc.next();
			
			//3. 성별 수정(제대로 쓸 때까지 무한루프)
			while(true) {
				
				System.out.print("변경할 성별 입력 :");
				memberGender = sc.next().toUpperCase(); //입력받자마자 대문자로 변경
						
				if (memberGender.equals("M")||memberGender.equals("F") ) {
					break; //일치할 때만 반복 끝.
				} else {
					System.out.println("[M또는 F로 입력해주세요]");
				}
				System.out.println();
			}
			
			//서비스로 전달할 Member객체를 생성하기.
//[수업 ver.]			
//			Member member = new Member();
//			member.setMemberNo(loginMember.getMemberNo());
//			member.setMemberName(memberName);
//			member.setMemberGender(memberGender);
//			
//			int result = service.updateMine(member);
			
			
//내가 한 거			
			int result = service.updateMine(loginMember.getMemberId(),memberName,memberGender);
			
			if (result > 0) {
				//loginMember에 저장된 값과
				//db에 수정된 값을 동기화 하는 작업이 필요하다.
				loginMember.setMemberName(memberName);
				loginMember.setMemberGender(memberGender);
				
				System.out.println("\\n[회원정보가 수정되었습니다.]\\n");
				
			} else {
				System.out.println("\n[수정 실패]\n");
			}
		
		
		} catch(Exception e) {
		System.out.println("[회원 정보 수정 중 예외 발생]");
		e.printStackTrace();
		} 
	}
	
	/**
	 * 4. 비밀번호 변경
	 */
//	private void updatePw() {
//		
//		String memberPw1 = null;
//		String memberPw2 = null;
//		System.out.println("\n[비밀번호 변경]\n");
//		try {
//		
//			while(true) {
//				
//				System.out.print("비밀번호 : ");
//				memberPw1 = sc.next();
//				
//				System.out.print("비밀번호 확인 :");
//				memberPw2 = sc.next();
//				
//				//객체(String도 객체임)끼리 비교는 비교연산자 못 쓰고
//				//equals 써야 됨.
//				if(memberPw1.equals(memberPw2) ) { //일치할 경우
//					System.out.println("[일치합니다.]");
//					break; //일치할 때만 반복 끝.
//				} else { //일치하지 않을 경우
//					System.out.println("[비밀번호가 일치하지 않습니다.다시 입력해주세요]");
//					
//				}
//				System.out.println();
//			}
//			
//			int result = service.updatePw(loginMember.getMemberId(),memberPw1);
//	
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}

	private void updatePw() {
		System.out.println("\n[비밀번호 변경]\n");
		try {
			System.out.print("현재 비밀번호 : ");
			String currentPw = sc.next();
			
			String newPw1 = null;
			String newPw2 = null;
			
			while(true) {
				
				System.out.print("새 비밀번호 : ");
				newPw1 = sc.next();
				
				System.out.print("새 비밀번호 확인 :");
				newPw2 = sc.next();
				
				//객체(String도 객체임)끼리 비교는 비교연산자 못 쓰고
				//equals 써야 됨.
				if(newPw1.equals(newPw2) ) { //일치할 경우
					System.out.println("[일치합니다.]");
					break; //일치할 때만 반복 끝.
				} else { //일치하지 않을 경우
					System.out.println("[새 비밀번호가 일치하지 않습니다.다시 입력해주세요]");
					
				}
				
			}//WHILE문 END	
			
		//서비스 호출 후 결과 반환 받기
			int result = service.updatePw(currentPw, newPw1,loginMember.getMemberId());
			
			if(result>0) {
				System.out.println("비번 변경 성공");
			} else {
				System.out.println("\n[현재 비밀번호가 일치하지 않습니다.]\n");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 5. 회원 탈퇴
	 */
	private void secession() {
		
		System.out.println("\n[회원 탈퇴]\n");
		
		try {
			//1. 현재 비번 입력 받음
			System.out.print("비밀번호 입력 :");
			String memberPw = sc.next();
			
			while(true) {
				//2. 정말로 할거냐 Y/N
				System.out.println("정말 탈퇴하시겠습니까? (Y/N)");
				char ch = sc.next().toUpperCase().charAt(0);
				
				
				//3. Y면 탈퇴하기
				if (ch == 'Y') {
					// 서비스 호출 후 결과 반환받기
					int result = service.secession(memberPw,loginMember.getMemberNo());
					
					if(result > 0) {
						System.out.println("\n[탈퇴되었습니다!]\n");
						
						//1. 메인 메뉴로 돌아가기
							input = 0; // 메인 메뉴로 이동...
							//(input변수를 필드로 이동시켜서 했다)
						//2. 로그 아웃 시키기...
							//어떻게 loginMember를 null로 바꿀까???
							//정적 메모리(공유 메모리) 영역이라고 하며 필드 또는 메서드에 작성되면 
							//프로그램 실행 시 static 메모리 영역에 [클래스명.필드명] 형식으로 할당됨.

							//같은 타입의 여러 객체가 공유하거나 프로그램 전체적으로 공유할 때 사용.
							MainView.loginMember = null; //로그아웃
						
					}else {
						System.out.println("\n[비밀번호가 일치하지 않습니다.]\n");
					}
					
					break; //while문 종료시키기...
					
				} else if (ch == 'N') {
					
					System.out.println("\n[취소되었습니다!]\n");
					break; //가장 가까이 있는 while문 종료
					
				} else {
					System.out.println("\n[Y 또는 N만 입력해주세요.]\n");
				}
				
				
					
			}
			
			
			
			
		} catch(Exception e) {
			System.out.println("\n<회원 탈퇴 중 예외 발생>\n");
		}
			
		
		
	}
	
	
	

	
}

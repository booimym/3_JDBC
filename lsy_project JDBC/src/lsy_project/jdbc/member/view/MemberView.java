package lsy_project.jdbc.member.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import lsy_project.jdbc.main.model.MainService;
import lsy_project.jdbc.member.VO.MemberVO;
import lsy_project.jdbc.member.model.MemberService;

	public class MemberView {

		Scanner sc = new Scanner(System.in);
		public static MemberVO loginMember = null;
		private MainService service = new MainService();
		private MemberService MeService = new MemberService();
		
//		public void memberMenu() {
//			
//			int input = -1;
//			
//			do {
//				
//				try {
//					
//					System.out.println("\n[마이페이지]\n");
//					System.out.println("1. 회원가입");
//					System.out.println("2. 로그인");
//					System.out.println("3. 회원정보수정");
//					
//					
//				}catch (InputMismatchException e) {
//					System.out.println("<입력 형식이 올바르지 않습니다.>");
//					sc.nextLine();
//				}
//				
//				
//			}while(input != 0);
//			
//			
//			
//		}
	
		public void login() {
		
			
			
			
			System.out.println("[로그인을 진행해주세요]");
			System.out.print("아이디 : ");
			String memberId = sc.next();
			System.out.print("비밀번호 : ");
			String memberPw = sc.next();
			
			
			try {
				
				loginMember = service.login(memberId,memberPw);
				
				if(loginMember == null) {
					System.out.println("회원 가입이 되어있지 않은 회원입니다.");
					System.out.println("회원 가입 화면으로 이동");
					signUp();
				}
				int input = -1;
				do { 
					System.out.println("\n[마이페이지]\n");
					System.out.println("1. 회원정보 조회");
					System.out.println("2. 회원정보 수정");
					System.out.println("0. 메인 메뉴로 돌아가기");
					System.out.print("\n [메뉴를 선택해주세요] : ");
					input = sc.nextInt();
					sc.nextLine();
					
					switch(input) {
					case 1 : selectMember(); break;
					case 2 : updateMember(); break;
					case 0 : System.out.println("메인메뉴로 돌아갑니다...");break;
					default : System.out.println("메뉴에 작성된 번호만 입력해주세요");
					}
					
					
				}while(input != 0);
				
			} catch(Exception e) {
				System.out.println("\n<로그인 중 에러 발생>\n");
				e.printStackTrace();
			}
		}
		private void selectMember() {
			
			System.out.println("\n[내 정보 조회]\n");
			System.out.println("회원 번호 : " + loginMember.getMemberNo());
			System.out.println("아이디 : " + loginMember.getMemberId());
			System.out.println("아름 : " + loginMember.getMemberNm());
			System.out.println("가입일 : " + loginMember.getEnrollDate());
			
		}
		private void updateMember() {
			
			int input = -1;
			
			do {
				
				try {
					
					System.out.println("\n[회원 정보 수정]\n");
					System.out.println("1. 이름 수정");
					System.out.println("2. 비밀번호 변경");
					System.out.println("0. 마이페이지로 돌아가기");
					
					System.out.print("\n [메뉴를 선택해주세요] :");
					input = sc.nextInt();
					sc.nextLine();
					
					switch(input) {
					case 1 : updateName();break;
					case 2 : updatePassWord();break;
					case 0 : System.out.println("수정을 취소하고 마이페이지로 돌아갑니다...");break;
					default : System.out.println("메뉴에 작성된 번호만 입력해주세요");
					}
					
				}catch(InputMismatchException e) {
					System.out.println("<입력 형식이 올바르지 않습니다.>");
					sc.nextLine();
				}
			}while(input != 0);
		}

	

		private void updatePassWord() {
			
			
			String memberPw = null;
			int input = -1;
			do {
				System.out.print("변경할 비밀번호 입력 : ");
				memberPw = sc.next();
				sc.nextLine();
				
				
				if(memberPw.length() <8) {
					System.out.println("비밀번호를 8자리 이상으로 입력해주세요.");
				
				} else {
					
					if(memberPw.equals(loginMember.getMemberPw())) {
						System.out.println("기존 비밀번호와 다른 비밀번호를 입력해주세요.");
					} else {
						input = 0;
					}
					
				}
			}while(input != 0);
			
			
			
			try {
				int result = service.updatePassword(loginMember.getMemberNo(),memberPw);
			
				if(result > 0) {
					System.out.println("[비번 수정 성공]");
				} else {
					System.out.println("[비번 수정 실패]");
				}
			
			}catch(Exception e) {
				System.out.println("\n<비번 수정 중 에러 발생>\n");
				e.printStackTrace();
			}
		}
		private void updateName() {
			
			System.out.print("변경할 이름 입력 : ");
			String memberName = sc.next();
			
			try {
				int result = service.updateName(loginMember.getMemberNo(),memberName);
			
				if(result > 0) {
					System.out.println("[이름 수정 성공]");
				} else {
					System.out.println("[이름 수정 실패]");
				}
			
			}catch(Exception e) {
				System.out.println("\n<이름 수정 중 에러 발생>\n");
				e.printStackTrace();
			}
		}
		public void signUp() {
		
			System.out.println("[회원 가입]");
			String memberId = null;
			String memberPw1 = null;
			String memberPw2 = null;
			String memberNm = null;
			
			try {
					//1. id 중복 체크
				
					while(true) {
					
						System.out.print("아이디 입력 :");
						memberId = sc.next();
						
						int result = service.idDupCheck(memberId);
						
						if(result == 0) {
							System.out.println("[사용 가능한 아이디입니다.]");
							break; //반복 끝
						} else { 
							System.out.println("[이미 사용중인 아이디입니다.]");
						}
					}
					
					//2.비번 일치 체크
					
					while(true) {
						
						do {
							System.out.print("비밀번호 : ");
							memberPw1 = sc.next();
							sc.nextLine();
							
							if(memberPw1.length() <8) {
								System.out.println("비밀번호를 8자리 이상으로 입력해주세요.");
							}
							
						}while(memberPw1.length() < 8);
						
						System.out.print("비밀번호 확인 :");
						memberPw2 = sc.next();
						
						
						if(memberPw1.equals(memberPw2) ) { 
							System.out.println("[비밀번호 일치]");
							break; //일치할 때만 반복 끝
						} else { 
							System.out.println("[비밀번호가 일치하지 않습니다.다시 입력해주세요]");
							
						}
						System.out.println();
					}	
					
					
					//3. 이름
					System.out.print("이름 입력 :");
					memberNm = sc.next();
					
					MemberVO memberVo = new MemberVO();
					memberVo.setMemberId(memberId);
					memberVo.setMemberPw(memberPw2);
					memberVo.setMemberNm(memberNm);
				
					int result = service.signUp(memberVo);
				
				
				
					
			}catch(Exception e) {
				System.out.println("\n<회원가입 중 예외 발생>\n");
				e.printStackTrace();
			}
		
		}
	
	
	}

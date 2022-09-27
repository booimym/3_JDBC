package lsy_project.jdbc.member.view;

import java.util.Scanner;

import lsy_project.jdbc.main.model.MainService;
import lsy_project.jdbc.member.VO.MemberVO;

public class MemberView {

		Scanner sc = new Scanner(System.in);
		public static MemberVO loginMember = null;
		private MainService service = new MainService();
	
public void login() {
		
		System.out.println("[로그인 화면]");
		System.out.print("아이디 : ");
		String memberId = sc.next();
		System.out.println("비밀번호 : ");
		String memberPw = sc.next();
		
		
		try {
			
			loginMember = service.login(memberId,memberPw);
			
		} catch(Exception e) {
			System.out.println("\n<로그인 중 에러 발생>\n");
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
					
					System.out.print("비밀번호 : ");
					memberPw1 = sc.next();
					
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

package edu.kh.jdbc.run;

import java.sql.SQLException;
import java.util.Scanner;

import edu.kh.jdbc.model.service.TestService;
import edu.kh.jdbc.model.vo.TestVO;

public class Run3 {

	public static void main(String[] args) {
		
		TestService service = new TestService();
		
		//번호,제목,내용을 입력받아
		//번호가 일치하는 행의 제목,내용을 수정하기
		
		//수정 성공 시 -> 수정되었습니다.
		//수정 실패 시 -> 일치하는 번호가 없습니다
		//예외 발생 시 -> 수정 중 예외가 발생했습니다.
		
		Scanner sc = new Scanner(System.in);
		System.out.print("번호입력 : ");
		int input1 = sc.nextInt();
		System.out.print("제목입력 : ");
		String input2 = sc.next();
		System.out.print("내용입력 : ");
		String input3 = sc.next();
		
		TestVO vo3 = new TestVO(input1,input2,input3);
		
		
			try {
				int result = service.update(vo3);
				
				if (result > 0) {
					System.out.println("insert 성공");
				} else {
					System.out.println("insert 실패");
					// insert 실패하는 경우
					// 서브쿼리를 이용한 insert 수행 시...
				}
				
			} catch (SQLException e) {
				System.out.println("SQL 수행 중 오류 발생");
				e.printStackTrace();
			}
		
			
		
		

	}

}

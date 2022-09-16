package edu.kh.jdbc.run;

import edu.kh.jdbc.model.service.TestService;
import edu.kh.jdbc.model.vo.TestVO;

public class Run2 {

	public static void main(String[] args) {
		
		// TB_TEST 테이블에 한 번에 3행 삽입
		
		TestService service = new TestService();
		
		TestVO vo1 = new TestVO(11,"제목11","내용11 입니다.");
		TestVO vo2 = new TestVO(12,"제목12","내용12 입니다.");
		TestVO vo3 = new TestVO(13,"제목13","내용13 입니다.");
		
		try {
			int result = service.insert(vo1,vo2,vo3);
			
			if(result > 0) {
				
				 System.out.println("삽입 성공!");
				 
			} else {
				System.out.println("실패,,,");
			}
			
		}catch(Exception e) {
			// service, dao 수행 중 발생한 예외를 throw한 걸 처리할 것임.
			System.out.println("SQL 수행 중 오류 발생");
			e.printStackTrace();
		}
		
	}
	
	
}

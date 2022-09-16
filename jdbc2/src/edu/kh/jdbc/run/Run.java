package edu.kh.jdbc.run;

import java.sql.SQLException;

import edu.kh.jdbc.model.service.TestService;
import edu.kh.jdbc.model.vo.TestVO;

public class Run {

	public static void main(String[] args) {
		
		TestService service = new TestService();
		
		// TB_TEST 테이블에 INSERT 수행을 해보겠어요!
		TestVO vo1 = new TestVO(1,"내용1","내용2");
		
		// TB_TEST 테이블에 INSERT를 수행하는 서비스 메서드를 호출 후
		// 결과를 반환 받기
		
		try {
			int result = service.insert(vo1);
			
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
		//서비스야 데이터베이스에 insert좀 해줘...라는 메소드 호출하고, 해줬는지 여부에 대한 결과를 반환...
		//insert를 성공하면 성공한 행 개수가 나오잖아.. 그 결과가 숫자니까 result변수의 데이터타입을 int로 써놓은거야

	}

}

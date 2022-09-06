package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.model.vo.Emp;

public class JDBCExample3 {

	public static void main(String[] args) {
		
		
		Scanner sc = new Scanner(System.in);
		
		//부서명을 입력받아 같은 부서에 있는 사원의
		//사원명, 부서명, 급여를 조회해보자!
		
		Connection conn = null;
		//접속방법
		Statement stmt = null;
		//Connection을 통해 SQL문을 전달하고, ResultSet을 반환하는 애
		
		ResultSet rs = null;
		
		try {
			
			System.out.print("부서명 입력 :");
			String input = sc.nextLine();
			
			// JDBC참조변수에 알맞은 객체를 대입하겠다!
			
			// 일단, Connection 만들어야 됨
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//우리가 라이브러리를 추가했음. 거기에 있는 클래스임.
			
			 String type = "jdbc:oracle:thin:@";
	         String ip = "localhost";
	         String port = ":1521"; 
	         String sid = ":XE"; 
	         String user = "kh_lsy";
	         String pw = "kh1234";
	         
	         conn = DriverManager.getConnection(type + ip + port + sid, user, pw);
			//jdbc:oracle:thin:@localhost:1521:XE == url 
	         
	         //Statement가 실행할 SQL을 작성한다.
	         //각 줄의 맨 앞에 띄어쓰기 작성해주기
	         String sql = "SELECT EMP_NAME, "
	         		+ " NVL(DEPT_TITLE, '부서없음') AS DEPT_TITLE , SALARY\r\n"
	         		+ " FROM EMPLOYEE\r\n"
	         		+ " LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)\r\n"
	         		+ " WHERE NVL(DEPT_TITLE, '부서없음') LIKE '%"+input+"%'";
	         
	         //중요!
	         //Java에서 작성되는 SQL에
	         //문자열(String) 변수를 추가(이어쓰기)할 경우
	         //''(DB 문자열 리터럴)이 누락되지 않도록 신경써줘야 한다.
	         
	         //만약 ''미작성 시 
	         //부적합한 식별자라고 나오는데,
	         //그 이유는 String값이 컬럼명으로 인식되기 때문...
	         
	         //Statement 객체를 생성한다. 
	         
	         stmt = conn.createStatement();
	         
	         //Statement 객체를 이용해서
	         //SQL(SELECT)을 DB에 전달하여 실행한 후
	         //ResultSet을 반환받아서 rs 변수에 대입
	         
	         rs = stmt.executeQuery(sql); 
	         
	         // 조회 결과(rs)를 list에 옮겨 담기.
	         
	         //객체 하나에 표를 다 담고 싶어! vo만들어서 타입 제한하기.
	         List<Emp> list = new ArrayList<>();
	         
	         while(rs.next()) { //다음행으로 이동해서 해당 행에 데이터가 있으면 true
	        	 
	        	 //현재 행에 존재하는 컬럼 값 얻어오기
	        	 String empName = rs.getString("EMP_NAME");
	        	 String depTitle = rs.getString("DEPT_TITLE");
	        	 int salary = rs.getInt("SALARY");
	        	 
	        	 //Emp 객체를 생성하여, 컬럼값 담기
	        	 
	        	 Emp emp = new Emp(empName, depTitle,salary);
	        	 
	        	 //생성된 Emp객체를 list에 담기
	        	 list.add(emp); 
	        	 
	        	 
	         }
	         
	         //만약 List에 추가된 Emp 객체가 없다면 "조회 결과가 없습니다"
	         //있다면 순차적으로 출력
	         
	         if(list.isEmpty()) { //List가 비어있을 경우
	        	 //isEmpty() : 비어있으면 true임
	        	 System.out.println("조회 결과가 없습니다.");
	        	 
	         }else {
	        	 
	        	 //향상된 for문
	        	 
	        	 for(Emp emp : list ) {
	        		 System.out.println(emp); //자동으로 컴파일러가 toString을 써줌
	        	 }
	         }
			
		} catch(Exception e){
			// 예외 최상위 부모인 Exception을 catch문에 작성하여
			// 발생하는 모든 예외를 처리
			e.printStackTrace();
		}finally {
			
			
			try {
				
				if(rs !=null ) rs.close();
				if(stmt !=null ) stmt.close();
				if(conn !=null ) conn.close();
				
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			
		}
	}

}

package edu.kh.emp.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.kh.emp.model.vo.Employee;

// DAO(Data Access Object, 데이터 접근 객체)
// -> 데이터 베이스에 접근(연결)하는 객체
// -> JDBC 코드 작성
public class EmployeeDAO {

	//JDBC 객체 참조 변수로 필드 선언(class 내부에서 공통으로 사용할 수 있다)
	
	private Connection conn; // Heap영역은 변수가 비어있을 수 없어서
							 // JVM이 지정한 기본값으로 초기화가 됨.
							 // 참조형의 초기값은 null임.
							 // (별도 초기화가 필요 없다는 뜻임)
							 // <지역변수>와의 차이점...
	private Statement stmt;
	private ResultSet rs;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user ="kh_lsy";
	private String pw = "kh1234";
	
	
	
	
	
	public List<Employee> selectAll() {
		// 1. 결과 저장용 변수를 선언하기.
		//    싹 다 담을 수 있는 변수!
		
		List<Employee> empList = new ArrayList<>();		
		
		try {
			
			//2. JDBC 참조변수에 객체를 대입한다.
			// -> conn,stat,rs에 객체를 대입한다는 뜻
			
			Class.forName(driver); //오라클 jdbc 드라이버 객체 메모리 로드
			
			conn = DriverManager.getConnection(url,user,pw);
			//오라클 jdbc 드라이버 객체를 이용해서 DB접속방법을 생성한다.
			
			
			String sql = "SELECT EMP_ID , EMP_NAME ,EMP_NO ,EMAIL ,PHONE ,\r\n"
					+ "	   NVL(DEPT_TITLE,'부서없음') DEPT_TITLE,\r\n"
					+ "	   JOB_NAME,SALARY \r\n"
					+ "FROM EMPLOYEE\r\n"
					+ "LEFT JOIN DEPARTMENT ON (DEPT_ID = DEPT_CODE)\r\n"
					+ "JOIN JOB USING (JOB_CODE)";
			
			// Statement객체 생성
			
			stmt = conn.createStatement();
			
			//SQL을 수행 후 결과(ResultSet)를 반환받는다.
			rs = stmt.executeQuery(sql);
			
			//3. 조회 결과를 얻어와 한 행씩 접근하여
			// Employee 객체 생성 후 컬럼 값 옮겨 담기
			// -> List 추가
			while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID");
				// EMP_Id 컬럼은 문자열 컬럼이지만
				// 저장용 값들이 모두 숫자 형태
				// -> DB에서 자동으로 형변환을 진행해서 얻어온다. 그래서 int타입으로 가져올 수 있다.
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId,empName,empNo,email,phone,departmentTitle,jobName,salary);
				
				empList.add(emp); //List에 담기
				
				
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
			//4. JDBC 객체 자원 반환
			
			try {
				
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			
		}
		
		return empList;
	}
	
	
	
}

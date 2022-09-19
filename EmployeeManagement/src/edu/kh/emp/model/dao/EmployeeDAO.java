package edu.kh.emp.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	//java에서 제공해주는 인터페이스
	//Statement의 자식으로, 향상된 기능을 제공한다.
	// -> ?기호 (placeholder/위치 홀더)를 이용해서 
	//    SQL에 작성되어지는 리터럴을 동적으로 제어함.
	//빈자리로 만들어놓고 그때그때 추가하겠다라는 거지
	
	// SQL ? 기호에 추가되는 값은 
	// 숫자인 경우 '' 없이 대입
	// 문자열인 경우 ''가 자동으로 추가되어 대입이 된다.
	private PreparedStatement pstmt;
	
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user ="kh_lsy";
	private String pw = "kh1234";
	
	
	//메소드마다 새로 연결할 거면, 처음부터 연결하면 안됨?
	//아무 것도 안 하고 접속만 해도 DB에 연결하면 부담...
	//원하는 요청 올 때마다, 접속하고, 끝나면 나가야 됨. 어쩔 수 없이 (접속 - 나감)이 반복된다
	
	
	
	public List<Employee> selectAll() {
		// 1. 결과 저장용 변수를 선언하기.
		//    싹 다 담을 수 있는 변수!
		
		List<Employee> empList = new ArrayList<>();		
		
		try {
			
			//2. JDBC 참조변수에 객체를 대입한다.
			// -> conn,stat,rs에 객체를 대입한다는 뜻
			
			Class.forName(driver); //오라클 jdbc 드라이버 클래스를 객체로 만들어서 메모리 로드
									//이름 부르면 객체로 만들어짐ㅋ
			
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
				//불러온 값들을 객체에 담기.
				
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





	/**3.사번이 일치하는 사원 정보 조회 DAO
	 * @param empId
	 * @return
	 */
	public Employee selectEmpId(int empId) {
		
		//결과 저장용 변수를 선언
		Employee emp = null;
		//왜 null이라고 씀?
		//만약 조회 결과가 있으면 Employee객체를 생성해서 emp에 대입하고
		//만약 조회 결과가 없으면 emp에 아무 것도 대입하지 않으려고
		//조회 결과 ox여부를 잘 알려줄 수 있음.
		
		try {
			
			Class.forName(driver); //드라이버 객체를 만들어서 메모리에 떠다니게 한다.
			conn = DriverManager.getConnection(url,user,pw);
			
			//SQL 작성
			
			//Statement생성
		 
			//SQL 수행 후 결과(ResultSet) 반환받기
			
			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE, \r\n"
					+ "      NVL(DEPT_TITLE, '부서없음') DEPT_TITLE, \r\n"
					+ "      JOB_NAME, SALARY\r\n"
					+ "FROM EMPLOYEE\r\n"
					+ "LEFT JOIN DEPARTMENT ON (DEPT_ID = DEPT_CODE)\r\n"
					+ "JOIN JOB USING(JOB_CODE)\r\n"
					+ "WHERE EMP_ID = " + empId ;
									//view에서 입력받은 사번
			
			stmt = conn.createStatement(); //statement는 connection내부를 왔다갔다 하니까
										 //connection 이용해서 만듦.
			
			
			//** 조회 결과가 최대 1행인 경우
			//   불필요한 조건 검사를 줄이기 위해 while문이 아니라, if문 사용을 권장한다 **
			
			if(rs.next()) { //조회 결과가 있을 경우에 해당함. - 다음 행이 있는 경우니까
				
				//int empId = rs.getInt("EMP_ID");
				//파라미터와 같은 값이므로 얻어올 필요 없음.
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				//조회 결과를 담은 Employee객체를 생성한 후, 
				//emp에 담는다.
				emp =new Employee(empId,empName,empNo,email,phone,
								departmentTitle,jobName,salary);
				
				
				
			}
			
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
			try {
				
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		return emp;
	}



	/** 주민등록번호가 일치하는 사원 정보 조회 DAO
	 * @param empNo
	 * @return
	 */
	public Employee selectEmpNo(String empNo) {
		
		//결과 저장용 변수를 선언한다.
		Employee emp = null;
		
		try {
			
			//connection 생성
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			
			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE, \r\n"
					+ "      NVL(DEPT_TITLE, '부서없음') DEPT_TITLE, \r\n"
					+ "      JOB_NAME, SALARY\r\n"
					+ "FROM EMPLOYEE\r\n"
					+ "LEFT JOIN DEPARTMENT ON (DEPT_ID = DEPT_CODE)\r\n"
					+ "JOIN JOB USING(JOB_CODE)\r\n"
					+ "WHERE EMP_NO = ?";
								//placeholder(위치홀더)
			
			//Statement 객체 사용시 순서
			//SQL 작성 -> Statement 생성 -> SQL 수행 후 결과 반환 순서였음.
			
			//PreparedStatement객체 사용시 순서
			//(위치홀더를 메꿔서 SQL 만드는) 준비를 하는 statement니까
			//SQL 작성 -> PreparedStatement객체 생성 (?가 포함된 SQL을 매개변수로 사용)
			//-> ?에 알맞은 값 대입
			//-> SQL 수행 후 결과 반환
			
			pstmt = conn.prepareStatement(sql);
			
			// ?에 알맞은 값을 대입하기
			pstmt.setString(1,empNo);
			
			
			rs = pstmt.executeQuery();
			// preparedStatement는 
			// 객체 생성시 이미 SQL이 담겨져 있는 상태이므로
			// SQL 수행 시 매개변수로 전달할 필요가 없다.
			
			// -> 실수로 SQL을 매개변수에 추가하면
			// ?에 작성 추가했던 값이 모두 사라져 수행 시 오류가 발생한다.
			
			if(rs.next()) { //조회 결과가 있을 경우에 해당함. - 다음 행이 있는 경우니까
				
				int empId = rs.getInt("EMP_ID");
				
				String empName = rs.getString("EMP_NAME");
//				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				//조회 결과를 담은 Employee객체를 생성한 후, 
				//emp에 담는다.
				emp =new Employee(empId,empName,empNo,email,phone,
								departmentTitle,jobName,salary);
				
				
				
			}
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			
			try {
				
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return emp;
	}


	/**사원 정보 추가 DAO
	 * @param emp
	 * @return (INSERT 성공한 행의 개수가 반환됨)
	 */
	public int insertEmployee(Employee emp) {
		
		// 결과 저장용 변수를 선언한다.
		int result = 0;
		
			try {
				
				//커넥션 생성
				Class.forName(driver); //"oracle.jdbc.driver.OracleDriver"이라고 또 써도 ㅇㅋ
				conn = DriverManager.getConnection(url,"kh_lsy","kh1234");
				
				//** DML을 수행할 예정 **
				//- 트랜잭션에 DML 구문이 임시 저장될 거임
				// -> 정상적인 DML인지를 판별해서 개발자가 직접 commit,rollback을 수행
				
				// 하지만, connection객체 생성 시
				// AutoCommit이 활성화되어있는 상태이기 때문에
				// 이를 해제하는 코드를 추가해야 함!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				
				conn.setAutoCommit(false); //해제하는 코드
				//true면 AutoCommit 활성화
				//false면 AutoCommit 비활성화
				// -> AutoCommit 비활성화를 해도, 
				//    conn.close() 구문이 수행되면 자동으로 Commit이 수행된다.
				//    따라서 close()수행 전에 커밋할지 롤백할지 트랜잭션 제어코드를 작성해야 한다.
				
				String sql 
				= "INSERT INTO EMPLOYEE VALUES(?,?,?,?,?,?,?,?,?,?,?,SYSDATE,NULL,DEFAULT)";
				//퇴사 여부 컬럼의 DEFAULT == 'N'이다.
				
				//preparedStatement 객체 생성하기.( 얘는 매개변수에 SQL이 필요하다)
				pstmt= conn.prepareStatement(sql);
				
				//?(placeholder)에 알맞은 값 대입하기
				//emp에 값을 담아 왔으니까.. emp객체에서 EmpId가져오기
				pstmt.setInt(1, emp.getEmpId());
				pstmt.setString(2, emp.getEmpName());
				pstmt.setString(3, emp.getEmpNo());
				pstmt.setString(4, emp.getEmail());
				pstmt.setString(5, emp.getPhone());
				pstmt.setString(6, emp.getDeptCode());
				pstmt.setString(7, emp.getJobCode());
				pstmt.setString(8, emp.getSalLevel());
				pstmt.setInt(9, emp.getSalary());
				pstmt.setDouble(10, emp.getBonus());
				pstmt.setInt(11, emp.getManagerId());
				
				result = pstmt.executeUpdate();				
				//executeUpdate() : DML(INSERT,UPDATE,DELETE) 수행 후 결과 행 개수를 반환
				//executeQuery() : SELECT 수행 후 ResultSet 반환
				
				
				//**트랜잭션 제어 처리**
				//=> DML 성공 여부에 따라서 commit,rollback 제어
				
				if (result>0)  conn.commit(); //DML 성공 시 commit
				else		   conn.rollback(); //DML 실패
			
				
				
				
				
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				
				try {
					
					
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
					
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				
			}
		
		
		
		
		// 결과 반환
		return result;
		
		
		
	}





	public int updateEmployee(Employee emp) {
		
		int result = 0 ; //결과 저장용 변수
		
		try {
			
			Class.forName(driver);
			conn= DriverManager.getConnection(url,user,pw);
			
			conn.setAutoCommit(false);
			
			String sql ="UPDATE EMPLOYEE SET "
					+ "EMAIL = ? , PHONE = ?, SALARY = ? "
					+ "WHERE EMP_ID = ?";
			
			// preparedStatement 생성
			pstmt = conn.prepareStatement(sql);
			
			// ?에 알맞은 값을 세팅
			pstmt.setString(1, emp.getEmail());
			pstmt.setString(2, emp.getPhone());
			pstmt.setInt(3, emp.getSalary());
			pstmt.setInt(4, emp.getEmpId());
			
			result = pstmt.executeUpdate(); //SQL이 실행되어서 반영된 행의 개수를 반환
			
			//트랜잭션 제어 처리
			 
			if(result == 0) conn.rollback();
			else			conn.commit();
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
			
			try {
				
				
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		return result;
	}

	
	public int deleteEmployee(int empId) {
		int result = 0;
		
		try {
			
			Class.forName(driver);
			conn= DriverManager.getConnection(url,user,pw);
			
			conn.setAutoCommit(false);
			
			String sql ="DELETE FROM EMPLOYEE "
					+ "WHERE EMP_ID = ?";
			
			// preparedStatement 생성
			pstmt = conn.prepareStatement(sql);
			
			// ?에 알맞은 값을 세팅
			
			pstmt.setInt(1, empId); //전달받은 empId 넣기
			
			result = pstmt.executeUpdate(); //SQL이 실행되어서 반영된 행의 개수를 반환
			
			//트랜잭션 제어 처리
			 
			if(result == 0) conn.rollback();
			else			conn.commit();
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
			
			try {
				
				
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return result;
	}





	/** 6. 입력 받은 부서와 일치하는 모든 사원 정보 조회
	 * @return
	 */
	public List<Employee> selectDeptEmp(String deptTitle) {
		
		
		
		List<Employee> list = new ArrayList<>();
		
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,pw);
			
			String sql = "SELECT EMP_ID,EMP_NAME,EMP_NO,EMAIL,NVL(PHONE,'폰번X') \"폰번호\","
					+ " NVL(DEPT_TITLE,'부서X') \"부서\",JOB_CODE,SALARY\r\n"
					+ "FROM EMPLOYEE\r\n"
					+ "LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)\r\n"
					+ "WHERE DEPT_TITLE = '" + deptTitle + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("폰번호");
				String jobName = rs.getString("JOB_CODE");
				int salary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId,empName,empNo,email,phone,
											deptTitle,jobName,salary);
				
				list.add(emp);
				
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
			try {
				
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		return list;
	}





	/**7. 입력 받은 급여 이상을 받는 모든 사원 정보 조회
	 * @param salary
	 * @return
	 */
	public List<Employee> selectSalaryEmp(int salary) {
		
		List<Employee> list = new ArrayList<>();
		
try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,pw);
			
			String sql = "SELECT EMP_ID,EMP_NAME,EMP_NO,EMAIL,NVL(PHONE,'폰번X') \"폰번호\","
					+ " NVL(DEPT_TITLE,'부서X') \"부서\",JOB_CODE,SALARY\r\n"
					+ "FROM EMPLOYEE\r\n"
					+ "LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)\r\n"
					+ "WHERE SALARY >=" + salary ;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("폰번호");
				String deptTitle = rs.getString("부서");
				String jobName = rs.getString("JOB_CODE");
				
				int salary1 = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId,empName,empNo,email,phone,
											deptTitle,jobName,salary1);
				
				list.add(emp);
				
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
			try {
				
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return list;
	}





	public Map<String, Integer> selectDeptTotalSalary() {
		
		Map<String, Integer> map = new HashMap<String,Integer>();
		
			try {
			
				Class.forName(driver);
				conn = DriverManager.getConnection(url,user,pw);
				
				String sql = "SELECT DEPT_CODE, SUM(SALARY)\r\n"
						+ "FROM EMPLOYEE\r\n"
						+ "GROUP BY DEPT_CODE\r\n"
						+ "HAVING DEPT_CODE IS NOT NULL"  ;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					
					String deptCode = rs.getString("DEPT_CODE");
					int sumSalary = rs.getInt("SUM(SALARY)");
					
					map.put(deptCode, sumSalary);
					
				}
			
			
			
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
			
				try {
					
					if(rs != null) rs.close();
					if(stmt != null) stmt.close();
					if(conn != null) conn.close();
					
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				
			}
		
		
		
		
		return map;
	}





	public Map<String, Double> selectJobAvgSalary() {
		
		Map<String,Double> map = new HashMap<String,Double>();	
		
			try {
			
				Class.forName(driver);
				conn = DriverManager.getConnection(url,user,pw);
				
				String sql = "SELECT JOB_NAME, ROUND(AVG(SALARY),1)\r\n"
						+ "FROM EMPLOYEE\r\n"
						+ "JOIN JOB USING (JOB_CODE)\r\n"
						+ "GROUP BY JOB_NAME"  ;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					
					String jobName = rs.getString("JOB_NAME");
					double avgSalary = rs.getDouble("ROUND(AVG(SALARY),1)");
					
					map.put(jobName,avgSalary);
					
				}
			
			
			
			}catch(Exception e) {
			e.printStackTrace();
			}finally {
			
				try {
					
					if(rs != null) rs.close();
					if(stmt != null) stmt.close();
					if(conn != null) conn.close();
					
					
				}catch(Exception e) {
					e.printStackTrace();
				}
			
			}
		
		
		
		
		return map;
	}
	
	
	
}

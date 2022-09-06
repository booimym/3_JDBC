package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.model.vo.Employee;

public class JDBCExample4 {
	
	// 직급명, 급여를 입력 받아
	// 해당 직급에서 입력 받는 급여보다 많이 받는 사원의
	// 이름, 직급명, 급여,연봉을 조회하여 출력하겠다.
	
	//단, 조회결과가 없으면 "조회 결과 없음"이라고 출력하고,
	//조회결과가 있으면 아래와 같이 출력
	//선동일/대표/8000000/96000000
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String type = "jdbc:oracle:thin:@";
			String ip = "localhost";
			String port = ":1521";
			String sid = ":XE";
			String user = "kh_lsy";
			String pw = "kh1234";
			
			conn = DriverManager.getConnection(type+ip+port+sid, user, pw);
			
			//그냥 type+ip+port+sid 을 합쳐서 url이라고 쓰는 게 더 편하다.
			
			//String url = "jdbc:oracle:thin:@localhost:1521:XE";
			//conn = DriverManager.getConnection(url, user, pw);
			
			
			
			
			System.out.print("직급명 :");
			String input1 = sc.nextLine();
			System.out.print("급여 :");
			int input2 = sc.nextInt();
			
			String sql = "SELECT EMP_NAME, JOB_NAME, SALARY , SALARY*12 AS 연봉"
			+ " FROM EMPLOYEE"
			+ " JOIN JOB USING (JOB_CODE)"
			+ " WHERE SALARY >= "+input2+" AND JOB_NAME = '"+input1+"'";
			
			stmt = conn.createStatement();
			
			rs =stmt.executeQuery(sql);
			
			List<Employee> list = new ArrayList<>(); //객체를 리스트에 담기!
			
			while(rs.next()) {
				
				String empName  = rs.getString("EMP_NAME");
				String jobName  = rs.getString("JOB_NAME");
				int salary  = rs.getInt("SALARY");
				int annualIncome = rs.getInt("연봉");
				
				Employee emp = new Employee(empName,jobName,salary,annualIncome);
				list.add(emp);
				
				//list.add(new Employee(empName,jobName,salary,annualIncome))
				//라고 한 줄로 써도 된다.
				
			}
			
			if(list.isEmpty()) {
				
				System.out.println("조회 결과 없음");
			}else {
				
				for(Employee emp : list ) {
					System.out.println(emp);
				}
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}finally {
			
			
			
		}
		
		
		
		
	}

}

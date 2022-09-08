package edu.kh.emp.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.kh.emp.model.dao.EmployeeDAO;
import edu.kh.emp.model.vo.Employee;

//화면용 클래스(입력(Scanner) / 출력(print()) )
public class EmployeeView {

	private Scanner sc = new Scanner(System.in);
	
	//DAO객체 생성
	private EmployeeDAO dao = new EmployeeDAO();
	
	
	//메인 메뉴
	public void displayMenu() {
		
		int input = 0;
		
		do {
			
			try {
				
				System.out.println("---------------------------------------------------------");
				System.out.println("----- 사원 관리 프로그램 -----");
				System.out.println("1. 새로운 사원 정보 추가");
				System.out.println("2. 전체 사원 정보 조회");
				System.out.println("3. 사번이 일치하는 사원 정보 조회");
				System.out.println("4. 사번이 일치하는 사원 정보 수정");
				System.out.println("5. 사번이 일치하는 사원 정보 삭제");
				
				
				System.out.println("6. 입력 받은 부서와 일치하는 모든 사원 정보 조회");
				//selectDeptEmp()
				System.out.println("7. 입력 받은 급여 이상을 받는 모든 사원 정보 조회");
				//selectSalaryEmp()
				System.out.println("8. 부서별 급여 합 전체 조회");
		        // selectDeptTotalSalary()
		        // DB 조회 결과를 HashMap<String, Integer>에 옮겨 담아서 반환
		        // 부서코드, 급여 합 조회
		            
		        System.out.println("10. 직급별 급여 평균 조회");
		        // selectJobAvgSalary()
		        // DB 조회 결과를 HashMap<String, Double>에 옮겨 담아서 반환 
		        // 직급명, 급여 평균 조회 (소수점 첫째 자리까지)
						
						System.out.println("9. 주민등록번호가 일치하는 사원 정보 조회");
				
				System.out.println("0. 프로그램 종료");
				
				System.out.print("메뉴 선택 >> ");
				input = sc.nextInt();
				
				System.out.println();
				
				switch(input) {
				case 1: insertEmployee() ; break;
				case 2: selectAll(); break;
				case 3: selectEmpId();break;
				case 4: updateEmployee() ; break;
				case 5: deleteEmployee() ; break;
				case 6: selectDeptEmp();break;
				case 7: selectSalaryEmp();break;
				case 8: selectDeptTotalSalary() ;break;
				case 9: selectEmpNo(); break;
				case 10: selectJobAvgSalary();break;
				
				case 0: System.out.println("프로그램 종료");break;
				default: System.out.println("메뉴에 번호만 입력해");
				}
				
			}catch(InputMismatchException e) {
				System.out.println("정수만 입력해주세요");
				input = -1; //반복문 첫 번째 바퀴에서 잘못 입력하면 종료되는 상황을 방지하려고
				sc.nextLine(); // 입력 버퍼에 남아있는 잘못된 문자열을 제거해서
							   // 무한반복을 방지한다.
			}
			
		}while(input != 0);
		
	}




	/**DB 조회 결과를 HashMap<String, Double>에 옮겨 담아서 반환 
	 * 10. 직급명, 급여 평균 조회 (소수점 첫째 자리까지)
	 */
	public void selectJobAvgSalary() {
		
		Map<String,Double> map = dao.selectJobAvgSalary();
		
		for(Map.Entry<String, Double> entry :map.entrySet()) {
			
			System.out.println("[직급명] :" + entry.getKey()+ ",[급여평균] :"+entry.getValue());
		
		}
//		printSalary(map);
	}
	
	




	/**
	 * 8. 부서별 급여 합 전체 조회
	 */
	public void selectDeptTotalSalary() {
		
		// DB 조회 결과를 HashMap<String, Integer>에 옮겨 담아서 반환
        // 부서코드, 급여 합 조회
		
//		Map<String, Integer> map = new HashMap<String,Integer>();
		Map<String, Integer> map = dao.selectDeptTotalSalary();
		
//		for(Map.Entry<String, Integer> entry :map.entrySet()) {
//			
//			System.out.println("[부서코드] :" + entry.getKey()+ ",[급여합] :"+entry.getValue());
//		}
		printSalary(map);
		
	}

	
	public void printSalary(Map<String, Integer> map) {
		
		for(Map.Entry<String, Integer> entry :map.entrySet()) {
			
			System.out.println( entry.getKey()+ " / "+entry.getValue());
		}
		
	}



	/**
	 * 7. 입력 받은 급여 이상을 받는 모든 사원 정보 조회
	 */
	public void selectSalaryEmp() {
		
		System.out.println("입력한 급여 이상을 받는 사원은?");
		System.out.print("급여 :");
		int salary = sc.nextInt();
		
		List<Employee> list = dao.selectSalaryEmp(salary);
		
		printAll(list);
	}




	/**
	 * 6. 입력 받은 부서와 일치하는 모든 사원 정보 조회
	 */
	public void selectDeptEmp() {
		
		System.out.print("부서명 입력 :");
		String deptTitle = sc.next();
		
		List<Employee> list = dao.selectDeptEmp(deptTitle);
		
		printAll(list);
		
	}




	/**
	 * 1. 사원 정보 추가
	 */
	private void insertEmployee() {
		System.out.println("<사원 정보 추가>");
		
		//14개 컬럼 중 11개를 입력받는 코드를 쓰겠다.
		
		//사번
		int empId = inputEmpId();
		
		//이름
		System.out.print("이름 : ");
		String empName = sc.next();
		//주민번호
		System.out.print("주민번호 : ");
		String empNo = sc.next();
		//이메일
		System.out.print("이메일 : ");
		String email = sc.next();
		//전화번호(-제외)
		System.out.print("전화번호(-제외) : ");
		String phone = sc.next();
		//부서코드(D1~D9)
		System.out.print("부서코드(D1~D9) : ");
		String deptCode = sc.next();
		//직급코드(J1~J7)
		System.out.print("직급코드(J1~J7) : ");
		String jobCode = sc.next();
		//급여등급(S1~S6)
		System.out.print("급여등급(S1~S6) : ");
		String salLevel = sc.next();
		//급여
		System.out.print("급여 : ");
		int salary = sc.nextInt();
		//보너스
		System.out.print("보너스 : ");
		double bonus = sc.nextDouble();
		//사수번호
		System.out.print("사수번호 : ");
		int managerId = sc.nextInt();
		
		
		//입력 받은 값을 
		//Employee객체에 담아서 dao로 전달하는 코드 만들기.
		
		Employee emp = new Employee(empId, empName, empNo, email, phone,
				salary, deptCode, jobCode, salLevel, bonus, managerId);
		
		
		int result = dao.insertEmployee(emp);
		// 왜 int형이냐면,
		// INSERT, UPDATE, DELETE 같은 DML 구문은
		// 수행 후 테이블에 반영된 행의 개수를 반환함.
		// 만약 조건이 잘못된 경우, 반영된 행이 없으므로 0 반환함.
		
		if ( result > 0) { //DML구문 성공 시
			
			System.out.println("사원 정보 추가 성공");
			
		} else { //DMP구문 실패 시
			
			System.out.println("사원 정보 추가 실패");
			
		}
		
	}








	/**
	 * 2. 전체 사원 정보 조회
	 */
	public void selectAll() {
		
		System.out.println("<전체 사원 정보 조회>");
		
		//DB에서 전체 사원 정보를 조회하여 List<Employee> 형태로 반환하는
		//dao.selectAll() 메서드를 호출한다.
		List<Employee> empList = dao.selectAll();
		//23개의 Employee객체가 담겨있음.
		
		printAll(empList);
		
	}
	
	
	/**전달받은 사원 List 모두 출력하기
	 * @param empList
	 */
	public void printAll(List<Employee> empList) {
		
	    if(empList.isEmpty()) {
	        System.out.println("조회된 사원 정보가 없습니다.");
	          
	    } else {
	        System.out.println("사번 |   이름  | 주민 등록 번호 |        이메일        |   전화 번호   | 부서 | 직책 | 급여" );
	        System.out.println("------------------------------------------------------------------------------------------------");
	        for(Employee emp : empList) { 
	             System.out.printf(" %2d  | %4s | %s | %20s | %s | %s | %s | %d\n",
	                   emp.getEmpId(), emp.getEmpName(), emp.getEmpNo(), emp.getEmail(), 
	                   emp.getPhone(), emp.getDepartmentTitle(), emp.getJobName(), emp.getSalary());
	        }
	    }
		
	}

	
	
	/**
	 * 3.사번이 일치하는 사원 정보 조회하기
	 */
	public void selectEmpId() {
		
		System.out.println("<사번이 일치하는 사원 정보 조회>");
		
		// 사번 입력 받기
		int empId = inputEmpId();
		
		// 입력 받은 사번을 DAO의 selectEmpId() 메소드로 전달하여
		// 조회된 사원 정보를 반환받기
		// Employee 객체 형태로 가져온다.
		Employee emp = dao.selectEmpId(empId);
		
		printOne(emp);
	}

	//사번을 입력받는 경우도 여러 번이니까 코드의 중복을 줄이기 위해 메소드 따로 만들기
	
	/**3.사번을 입력 받아 반환하는 메소드
	 * @return empId
	 */
	public int inputEmpId() {
		
		System.out.print("사번 입력 :");
		int empId = sc.nextInt();
		sc.nextLine();
		
		return empId;
		
		
	}
	
	/** 사원 1명 정보를 출력하겠다.
	 * @param emp
	 */
	public void printOne(Employee emp) {
		
		if(emp == null) {
	        System.out.println("조회된 사원 정보가 없습니다.");
	          
	    } else {
	        System.out.println("사번 |   이름  | 주민 등록 번호 |        이메일        |   전화 번호   | 부서 | 직책 | 급여" );
	        System.out.println("------------------------------------------------------------------------------------------------");
	       
	             System.out.printf(" %2d  | %4s | %s | %20s | %s | %s | %s | %d\n",
	                   emp.getEmpId(), emp.getEmpName(), emp.getEmpNo(), emp.getEmail(), 
	                   emp.getPhone(), emp.getDepartmentTitle(), emp.getJobName(), emp.getSalary());
	        
	    }
		
	}
	
	/**
	 * 4.사번이 일치하는 사원 정보 수정(이메일,전화번호,급여)
	 */
	public void updateEmployee() {
		System.out.println("<사번이 일치하는 사원 정보 수정>");
		
		int empId = inputEmpId(); //사번입력
		
		System.out.print("이메일: ");
		String email = sc.next();
		System.out.print("전화번호(-제외) : ");
		String phone = sc.next();
		System.out.print("급여: ");
		int salary = sc.nextInt();
		
		
		//기본 생성자로 객체 생성 후 setter를 이용해서 초기화하는 과정...
		Employee emp = new Employee();
		
		emp.setEmpId(empId);
		emp.setEmail(email);
		emp.setPhone(phone);
		emp.setSalary(salary);
		
		int result = dao.updateEmployee(emp); //UPDATE(DML) -> 반환된 행의 개수 반환(int형)
		
		if(result >0 ) {
			System.out.println("사원 정보가 수정되었습니다.");
		}else {
			System.out.println("사번이 일치하는 사원이 존재하지 않습니다.");
		}
		
	}
	
	/**5.사번이 일치하는 사원 정보 삭제하기
	 * 
	 */
	public void deleteEmployee() {
		
		System.out.println("<사번이 일치하는 사원 정보 삭제>");
		
		int empId = inputEmpId();
		
		System.out.print("정말 삭제하시겠습니까?(Y/N)");
		char input = sc.next().toUpperCase().charAt(0);
		//입력받은 문자열을 대문자로 만드는 메소드...(모두 대문자로 변환되기 때문에 대소문자 구분없이 입력하면 처리됨)
		
		if(input == 'Y') {
			
			//삭제를 수행하는 DAO 호출
			//성공:"삭제되었습니다"
			//실패: "사번이 일치하는 사원이 존재하지 않습니다.
			
//			Employee emp = new Employee();
//			emp.setEmpId(empId);
//			
//			int result = dao.deleteEmployee(emp);
			
			int result = dao.deleteEmployee(empId);
			
			
			
			
			
			if(result >0 ) {
				System.out.println("삭제되었습니다.");
			}else {
				System.out.println("사번이 일치하는 사원이 존재하지 않습니다.");
			}
		}else {
			
			System.out.println("취소되었습니다.");
			
		}
		
		
		
		
	}
	
	
	/**
	 * 9. 주민등록번호가 일치하는 사원 정보를 조회하겠다.
	 */
	public void selectEmpNo() {
		System.out.println("<주민등록번호가 일치하는 사원 정보 조회>");
		
		System.out.print("민번 입력 :");
		String empNo = sc.next();
		
		Employee emp = dao.selectEmpNo(empNo);
		
		printOne(emp);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
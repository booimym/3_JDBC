package edu.kh.jdbc.model.vo;


//VO (Value Object) : 값 저장용 객체 - (저장된 값을 읽는 용도로 사용한다.)

//-> 비슷한 단어로 DTO(Data Transfer Object) - 데이터를 교환하는 용도의 객체
//										(값을 읽고 쓰고 하는 용도)

// VO, DTO 모두 값을 저장하는 용도
public class Emp { // DB에서 조회된 사원 한명(1행)의 정보를 저장하는 객체

	private String empName;
	private String depTitle;
	private int salary;
	
	public Emp() {} //기본 생성자
	
	//3개 다 전달 받아서 초기화하는 매개변수 생성자 만들기
	public Emp(String empName,String depTitle,int salary) {
		this.empName = empName;
		this.depTitle = depTitle;
		this.salary = salary;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDepTitle() {
		return depTitle;
	}

	public void setDepTitle(String depTitle) {
		this.depTitle = depTitle;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	//객체가 가지고 있는 필드를 한번에 보여주고 싶을 때 toString
	@Override
	public String toString() {
		return "이름 : " + empName + " / 부서명 : " + depTitle + "/ 급여 : " + salary;
	}
	
	
}

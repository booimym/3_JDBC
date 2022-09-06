package edu.kh.jdbc.model.vo;

public class Employee {

	private String empName;
	private String jobName;
	private int salary;
	private int annualIncome; //연봉(연간 수입)
	private String hireDate; //원래는 날짜지만, 
							 //조회되어 가져오는 입사일의 데이터타입이 문자열이기 때문에 String씀.
	private char gender; //DB에는 문자 하나를 나타내는 자료형이 없으므로
						 //어떻게 처리해야할지 생각이 필요함.
	
	
	public Employee() {}
	
	
	//생성자 : 객체 만들 때 필드 초기화
	public Employee(String empName, String jobName, int salary, int annualIncome) {
		super();
		this.empName = empName;
		this.jobName = jobName;
		this.salary = salary;
		this.annualIncome = annualIncome;
	}
	
	

//	public Employee(String empName, String hireDate, String gender) {
//		super();
//		this.empName = empName;
//		this.hireDate = hireDate;
//		this.gender = gender;
//	}


	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public int getAnnualIncome() {
		return annualIncome;
	}
	public void setAnnualIncome(int annualIncome) {
		this.annualIncome = annualIncome;
	}


	public String getHireDate() {
		return hireDate;
	}
	
	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}
	
	public char getGender() {
		return gender;
	}
	
	public void setGender(char gender) {
		this.gender = gender;
	}


//	@Override
//	public String toString() {
//		return "Employee [empName=" + empName + ", hireDate=" + hireDate + ", gender=" + gender + "]";
//	}
//	
	

	@Override
	public String toString() {
		return  empName + " / " + jobName + " / "  + salary + " / " 
				+ annualIncome ;
	}
	
	
	
	
}

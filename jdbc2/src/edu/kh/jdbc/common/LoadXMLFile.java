package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


public class LoadXMLFile {
			//XML 읽어오는 용도
	public static void main(String[] args) {
		
		// XML 파일 읽어오기 (Properties, FileInputStream을 이용할 것임)
		
		try {
			
			
			Properties prop = new Properties();
			//Map 클래스의 후손
			//Map<String,String>형태로 제한되어 있음.
			
			//driver.xml 파일을 읽어오기 위한 InputStream 객체 생성
			FileInputStream fis = new FileInputStream("driver.xml");
			
			//연결된 driver.xml 파일에 있는 내용을 모두 읽어와
			//properties 객체에 k:v 형식으로 저장할 것임.
			prop.loadFromXML(fis);
			
			System.out.println(prop);//다 읽어왔는지 확인용도로 실행...
			
			// Property : 속성(데이터)
			
			// prop.getProperty("key") : key가 일치하는 속성(데이터)를 얻어오는 메소드.
			
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String user = prop.getProperty("user");
			String password = prop.getProperty("password");
			
			
			System.out.println();
			
			//이제 커넥션 생성가능 ㅋ
			
			//driver.xml 파일에서 읽어온 값들을 이용해 Connection 생성 해보자!
			Class.forName(driver); //1. 드라이버의 값을 메모리에 로드한다.
			Connection conn = DriverManager.getConnection(url,user,password); //db에 연결됨
			
			System.out.println(conn);
			
			
			// 왜 이런 방법을 택했을까.........
			// 왜 XML파일을 이용해서 DB 연결 정보를 읽어와야 될까?
			// 1. 코드 중복 제거 
			//	각각의 DAO CLASS를 만들어야 될 때가 있는데, 그러면 매번 커넥션 생성코드 만들어야 되니까...
			// 2. 별도 관리 용도 (관리의 용이성을 위해서...)
			// 드라이버만 별도로 파일을 생성해놓으니까 수정할 때도 바로 찾기 쉬움.
			// 3. (제일 큰 이유) 재 컴파일을 진행하지 않기 위해서
			// 컴파일이란? 내가 짠 코드를 컴퓨터가 읽을 수 있게 변환하는 작업
			// 코드가 길수록 컴파일에 소요되는 시간이 길다.
			// 만약에 DAO에 써져있는 경우, driver 수정을 하면, 이것만 한글자만 수정한다고 해도 dao싹다 컴파일 해야되는 거임.....ㅎ
			// 외부파일에 들어있는 걸 수정하면 수정할 때마다 컴파일 안 해도 되지 ㅋ
			// ->코드 수정으로 인한 컴파일 소요시간을 없앨 수 있다~(파일의 내용을 읽어오는 코드만 작성해두면, Java코드 수정 없이 파일 내용만 수정하면 재 컴파일은 수행되지 않는다!
			// 4. xml에다가 sql을 적으면, 작성된 문자열 모양 그대로(띄어쓰기 등) 가져오기 때문에, sql 작성시 편리하다.
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}

}

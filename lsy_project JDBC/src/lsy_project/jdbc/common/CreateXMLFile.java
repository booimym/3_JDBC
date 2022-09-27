package lsy_project.jdbc.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class CreateXMLFile {
			//XML만드는 용도의 클래스 -> 출력이니까 io관련된 거 씀 -> ioe에러
	
	public static void main(String[] args) {
		
		// XML : (extensible Markup Language) : 단순화된 데이터 기술 형식
					         //markup : 그냥 글자들을 특정한 마크로 만드는 것임. 
		
		
		// XML에 저장되는 데이터의 형식은 Key : Value 형식(Map)을 띄고 있다. 
		// -> Key, Value 모두 String(문자열) 형식
		// -> Map<String, String> 
		
		// XML 파일을 읽고 쓰기 위한 IO 관련 클래스가 필요함.
		
		// * Properties 컬렉션 객체 * 
		// - Map의 후손 클래스
		// -> Key, Value 모두 String(문자열) 형식
		// - XML 파일을 읽고 쓰는데 특화된 메서드 제공
		
		try {
			
			Scanner sc = new Scanner(System.in);

			
			//Properties 객체 생성
			Properties prop = new Properties(); // Map 후손 클래스
			
			System.out.print("생성할 파일 이름: ");
			String fileName = sc.nextLine();
			
			//FileOutputStream 생성 - 파일을 내보낼 거임.(출력)(저장)
			FileOutputStream fos = new FileOutputStream(fileName + ".xml"); //이 이름과 확장자로 내보내짐.
			
			//Properties 객체를 이용해서 XML 파일을 생성
			
			prop.storeToXML(fos, fileName + ".xml");
			//XML형태로 (출력)저장하겠다는 뜻( , 어떤 XML파일인지 코멘트나 주석임)
			
			System.out.println(fileName + ".xml 파일 생성 완료");
			
		} catch(IOException e) { //XML만드는 용도의 클래스 -> 출력(파일을 내보낼 것임)이니까 IO관련된 거 씀 -> IOE에러
			e.printStackTrace();
		}
		
	}
}

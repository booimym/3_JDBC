import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

	public static void main(String[] args) {
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("숫자 입력 :");
			int input = Integer.parseInt(br.readLine());
			
			for(int i =  0 ; ;i++) {
				int gob = input * i ;
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out)); 
				//br.write(input + "+"  );
			}
			
			
			
		} catch(IOException e) {
			
		}
		
		System.out.println(2*3);
		
	}
}

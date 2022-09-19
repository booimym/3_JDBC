package edu.kh.jdbc.main.run;

import edu.kh.jdbc.main.view.MainView;

//실행용 클래스
public class MaInRun {

	public static void main(String[] args) {
		
		new MainView().mainMenu();
		
		//System.exit(0); //내부적으로 존재함(컴파일러가 자동으로 추가해준다)
	}

}

package boo.jdbc.view;

import java.util.Scanner;

import boo.jdbc.service.Service;
import boo.jdbc.vo.VO;

public class View {

	Scanner sc = new Scanner(System.in);
	private Service service = new Service();
	
	/**
	 * 영화 상세 조회
	 */
	public void select() {
		
		try {
		System.out.println("엔터키되는지 실험해보자");
		
		System.out.println("영화 제목 검색 :");
		String input = sc.nextLine();
		
		VO vo = service.select(input);
		
		System.out.println(vo.getMovieContent());
		
		}catch (Exception e) {
			System.out.println("조회 중 에러 발생");
			e.printStackTrace();
		}
	}
	
	
}

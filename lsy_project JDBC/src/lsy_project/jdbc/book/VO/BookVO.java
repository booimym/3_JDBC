package lsy_project.jdbc.book.VO;

public class BookVO {
	
	int reservNo;
	int memberNo;
	String memberNm;
	int allMoviesNo;
	String theaterNm;
	String screenNm;
	String seatNm;
	
	public BookVO() {}

	public int getReservNo() {
		return reservNo;
	}

	public void setReservNo(int reservNo) {
		this.reservNo = reservNo;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberNm() {
		return memberNm;
	}

	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}

	public int getAllMoviesNo() {
		return allMoviesNo;
	}

	public void setAllMoviesNo(int allMoviesNo) {
		this.allMoviesNo = allMoviesNo;
	}

	public String getTheaterNm() {
		return theaterNm;
	}

	public void setTheaterNm(String theaterNm) {
		this.theaterNm = theaterNm;
	}

	public String getScreenNm() {
		return screenNm;
	}

	public void setScreenNm(String screenNm) {
		this.screenNm = screenNm;
	}

	public String getSeatNm() {
		return seatNm;
	}

	public void setSeatNm(String seatNm) {
		this.seatNm = seatNm;
	}
	
	
}

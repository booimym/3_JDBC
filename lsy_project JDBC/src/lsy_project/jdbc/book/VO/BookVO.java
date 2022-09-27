package lsy_project.jdbc.book.VO;

public class BookVO {
	
	int reservNo;
	int memberNo;
	String memberNm;
	int allMoviesNo;
	String theaterNm;
	String screenNm;
	int seatNo;
	String seatNm;
	String movieTitle;
	String startTime;
	int countSeat;
	
	public int getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(int seatNo) {
		this.seatNo = seatNo;
	}

	public int getCountSeat() {
		return countSeat;
	}

	public void setCountSeat(int countSeat) {
		this.countSeat = countSeat;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

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

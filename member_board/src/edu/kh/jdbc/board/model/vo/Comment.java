package edu.kh.jdbc.board.model.vo;

//댓글 1개에 대한 값을 저장하는 VO
public class Comment {

	private int commentNo; //댓글 번호
	private String commentContent; //댓글 내용
	private int memberNumber; //작성자 회원 번호
	private String memberName; //작성자 회원 이름
	private String createDate; //작성일
	private int boardNo; //작성된 게시글 번호 //게시글 등록,수정,삭제할 때 이용할 것임...
	
	//기본 생성자
	public Comment() {}
	
	//getter setter
	public int getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public int getMemberNumber() {
		return memberNumber;
	}

	public void setMemberNumber(int memberNumber) {
		this.memberNumber = memberNumber;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	
	
	
	
}

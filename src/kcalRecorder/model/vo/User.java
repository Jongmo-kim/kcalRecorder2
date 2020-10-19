package kcalRecorder.model.vo;

public class User {
	private int no;
	private String id;
	private String pw;
	private String nickname;
	public String getId() {
		return id;
	}
	
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public User(String id, String pw, String nickname) {
		super();
		this.id = id;
		this.pw = pw;
		this.nickname = nickname;
	}
	public User() {
		super();
	}
	
	
}

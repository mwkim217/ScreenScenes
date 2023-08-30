package object;

import java.sql.Blob;

public class User {
	private int userno;
	private String id;
	private String password;
	private String nickname;
	private Blob profile;

	public User(int userno, String id, String password, String nickname) {
		super();
		this.userno = userno;
		this.id = id;
		this.password = password;
		this.nickname = nickname;
	}

	public User(int userno, String id, String password, String nickname, Blob profile) {
		this.userno = userno;
		this.id = id;
		this.password = password;
		this.nickname = nickname;
		this.profile = profile;
	}
	
	public User(int userno, String id, String nickname, Blob profile) {
		this.userno = userno;
		this.id = id;
		this.nickname = nickname;
		this.profile = profile;
	}

	public int getUserno() {
		return userno;
	}

	public void setUserno(int userno) {
		this.userno = userno;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Blob getProfile() {
		return profile;
	}

	public void setProfile(Blob profile) {
		this.profile = profile;
	}
}

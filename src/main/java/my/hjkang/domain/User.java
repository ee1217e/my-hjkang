package my.hjkang.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User {
	@Id
	@GeneratedValue // AI
	@JsonProperty
	private long id;

	// F3 눌러서 Column 확인하자
	@Column(length = 20, nullable = false, unique = true)
	@JsonProperty
	private String userId;

	@Column(nullable = false)
	@JsonProperty
	private String password;

	@Column(nullable = false)
	@JsonProperty
	private String name;

	private String email;
	
	public User(){}

	public User(long id, String userId, String password, String name, String email) {
		this.id = id;
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
	}
	
	public long getId() {
		return id;
	}

	public boolean matchId(Long newId) {
		if (newId == null) {
			return false;
		}
		return newId.equals(id);
	}

	public boolean matchPassword(String password) {
		return this.password.equals(password);
	}

	public void update(User updateUser) {
		if (!matchPassword(updateUser.password)) {
			throw new IllegalStateException("비밀번호가 다릅니다.");
		}
		this.name = updateUser.name;
		this.email = updateUser.email;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", password=" + password + ", name=" + name + ", email="
				+ email + "]";
	}

}

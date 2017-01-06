package my.hjkang.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Question {

	@Id
	@GeneratedValue // AI
	private long id;

	@Column(nullable = false)
	private String writer;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String contents;

	private String reg_date;

	public void setId(long id) {
		this.id = id;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public void setRegDate(String reg_date) {
		this.reg_date = reg_date;
	}

	@Override
	public String toString() {
		return "Qna [id=" + id + ", writer=" + writer + ", title=" + title + ", contents=" + contents + ", reg_date="
				+ reg_date + "]";
	}

}

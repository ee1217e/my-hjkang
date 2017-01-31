package my.hjkang.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DeleteStatus {

	@Id
	@GeneratedValue // AI
	private long id;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_to_answer2"))
	private Question question;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_user_to_answer"))
	private User writer;

	private String regDate;

	public DeleteStatus() {
	}

	public DeleteStatus(long id, Question question, User writer, String regDate) {
		super();
		this.id = id;
		this.question = question;
		this.writer = writer;
		this.regDate = regDate;
	}

	public void setRegDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm");
		Date date = new Date();
		this.regDate = sdf.format(date);
	}
	
	public void create(Question question, User user) {
		this.question = question;
		this.writer = user;
		setRegDate();
	}

	@Override
	public String toString() {
		return "DeleteStatus [id=" + id + ", question=" + question + ", writer=" + writer + ", regDate=" + regDate
				+ "]";
	}

}

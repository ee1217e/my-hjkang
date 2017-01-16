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
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_to_answer"))
	private Question question;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_user_to_answer"))
	private User writer;

	private String deleteTime;

	public DeleteStatus() {
	}

	public DeleteStatus(long id, Question question, User writer, String deleteTime) {
		super();
		this.id = id;
		this.question = question;
		this.writer = writer;
		this.deleteTime = deleteTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public String getDeleteTime() {
		return deleteTime;
	}

	public String deleteTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm");
		Date date = new Date();
		return sdf.format(date);
	}

	public void create(Question question, User user) {
		this.question = question;
		this.writer = user;
		this.deleteTime = deleteTime();
	}

	@Override
	public String toString() {
		return "DeleteStatus [id=" + id + ", question=" + question + ", writer=" + writer + ", deleteTime=" + deleteTime
				+ "]";
	}

}

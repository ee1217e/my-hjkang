package my.hjkang.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Answer {
	@Id
	@GeneratedValue // AI
	@JsonProperty
	private long id;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_to_answer"))
	@JsonProperty
	private Question question;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_user_to_answer"))
	@JsonProperty
	private User writer;

	@Column(nullable = false)
	@JsonProperty
	private String contents;

	@Column(nullable = false)
	@JsonProperty
	private String regDate;
	
	public Answer(){
	}

	public Answer(User writer, String contents, Question question) {
		this.writer = writer;
		this.contents = contents;
		this.question = question;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm");
		Date date = new Date();
		this.regDate = sdf.format(date);
	}
	
	public User getWriter() {
		return writer;
	}
	
	public boolean loginUserAnswerWriterCheck(User sessionUser){
		return sessionUser.getId() == this.writer.getId();
	}

	
	@Override
	public String toString() {
		return "Answer [id=" + id + ", question=" + question + ", writer=" + writer + ", contents=" + contents
				+ ", regDate=" + regDate + "]";
	}

}

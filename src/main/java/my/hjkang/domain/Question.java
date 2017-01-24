package my.hjkang.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Question {

	@Id
	@GeneratedValue // AI
	@JsonProperty
	private long id;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_to_write"))
	@JsonProperty
	private User writer;

	@OneToMany(mappedBy = "question")
	@OrderBy("id DESC")
	@JsonIgnore
	@JsonProperty
	private List<Answer> answers;

	@Column(nullable = false)
	@JsonProperty
	private String title;

	@Column(nullable = false)
	@JsonProperty
	private String contents;

	@Column(nullable = false)
	@JsonProperty
	private String regDate;

	@Column
	@JsonProperty
	private int deleteStatus;

	public Question() {

	}
	
	public Question(String title, String contents, User writer){
		this.title = title;
		this.contents = contents;
		this.writer = writer;
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm");
		Date date = new Date();
		this.regDate = sdf.format(date);
	}

	public Question(long id, User writer, List<Answer> answers, String title, String contents) {
		super();
		this.id = id;
		this.writer = writer;
		this.answers = answers;
		this.title = title;
		this.contents = contents;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public void setRegDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm");
		Date date = new Date();
		this.regDate = sdf.format(date);
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public int getDeleteStatus() {
		return deleteStatus;
	}

	private boolean isEmptyAnswers() {
		return answers == null;
	}

	private void deleteStatusUpdate() {
		this.deleteStatus = 1;
	}

	private boolean qnaAnswerWriterCheck(Answer answer) throws Exception {
		return answer.getWriter() == this.writer;
	}

	public boolean loginUserWriterCheck(User user) throws Exception {
		return this.writer.getId() == user.getId();
	}

	private boolean isQnaAnswerWriter() throws Exception {
		for (Answer answer : answers) {
			if (!qnaAnswerWriterCheck(answer)) {
				return false;
			}
		}

		return true;
	}

	public boolean delete(User user) throws Exception {
		if (!loginUserWriterCheck(user)) {
			throw new Exception("자신이 등록한 글만 삭제할 수 있습니다.");
		}

		if (isEmptyAnswers()) {
			deleteStatusUpdate();
			return true;
		}

		if (!isQnaAnswerWriter()) {
			throw new Exception("질문자와 답변자가 다른 경우 삭제할 수 없습니다.");
		}

		deleteStatusUpdate();

		return true;
	}

	public boolean update(User user, String title, String contents) throws Exception {
		if (!loginUserWriterCheck(user)) {
			throw new Exception("자신이 등록한 글만 수정할 수 있습니다.");
		}

		this.title = title;
		this.contents = contents;

		return true;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", writer=" + writer + ", answers=" + answers + ", title=" + title + ", contents="
				+ contents + ", regDate=" + regDate + ", deleteStatus=" + deleteStatus + "]";
	}

}

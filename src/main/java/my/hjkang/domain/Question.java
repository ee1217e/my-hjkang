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

@Entity
public class Question {

	@Id
	@GeneratedValue // AI
	private long id;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_to_write"))
	private User writer;

	@OneToMany(mappedBy = "question")
	@OrderBy("id DESC")
	private List<Answer> answers;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String contents;

	@Column(nullable = false)
	private String regDate;

	@Column
	private int deleteStatus;

	public Question() {
	}

	public Question(long id, User writer, List<Answer> answers, String title, String contents) {
		super();
		this.id = id;
		this.writer = writer;
		this.answers = answers;
		this.title = title;
		this.contents = contents;
	}

	public boolean delete(User user) throws Exception {
		if(!loginUserWriterCheck(user)){
			throw new Exception("자신이 등록한 글만 삭제할 수 있습니다.");
		}

		if (answers == null) {
			deleteStatusUpdate();
			return true;
		}

		for (Answer answer : answers) {
			if(!qnaAndAnswerWriterCheck(answer)){
				throw new Exception("질문자와 답변자가 다른 경우 삭제할 수 없습니다.");
			}
		}

		deleteStatusUpdate();

		return true;
	}

	private void deleteStatusUpdate() {
		this.deleteStatus = 1;
	}
	
	private boolean qnaAndAnswerWriterCheck(Answer answer) throws Exception {
		return answer.getWriter() == this.writer;
	}

	private boolean loginUserWriterCheck(User user) throws Exception {
		return this.writer.getId() == user.getId();
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
	
	@Override
	public String toString() {
		return "Question [id=" + id + ", writer=" + writer + ", answers=" + answers + ", title=" + title + ", contents="
				+ contents + ", regDate=" + regDate + ", deleteStatus=" + deleteStatus + "]";
	}

}

package my.hjkang.domain;

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
	private String reg_date;

	@Column
	private String deleteStatus;

	public Question() {
	}

	public Question(long id, User writer, List<Answer> answers, String title, String contents, String reg_date,
			String deleteStatus) {
		super();
		this.id = id;
		this.writer = writer;
		this.answers = answers;
		this.title = title;
		this.contents = contents;
		this.reg_date = reg_date;
		this.deleteStatus = deleteStatus;
	}

	public boolean delete(User user) throws Exception {
		user.loginUserWriterCheck(user);

		if (answers == null) {
			deleteStatusUpdate();
			return true;
		}

		for (Answer answer : answers) {
			qnaAndAnswerWriterCheck(answer);
		}

		deleteStatusUpdate();

		return true;
	}

	private void deleteStatusUpdate() {
		this.deleteStatus = "deleted";
	}

	private void qnaAndAnswerWriterCheck(Answer answer) throws Exception {
		if (answer.getWriter() != this.writer) {
			throw new Exception("질문자와 답변자가 다른 경우 삭제할 수 없습니다.");
		}
	}

	/*private void loginUserWriterCheck(User user) throws Exception {
		if (this.writer.getId() != user.getId()) {
			throw new Exception("자신이 등록한 글만 삭제할 수 있습니다.");
		}
	}*/

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public String getDeleteStatus() {
		return deleteStatus;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", writer=" + writer + ", answers=" + answers + ", title=" + title + ", contents="
				+ contents + ", reg_date=" + reg_date + ", deleteStatus=" + deleteStatus + "]";
	}

}

package my.hjkang.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import my.hjkang.domain.Answer;
import my.hjkang.domain.DeleteStatus;
import my.hjkang.domain.Question;
import my.hjkang.domain.User;

@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceTest {

	@Test
	public void 사용자동일() throws Exception {
		User writer = new User(1L, "hjkang", "abc", "강현지", "hjkang@rockplace.co.kr");
		User sessionUser = new User(1L, "hjkang", "abc", "강현지", "hjkang@rockplace.co.kr");

		assertEquals(writer.getId(), sessionUser.getId());
	}

	@Test
	public void 댓글없을때삭제가능() throws Exception {
		User writer = new User(1L, "hjkang", "abc", "강현지", "hjkang@rockplace.co.kr");
		Question question = new Question(1L, writer, null, "제목", "내용");

		boolean result = question.delete(writer);
		assertTrue(result);
	}

	@Test
	public void 질문자답변자같으면삭제가능() throws Exception {
		User writer = new User(1L, "hjkang", "abc", "강현지", "hjkang@rockplace.co.kr");
		
		List<Answer> answerList = new ArrayList<Answer>();
		answerList.add(new Answer(1L, writer, "댓글1"));
		answerList.add(new Answer(2L, writer, "댓글2"));
		
		Question question = new Question(1L, writer, answerList, "제목", "내용");

		question.delete(writer);
	}

	@Test
	public void 질문자답변자다르면삭제불가능() throws Exception {
		User writer = new User(1L, "hjkang", "abc", "강현지", "hjkang@rockplace.co.kr");
		User sessionUser = new User(2L, "qhrja", "abc", "박보검", "qhrja@rockplace.co.kr");

		List<Answer> answerList = new ArrayList<Answer>();
		answerList.add(new Answer(1L, writer, "댓글1"));
		answerList.add(new Answer(2L, sessionUser, "댓글2"));
		
		Question question = new Question(1L, writer, answerList, "제목", "내용");

		question.delete(writer);
	}

	@Test
	public void 질문삭제상태변경() throws Exception {
		User writer = new User(1L, "hjkang", "abc", "강현지", "hjkang@rockplace.co.kr");
		Question question = new Question(1L, writer, null, "제목", "내용");

		question.delete(writer);
		assertEquals(question.getDeleteStatus(), 1);
	}

	@Test
	public void 질문삭제시_삭제로그추가() throws Exception {
		User writer = new User(1L, "hjkang", "abc", "강현지", "hjkang@rockplace.co.kr");
		Question question = new Question(1L, writer, null, "제목", "내용");
		
		DeleteStatus deleteStatus = new DeleteStatus(1L, question, writer, "17-01-12 05:03");
		assertEquals(deleteStatus.getQuestion(), question);
	}
	
}

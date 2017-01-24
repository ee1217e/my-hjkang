package my.hjkang.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import my.hjkang.domain.Answer;
import my.hjkang.domain.Question;
import my.hjkang.domain.User;

@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceTest {
	
	User writer;
	Question question;
	
	@Before
	public void before(){
		writer = new User(1L, "hjkang", "abc", "강현지", "hjkang@rockplace.co.kr");
		question = new Question("제목", "내용", writer, null);
	}

	@Test
	public void 사용자동일() throws Exception {
		User sessionUser = new User(1L, "hjkang", "abc", "강현지", "hjkang@rockplace.co.kr");

		assertEquals(writer.getId(), sessionUser.getId());
	}

	@Test
	public void 댓글없을때삭제가능() throws Exception {
		boolean result = question.delete(writer);
		assertTrue(result);
	}

	@Test
	public void 질문자답변자같으면삭제가능() throws Exception {
		List<Answer> answerList = new ArrayList<Answer>();
		answerList.add(new Answer(writer, "내용", question));
		
		question = new Question("제목", "내용", writer, null);

		question.delete(writer);
	}

	@Test
	public void 질문자답변자다르면삭제불가능() throws Exception {
		List<Answer> answerList = new ArrayList<Answer>();
		answerList.add(new Answer(writer, "내용", question));
		
		question = new Question("제목", "내용", writer, null);

		question.delete(writer);
	}
}

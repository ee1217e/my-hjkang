package my.hjkang.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import my.hjkang.domain.Answer;
import my.hjkang.domain.DeleteStatus;
import my.hjkang.domain.Question;
import my.hjkang.domain.QuestionRepository;
import my.hjkang.domain.User;
import my.hjkang.domain.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private QuestionRepository questionRepository;

	@InjectMocks
	private UserService userService;
	
	@InjectMocks
	private QuestionService questionService;
	
	@Test
	public void 사용자동일() throws Exception {
		User newUser = new User(1L, "hjkang", "abc", "강현지", "hjkang@rockplace.co.kr");
		// 인자값이 있는 경우는 when()
		when(userRepository.findOne(1L)).thenReturn(newUser);
		User user = userService.findById(1L);
		
		Question question = new Question(1L, user, null, "제목", "내용", "2017-01-16", null);
		when(questionRepository.findOne(1L)).thenReturn(question);
		Question qna = questionService.findById(1L);
		
		assertEquals(user, qna.getWriter());
	}

	@Test
	public void 댓글없을때삭제가능() throws Exception {
		User writer = new User(1L, "hjkang", "abc", "강현지", "hjkang@rockplace.co.kr");
		Question question = new Question(1L, writer, null, "제목", "내용", "2017-01-16", null);
		when(questionRepository.findOne(1L)).thenReturn(question);
		Question qna = questionService.findById(1L);

		questionService.delete(qna.getId(), writer);
		assertEquals(question.getDeleteStatus(), "deleted");
	}

	@Test
	public void 질문자답변자같으면삭제가능() throws Exception {
		User writer = new User(1L, "hjkang", "abc", "강현지", "hjkang@rockplace.co.kr");
		Question question = new Question(1L, writer, null, "제목", "내용", "2017-01-11", null);

		List<Answer> answerList = new ArrayList<Answer>();

		answerList.add(new Answer(1L, question, writer, "댓글1"));
		answerList.add(new Answer(2L, question, writer, "댓글2"));

		question.setAnswers(answerList);
		question.delete(writer);
	}

	@Test
	public void 질문자답변자다르면삭제불가능() throws Exception {
		User writer = new User(1L, "hjkang", "abc", "강현지", "hjkang@rockplace.co.kr");
		Question question = new Question(1L, writer, null, "제목", "내용", "2017-01-11", null);
		User sessionUser = new User(2L, "qhrja", "abc", "박보검", "qhrja@rockplace.co.kr");

		List<Answer> answerList = new ArrayList<Answer>();

		answerList.add(new Answer(1L, question, writer, "댓글1"));
		answerList.add(new Answer(2L, question, sessionUser, "댓글2"));

		question.setAnswers(answerList);
		question.delete(writer);
	}

	@Test
	public void 질문삭제상태변경() throws Exception {
		User writer = new User(1L, "hjkang", "abc", "강현지", "hjkang@rockplace.co.kr");
		Question question = new Question(1L, writer, null, "제목", "내용", "2017-01-11", null);
		User sessionUser = new User(1L, "hjkang", "abc", "강현지", "hjkang@rockplace.co.kr");

		question.delete(sessionUser);
		assertEquals(question.getDeleteStatus(), "deleted");
	}

	@Test
	public void 질문삭제시로그추가() throws Exception {
		User writer = new User(1L, "hjkang", "abc", "강현지", "hjkang@rockplace.co.kr");
		Question question = new Question(1L, writer, null, "제목", "내용", "2017-01-11", null);

		DeleteStatus deleteStatus = new DeleteStatus(1L, question, writer, "17-01-12 05:03");
		assertEquals(deleteStatus.getQuestion(), question);
	}

}

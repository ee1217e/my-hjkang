package my.hjkang.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import my.hjkang.domain.Answer;
import my.hjkang.domain.Question;
import my.hjkang.domain.Result;
import my.hjkang.domain.User;
import my.hjkang.service.AnswerService;
import my.hjkang.service.QuestionService;

@RestController
public class ApiAnswerController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerService answerService;

	@PostMapping("/api/questions/{questionId}/answers")
	public Answer addAnswer(@PathVariable long questionId, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return null;
		}
		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionService.findById(questionId);
		Answer answer = new Answer(sessionUser, contents, question);

		return answerService.create(answer);
	}
	
	@DeleteMapping("/api/questions/{questionId}/answers/{id}")
	public Result deleteAnswer(@PathVariable long questionId, @PathVariable long id, HttpSession session){
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인이 필요합니다.");
		}
		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Answer answer = answerService.findById(id);
		
		if(!answer.loginUserAnswerWriterCheck(sessionUser)){
			return Result.fail("자신의 댓글만 삭제할 수 있습니다.");
		}
		
		answerService.delete(id, sessionUser);
		
		return Result.ok();
	}
}

package my.hjkang.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import my.hjkang.domain.Answer;
import my.hjkang.domain.Question;
import my.hjkang.domain.User;
import my.hjkang.service.AnswerService;
import my.hjkang.service.QuestionService;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

	@Autowired
	AnswerService answerService;

	@Autowired
	QuestionService questionService;

	@PostMapping("")
	public String create(@PathVariable Long questionId, Model model, HttpSession session, String contents) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login";
		}
		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionService.findById(questionId);
		
		Answer answer = new Answer(sessionUser, contents, question);
		answerService.save(answer);

		return "redirect:/questions/{questionId}/view";
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session){
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login";
		}
		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Answer answer = answerService.findById(id);
		
		if(!answer.loginUserAnswerWriterCheck(sessionUser)){
			throw new IllegalStateException("자신의 댓글만 삭제할 수 있습니다.");
		}
		
		answerService.delete(id, sessionUser);
		
		return "redirect:/questions/{questionId}/view";
	}
}

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
import my.hjkang.domain.AnswerRepository;
import my.hjkang.domain.Question;
import my.hjkang.domain.QuestionRepository;
import my.hjkang.domain.User;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

	@Autowired
	AnswerRepository answerRepository;

	@Autowired
	QuestionRepository questionRepository;

	@PostMapping("")
	public String create(@PathVariable Long questionId, Model model, HttpSession session, Answer answer) {
		User sessionUser = (User) session.getAttribute("sessionUser");
		if (sessionUser == null) {
			return "redirect:/users/login";
		}
		Question question = questionRepository.findOne(questionId);
		answer.setWriter(sessionUser);
		answer.setQuestion(question);
		
		answerRepository.save(answer);

		return "redirect:/questions/{questionId}/view";
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session){
		User sessionUser = (User) session.getAttribute("sessionUser");
		if (sessionUser == null) {
			return "redirect:/users/login";
		}
		
		Answer answer = answerRepository.findOne(id);
		
		if(!sessionUser.matchId(answer.getWriter().getId())){
			throw new IllegalStateException("자신의 댓글만 삭제할 수 있습니다.");
		}
		
		answerRepository.delete(id);
		
		return "redirect:/questions/{questionId}/view";
	}
}

package my.hjkang.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import my.hjkang.domain.Question;
import my.hjkang.domain.User;
import my.hjkang.service.QuestionService;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@GetMapping("/form")
	public String form(HttpSession session, Model model) {
		User sessionUser = (User) session.getAttribute("sessionUser");

		if (sessionUser == null) {
			return "redirect:/users/login";
		}

		model.addAttribute("questions", questionService.findAll());

		return "qna/form";
	}

	@PostMapping("")
	public String create(Question question, HttpSession session) {
		User sessionUser = (User) session.getAttribute("sessionUser");
		question.setWriter(sessionUser);

		if (sessionUser == null) {
			return "redirect:/users/login";
		}

		question.setRegDate(question.nowTime());
		questionService.create(question);

		return "redirect:/";
	}

	@GetMapping("/{id}/view")
	public String view(@PathVariable Long id, Model model) {
		model.addAttribute("questions", questionService.findById(id));

		return "qna/show";
	}

	@DeleteMapping("/{id}/delete")
	public String delete(@PathVariable Long id, HttpSession session) throws Exception {
		User user = (User) session.getAttribute("sessionUser");
		questionService.delete(id, user);

		return "redirect:/";
	}

}

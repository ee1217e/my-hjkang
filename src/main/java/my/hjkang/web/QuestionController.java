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
	
	/*private static final Logger log = LoggerFactory.getLogger(QuestionController.class);*/

	@Autowired
	private QuestionService questionService;

	@GetMapping("/form")
	public String form(HttpSession session, Model model) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login";
		}

		model.addAttribute("questions", questionService.findAll());

		return "qna/form";
	}

	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login";
		}
		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question question = new Question(title, contents, sessionUser);

		questionService.save(question);

		return "redirect:/";
	}

	@GetMapping("/{id}/view")
	public String view(@PathVariable Long id, Model model) {
		model.addAttribute("questions", questionService.findById(id));
		return "qna/show";
	}

	@DeleteMapping("/{id}/delete")
	public String delete(@PathVariable Long id, HttpSession session) throws Exception {
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		questionService.delete(id, sessionUser);
		return "redirect:/";
	}
	
	@GetMapping("/{id}/updateForm")
	public String updateForm(@PathVariable Long id, HttpSession session, Model model, User user) throws Exception {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login";
		}
		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionService.findById(id);
		
		if(!question.loginUserWriterCheck(sessionUser)){
			throw new IllegalStateException("자신의 글만 수정할 수 있습니다.");
		}
		
		model.addAttribute("question", question);
		return "qna/updateForm";
	}
	
	@PostMapping("/{id}/update")
	public String update(@PathVariable Long id, HttpSession session, String title, String contents) throws Exception {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login";
		}
		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		
		questionService.update(id, sessionUser, title, contents);
		return "redirect:/questions/{id}/view";
	}
	
	/*@GetMapping("/form")
	public String form(HttpSession session, Model model) {
		User sessionUser = (User) session.getAttribute("sessionUser");

		if (sessionUser == null) {
			return "redirect:/users/login";
		}

		model.addAttribute("questions", questionService.findAll());

		return "qna/form";
	}*/

}

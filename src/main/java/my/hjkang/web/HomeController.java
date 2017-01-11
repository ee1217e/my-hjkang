package my.hjkang.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import my.hjkang.domain.QuestionRepository;
import my.hjkang.domain.User;

@Controller
public class HomeController {
	
	@Autowired
	private QuestionRepository qnaRepository;
	
	@GetMapping("/")
	public String home(HttpSession session, Model model){
		User sessionUser = (User) session.getAttribute("sessionUser");
		
		model.addAttribute("sessionedUser", sessionUser);
		model.addAttribute("questions", qnaRepository.findAll());
		return "index";
	}
}

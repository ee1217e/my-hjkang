package my.hjkang.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import my.hjkang.service.QuestionService;

@Controller
public class HomeController {
	
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/")
	public String home(HttpSession session, Model model){
		model.addAttribute("questions", questionService.findByDeleteStatus());
		return "index";
	}
}

package my.hjkang.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import my.hjkang.domain.QuestionRepository;

@Controller
public class HomeController {
	
	@Autowired
	private QuestionRepository qnaRepository;
	
	@GetMapping("")
	public String home(){
		return "index";
	}
	
	@GetMapping("/")
	public String qnaList(Model model){
		model.addAttribute("questions", qnaRepository.findAll());
		return "index";
	}
}

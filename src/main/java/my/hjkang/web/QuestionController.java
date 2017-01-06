package my.hjkang.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import my.hjkang.domain.Question;
import my.hjkang.domain.QuestionRepository;

@Controller
public class QuestionController {
	
	@Autowired
	private QuestionRepository qnaRepository;
	
	@GetMapping("/questionForm")
	public String form(){
		return "qna/form";
	}
	
	@PostMapping("/questions")
	public String qnaCreate(Question qna){
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm");
		Date date = new Date();
		
		qna.setRegDate(sdf.format(date));
		qnaRepository.save(qna);
		return "redirect:/";
	}
	
	@GetMapping("/")
	public String qnaList(Model model){
		model.addAttribute("questions", qnaRepository.findAll());
		return "index";
	}
		
	@GetMapping("/question")
	public String qnaShow(Model model, long id){
		model.addAttribute("qna", qnaRepository.findOne(id));
		return "qna/show";
	}
}

package my.hjkang.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import my.hjkang.domain.Question;
import my.hjkang.domain.QuestionRepository;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	private QuestionRepository qnaRepository;
	
	@GetMapping("/form")
	public String form(){
		return "qna/form";
	}
	
	@PostMapping("")
	public String questionCreate(Question qna){
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm");
		Date date = new Date();
		
		qna.setRegDate(sdf.format(date));
		qnaRepository.save(qna);
		return "redirect:/";
	}
		
	@GetMapping("/{id}/view")
	public String questionView(@PathVariable Long id, Model model){
		model.addAttribute("qna", qnaRepository.findOne(id));
		return "qna/show";
	}
}

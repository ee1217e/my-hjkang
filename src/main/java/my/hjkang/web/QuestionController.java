package my.hjkang.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import my.hjkang.domain.Question;
import my.hjkang.domain.QuestionRepository;
import my.hjkang.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	private QuestionRepository qnaRepository;
	
	@GetMapping("/form")
	public String form(HttpSession session, Model model){
		User sessionUser = (User) session.getAttribute("sessionUser");
		
		if(sessionUser == null){
			return "redirect:/users/login";
		}
		
		model.addAttribute("questions", qnaRepository.findAll());
		
		return "qna/form";
	}
	
	@PostMapping("")
	public String create(Question qna, HttpSession session){
		User sessionUser = (User) session.getAttribute("sessionUser");
		qna.setWriter(sessionUser);
		
		if(sessionUser == null){
			return "redirect:/users/login";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm");
		Date date = new Date();
		
		qna.setRegDate(sdf.format(date));
		qnaRepository.save(qna);
		
		return "redirect:/";
	}
		
	@GetMapping("/{id}/view")
	public String view(@PathVariable Long id, Model model){
		model.addAttribute("questions", qnaRepository.findOne(id));
		
		return "qna/show";
	}
}

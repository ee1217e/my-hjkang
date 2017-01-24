package my.hjkang.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import my.hjkang.domain.Question;
import my.hjkang.domain.QuestionRepository;

@RestController
public class ApiQuestionController {

	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("/api/questions")
	public Iterable<Question> list(){
		return questionRepository.findAll();
	}
}

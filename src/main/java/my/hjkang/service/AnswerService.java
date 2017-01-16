package my.hjkang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.hjkang.domain.Answer;
import my.hjkang.domain.AnswerRepository;

@Service
public class AnswerService {
	
	@Autowired
	AnswerRepository answerRepository;

	public void create(Answer answer){
		answerRepository.save(answer);
	}
	
	public Answer findById(long id){
		return answerRepository.findOne(id);
	}
	
	public void delete(long questionId){
		answerRepository.delete(questionId);
	}
}

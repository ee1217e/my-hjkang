package my.hjkang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.hjkang.domain.Answer;
import my.hjkang.domain.AnswerRepository;
import my.hjkang.domain.User;

@Service
public class AnswerService {
	
	@Autowired
	AnswerRepository answerRepository;

	public Answer save(Answer answer){
		return answerRepository.save(answer);
	}
	
	public Answer findById(long id){
		return answerRepository.findOne(id);
	}
	
	public void delete(long id, User sessionUser){
		Answer answer = findById(id);
		
		if(answer.loginUserAnswerWriterCheck(sessionUser)){
			answerRepository.delete(id);
		}
		
	}
}

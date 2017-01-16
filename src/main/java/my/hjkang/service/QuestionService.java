package my.hjkang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.hjkang.domain.DeleteStatus;
import my.hjkang.domain.DeleteStatusRepository;
import my.hjkang.domain.Question;
import my.hjkang.domain.QuestionRepository;
import my.hjkang.domain.User;

@Service
public class QuestionService {

	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	DeleteStatusRepository deleteStatusRepository;

	public Iterable<Question> findAll() {
		return questionRepository.findAll();
	}

	public void create(Question question) {
		questionRepository.save(question);
	}

	public Question findById(long id) {
		return questionRepository.findOne(id);
	}

	public void delete(long id, User user) throws Exception {
		Question question = findById(id);
		question.delete(user);
		create(question);
		
		deleteStatusCreate(user, question);
	}

	private void deleteStatusCreate(User user, Question question) {
		DeleteStatus deleteStatus = new DeleteStatus();
		deleteStatus.create(question, user);
		deleteStatusRepository.save(deleteStatus);
	}

	/*
	 * public void delete(long id){ questionRepository.delete(id); }
	 */

}

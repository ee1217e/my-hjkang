package my.hjkang.service;

import java.util.List;

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

	public List<Question> findByDeleteStatus() {
		return questionRepository.findByDeleteStatus(0);
	}
	
	public void save(Question question) {
		questionRepository.save(question);
	}

	public Question findById(long id) {
		return questionRepository.findOne(id);
	}

	public void delete(long id, User user) throws Exception {
		Question question = findById(id);
		question.delete(user);
		save(question);
		
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
	
	public void update(long id, User sessionUser, String title, String contents) throws Exception {
		Question question = findById(id);
		question.update(sessionUser, title, contents);
		
		questionRepository.save(question);
	}

}

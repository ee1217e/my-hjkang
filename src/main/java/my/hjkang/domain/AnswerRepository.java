package my.hjkang.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long>{
	void delete(long questionId);
	
	long countByQuestionId(long id);
}

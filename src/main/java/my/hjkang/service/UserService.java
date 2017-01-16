package my.hjkang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.hjkang.domain.User;
import my.hjkang.domain.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public User findById(Long id){
		return userRepository.findOne(id);
	}

	public User findByUserId(String userId){
		return userRepository.findByUserId(userId);
	}
	
	public void create(User user){
		userRepository.save(user);
	}
	
	public Iterable<User> findAll(){
		return userRepository.findAll();
	}
}

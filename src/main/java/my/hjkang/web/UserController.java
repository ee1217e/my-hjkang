package my.hjkang.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import my.hjkang.domain.User;
import my.hjkang.domain.UserRepository;

@Controller
@RequestMapping("/users") // 중복 제거
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@PostMapping("")
	public String create(User user){
		System.out.println("★★★★★★★★★"+user.toString());
		userRepository.save(user);
		
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String list(Model model){
		model.addAttribute("users", userRepository.findAll());
		return "user/list";
	}
	
}

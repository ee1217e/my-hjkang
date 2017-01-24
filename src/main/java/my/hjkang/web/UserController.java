package my.hjkang.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import my.hjkang.domain.User;
import my.hjkang.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/form")
	public String form(){
		return "user/form";
	}
	
	@GetMapping("/login")
	public String loginForm(){
		return "user/login";
	}
	
	@PostMapping("/loginOk")
	public String login(String userId, User sessionUser, HttpSession session){
		User user = userService.findByUserId(userId);
		
		if(user == null){
			return "redirect:/users/login";
		}
		if(!user.matchPassword(sessionUser)){
			return "redirect:/users/login";
		}
		
		session.setAttribute("sessionUser", user);
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session){
		session.removeAttribute("sessionUser");
		
		return "redirect:/users/login";
	}

	@PostMapping("")
	public String create(User user){
		log.debug("user : {}", user);
		/*log.debug("user : {}, name : {}", user, "name");*/
		
		userService.create(user);
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String list(Model model, HttpSession session){
		model.addAttribute("users", userService.findAll());
		
		return "user/list";
	}
	
	@GetMapping("/{id}/updateForm")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session){
		if(!HttpSessionUtils.isLoginUser(session)){
			return "redirect:/users/login";
		}
		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		if(!sessionUser.matchId(id)){
			throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
		}
		
		User user = userService.findById(id);
		
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	
	@PostMapping("/{id}/update")
	public String update(@PathVariable Long id, User updateUser, HttpSession session){
		if(!HttpSessionUtils.isLoginUser(session)){
			return "redirect:/users/login";
		}
		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		if(!sessionUser.matchId(id)){
			throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
		}
		
		User user = userService.findById(id);
		user.update(updateUser);
		userService.create(user);
		
		return "redirect:/users";
	}
}

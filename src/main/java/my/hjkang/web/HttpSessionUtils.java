package my.hjkang.web;

import javax.servlet.http.HttpSession;

import my.hjkang.domain.User;

public class HttpSessionUtils {
	public static final String USER_SESSION_KEY = "sessionUser";
	
	public static boolean isLoginUser(HttpSession session){
		Object sessionUser = session.getAttribute(USER_SESSION_KEY);
		if(sessionUser == null){
			return false;
		}
		return true;
	}
	
	public static User getUserFromSession(HttpSession session){
		if(!isLoginUser(session)){
			return null;
		}
		
		User sessionUser = (User)session.getAttribute(USER_SESSION_KEY);
		return sessionUser;
	}
}

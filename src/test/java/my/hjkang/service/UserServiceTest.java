package my.hjkang.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import my.hjkang.domain.User;
import my.hjkang.domain.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	
	@Mock private UserRepository userRepository;
	
	@InjectMocks private UserService userService;
	
	@Test
	public void findOne() {
		User newUser = new User(1L, "hjkang", "abc", "강현지", "hjkang@rockplace.co.kr");
		// 인자값이 있는 경우는 when()
		when(userRepository.findOne(1L)).thenReturn(newUser);
		User user = userService.findById(1L);
		
		assertEquals(newUser, user);
	}
	
	@Test
	public void create() throws Exception {
		User user = new User(1L, "hjkang", "abc", "강현지", "hjkang@rockplace.co.kr");
		userService.create(user);
		// 인자값이 없는 경우는 verify()
		verify(userRepository).save(user);
	}

}

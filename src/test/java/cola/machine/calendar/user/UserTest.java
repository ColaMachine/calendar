package cola.machine.calendar.user;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cola.machine.calendar.user.bean.User;
import cola.machine.calendar.user.service.UserService;

public class UserTest {
	UserService userService = null;

	@Before
	public void init() {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"\\config\\xml\\applicationContext.xml");
		userService = (UserService) context.getBean("userService");
	}
/*
	@Test
	public void countAll() {
		try {
			int num = userService.countAll();
			System.out.println(num);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void userSaveTest() {

		User user = new User();
		user.setCo_user_email("371452875@qq.com");
		user.setCo_user_loginname("371452875@qq.com");
		user.setCo_user_pwd("321");
		user.setCo_user_id("321");
		user.setCo_user_name("371452875@qq.com");
		userService.saveUser(user);

	}

	@Test
	public void getUserByLoginName() {

		User user = userService.getUserByLoginName("371452875@qq.com");
		assertEquals(null, user.getCo_user_loginname(), "371452875@qq.com");
		System.out.println("查找到的username 为：" + user.getCo_user_loginname());

	}

	@Test
	public void getUserByUseridTest() {
		User user = userService.getUserByUserid("321");
		System.out.println(user.getCo_user_email());
	}

	@Test
	public void getUserByEmailTest() {
		User user = userService.getUserByEmail("371452875@qq.com");
		System.out.println(user.getCo_user_email());
	}
	
	@Test
	public void getUserByParamTest() {
		HashMap map =new HashMap();
		map.put("co_user_email", "371452875@qq.com");
		List list= userService.getUsersByParam(map);
		assertEquals(null, list.size(),1);
	}*/
}

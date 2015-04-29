package cola.machine.calendar.user.service;

import java.util.HashMap;
import java.util.List;

import cola.machine.calendar.user.bean.User;
import cola.machine.calendar.user.dao.UserMapper;



public interface UserService {
	/**
	*
	*/
	public void setUserMapper(UserMapper userMapper);
	public User getUserByLoginName(String loginname );
	public User saveUser(User user );
	//public User getUserByName(String name );
	public User getUserByEmail(String name );
	public User getUserByUserid(String id ); 
	public User addUser(User user);
	
	//public User loginValid(User user);
	public List<HashMap>  getUsersByParam(HashMap map);
	public int countAll();
	/**
	 * 
	 * @param loginname
	 * @param UnencryptedPwd
	 * @return
	 */
	HashMap<String, String> loginValid(String loginname, String UnencryptedPwd);
	public HashMap saveRegisterUser(User user); 
}

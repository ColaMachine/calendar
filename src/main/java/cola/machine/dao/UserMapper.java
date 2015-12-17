package cola.machine.dao;

import java.util.HashMap;
import java.util.List;

import cola.machine.bean.User;

public interface UserMapper {
	public int countAll();
	
	public User selectUserByUserId(String userId);

	public User selectUserByLoginName(String loginname);

	public void insertUser(User user);

	public void updateUser(User user);

	public void deleteUser(int userId);

	public List<User> selectAll();

	public List<User> fuzzyQuery(String name);

	public User selectUserByEmail(String name);
	
	public List<HashMap> getUsersByParam(HashMap map);

	public void restPwd(User user);
}

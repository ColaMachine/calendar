package cola.machine.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.action.ResultDTO;
import cola.machine.bean.User;
import cola.machine.dao.UserMapper;



/**
 * @author colamachine
 *
 */
/**
 * @author colamachine
 *
 */
/**
 * @author colamachine
 *
 */
/**
 * @author colamachine
 *
 */
/**
 * @author colamachine
 *
 */
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
	public List<Map>  getUsersByParam(Map map);
	public int countAll();

	/**
	 * 说明:登录校验 用户名和密码
	 * @param loginname
	 * @param UnencryptedPwd
	 * @return
	 * @author dozen.zhang
	 * @date 2015年5月14日下午2:01:16
	 */
	ResultDTO loginValid(String loginname, String UnencryptedPwd);
	/**
	 * 说明:保存注册信息
	 * @param user
	 * @return
	 * @author dozen.zhang
	 * @date 2015年5月14日下午1:57:08
	 */
	public ResultDTO saveRegisterUser(User user);
	
	/**注册完成发送激活邮件
	 * 说明:
	 * @param email
	 * @return
	 * @author dozen.zhang
	 * @date 2015年5月14日下午1:56:40
	 */
	public ResultDTO saveSenPwdrstEmail(String email);
	
	/**
	 * 说明:激活指定账户
	 * @param activeid
	 * @return
	 * @author dozen.zhang
	 * @date 2015年5月14日下午2:55:20
	 */
	public ResultDTO updateUserActive(String activeid);
	/**
	 * 说明:密码重置
	 * @param pwd
	 * @param code
	 * @return
	 * @author dozen.zhang
	 * @date 2015年5月20日下午4:55:50
	 */
	public ResultDTO savePwdrst(String pwd, String code); 
}

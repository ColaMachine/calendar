package cola.machine.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public List<Map> getUsersByParam(Map map);

	public void restPwd(User user);
	
	

    
    int deleteByPrimaryKey(String userid);

    
    int insert(User record);

   
    int insertSelective(User  record);

    
    User  selectByPrimaryKey(String id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param User  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 说明:根据主键修改record完整内容
     * @param User  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(User record);

    /**
     * 说明:根据map查找bean结果集
     * @param User  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<User> listByParams(Map map);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param User  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<User> listByParams4Page(Map map);
    
    /**
     * 说明:根据map查找map结果集
     * @param User  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(User record);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param User  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<User> selectBeanByMap4Page(HashMap map);
    
    int countByBean(User record);*/
    
    int countByParams(HashMap map);
      
}

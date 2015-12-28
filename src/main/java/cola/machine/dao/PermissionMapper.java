package cola.machine.dao;

import java.util.HashMap;
import java.util.List;

import cola.machine.bean.Permission;

public interface PermissionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_permission
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_permission
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    int insert(Permission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_permission
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    int insertSelective(Permission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_permission
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    Permission selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_permission
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    int updateByPrimaryKeySelective(Permission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_permission
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    int updateByPrimaryKey(Permission record);

    /**
     * 说明:全部查询
     * @return
     * @return list<cola.machine.service.Permission>
     * @author dozen.zhang
     * @date 2015年12月21日下午5:01:39
     */
    List<Permission> selectByParam(HashMap map);
}
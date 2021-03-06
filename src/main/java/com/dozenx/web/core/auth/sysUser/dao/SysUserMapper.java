package com.dozenx.web.core.auth.sysUser.dao;

import com.dozenx.web.core.auth.sysUser.bean.SysUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SysUserMapper {


    /**
     * @Author: dozen.zhang
     * @Description:删除接口调用
     * @Date: 2018/2/7
     */
    int deleteByPrimaryKey(Long id);


    int insert(SysUser record);


    int insertSelective(SysUser record);


    SysUser  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param sysUser
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysUser sysUser);

    /**
     * 说明:根据主键修改record完整内容
     * @param sysUser
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysUser sysUser);

    /**
     * 说明:根据map查找bean结果集
     * @param sysUser
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysUser> listByParams(Map sysUser);

    /**
     * 说明:根据bean查找bean结果集
     * @param sysUser
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysUser> listByParams4Page(Map sysUser);


    /**
     * 说明:根据bean查找bean结果集
     * @param sysUser
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<HashMap<String,Object>> listRoleByParams4Page(Map sysUser);
//    SysUser   selectWithRoleInfoByPrimaryKey(Long id);
    /**
     * 说明:根据map查找map结果集
     * @param sysUser
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysUser sysUser);*/


    /**
     * 说明:根据map查找map结果集
     * @param sysUser
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysUser> selectBeanByMap4Page(HashMap map);

    int countByBean(SysUser record);*/

    int countByParams(HashMap map);

    int countByOrParams(HashMap map);






}

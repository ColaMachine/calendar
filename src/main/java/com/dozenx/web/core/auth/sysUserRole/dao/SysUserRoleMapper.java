package com.dozenx.web.core.auth.sysUserRole.dao;

import com.dozenx.web.core.auth.sysUserRole.bean.SysUserRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SysUserRoleMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(SysUserRole record);

   
    int insertSelective(SysUserRole record);

    
    SysUserRole  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param sysUserRole
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysUserRole sysUserRole);

    /**
     * 说明:根据主键修改record完整内容
     * @param sysUserRole
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysUserRole sysUserRole);

    /**
     * 说明:根据map查找bean结果集
     * @param sysUserRole
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysUserRole> listByParams(Map sysUserRole);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param sysUserRole
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysUserRole> listByParams4Page(Map sysUserRole);
    
    /**
     * 说明:根据map查找map结果集
     * @param sysUserRole
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysUserRole sysUserRole);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param sysUserRole
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysUserRole> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysUserRole record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
    int deleteExtra(HashMap map);
}

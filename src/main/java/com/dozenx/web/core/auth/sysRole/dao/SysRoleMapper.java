package com.dozenx.web.core.auth.sysRole.dao;

import com.dozenx.web.core.auth.sysRole.bean.SysRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SysRoleMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(SysRole record);

   
    int insertSelective(SysRole record);

    
    SysRole  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param sysRole
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysRole sysRole);

    /**
     * 说明:根据主键修改record完整内容
     * @param sysRole
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysRole sysRole);

    /**
     * 说明:根据map查找bean结果集
     * @param sysRole
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysRole> listByParams(Map sysRole);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param sysRole
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysRole> listByParams4Page(Map sysRole);
    
    /**
     * 说明:根据map查找map结果集
     * @param sysRole
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysRole sysRole);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param sysRole
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysRole> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysRole record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}

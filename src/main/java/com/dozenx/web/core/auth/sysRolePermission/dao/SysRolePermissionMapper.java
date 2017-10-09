package com.dozenx.web.core.auth.sysRolePermission.dao;

import com.dozenx.web.core.auth.sysRolePermission.bean.SysRolePermission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SysRolePermissionMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(SysRolePermission record);

   
    int insertSelective(SysRolePermission record);

    
    SysRolePermission  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param sysRolePermission
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysRolePermission sysRolePermission);

    /**
     * 说明:根据主键修改record完整内容
     * @param sysRolePermission
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysRolePermission sysRolePermission);

    /**
     * 说明:根据map查找bean结果集
     * @param sysRolePermission
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysRolePermission> listByParams(Map sysRolePermission);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param sysRolePermission
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysRolePermission> listByParams4Page(Map sysRolePermission);
    
    /**
     * 说明:根据map查找map结果集
     * @param sysRolePermission
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysRolePermission sysRolePermission);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param sysRolePermission
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysRolePermission> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysRolePermission record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
    int deleteExtra(HashMap map);
}

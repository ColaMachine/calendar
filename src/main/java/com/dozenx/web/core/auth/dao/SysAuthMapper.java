package com.dozenx.web.core.auth.dao;

import com.dozenx.web.core.auth.sysPermission.bean.SysPermission;

import java.util.List;


public interface SysAuthMapper {
    
    /**
     * 说明:根据用户id获取所有权限资源
     * @param id
     * @return
     * @return List<SysResource>
     * @author dozen.zhang
     * @date 2016年3月18日下午9:01:44
     */
/*    public List<SysResource> selectResourceByUserId(Long id);
    public List<SysResource> selectMenuResourceByUserId(Long id);*/
    public List<SysPermission> selectPermissionByUserId(Long id);
    //public List<SysPermission> selectMenuResourceByUserId(Long id);
}

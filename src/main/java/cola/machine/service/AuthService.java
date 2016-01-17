/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package cola.machine.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cola.machine.bean.Permission;
import cola.machine.bean.Role;
import cola.machine.bean.RolePer;
import cola.machine.bean.UserRole;
import cola.machine.dao.PermissionMapper;
import cola.machine.dao.RoleMapper;
import cola.machine.dao.RolePerMapper;
import cola.machine.dao.UserRoleMapper;
import cola.machine.util.CacheUtil;
import cola.machine.util.ResultUtil;
import cola.machine.util.StringUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.ValidateUtil;
import core.page.Page;
import core.action.ResultDTO;

@Service("authService")
public class AuthService extends BaseService {
	private static final Logger logger = LoggerFactory
			.getLogger(AuthService.class);
	@Resource
	private RoleMapper roleMapper;
	@Resource
	private PermissionMapper permissionMapper;
@Resource
private RolePerMapper rolePerMapper;
@Resource
private UserRoleMapper userRoleMapper;
	/**
	 * 说明:
	 * 
	 * @param page
	 * @return
	 * @return List<Role>
	 * @author dozen.zhang
	 * @date 2015年11月15日下午12:36:24
	 */
	public List<Role> listRole(Page page) {
		HashMap map = new HashMap();
		map.put("page", page);
		return roleMapper.selectByPage(map);
	}
	
	
	
	/*
	*//**
	 * 说明:
	 * 
	 * @param id
	 * @param roleName
	 * @param remarks
	 * @param permissions
	 * @return
	 * @return Object
	 * @author dozen.zhang
	 * @date 2015年11月15日下午1:33:54
	 */
	public ResultDTO saveRoleAndPermission(String id, String roleName,
			String remark, String permissions) {
		int serviceCode =001;
		if (StringUtil.isBlank(roleName)) {
			logger.info("err.param.role.remark");
			return ResultUtil.getWrongResultFromCfg("err.param.role.name");
		}
		if (StringUtil.isBlank(remark)) {
			logger.info("err.param.role.remark");
			return ResultUtil.getWrongResultFromCfg("err.param.role.remarks");
		}
		CacheUtil.getInstance().clearCache("getPermissionStrByRoleId:" + id);

		CacheUtil.getInstance().clearCache("getPermissionListByRoleId:" + id);
		// 判断是否有相同的角色名

		
		 
		// 进行字段验证
		Role role = new Role();
		role.setName(roleName);
		role.setRemark(remark);
		role.setState("是");
		ValidateUtil<Role> vu = new ValidateUtil<Role>();
		ResultDTO result = vu.valid(role);
		if (result.getR() != 1) {
			// resultDTO.clone(result);
			return result;
		}
		if (StringUtil.isBlank(id)) {
			Role example = new Role();
			int count = roleMapper.countByName(role.getName());
			if (count > 0) {
			    return ResultUtil.getWrongResultFromCfg("err.param.role.roleName.same");
			}
			role.setId(UUIDUtil.getUUID());
			roleMapper.insert(role);
		} else {
			role.setId(id);
			roleMapper.updateByPrimaryKey(role);
		}
		String[] permissionArr = permissions.split(",");
		// 先删除相关的角色对应的数据
		if (!StringUtil.isBlank(id))
			rolePerMapper.deleteByRoleId(role.getId());
		for (String permission : permissionArr) {
			if (permissionMapper.selectByPrimaryKey(
					permission) != null) {
				RolePer rolePermissions = new RolePer();
				rolePermissions.setId(UUIDUtil.getUUID());
				rolePermissions.setPerId(permission);
				rolePermissions.setRoleId(role.getId());
				rolePerMapper.insert(rolePermissions);
			}
		}
		// 如果有同样的角色名称
		// resultDTO.setR(1);
		// resultDTO.setMsg("保存成功");
		return new ResultDTO(1, "保存成功");

	}
	

    /**
     * 说明:根据角色取对应权限关系
     * 用于角色编辑
     * @param id 角色id
     * @return
     * @return HashMap
     * @author dozen.zhang
     * @date 2015年12月18日下午10:32:38
     */
    public ResultDTO getRoleAndPermissions(String id) {
        Role role = roleMapper.selectByPrimaryKey(id);
        List<RolePer> permissions = rolePerMapper.selectByRoleId(id);
        String[] permissionIds = new String[permissions.size()];
        for (int i = 0; i < permissions.size(); i++) {
            permissionIds[i] = permissions.get(i).getPerId();
        }
        HashMap returnMap = new HashMap();
        returnMap.put("id", role.getId());
        returnMap.put("name", role.getName());
        returnMap.put("remark", role.getRemark());
        returnMap.put("permissionids", permissionIds);
        return ResultUtil.getResult(returnMap);
    }
    
    /**
     * 根据角色id 删除角色
     * 
     * @param id 参数
     * @throws Exception 抛出异常
     */
    public ResultDTO deleteRoleById(String id)  {
        // 当删除角色的时候如果发现用关联用户不删除角色
        roleMapper.deleteByPrimaryKey(id);
        // 删除对应的user_role数据
      /*  UserRolesExample example = new UserRolesExample();
        com.opms.entity.system.UserRolesExample.Criteria c = example.createCriteria();
        c.andRoleIdEqualTo(id);*/
        userRoleMapper.deleteByRoleId(id);
        // 删除对应的role permission关系数据
        rolePerMapper.deleteByRoleId(id);
        //清空角色 对应的权限 缓存
        CacheUtil.getInstance().clearCache("getPermissionStrByRoleIdWithCache:"+id);
        return ResultUtil.getSuccResult();
    }
    /**
     * 根据用户id 删除角色用户对应关系
     * @param id 参数
     * @throws Exception 抛出异常
     */
    public void deleteUserRoleByUserId(String id) throws Exception {
        // 删除对应的user_role数据
        userRoleMapper.deleteByUserId(id);
    }
 
    
    /**
     * @param id 参数
     * @return int
     * @throws Exception 抛出异常
     */
    public int countUserByRoleId(String id) throws Exception {
        // 当删除角色的时候如果发现用关联用户不删除角色
        int count = userRoleMapper.countByRoleid(id);
        return count;
    }
    
    /**
     * 返回所有角色列表，根据用户id同时返回该用户是否具有该角色的信息
     * 
     * @param userId 用户id
     * @return List
     * @author 宋展辉 2015年10月26日 下午2:17:51
     */
    public List<Role> listByUserId(Long userId) {
        List<Role> roles = roleMapper.selectAllByUserId(userId);
        return roles;
    }
    
  
    /**
     * 说明:判断某用户是否拥有该角色
     * @param userId 用户id
     * @param roleId 角色id
     * @return
     * @return boolean
     * @author dozen.zhang
     * @date 2015年12月18日下午11:33:17
     */
    public boolean checkRole(long userId, long roleId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("roleId", roleId);
        int count = roleMapper.countByUserIdAndRoleId(map);
        return count >= 1;
    }
    /**
     * @return List
     */
    public List<Role> findAllRole() {
        return roleMapper.selectAllRole();
    }
    /**
     * 修改用户角色分配
     * 
     * @param userId 用户id
     * @param roleIds 角色id
     * @throws Exception 抛出异常
     * @author 宋展辉 2015年10月26日 下午3:43:00
     */
    public void updateUserRole(String userId, String roleIds) throws Exception {
        userRoleMapper.deleteByUserId(userId);
        if (!StringUtil.isEmpty(roleIds)) {
            String[] ARole = roleIds.split(",");
            UserRole userRole = new UserRole();
            for (int i = 0; i < ARole.length; i++) {
                userRole.setUserId(userId);
                userRole.setRoleId(ARole[i]);
                userRoleMapper.insertSelective(userRole);
            }
        }
    }

    /**
     * 根据用户id 获取角色
     * 
     * @param id 参数
     * @return List
     */
    public List<UserRole> getUserRolesByUserid(String id) {
        return userRoleMapper.selectByUserId(id);
    }

    /**
     * 根据用户id 获取角色
     * 
     * @param id 参数
     * @return List
     */
    public List<Role> getRoleListByUserid(String id) {
        return roleMapper.selectByUserId(id);
    }
    /**
     * 根据用户id 获取角色
     * 
     * @param id 参数
     * @return Long[]
     */
    public String[] getRoleIdsByUseridWithCache(String id) {
        Object object = CacheUtil.getInstance().readCache("getRoleIdsByUserid:"+id, Long[].class);
                
        if(object==null){
            List<UserRole> userRoles= this.getUserRolesByUserid(id);
            String[] s = new String[userRoles.size()];
            for (int i=0;i<s.length;i++){
                UserRole userRole = userRoles.get(i);
                s[i]=userRole.getRoleId();
            }
            CacheUtil.getInstance().writeCache("getRoleIdsByUseridWithCache:"+id, s);
            return s;
        }
        String[] roleIds=(String[])object ;
        return roleIds;
    }
    
    /**
     * 根据用户id 获取角色
     * 
     * @param id 参数
     * @return List<Role>
     */
    public List<Role> getRoleListByUseridWithCache(String id) {
        Object object = CacheUtil.getInstance().readCache("getRoleListByUserid:"+id, List.class);
                
        if(object==null){
            List<Role> userRoles= this.getRoleListByUserid(id);
            CacheUtil.getInstance().writeCache("getRoleListByUserid:"+id, userRoles);
            return userRoles;
        }
        return (List<Role>)object;
    }



    /**
     * 说明:
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年12月21日下午5:00:53
     */
    public List<Permission> listPermissions(HashMap map) {
       return  permissionMapper.selectByParam(map);
    }



    /**
     * 说明:保存permission
     * @param permission
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年12月21日下午5:43:58
     */
    public Object savePermission(Permission permission) {
        if(StringUtil.isBlank(permission.getId())){
            permission.setId(UUIDUtil.getUUID());
            permissionMapper.insert(permission);
        }else{
            permissionMapper.updateByPrimaryKey(permission);
        }
        return ResultUtil.getSuccResult();
    }


    /**
     * 说明:删除permission
     * @param id
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年12月21日下午5:45:39
     */
    public Object deletePermission(String id) {
        permissionMapper.deleteByPrimaryKey(id);
        return ResultUtil.getSuccResult();
    }
    
    /**
     * 根据用户id 获取角色
     * 
     * @param id 参数
     * @return List
     */
    /*public List<UserRoles> getRoleByUseridInSession(Integer id) {
        UserRolesExample example = new UserRolesExample();
        com.opms.entity.system.UserRolesExample.Criteria c = example.createCriteria();
        c.andUserIdEqualTo((long) id);
        return centerRbacUserRolesMapper.selectByExample(example);
    }*/

    // 缓存所有的角色对应权限
/*    public void cacheAllRolePermissions() {
        List<Role> roleList = findAllRole();
        for (Role role : roleList) {
            Long roleId = role.getId();
            String permissions = permissionService.getPermissionsStrByRoleId(roleId);
            CacheUtil.getInstance().writeCache("rolepermissions:" + role.getId(), permissions);
        }
    }*/

/*    public void updatePermissionSByRoleIdInCache(Long roleId) {
        String permissions = permissionService.getPermissionsStrByRoleId(roleId);
        CacheUtil.getInstance().writeCache("rolepermissions:" + roleId, permissions);
    }
*/
    // 根据用户获取对应角色 并缓存在用户session 中
    /*public void cacheRoleInSession(HttpServletRequest request, Integer userId) {

        List<UserRoles> roleList = this.getRoleByUserid(userId);
        Long[] s = new Long[roleList.size()];
        for (int i = 0; i < roleList.size(); i++) {
            UserRoles roles = roleList.get(i);
            s[i] = roles.getRoleId();
        }
        request.getSession().setAttribute("roleids", s);
    }*/


    /**
     * 根据用户id查询用户角色
     * @param userid 参数
     * @return UserRoles
     * xhb
     */
    /*public UserRoles searchById(Long userid) {
        return centerRbacUserRolesMapper.selectByUserid(userid);
    }*/
}

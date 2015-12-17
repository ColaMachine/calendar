/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package cola.machine.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.awifi.core.page.Page;
import com.awifi.util.StringUtils;

import core.action.ResultDTO;
import cola.machine.bean.Role;
import cola.machine.bean.RolePer;
import cola.machine.core.msg.ErrorType;
import cola.machine.dao.PermissionMapper;
import cola.machine.dao.RoleMapper;

@Service("authService")
public class AuthService extends BaseService {
	private static final Logger logger = LoggerFactory
			.getLogger(AuthService.class);
	@Resource
	private RoleMapper roleMapper;
	@Resource
	private PermissionMapper permissionMapper;

	/**
	 * 说明:
	 * 
	 * @param page
	 * @return
	 * @return List<Role>
	 * @author dozen.zhang
	 * @date 2015年11月15日下午12:36:24
	 */
	/*public List<Role> listRole(Page page) {
		HashMap map = new HashMap();
		map.put("page", page);
		return roleMapper.selectByPage(map);
	}

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
	 *//*
	public ResultDTO saveRoleAndPermission(String id, String roleName,
			String remarks, String permissions) {
		int serviceCode =001;
		if (StringUtils.isBlank(roleName)) {
			logger.info("err.param.role.remarks");
			return MsgUtil.createResultDTOByErroCode("err.param.role.roleName");
		}
		if (StringUtils.isBlank(remarks)) {
			logger.info("err.param.role.remarks");
			return MsgUtil.createResultDTOByErroCode("err.param.role.remarks");
		}
		CacheUtil.getInstance().clearCache("getPermissionStrByRoleId:" + id);

		CacheUtil.getInstance().clearCache("getPermissionListByRoleId:" + id);
		// 判断是否有相同的角色名

		
		 * if(centerRbacRoleMapper.selectByName(roleName)>0){ throw new
		 * Exception("err.param.role.rolename.same"); }
		 
		// 进行字段验证
		Role role = new Role();
		role.setName(roleName);
		role.setRemark(remarks);
		role.setState("是");
		ValidateUtil<Role> vu = new ValidateUtil<Role>();
		ResultDTO result = vu.valid2(role);
		if (result.getR() != 1) {
			// resultDTO.clone(result);
			return result;
		}
		if (StringUtils.isBlank(id)) {
			Role example = new Role();
			Criteria c = example.createCriteria();
			c.andRoleNameEqualTo(role.getRoleName());
			int count = centerRbacRoleMapper.countByExample(example);
			if (count > 0) {
				throw new Exception("err.param.role.roleName.same");
			}
			centerRbacRoleMapper.insert(role);
		} else {
			role.setId(Long.valueOf(id));
			centerRbacRoleMapper.updateByPrimaryKey(role);
		}
		String[] permissionArr = permissions.split(",");
		// 先删除相关的角色对应的数据
		if (!StringUtils.isBlank(id))
			centerRbacRolePermissionsMapper.deleteByRoleid(role.getId());
		for (String permission : permissionArr) {
			if (centerRbacPermissionMapper.selectByPrimaryKey(Long
					.valueOf(permission)) != null) {
				RolePer rolePermissions = new RolePer();
				rolePermissions.setPerId(Integer.valueOf(permission));
				rolePermissions.setRoleId(role.getId());
				rolePermissions.setCreateDate(new Date());
				rolePermissions.setStatus(1);
				rolePermissions.setStatusDate(new Date());
				centerRbacRolePermissionsMapper.insert(rolePermissions);
			}
		}
		// 如果有同样的角色名称
		// resultDTO.setR(1);
		// resultDTO.setMsg("保存成功");
		return new ResultDTO(1, "保存成功");

	}*/
}

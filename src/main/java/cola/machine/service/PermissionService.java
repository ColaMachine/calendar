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
import cola.machine.dao.PermissionMapper;
import cola.machine.util.CacheUtil;
import cola.machine.util.ResultUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.ValidateUtil;

import com.awifi.core.page.Page;
import com.awifi.util.StringUtils;

import core.action.ResultDTO;

@Service("permissionService")
public class PermissionService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(PermissionService.class);
    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 说明:list by page and params
     * @param page
     * @return
     * @return List<Role>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<Permission> list(HashMap params) {
        return permissionMapper.selectByPage(params);
    }
    
    /*
    *//**
     * 说明:
     * @param Permission
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(Permission permission) {
        // 进行字段验证
       ValidateUtil<Permission> vu = new ValidateUtil<Permission>();
        ResultDTO result = vu.valid(permission);
        if (result.getR() != 1) {
            return result;
        }
         //逻辑业务判断判断
       
       //判断是更新还是插入
        if (permission.getId()==null) {
            permissionMapper.insert(permission);
        } else {
             permissionMapper.updateByPrimaryKey(permission);
        }
        return new ResultDTO(1, "保存成功");
    }
    
    /**
     * 说明:根据主键删除数据
     * description:delete by key
     * @param id
     * @return void
     * @author dozen.zhang
     * @date 2015年12月27日下午10:56:38
     */
    public void delete(String id){
        permissionMapper.deleteByPrimaryKey(id);
    }
}


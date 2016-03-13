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

import cola.machine.bean.ZUser;
import cola.machine.dao.ZUserMapper;
import cola.machine.util.CacheUtil;
import cola.machine.util.ResultUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.StringUtil;
import core.page.Page;


import core.action.ResultDTO;

@Service("zUserService")
public class ZUserService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(ZUserService.class);
    @Resource
    private ZUserMapper zUserMapper;

    /**
     * 说明:list by page and params
     * @param page
     * @return
     * @return List<Role>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<ZUser> listByParams4Page(HashMap params) {
        return zUserMapper.listByParams4Page(params);
    }
     public List<ZUser> listByParams(HashMap params) {
        return zUserMapper.listByParams(params);
    }
    /*
    *//**
     * 说明:
     * @param ZUser
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(ZUser zUser) {
        // 进行字段验证
       ValidateUtil<ZUser> vu = new ValidateUtil<ZUser>();
        ResultDTO result = vu.valid(zUser);
        if (result.getR() != 1) {
            return result;
        }
         //逻辑业务判断判断
       
       //判断是更新还是插入
        if (zUser.getId()==null) {
               
            zUserMapper.insert(zUser);
        } else {
             zUserMapper.updateByPrimaryKey(zUser);
        }
        return ResultUtil.getSuccResult();
    }
 
    /**
    * 说明:根据主键删除数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public void delete(Integer  id){
        zUserMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public ZUser selectByPrimaryKey(Integer id){
       return zUserMapper.selectByPrimaryKey(id);
    }
}

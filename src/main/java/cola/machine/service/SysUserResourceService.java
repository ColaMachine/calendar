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

import cola.machine.bean.SysUserResource;
import cola.machine.dao.SysUserResourceMapper;
import cola.machine.util.CacheUtil;
import cola.machine.util.ResultUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.StringUtil;
import core.page.Page;

import core.action.ResultDTO;

@Service("sysUserResourceService")
public class SysUserResourceService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysUserResourceService.class);
    @Resource
    private SysUserResourceMapper sysUserResourceMapper;
    /**
     * 说明:list by page and params
     * @param page
     * @return
     * @return List<Role>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysUserResource> listByParams4Page(HashMap params) {
        return sysUserResourceMapper.listByParams4Page(params);
    }
     public List<SysUserResource> listByParams(HashMap params) {
        return sysUserResourceMapper.listByParams(params);
    }

    /*
     * 说明:
     * @param SysUserResource
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysUserResource sysUserResource) {
        // 进行字段验证
       ValidateUtil<SysUserResource> vu = new ValidateUtil<SysUserResource>();
        ResultDTO result = vu.valid(sysUserResource);
        if (result.getR() != 1) {
            return result;
        }
         //逻辑业务判断判断
       
       //判断是更新还是插入
        if (sysUserResource.getId()==null) {
               
            sysUserResourceMapper.insert(sysUserResource);
        } else {
             sysUserResourceMapper.updateByPrimaryKey(sysUserResource);
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
    public void delete(Long  id){
        sysUserResourceMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SysUserResource selectByPrimaryKey(Long id){
       return sysUserResourceMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sysUserResourceMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}

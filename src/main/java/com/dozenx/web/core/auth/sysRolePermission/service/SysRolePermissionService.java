/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysRolePermission.service;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.dozenx.web.core.auth.sysPermission.bean.SysPermission;
import com.dozenx.web.core.auth.sysPermission.dao.SysPermissionMapper;
import com.dozenx.web.core.auth.sysRole.bean.SysRole;
import com.dozenx.web.core.auth.sysRole.dao.SysRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.core.auth.sysRolePermission.bean.SysRolePermission;
import com.dozenx.web.core.auth.sysRolePermission.dao.SysRolePermissionMapper;
import com.dozenx.util.ResultUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.base.BaseService;

import com.dozenx.web.core.log.ResultDTO;

@Service("sysRolePermissionService")
public class SysRolePermissionService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysRolePermissionService.class);
    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysPermissionMapper sysPermissionMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysRolePermission> listByParams4Page(HashMap params) {
        return sysRolePermissionMapper.listByParams4Page(params);
    }
    public List<SysRolePermission> listByParams(HashMap params) {
        return sysRolePermissionMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return sysRolePermissionMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysRolePermission
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysRolePermission sysRolePermission) {
        // 进行字段验证
      /* ValidateUtil<SysRolePermission> vu = new ValidateUtil<SysRolePermission>();
        ResultDTO result = vu.valid(sysRolePermission);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (sysRolePermission.getId()==null ||  this.selectByPrimaryKey(sysRolePermission.getId())==null) {

            sysRolePermissionMapper.insert(sysRolePermission);
        } else {
            sysRolePermissionMapper.updateByPrimaryKeySelective(sysRolePermission);
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
        sysRolePermissionMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SysRolePermission selectByPrimaryKey(Long id){
       return sysRolePermissionMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sysRolePermissionMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
     /**
         * 多项关联保存
         * @param uids
         * @param rids
         * @return
         */
        public ResultDTO msave(String rids,String pids) {
            if(StringUtil.isBlank(rids)){
                return ResultUtil.getResult(101,"参数错误");
            }

            String[] ridAry= rids.split(",");
            String[] pidAry=pids.split(",");
            Long[] ridAryReal =new  Long[ridAry.length];
            Long[] pidAryReal =new  Long[pidAry.length];
            for(int i=0;i<ridAry.length;i++){
                if(!StringUtil.checkNumeric(ridAry[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                ridAryReal[i]=Long.valueOf(ridAry[i]);
            }
            if(StringUtil.isBlank(pids)){
                pidAryReal=null;
                 pidAry=null;
            }
            if(pidAry!=null)
            for(int i=0;i<pidAry.length;i++){
                if(!StringUtil.checkNumeric(pidAry[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                pidAryReal[i]=Long.valueOf(pidAry[i]);
            }
            //验证父亲id 正确性 是否存在
             if(ridAryReal!=null)
            for(int i=0;i< ridAryReal.length;i++){
                //
                SysRole sysRole = sysRoleMapper.selectByPrimaryKey(ridAryReal[i]);
                if(sysRole==null ){
                    return ResultUtil.getResult(101,"数据不存在");
                }
                //查询的数据不存在
            }
             if(pidAryReal!=null)
            for(int i=0;i<pidAryReal.length;i++){
                 SysPermission sysPermission = sysPermissionMapper.selectByPrimaryKey(pidAryReal[i]);
                //查询的数据不存在
                if(sysPermission==null ){
                    return ResultUtil.getResult(101,"数据不存在");
                }
            }
             HashMap params =new HashMap();
            //验证子id 正确性 是否存在
             if(pidAryReal!=null)
            for(int i=0;i<ridAryReal.length;i++){
                for(int j=0;j<pidAryReal.length;j++){
                   SysRolePermission sysRolePermission =new  SysRolePermission();
                    Long rid =ridAryReal[i];
                    Long pid =pidAryReal[j];
                    //查找是否已经有关联数据了

                    params.put("pid",pid);
                    params.put("rid",rid);
                    int count = sysRolePermissionMapper.countByParams(params);
                    if(count>0)continue;
                    sysRolePermission.setPid(pid);
                    sysRolePermission.setRid(rid);
                    sysRolePermissionMapper.insert(sysRolePermission);
                }
            }
            //删除多余的数据
            params.clear();
            params.put("pids",pidAryReal);
            params.put("rids",ridAryReal);
            sysRolePermissionMapper.deleteExtra(params);
            //delete from SysUserRole where uid in (1,2,3,4,5) and rid not in(1,2,3)
            return ResultUtil.getSuccResult();
        }
}

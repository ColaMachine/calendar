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

import cola.machine.bean.SysUser;
import cola.machine.dao.SysUserMapper;
import cola.machine.util.CacheUtil;
import cola.machine.util.ResultUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.StringUtil;
import core.page.Page;
import java.util.StringTokenizer;
import cola.machine.bean.SysUserRole;
import cola.machine.dao.SysUserRoleMapper;

import core.action.ResultDTO;

@Service("sysUserService")
public class SysUserService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysUserService.class);
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    protected SysUserRoleMapper sysUserRoleMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysUser> listByParams4Page(HashMap params) {
        return sysUserMapper.listByParams4Page(params);
    }
    public List<SysUser> listByParams(HashMap params) {
        return sysUserMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return sysUserMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysUser
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysUser sysUser) {
        // 进行字段验证
       ValidateUtil<SysUser> vu = new ValidateUtil<SysUser>();
        ResultDTO result = vu.valid(sysUser);
        if (result.getR() != 1) {
            return result;
        }
         //逻辑业务判断判断
       //判断是否有uq字段
               HashMap params =new HashMap();
        params.put("telno",sysUser.getTelno());
        int count = sysUserMapper.countByOrParams(params);
        if(StringUtil.isNull(sysUser.getId())&& count>0||count>1 ){
            return ResultUtil.getResult(302,"字段唯一不能重复");
        }

       //判断是更新还是插入
        if (sysUser.getId()==null) {
               
            sysUserMapper.insert(sysUser);
        } else {
             sysUserMapper.updateByPrimaryKey(sysUser);
        }
        return ResultUtil.getSuccResult();
    }
     /*
     * 说明:和关联关系一起保存
     * @param SysUser
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO saveWithChilds(SysUser sysUser,String childids) {
        // 进行字段验证
        ValidateUtil<SysUser> vu = new ValidateUtil<SysUser>();
        ResultDTO result = vu.valid(sysUser);
        if (result.getR() != 1) {
            return result;
        }
         //逻辑业务判断判断
       
        //判断是更新还是插入
        boolean addFlag =false;

        if (sysUser.getId()==null) {
               
            sysUserMapper.insert(sysUser);
            addFlag=true;
        } else {
             sysUserMapper.updateByPrimaryKey(sysUser);
        }

         /***删除掉不在关系中的数据****/
        HashMap params =new HashMap();
        params.put("uid", sysUser.getId());
        List<SysUserRole> list = sysUserRoleMapper.listByParams(params);
        for(SysUserRole sysUserRole : list){
            String roleid = ""+sysUserRole.getRoleid();
            if( !StringUtil.splitStrContains(childids,roleid)){
                sysUserRoleMapper.deleteByPrimaryKey(sysUserRole.getId());
            }
        }
        
        if(!StringUtil.isBlank(childids)){
            StringTokenizer st = new StringTokenizer(childids,",",false);
            while( st.hasMoreElements() ){
                String stNow=  st.nextToken(); 
                //System.out.println(stNow);
                //进行ids验证
                if(!StringUtil.checkNumeric(stNow)){
                    return ResultUtil.getResult(302,"子元素id格式不正确");
                }
                //查询是否有关联数据有的话就不用再插入了 如果是新增数据就不用判断了
                if(!addFlag){
                    params.clear();
                    params.put("uid", sysUser.getId());
                    params.put("roleid", stNow);
                    int count = sysUserRoleMapper.countByParams(params);
                    if(count>0){
                        continue;
                    }
                    
                }
                SysUserRole sysUserRole=new SysUserRole();
              
                sysUserRole.setUid(sysUser.getId());
                sysUserRole.setRoleid(Long.valueOf(stNow));
                sysUserRoleMapper.insert( sysUserRole);
            }
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
        sysUserMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SysUser selectByPrimaryKey(Long id){
       return sysUserMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sysUserMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}

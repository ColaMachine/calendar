/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysUser.service;

import com.dozenx.util.*;
import com.dozenx.web.core.auth.sysRole.bean.SysRole;
import com.dozenx.web.core.auth.sysRole.service.SysRoleService;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.auth.sysUser.dao.SysUserMapper;
import com.dozenx.web.core.auth.sysUserRole.service.SysUserRoleService;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.location.service.LocationService;
import com.dozenx.web.core.log.ErrorMessage;
import com.dozenx.web.core.log.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysUserService")
public class SysUserService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysUserService.class);
    @Resource
    private SysUserMapper sysUserMapper;
    /** 省市区服务**/
    @Resource
    private LocationService locationService;
    /** 用户角色服务**/
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysUser> listByParams4Page(Map params) {
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

        //逻辑业务判断判断
        //判断是否有uq字段

        //TODO 通过手机号码查用户信息 需要独立出来一个方法
        HashMap params =new HashMap();
        params.put("telno",sysUser.getTelno());
        int count = sysUserMapper.countByOrParams(params);
        if(StringUtil.isNull(sysUser.getId())&& count>0 ){
            return ResultUtil.getResult(10002001,ErrorMessage.getErrorMsg("err.user.telno.repeat"));
        }
        int result;
        //判断是更新还是插入
        if (sysUser.getId()!=null &&  this.selectByPrimaryKey(sysUser.getId())==null) {
            return ResultUtil.getResult(10003002, ErrorMessage.getErrorMsg("err.db.not.find.msg"));
        }
        if (sysUser.getId()==null ) {
            sysUser.setStatus(1);
            sysUser.setCreatetime(DateUtil.getNowTimeStamp());
            sysUser.setUpdatetime(sysUser.getCreatetime());
            result =sysUserMapper.insertSelective(sysUser);
        } else {
            sysUser.setUpdatetime(new Timestamp(new Date().getTime()));
            result = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        }

        return  ResultUtil.getResult(result-1);

    }

    /*
    * 说明:
    * @param SysUser
    * @return
    * @return Object
    * @author dozen.zhang
    * @date 2015年11月15日下午1:33:54
    */
    public ResultDTO saveWithRoleInfo(SysUser sysUser) {

        ResultDTO result = this.save(sysUser);
        if(!result.isRight()){
            return result;

        }

        Long id  =sysUser.getId();
        Long[] roleId = sysUser.getRoleIds();
        sysUserRoleService.batchUpdate(new Long[]{id},roleId);
        return  ResultUtil.getSuccResult();
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

    /**
     * @Author: dozen.zhang
     * @Description:附加了角色的查询信息
     * @Date: 2018/2/26
     */
    public List<HashMap<String,Object>> listRoleByParams4Page(Map params) {

        //顺便加上一个地区信息
        List<HashMap<String,Object> >  list = sysUserMapper.listRoleByParams4Page(params);
        for(HashMap<String,Object>  map : list){
            Long province = MapUtils.getLong(map,"province");
            String location="";
            if(province !=null){
                String provinceName = locationService.getNameById(province);
                if(provinceName!=null)
                    location+=provinceName;
            }
            Long city = MapUtils.getLong(map,"city");
            if(city !=null){
                String cityName = locationService.getNameById(city);
                if(cityName!=null)
                    location+=cityName;
            }
            Long county = MapUtils.getLong(map,"county");
            if(county !=null){
                String countyName = locationService.getNameById(county);
                if(countyName!=null){
                    location+=countyName;
                }
            }

            //查找出该用户的角色信息 放入到roleName中 和 roleId当中
            String roleNames ="";
            List<SysRole> roleList = sysRoleService.listByUserId(MapUtils.getLong(map,"id"));

            // Long roleIdAry[] = new Long[roleList.size()];
            if(roleList!=null && roleList.size()>0){
                for(int i=0;i<roleList.size();i++){
                    //  roleIdAry[i] = roleList.get(i).getId();
                    roleNames+= roleList.get(i).getName()+" ";
                }
            }
            map.put("roleNames",roleNames);
            map.put("location",location);
        }
        return list;
    }

    public SysUser selectWithRoleInfoByPrimaryKey(Long id){


        List<SysRole> roleList = sysRoleService.listByUserId(id);
        Long roleIdAry[] = new Long[roleList.size()];
        if(roleList!=null && roleList.size()>0){
            for(int i=0;i<roleList.size();i++){
                roleIdAry[i] = roleList.get(i).getId();
                // roleNames+= roleList.get(i).getName()+" ";
            }
        }

        SysUser sysUser =  sysUserMapper.selectByPrimaryKey(id);
        sysUser.setRoleIds(roleIdAry);
        return sysUser;
    }

}

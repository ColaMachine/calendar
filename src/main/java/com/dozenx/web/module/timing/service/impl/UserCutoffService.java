/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.timing.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.dozenx.web.module.timing.bean.UserCutoff;
import com.dozenx.web.module.timing.dao.MerchantUserCutoffDateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class UserCutoffService  {
    private static final Logger logger = LoggerFactory.getLogger(UserCutoffService.class);

    @Resource
    private MerchantUserCutoffDateMapper userCutoffMapper;

    
    /**
     * 
    * 迭代XX：根据id删除一条时长信息
    * @Description: deleteByPrimaryKey
    * @param id
    * @return
    * @return Integer   
    * @author zcj
    * @date   2016-11-30 上午9:47:53
     */
    public Integer deleteByPrimaryKey(Long id) {
        
        int a = userCutoffMapper.deleteByPrimaryKey(id);
        return a;
    }
    
   
   /**
    * 
   * 迭代XX：添加一条时长信息
   * @Description: insertSelective
   * @param merchantId
   * @param userId
   * @param cutoffDate
   * @param remarks
   * @return
   * @return Integer    0-失败 1-成功
   * @author zcj
   * @date   2016-11-30 上午10:04:26
    */
    public Integer insertSelective(Long merchantId,Long userId,Date cutoffDate,String remarks ) {
        int result = 0 ;
        if(merchantId == null || userId == null ){
          logger.info("添加时长时商户id和用户id不能为空"+"商户id："+merchantId+"用户id："+userId);
          return result;
        }
        UserCutoff md = new UserCutoff();
        md.setCutoffDate(cutoffDate);
        md.setMerchantId(merchantId);
        md.setRemarks(remarks);
        md.setUserId(userId);
        result = userCutoffMapper.insertSelective(md);
        if(result>0){
            result = 1 ;   
        }
        return result;
    }
    /**
     * 
    * 迭代XX：根据id查询时长记录
    * @Description: selectByPrimaryKey
    * @param id
    * @return
    * @return MerchantUserCutoffDate   
    * @author zcj
    * @date   2016-11-30 上午10:15:50
     */
    public UserCutoff selectByPrimaryKey(Long id) {
        UserCutoff result ;
        result = userCutoffMapper.selectByPrimaryKey(id);
        return result;
    }
    
   /**
    * 
   * 迭代XX：TODO 根据商户id和用户id修改一条时长记录
   * @Description: updateByPrimaryKeySelective
   * @param merchantId
   * @param userId
   * @param cutoffDate
   * @param remarks
   * @return
   * @return Integer     0-失败 1-成功
   * @author zcj
   * @date   2016-11-30 上午10:21:48
    */
    public Integer updateByPrimaryKeySelective(Long id,Long merchantId,Long userId,Date cutoffDate,String remarks) {
        int result = 0 ;
        if(merchantId == null || userId == null ){
          logger.info("添加时长时商户id和用户id不能为空"+"商户id："+merchantId+"用户id："+userId);
          return result;
        }
        UserCutoff md = new UserCutoff();
        md.setId(id);
        md.setCutoffDate(cutoffDate);
        md.setMerchantId(merchantId);
        md.setRemarks(remarks);
        md.setUserId(userId);
        result = userCutoffMapper.updateByPrimaryKeySelective(md);
        if(result>0){
            result = 1 ;   
        }
        return result;
    }
    /**
     * 
    * 迭代XX：根据id修改一条记录
    * @Description: updateByPrimaryKey
    * @param record
    * @return
    * @return Integer   
    * @author zcj
    * @date   2016-11-30 上午10:23:30
     */
    public Integer updateByPrimaryKey(UserCutoff record) {
        int a = userCutoffMapper.updateByPrimaryKey(record);
        return a;
    }
    
    /**
     * 
    * 迭代XX：根据商户id和用户id查询时长记录
    * @Description: selectByPrimaryKey
    * @param map
    * @return
    * @return MerchantUserCutoffDate   
    * @author zcj
    * @date   2016-11-30 上午10:15:50
     */
    public List<UserCutoff> selectBymap(Map<String,Object> map) {
        List<UserCutoff> result  = userCutoffMapper.selectByMap(map);
        return result;
    }
    /**
     * 
    * 迭代XX：统计数据条数
    * @Description: queryCountByParam
    * @param id
    * @param merchantId
    * @param userId
    * @param cutoffDate
    * @param remarks
    * @return
    * @return Integer   
    * @author zcj
    * @date   2016-12-7 下午4:52:18
     */
    public Integer queryCountByParam(Long id,Long merchantId,Long userId,Date cutoffDate,String remarks) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("merchantId", merchantId);
        map.put("userId", userId);
        map.put("cutoffDate", cutoffDate);
        map.put("remarks", remarks);
        int a = userCutoffMapper.queryCountByParam(map);
        return a;
    }

}

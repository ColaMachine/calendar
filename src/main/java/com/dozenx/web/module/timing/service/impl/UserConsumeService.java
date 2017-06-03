package com.dozenx.web.module.timing.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.dozenx.util.DateUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.module.timing.bean.TimeConsume;
import com.dozenx.web.module.timing.bean.UserCutoff;
import com.dozenx.web.module.timing.dao.UserConsumeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 用户消费记录接口实现
 * @author wujh
 *
 */
@Service(value="localUserConsumeService")
public class UserConsumeService{
    
    private static final Logger logger = LoggerFactory.getLogger(UserConsumeService.class);
    
    @Resource
    private UserConsumeMapper userConsumeMapper;
    
    @Resource
    private UserCutoffService userCutoffService;
    
    /**
     * 新增消费记录
     * @param consumeObject
     * @return 
     */
    public int add(TimeConsume consumeObject) throws Exception {
        if (consumeObject.getMerchantId() == null || consumeObject.getUserId() == null
                || consumeObject.getConsumeType() == null) {
            throw new Exception("新增消费数据出错,必要参数缺少");
        }
        //create_date如果为空默认保存当前日期时间
        if(consumeObject.getCreateDate()==null){
            consumeObject.setCreateDate(DateUtil.getNowDate());
        }
        return userConsumeMapper.insertSelective(consumeObject);
    }
    
    /**
     * 查询消费记录列表
     * @param map
     * @return
     */
    public List<TimeConsume> queryListByParam(Map<String,Object> map) throws Exception{
        List<TimeConsume> consumeList = null;
        String consumeType = map.get("consumeType")+"";
        if(!"2".equals(consumeType)&&!"3".equals(consumeType)){
            throw new Exception("根据条件查询消费数据出错:consumeType参数目前仅支持值为2或者3");
        }
        if(map.get("pageNum")!=null&&map.get("pageSize")!=null){
            int pageNum = Integer.valueOf(map.get("pageNum").toString());
            int pageSize = Integer.valueOf(map.get("pageSize").toString());
            if(pageNum<=0||pageSize<=0){
                throw new Exception("根据分页条件查询消费记录出错:分页条件出错");
            }
            map.put("start", (pageNum - 1) * pageSize);
            map.put("pageSize",pageSize);
            consumeList = userConsumeMapper.unionListByParams4Page(map);
        }else{
            consumeList = userConsumeMapper.listByParams(map);
        }
        return consumeList;
    }
    
    /**
     * 查询记录数
     * @param map
     * @return
     */
    public int queryCountByParam(Map<String,Object> map) throws Exception{
    	String consumeType = map.get("consumeType")+"";
    	if(!"2".equals(consumeType)&&!"3".equals(consumeType)){
    		throw new Exception("根据条件查询消费数据出错:consumeType参数目前仅支持值为2或者3");
    	}
    	return userConsumeMapper.unionCountByParams(map);
    }
    
    /**
     * 查询记录列表
     * @param UserConsume
     * @return
     */
    public List<TimeConsume> queryListByParam(TimeConsume consumeObject) throws Exception{
        List<TimeConsume> consumeList = null;
        if(consumeObject==null){
        	throw new Exception("传入的对象为空");
        }
        String consumeType = consumeObject.getConsumeType()+"";
        if(!"2".equals(consumeType)&&!"3".equals(consumeType)){
            throw new Exception("根据条件查询消费数据出错:consumeType参数目前仅支持值为2或者3");
        }
        Map<String,Object> map = new HashMap<String,Object>();
        if (consumeObject.getId()!=null&&consumeObject.getId()> 0) {
            map.put("id", consumeObject.getId());
        }
        if (consumeObject.getMerchantId()!=null&&consumeObject.getMerchantId() > 0) {
            map.put("merchantId", consumeObject.getMerchantId());
        }
        if (consumeObject.getUserId()!=null&&consumeObject.getUserId() > 0) {
            map.put("userId", consumeObject.getUserId());
        }
        if (consumeObject.getConsumeType()!=null&&consumeObject.getConsumeType() > 0) {
            map.put("consumeType", consumeObject.getConsumeType());
        }
        if (consumeObject.getPackageId()!=null&&consumeObject.getPackageId()>0) {
            map.put("packageId", consumeObject.getPackageId());
        }
        map.put("consumeType", consumeObject.getConsumeType());
        if (!StringUtil.isBlank(consumeObject.getOrderId())) {
            map.put("orderId", consumeObject.getOrderId());
        }
        consumeList = userConsumeMapper.listByParams(map);
    	return consumeList;
    }
   
    /**
     * 查询记录数
     * @param UserConsume
     * @return
     */
    public int queryCountByParam(TimeConsume consumeObject) throws Exception{
        if(consumeObject==null){
        	throw new Exception("传入的对象为空");
        }
        String consumeType = consumeObject.getConsumeType()+"";
        if(!"2".equals(consumeType)&&!"3".equals(consumeType)){
            throw new Exception("根据条件查询消费数据出错:consumeType参数目前仅支持值为2或者3");
        }
        Map<String,Object> map = new HashMap<String,Object>();
        if (consumeObject.getId()!=null&&consumeObject.getId()> 0) {
            map.put("id", consumeObject.getId());
        }
        if (consumeObject.getMerchantId()!=null&&consumeObject.getMerchantId() > 0) {
            map.put("merchantId", consumeObject.getMerchantId());
        }
        if (consumeObject.getUserId()!=null&&consumeObject.getUserId() > 0) {
            map.put("userId", consumeObject.getUserId());
        }
        if (consumeObject.getConsumeType()!=null&&consumeObject.getConsumeType() > 0) {
            map.put("consumeType", consumeObject.getConsumeType());
        }
        if (consumeObject.getPackageId()!=null&&consumeObject.getPackageId()>0) {
            map.put("packageId", consumeObject.getPackageId());
        }
        map.put("consumeType", consumeObject.getConsumeType());
        if (!StringUtil.isBlank(consumeObject.getOrderId())) {
            map.put("orderId", consumeObject.getOrderId());
        }
    	return userConsumeMapper.countByParams(map);
    }
    
    /**
     * 增加消费记录并更新上网时限表
     * @param map
     */
//    public void addCompConsume(Map<String,Object> map) throws Exception{
//        if (map.get("merchantId")==null || map.get("userId") ==null || map.get("consumeType") == null || map.get("cutOffDate") == null) {
//            throw new Exception("保存消费记录出错：必要参数缺少");
//        }
//        try {
//            String json = JSON.toJSONString(map);
//            UserConsume consumeObject = JSONObject.parseObject(json, UserConsume.class);
//            this.add(consumeObject);
//            UserCutoff cutoffObject = JSONObject.parseObject(json, UserCutoff.class);
//            List<UserCutoff> list = userCutoffService.selectBymap(map);
//            if(list!=null&&list.size()>0){
//                UserCutoff cutoff = (UserCutoff)list.get(0);
//                userCutoffService.updateByPrimaryKeySelective(cutoff.getId(),cutoff.getMerchantId(),cutoff.getUserId(),cutoff.getCutoffDate(),cutoff.getRemarks());
//            }else{
//                userCutoffService.insertSelective(cutoffObject.getMerchantId(),cutoffObject.getUserId(),cutoffObject.getCutoffDate(),cutoffObject.getRemarks());
//            }
//        } catch (Exception e) {
//            throw e;
//        }
//    }
    
    /**
     * 增加消费记录并更新上网时限表
     * @param map
     */
    public void addCompConsume(TimeConsume userConsume) throws Exception{
    	if (userConsume.getMerchantId() == null || userConsume.getUserId() == null || userConsume.getConsumeType() == null || userConsume.getEndDate() == null) {
    		throw new Exception("保存消费记录出错，缺少必要参数");
    	}
    	try {
			this.add(userConsume);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("merchantId", userConsume.getMerchantId());
			map.put("userId", userConsume.getUserId());
            List<UserCutoff> list = userCutoffService.selectBymap(map);
            if(list!=null&&list.size()>0){
                if(list.size()>1){
                	logger.info("警告：根据商户和用户ID在时限表中查到多条数据"+list.toArray());
                }
            	UserCutoff cutoff = (UserCutoff)list.get(0);
                userCutoffService.updateByPrimaryKeySelective(cutoff.getId(),cutoff.getMerchantId(),cutoff.getUserId(),userConsume.getEndDate(),cutoff.getRemarks());
            }else{
            	userCutoffService.insertSelective(userConsume.getMerchantId(),userConsume.getUserId(),userConsume.getEndDate(),userConsume.getRemarks());
            }
		} catch (Exception e) {
			logger.error("addCompConsume接口出错："+e.getMessage());
			throw e;
		}
    }
    
    
    /**
     * 获取用户消费总金额
     * @param map
     * @return
     * @throws Exception
     */
    public Double getUserTotalPayment(Map<String,Object> map) throws Exception{
        String consumeType = map.get("consumeType")+"";
        if(!"2".equals(consumeType)&&!"3".equals(consumeType)){
            throw new Exception("根据条件查询消费数据出错:consumeType参数目前仅支持值为2或者3");
        }
        return userConsumeMapper.getUserTotalPayment(map);
    }
}

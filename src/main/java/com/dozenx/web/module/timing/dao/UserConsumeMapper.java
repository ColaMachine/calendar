package com.dozenx.web.module.timing.dao;

import com.dozenx.web.module.timing.bean.TimeConsume;

import java.util.List;
import java.util.Map;

/**
 * 用户消费记录
 */
public interface UserConsumeMapper {

    TimeConsume selectByPrimaryKey(Integer id);
    
    int insertSelective(TimeConsume consume);

    int updateByPrimaryKeySelective(TimeConsume consume);

    List<TimeConsume> listByParams(Map<String, Object> map);
    
    List<TimeConsume> listByParams4Page(Map<String, Object> map);
    
    int countByParams(Map<String, Object> map);
    
   List<TimeConsume> unionListByParams4Page(Map<String, Object> map);
    
    int unionCountByParams(Map<String, Object> map);

    Double getUserTotalPayment(Map<String, Object> map);
}

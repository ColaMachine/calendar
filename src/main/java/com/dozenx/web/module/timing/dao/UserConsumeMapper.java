package com.dozenx.web.module.timing.dao;

import com.dozenx.web.module.timing.bean.UserConsume;

import java.util.List;
import java.util.Map;

/**
 * 用户消费记录
 */
public interface UserConsumeMapper {

    UserConsume selectByPrimaryKey(Integer id);
    
    int insertSelective(UserConsume consume);

    int updateByPrimaryKeySelective(UserConsume consume);

    List<UserConsume> listByParams(Map<String, Object> map);
    
    List<UserConsume> listByParams4Page(Map<String, Object> map);
    
    int countByParams(Map<String, Object> map);
    
   List<UserConsume> unionListByParams4Page(Map<String, Object> map);
    
    int unionCountByParams(Map<String, Object> map);

    Double getUserTotalPayment(Map<String, Object> map);
}

package com.dozenx.web.module.timing.dao;


import com.dozenx.web.module.timing.bean.UserCutoff;

import java.util.List;
import java.util.Map;




public interface MerchantUserCutoffDateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserCutoff record);

    int insertSelective(UserCutoff record);

    UserCutoff selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserCutoff record);

    int updateByPrimaryKey(UserCutoff record);
    
    List<UserCutoff> selectByMap(Map<String, Object> map);
    
    int queryCountByParam(Map<String, Object> map);
}
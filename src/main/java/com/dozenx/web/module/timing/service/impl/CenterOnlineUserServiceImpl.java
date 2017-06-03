package com.dozenx.web.module.timing.service.impl;


import java.util.List;
import java.util.Map;

import com.dozenx.web.module.timing.bean.OnlineUser;
import com.dozenx.web.module.timing.dao.CenterOnlineUserMapper;
import com.dozenx.web.module.timing.service.ICenterOnlineUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("CenterOnlineUserService")
public class CenterOnlineUserServiceImpl implements ICenterOnlineUserService {

    /** 
    * @Fields onlineUserMapper 
    */ 
    @Autowired
    private CenterOnlineUserMapper onlineUserMapper;
   
    @Override
    public long add(OnlineUser onlineUser) {
        return onlineUserMapper.add(onlineUser);
    }
    
    @Override
    public void update(OnlineUser onlineUser) {
        onlineUserMapper.update(onlineUser);
    }

    @Override
    public int queryOnlineUserCount(Map<String, Object> map) {
        return onlineUserMapper.queryOnlineUserCount(map);
    }

    @Override
    public OnlineUser queryLastOnlineUser(Map<String, Object> map) {
        return onlineUserMapper.queryLastOnlineUser(map);
    }

    @Override
    public List<OnlineUser> queryVipUserList(Map<String, Object> map) {
        return onlineUserMapper.queryVipUserList(map);
    }

    @Override
    public int queryVipUserCount(Map<String, Object> map) {  
        return onlineUserMapper.queryVipUserCount(map);
    }

    @Override
    public int updateVipUser(OnlineUser onlineUser) {
        return onlineUserMapper.updateVipUser(onlineUser);
    }

}

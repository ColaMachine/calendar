package com.dozenx.web.module.timing.service.impl;


import com.dozenx.web.module.buy.bean.OnlineData;
import com.dozenx.web.module.timing.dao.CenterOnlineDataMapper;
import com.dozenx.web.module.timing.service.ICenterOnlineDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("CenterOnlineDataService")
public class CenterOnlineDataServiceImpl implements ICenterOnlineDataService {

    /** 
    * @Fields onlineDataMapper 
    */ 
    @Autowired
    private CenterOnlineDataMapper onlineDataMapper;
    
    @Override
    public void add(OnlineData onlineData) {
        onlineDataMapper.add(onlineData);
    }

}

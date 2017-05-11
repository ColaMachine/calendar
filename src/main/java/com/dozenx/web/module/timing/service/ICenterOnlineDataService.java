package com.dozenx.web.module.timing.service;


import com.dozenx.web.module.timing.bean.OnlineDataObject;

/**
 * 推送数据记录操作服务
 * @author zhouwx
 * @since 2016-03-24
 */
public interface ICenterOnlineDataService {
    /**
     * 新增推送数据记录
     * @param onlineData 
     * 推送数据对象
     * @return
     */
    void add(OnlineDataObject onlineData);
}

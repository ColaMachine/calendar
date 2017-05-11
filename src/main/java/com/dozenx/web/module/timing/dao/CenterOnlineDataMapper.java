package com.dozenx.web.module.timing.dao;


import com.dozenx.web.module.timing.bean.OnlineDataObject;

/**
 * 保存电渠推送数据记录
 * @author zhouwx
 * @since 2016-03-24
 */
public interface CenterOnlineDataMapper {
    /**
     * 新增推送数据记录
     * @param onlineData
     * 推送数据对象
     * @return
     */
    void add(OnlineDataObject onlineData);
}

package com.dozenx.web.module.timing.service;


import com.dozenx.web.module.timing.bean.OnlineUser;

import java.util.List;
import java.util.Map;



/**
 * 推送用户数据服务
 * @author zhouwx
 * @since 2016-03-24
 */
public interface ICenterOnlineUserService {
    /**
     * 新增推送用户
     * @param onlineUser
     * 推送用户对象
     * @return 成功数量
     */
    long add(OnlineUser onlineUser);
    /**
     * 更新推送用户数据
     * @param onlineUser
     * 推送数据对象
     */
    void  update(OnlineUser onlineUser);
    /**
     * 根据手机号、时间查询有效记录数
     * @param map
     * telephone:手机号码
     * nowTime:当前时间
     * @return
     * 记录数
     */
    int queryOnlineUserCount(Map<String, Object> map);
    /**
     * 根据手机号查询最新记录
     * @param map
     * telephone:手机号码 
     * @return
     * OnlineUserObject 推送用户数据对象
     */
    OnlineUser queryLastOnlineUser(Map<String, Object> map);
    /**
     * 根据条件查询电渠用户数据列表
     * @param map Map<String,Object> map
     * @return
     * List<OnlineUserObject>
     */
    List<OnlineUser> queryVipUserList(Map<String, Object> map);
    /**
     * 根据条件查询电渠用户记录数
     * @param map Map<String,Object> map
     * @return
     * int 记录数
     */
    int queryVipUserCount(Map<String, Object> map);
    /**
     * 更新商户id
     * @param onlineUser 用户信息
     * @return
     * int 更新记录数
     */
    int updateVipUser(OnlineUser onlineUser);
}

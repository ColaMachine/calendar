package com.dozenx.web.third.dbcenter.service;

import com.dozenx.util.JsonUtil;
import com.dozenx.web.third.dbcenter.bean.PubUser;
import com.dozenx.web.third.dbcenter.util.AwifiHttpRequestUtil;
import com.dozenx.web.util.ConfigUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dozen.zhang on 2017/3/1.
 * 数据中心UserBaseService的封装 切勿在此写业务逻辑
 */
@Service
public class UserBaseService {

    /**
     * 根据用户id 请求用户基础信息 调用数据中心 userBaseService 接口 //query　auth user by param
     * @param id userId
     * @return  pubUser 用户基础信息
     * @throws Exception
     */
    public PubUser queryByUserId(Long id) throws Exception {
        String url = ConfigUtil.getConfig("UserBaseService_url")+"user-base/user?params={json}";
        if(id==null){
            throw new Exception("id can't be null in UserBaseService.queryByUserId");
        }
        Map<String,Long> param =new HashMap<>();
        param.put("id",id);
        String jsonStr= AwifiHttpRequestUtil.sendGet(url,param);
        return JsonUtil.toJavaBean(jsonStr,PubUser.class);

    }

    /**
     * 更新用户信息 更新时报报错
     * @param pubUser 用户基础信息
     * @throws Exception
     */
    public void update(PubUser pubUser)throws Exception {
        String url = ConfigUtil.getConfig("UserBaseService_url")+"user-base/user";
        if(pubUser.getId()==null){
            throw new Exception("id can't be null in pubUser");
        }
        String param = JsonUtil.toJsonString(pubUser);
        /*String jsonStr=*/ AwifiHttpRequestUtil.sendGet(url,param);

    }
}

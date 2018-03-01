package com.dozenx.web.third.dbcenter.service;

import com.dozenx.util.JsonUtil;
import com.dozenx.web.third.dbcenter.bean.PubDeviceQueryResult;
import com.dozenx.web.third.dbcenter.util.AwifiHttpRequestUtil;
import com.dozenx.web.util.ConfigUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dozen.zhang on 2017/2/28.
 * 数据中心商户设备服务
 */
@Service
public class MerchantDeviceBaseService {

    /**
     * 2.1.1.6 根据设备查询设备信息
     * GET IP:28850/merchant-device/devicebase/detail?params={json}
     *
     * 方法名				queryDetailById
     方法说明				根据deviceId或者entityId查询设备详情，处理失败异常返回
     输入参数				"String json
     {

     ""deviceId"":""""//设备虚拟编号//(String)
     ""entityId"":"""",//实体设备编号//(Long)

     }
     输出结果				"成功：{""suc"":true,""msg"":""成功"",""rs"":[{PubEntityDetailQueryResult1},{PubEntityDetailQueryResult2}...]}
     失败：{""suc"":false,""msg"":""错误信息"",""code"":""错误编码""}
     若失败错误码如下：
     DBC-201001006001（json解析出错），
     DBC-201001006004（参数check不通过 ），
     DBC-201001006402（查询数据库异常 ），"
     */
    public PubDeviceQueryResult queryDetailById(String id ) throws Exception {
        String url = ConfigUtil.getConfig("MerchantDeviceBaseService_url")+"detail?params={json}";
        Map param =new HashMap();
        param.put("deviceId",id);
        String jsonStr= AwifiHttpRequestUtil.sendGet(url,param);
        List<PubDeviceQueryResult> results = JsonUtil.toList(jsonStr,PubDeviceQueryResult.class);
        if(results!=null && results.size()>0){
            return results.get(0);
        }else{
            return null;
        }

    }
}

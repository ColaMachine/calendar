package com.dozenx.web.third.dbcenter.service;

import com.dozenx.util.JsonUtils;
import com.dozenx.web.module.merchant.bean.CenterPubMerchant;
import com.dozenx.web.third.dbcenter.util.AwifiHttpRequestUtil;
import com.dozenx.web.util.ConfigUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dozen.zhang on 2017/2/27.
 * 数据中心图片基础服务
 */
@Service
public class MerchantBaseService {
    /*@Value("${dbcenter.MerchantBaseService.url}")
    private String url;*/


 /* String json
    {
        "id":11,                   --商户编号
        "type":"xxx"               --层级关系
            --nextLevel 只查当前节点的下一层
        --nextAll查当前节点所有不包括当前
                --nextAllWithThis查当前节点所有包含当前
        "ids":"11,12,13",          --商户编号多个
        "cascadeLevel":0,          --商户层级，
        --0为最高级
        "merchantName":"",         --商户名
        "merchantNameRLike":"",    --商户名,右模糊查询
        "merchantNameLike":"" ,     --商户名,全模糊
        "industry":11,             --行业编号
        "industryRLike":11,        --行业编号,右模糊查询
        "merchantType":1/2,        --1：商客/2：行客如果该字段为空为，默认为1
        "merchantProject":""       --商户所属工程
        "notEqualProjectId":"11"   --排除的工程编号
        "userId":11,               --关系用户编号
        "province":11,             --省
        "city":11,                 --市
        "county":11,               --县
        "town":11,                 --镇
        "village":11,              --村
        "createTimeStart":"",      --开始时间 yyyyMMdd
        "createTimeEnd":"",        --结束时间  yyyyMMdd
        "merchantFlag":"",         --查询商户类别 值:normal 正常查询,prodevice 项目型商户查询 当为空时默认值为normal
        "openTime":"",             --营业开门时间
        "closeTime":"",            --营业开门时间
        "style":"",                --商户样式
        "status":1,                --状态
        "pageNum":1,               --分页开始页从1开始
        "pageSize":20              --每页大小
    }*/
   /* 成功：{"suc":true,"msg":"成功","rs":[{CenterPubMerchant --商户对象},{...},{...}]}
    商户格式参考2.1.1.3的返回
    失败：{"suc":false,"msg":"错误信息","code":"错误编码"}*/
    /**
     * 根据参数查询商户信息
     * @param param  参见上面
     * @return List<CenterPubMerchant> 商户信息列表
     * @author dozhangzheng
     * @2017-2-28 15:21:34
     */
   public List<CenterPubMerchant>  queryListByParam(Map param) throws Exception {

       String url = ConfigUtil.getConfig("MerchantBaseService_url")+"merchant/list?params={json}";
        String jsonStr= AwifiHttpRequestUtil.sendGet(url,param);
        List<CenterPubMerchant> list = JsonUtils.toList(jsonStr,CenterPubMerchant.class);
        return list;
   }
    /**
     * 根据参数查询商户信息
     * @param param  参见上面
     * @return List<CenterPubMerchant> 商户信息列表
     * @author dozhangzheng
     * @2017-2-28 15:21:34
     */
    public CenterPubMerchant  queryById(Long id) throws Exception {

        String url = ConfigUtil.getConfig("MerchantBaseService_url")+"merchant-base/merchant/id?params={json}";
        Map param =new HashMap();
        param.put("id",id);
        String jsonStr= AwifiHttpRequestUtil.sendGet(url,param);
       CenterPubMerchant result = JsonUtils.toJavaBean(jsonStr,CenterPubMerchant.class);
        return result;
    }

    public static void main(String args[]){
        Map param= new HashMap();
        String  url = "http://192.168.41.44:28860/merchant-base/merchant/list?params=";
        param.put("merchantNameRLike","杭州");

        String jsonStr="";
        try {
            jsonStr = AwifiHttpRequestUtil.sendGet(url,param);
            List<CenterPubMerchant> list = JsonUtils.toList(jsonStr,CenterPubMerchant.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.dozenx.web.third.dbcenter.util;

import com.dozenx.util.HttpRequestUtil;
import com.dozenx.util.JsonUtils;
import com.dozenx.util.TimeUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.third.dbcenter.DBException;
import com.dozenx.web.third.dbcenter.DBResultDTO;
import com.dozenx.web.third.dbcenter.bean.Token;
import com.dozenx.web.third.dbcenter.bean.TokenResult;
import com.dozenx.web.util.ConfigUtil;

import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by dozen.zhang on 2017/2/27.
 * 此类是专门为了数据中心dubbo 改造成http形式设计,请求前所有接口都需要获得token 再带入token 发起http请求
 */
public class AwifiHttpRequestUtil {
    public static Token token;

    /**
     * 发送http get请求
     * @param url url地址
     * @param param map结构参数
     * @return jsonSr
     * @throws Exception
     */
    public static String sendGet(String url, Map param) throws Exception {
        //检查token
        String paramStr = JsonUtils.toJsonString(param);
        return sendGet(url, paramStr);
    }

    /**
     * 发送http get 请求
     * @param url url地址
     * @param param 参数
     * @return jsonSr
     * @throws Exception
     */
    public static String sendGet(String url, String param) throws Exception {
        //检查token
        if (token == null || token.getLoseTimestamp() < TimeUtil.getNowTime()) {
            //  http://192.168.41.49:8085/awifi-oauth-server-web/tokenController/authorizationGrant?oauthCode=msp_tp53
            String tokenUrl = ConfigUtil.getConfig("dbcenter.token.url");
            String result = HttpRequestUtil.sendGet(tokenUrl);
            TokenResult tokenResult = JsonUtils.toJavaBean(result, TokenResult.class);
            if ("success".equals(tokenResult.getState())) {

                token = tokenResult.getData();

                if (token == null) {
                    throw new Exception("token 获取失败");
                }
            }
        }
        //param.put("access_token" ,token.getOauthToken());
        url = url.replace("{json}", URLEncoder.encode(param, Constants.CHARSET)) + "&access_token=" + token.getOauthToken();
        String resultStr = HttpRequestUtil.sendGet(url);
        if ("400".equals(resultStr)) {
            throw new Exception("结果400");
        }
        DBResultDTO resultDTO = JsonUtils.toJavaBean(resultStr, DBResultDTO.class);
        if (resultDTO.isSuc()) {
            return resultDTO.getRs();
        } else {
            throw new DBException(resultDTO);
        }
    }

    /**
     * 发送httppost 请求
     * @param url 地址
     * @param param 参数
     * @return jsonSr
     * @throws Exception
     */
    public static String sendPost(String url, Map<String,String > param) throws Exception {
        //检查token

        if (token == null || token.getLoseTimestamp() < TimeUtil.getNowTime()) {

            String tokenUrl = ConfigUtil.getConfig("dbcenter.token.url");
            String result = HttpRequestUtil.sendGet(tokenUrl);
            TokenResult tokenResult = JsonUtils.toJavaBean(result, TokenResult.class);
            if ("success".equals(tokenResult.getState())) {

                token = tokenResult.getData();

                if (token == null) {
                    throw new Exception("token 获取失败");
                }
            }
        }
        param.put("access_token", token.getOauthToken());

        String resultStr = HttpRequestUtil.sendPost(url, param);
        if ("400".equals(resultStr)) {
            throw new Exception("结果400");
        }
        DBResultDTO resultDTO = JsonUtils.toJavaBean(resultStr, DBResultDTO.class);
        if (!resultDTO.isSuc()) {
            return resultDTO.getRs();
        } else {
            throw new DBException(resultDTO);
        }
    }
}

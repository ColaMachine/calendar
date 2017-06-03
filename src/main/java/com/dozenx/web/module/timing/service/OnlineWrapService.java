package com.dozenx.web.module.timing.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dozenx.util.StringUtil;
import com.dozenx.web.module.buy.bean.OnlineData;
import com.dozenx.web.module.timing.bean.OnlineUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.annotation.Resource;


@Service
public class OnlineWrapService {
    
    /**
     * @Fields LOG :
     */
    private static Logger LOG = LoggerFactory.getLogger(OnlineWrapService.class);
    
   
    
    /** 按日志规范的日志码 */
    private static String ONLINE_SERVICE_CODE ="ONLINE_SERVICE_CODE";
    
    /** 日志产生的位置 */
    private static String ONLINE_SERVICE_PATH ="ONLINE_SERVICE_PATH";
    
    /** 业务关键词对应名称 */
    private static String ONLINE_SERVICE_APPKEY = "DUBBO";
    
    /** 业务关键词对应值 */
    private static String ONLINE_SERVICE_APPKEY_VALUE = "OnlineService";
    
    /** 方法执行开始 */
    private static String ONLINE_SERVICE_EXEC_BEGIN = "exec begin";
    
    /** 方法执行成功 */
    private static String ONLINE_SERVICE_EXEC_SUCCESS = "exec success";
    
    /** 错误编码 */
    // 平台编码+服务编码+类编码+方法编码+错误编码
    /** addOrder */
    public static final String SAVEONLINEUSERDATA = "001";
    
    /**
     * @Fields QUERYONLINEUSERCOUNT : TODO
     */
    public static final String QUERYONLINEUSERCOUNT = "002";
    
    /**
     * @Fields SAVEONLINEDATA : TODO
     */
    public static final String SAVEONLINEDATA = "003";
    
    /**
     * @Fields QUERYVIPUSERLIST : TODO
     */
    public static final String QUERYVIPUSERLIST = "004";
    
    /**
     * @Fields QUERYVIPUSERCOUNT : TODO
     */
    public static final String QUERYVIPUSERCOUNT = "005";
    
    /**
     * @Fields UPDATEVIPUSER : TODO
     */
    public static final String UPDATEVIPUSER = "006";
    
    /**
     * @Fields onlineDataService : TODO
     */
    @Resource(name="CenterOnlineDataService")
    private ICenterOnlineDataService onlineDataService;
    @Resource(name="CenterOnlineUserService")
    private ICenterOnlineUserService onlineUserService;

    public void saveOnlineUserData(OnlineUser onlineUser)
        throws Exception {
        Long logBeginDate = System.currentTimeMillis();
        
        String globalKey = null;
        String globalValue = null;
        String telephone = null;
        String processFlg = null;
        long startTime = 0;
        long id = 0;
       
        if (onlineUser != null) {
            telephone = onlineUser.getTelephone().trim();
            processFlg = onlineUser.getProcessFlg().trim();
        }
        
        if (StringUtil.isBlank(telephone) || StringUtil.isBlank(processFlg)) {
            /**
             * 缺少参数
             */
            String code = ONLINE_SERVICE_CODE + SAVEONLINEUSERDATA;
            String simple = "新增推送用户数据出错(" + code + ")";
            String detail = simple + "。缺少必要参数";
            LOG.debug(detail);
            // 发送错误日志 抛出异常
            throw new Exception(detail);
        } else {
            OnlineUser existUserObject = null;
            long existEndTime = 0;
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("telephone", telephone);
            try {
                existUserObject = onlineUserService.queryLastOnlineUser(map);
            } catch (Exception e) {
                String code =
                    ONLINE_SERVICE_CODE + SAVEONLINEUSERDATA ;
                String simple = "新增推送用户数据queryLastOnlineUser出错(" + code + ")";
                String detail = simple + "|错误:" + e.getMessage();
                LOG.debug(detail);
                // 发送错误日志 抛出异常
                throw new Exception( detail);
            }
            if (processFlg.equals("add")) {
                startTime = onlineUser.getStartTime();
                if (startTime <= 0) {
                    /**
                     * 缺少参数
                     */
                    String code =
                        ONLINE_SERVICE_CODE + SAVEONLINEUSERDATA ;
                    String simple = "新增推送用户数据出错(" + code + ")";
                    String detail = simple + "。缺少必要参数";
                    LOG.debug(detail);
                    // 发送错误日志 抛出异常
                    throw new Exception(detail);
                }
                if (existUserObject != null) {
                    existEndTime = existUserObject.getEndTime();
                    if (existEndTime <= 0) {
                        String code =
                            ONLINE_SERVICE_CODE + SAVEONLINEUSERDATA ;
                        String simple = "新增推送用户数据出错(" + code + ")";
                        String detail = simple + "。该手机号最新记录没有结束时间";
                        LOG.debug(detail);
                        // 发送错误日志 抛出异常
                        throw new Exception(detail);
                    }
                }
                try {
                    onlineUserService.add(onlineUser);
                    id = onlineUser.getId();
                    if (id <= 0) {
                        LOG.error("新增推送用户数据出错,消息:" +JSON.toJSONString(onlineUser));
                    } else {
                       
                    }
                } catch (Exception e) {
                    String code =
                        ONLINE_SERVICE_CODE + SAVEONLINEUSERDATA ;
                    String simple = "新增推送用户数据出错(" + code + ")";
                    String detail = simple + "|错误:" + e.getMessage();
                    LOG.debug(detail);
                    // 发送错误日志 抛出异常
                    throw new Exception( detail);
                }
            } else if (processFlg.equals("del")) {
                /**
                 * 更新参数判断
                 */
                if (onlineUser.getEndTime() <= 0) {
                    /**
                     * 缺少参数
                     */
                    String code =
                        ONLINE_SERVICE_CODE + SAVEONLINEUSERDATA ;
                    String simple = "更新推送用户数据出错(" + code + ")";
                    String detail = simple + "。缺少必要参数";
                    LOG.debug(detail);
                    // 发送错误日志 抛出异常
                    throw new Exception(detail);
                } else {
                    if (existUserObject != null) {
                        existEndTime = existUserObject.getEndTime();
                        if (existEndTime > 0) {
                            /**
                             * 缺少参数
                             */
                            String code =
                                ONLINE_SERVICE_CODE + SAVEONLINEUSERDATA
                                    ;
                            String simple = "更新推送用户数据出错(" + code + ")";
                            String detail = simple + "。该手机号记录存在且结束时间不为空";
                            LOG.debug(detail);
                            // 发送错误日志 抛出异常
                            throw new Exception(detail);
                        } else {
                            onlineUser.setId(existUserObject.getId());
                            try {
                                onlineUserService.update(onlineUser);
                                
                            } catch (Exception e) {
                                String code =
                                    ONLINE_SERVICE_CODE + SAVEONLINEUSERDATA
                                        ;
                                String simple = "更新推送用户数据出错(" + code + ")";
                                String detail = simple + "|错误:" + e.getMessage();
                                LOG.debug(detail);
                                // 发送错误日志 抛出异常
                                throw new Exception(detail);
                            }
                        }
                    } else {
                        /**
                         * 缺少参数
                         */
                        String code =
                            ONLINE_SERVICE_CODE + SAVEONLINEUSERDATA ;
                        String simple = "更新推送用户数据出错(" + code + ")";
                        String detail = simple + "。该手机号记录不存在";
                        LOG.debug(detail);
                        // 发送错误日志 抛出异常
                        throw new Exception(detail);
                    }
                }
            } else {
                /**
                 * 缺少参数
                 */
                String code =
                    ONLINE_SERVICE_CODE + SAVEONLINEUSERDATA ;
                String simple = "保存推送用户数据出错(" + code + ")";
                String detail = simple + "。processFlg参数有误";
                LOG.debug(detail);
                // 发送错误日志 抛出异常
                throw new Exception(detail);
            }
        }
        
        // 请求成功耗时日志
        
    }
    
    
    public int queryOnlineUserCount(OnlineUser onlineUser)
        throws Exception {
        Long logBeginDate = System.currentTimeMillis();
      
        String globalKey = null;
        String globalValue = null;
        String telephone = null;
        long nowTime = 0;
        int count = 0;
        Map<String, Object> map = new HashMap<String, Object>();
       
        if (onlineUser != null) {
            telephone = onlineUser.getTelephone().trim();
            nowTime = onlineUser.getNowTime();
           
        }
       
        if (StringUtil.isBlank(telephone) || nowTime <= 0) {
            /**
             * 缺少参数
             */
            String code = ONLINE_SERVICE_CODE + QUERYONLINEUSERCOUNT ;
            String simple = "查询推送用户数据出错(" + code + ")";
            String detail = simple + "。缺少必要参数";
            LOG.debug(detail);
            // 发送错误日志 抛出异常
            throw new Exception(detail);
        } else {
            map.put("telephone", telephone);
            map.put("nowTime", nowTime);
            try {
                count = onlineUserService.queryOnlineUserCount(map);
              
              
            } catch (Exception e) {
                String code =
                    ONLINE_SERVICE_CODE + QUERYONLINEUSERCOUNT ;
                String simple = "查询推送用户数据出错(" + code + ")";
                String detail = simple + "|错误:" + e.getMessage();
                LOG.debug(detail);
                // 发送错误日志 抛出异常
                throw new Exception( detail);
            }
        }
       
        return count;
    }
    
    
    public void saveOnlineData(OnlineData onlineData)
        throws Exception {
        Long logBeginDate = System.currentTimeMillis();
        String globalKey = null;
        String globalValue = null;
       
        if (onlineData != null) {
            LOG.info(JSON.toJSONString(onlineData));
           
            try {
                onlineDataService.add(onlineData);
                // 发送正确日志
                
            } catch (Exception e) {
                String code = ONLINE_SERVICE_CODE + SAVEONLINEDATA ;
                String simple = "新增推送数据出错(" + code + ")";
                String detail = simple + "|错误:" + e.getMessage();
                LOG.error(detail);
                // 发送错误日志 抛出异常
                throw new Exception(detail);
            }
        } else {
            /**
             * 缺少参数
             */
            String code = ONLINE_SERVICE_CODE + SAVEONLINEDATA ;
            String simple = "新增推送数据出错(" + code + ")";
            String detail = simple + "。参数为空";
            LOG.debug(detail);
            // 发送错误日志 抛出异常
            throw new Exception(detail);
        }
        // 请求成功耗时日志
      
            
    }
    
    public String queryVipUserList(OnlineUser onlineUser)
        throws Exception {
        Long logBeginDate = System.currentTimeMillis();
        String resJson = null;
       
        String globalKey = null;
        String globalValue = null;
        Integer pageNum = null;
        Integer pageSize = null;
        Map<String, Object> map = new HashMap<String, Object>();
        List<OnlineUser> list = new ArrayList<OnlineUser>();
        
        if (onlineUser != null) {
           
            pageNum = 0;
            pageSize = 1000;
           
            
            if (StringUtil.isBlank(onlineUser.getTelephone())) {
                map.put("telephone", onlineUser.getTelephone());
            }
            if (onlineUser.getStartTime() > 0) {
                map.put("startTime", onlineUser.getStartTime());
            }
            if (onlineUser.getEndTime() > 0) {
                map.put("endTime", onlineUser.getEndTime());
            }
            if (onlineUser.getProvince() > 0) {
                map.put("province", onlineUser.getProvince());
            }
            if (onlineUser.getCity() > 0) {
                map.put("city", onlineUser.getCity());
            }
            if (onlineUser.getCounty() > 0) {
                map.put("county", onlineUser.getCounty());
            }
            if (onlineUser.getMerchantId() > 0) {
                map.put("merchantId", onlineUser.getMerchantId());
            }
            if (StringUtil.isNotBlank(onlineUser.getMerchantName())) {
                map.put("merchantName", onlineUser.getMerchantName());
            }
            if (null != pageNum && null != pageSize && pageNum > 0 && pageSize > 0) {
                map.put("start", (pageNum - 1) * pageSize);
                map.put("pageSize", pageSize);
            }
            
            /**
             * 执行查询
             */
            try {
                list = onlineUserService.queryVipUserList(map);
               
            } catch (Exception e) {
                String code = ONLINE_SERVICE_CODE + QUERYVIPUSERLIST ;
                String simple = "查询白名单数据出错(" + code + ")";
                String detail = simple + ".详细信息：" + e.getMessage() + "|参数:" + JSON.toJSONString(map);
                LOG.debug(detail);
                // 发送错误日志 抛出异常
                throw new Exception(detail);
            }
        }
        resJson = JSONObject.toJSONString(list);
     
        return resJson;
    }
    
    public int queryVipUserCount(OnlineUser onlineUser)
        throws Exception {
        Long logBeginDate = System.currentTimeMillis();
        int count = 0;
       
        String globalKey = null;
        String globalValue = null;
        Map<String, Object> map = new HashMap<String, Object>();
     
        if (onlineUser != null) {
           
            
            if (StringUtil.isNotBlank(onlineUser.getTelephone())) {
                map.put("telephone", onlineUser.getTelephone());
            }
            if (onlineUser.getStartTime() > 0) {
                map.put("startTime", onlineUser.getStartTime());
            }
            if (onlineUser.getEndTime() > 0) {
                map.put("endTime", onlineUser.getEndTime());
            }
            if (onlineUser.getProvince() > 0) {
                map.put("province", onlineUser.getProvince());
            }
            if (onlineUser.getCity() > 0) {
                map.put("city", onlineUser.getCity());
            }
            if (onlineUser.getCounty() > 0) {
                map.put("county", onlineUser.getCounty());
            }
            if (onlineUser.getMerchantId() > 0) {
                map.put("merchantId", onlineUser.getMerchantId());
            }
            if (StringUtil.isNotBlank(onlineUser.getMerchantName())) {
                map.put("merchantName", onlineUser.getMerchantName());
            }
            /**
             * 执行查询
             */
            try {
                count = onlineUserService.queryVipUserCount(map);
                // 发送正确日志
               
            } catch (Exception e) {
                // 转换json出错
                String code = ONLINE_SERVICE_CODE + QUERYVIPUSERCOUNT ;
                String simple = "查询白名单数据记录出错(" + code + ")";
                String detail = simple + ".详细信息：" + e.getMessage() + "|参数:" + JSON.toJSONString(map);
                LOG.debug(detail);
                // 发送错误日志 抛出异常
                throw new Exception( detail);
            }
        }
        // 请求成功耗时日志
        Long logEndDate = System.currentTimeMillis();
   
        return count;
    }
    
    public int updateVipUser(OnlineUser onlineUser)
        throws Exception {
        Long logBeginDate = System.currentTimeMillis();
        int updateCount = 0;
       
        String globalKey = null;
        String globalValue = null;
       
        if (onlineUser != null) {
          
            
            
            
            /**
             * 参数判断
             */
            if (onlineUser.getMerchantId() == 0 || StringUtil.isBlank(onlineUser.getTelephone())) {
                String code = ONLINE_SERVICE_CODE + UPDATEVIPUSER ;
                String simple = "更新白名单merchantId出错,参数为空(" + code + ")";
                String detail = simple + "|参数:" + JSON.toJSONString(onlineUser);
                LOG.debug(detail);
                // 发送错误日志 抛出异常
                throw new Exception(detail);
            }
            try {
                updateCount = onlineUserService.updateVipUser(onlineUser);
                if (updateCount > 0) {
                    // 发送正确日志
                   
                }
            } catch (Exception e) {
                String code = ONLINE_SERVICE_CODE + UPDATEVIPUSER ;
                String simple = "更新白名单merchantId出错(" + code + ")";
                String detail = simple + ".详细信息：" + e.getMessage() + "|参数:" + JSON.toJSONString(onlineUser);
                LOG.debug(detail);
                // 发送错误日志 抛出异常
                throw new Exception( detail);
            }
        }
        // 请求成功耗时日志
        Long logEndDate = System.currentTimeMillis();
       
        return updateCount;
    }
}

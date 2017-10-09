package com.dozenx.web.module.timing.service;


import com.dozenx.util.StringUtil;
import com.dozenx.web.module.buy.order.bean.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("localOrderService")
public class OrderWrapService {
    
    
    
    /** 业务关键词对应名称 */
    private static final String ORDER_SERVICE_APPKEY = "DUBBO";
    
    /** 业务关键词对应值 */
    private static final String ORDER_SERVICE_APPKEY_VALUE = "OrderService";
    
    /** 方法执行开始 */
    private static final String ORDER_SERVICE_EXEC_BEGIN = "exec begin";
    
    /** 方法执行成功 */
    private static final String ORDER_SERVICE_EXEC_SUCCESS = "exec success";
    
    /** 错误编码 */
    // 平台编码+服务编码+类编码+方法编码+错误编码
    /** addOrder */
    public static final String ADD = "001";
    
    /** updateOrder */
    public static final String UPDATE = "002";
    
    /** queryOrderListByParam */
    public static final String QUERYORDERISTBYPARAM = "003";
    
    /** queryOrderCountByParam */
    public static final String QUERYCOUNTBYPARAM = "004";
    
    /** FINISHORDER **/
    public static final String FINISHORDER = "005";
    
    /**
     * LOG
     */
    private static Logger LOG = LoggerFactory.getLogger(OrderWrapService.class);
    

    @Resource(name="CenterOrderService")
    private ICenterOrderService centerOrderService;
    
  
    public String add(Order order)
        throws Exception {
        Long logBeginDate = System.currentTimeMillis();
       
        String orderId = "";
        long seq = 0L;
        String globalKey = "";
        String globalValue = "";
        
        /**
         * 参数判断
         */
        if (order.getMerchantId() <= 0 || order.getUserId() <= 0 || order.getPackageId() <= 0
            || order.getPackageNum() <= 0 || order.getRechargeType() <= 0) {
            // 缺少必要参数
            String code = "ORDER_SERVICE_CODE + ADD + ErrorConstant.ERROR_NUM_002";
            String simple = "新增订单数据出错(" + code + ")";
            String detail = simple + "。必要参数缺少";
            LOG.debug(detail);
            // 发送错误日志 抛出异常
            throw new Exception( detail);
        } else {
            /**
             * 生成orderId yyyyMMddhhmmss(000000~999999)
             */
            
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
                String dateStr = format.format(new Date());
                String seqStr = StringUtil.getRandomString(6);
              
                orderId = dateStr + seqStr;
                order.setOrderId(orderId);
                centerOrderService.addOrder(order);
                // 发送正确日志
              
            } catch (Exception e) {
                String code = "ORDER_SERVICE_ADD";
                String simple = "新增订单数据出错(" + code + ")";
                String detail = simple + "|错误:" + e.getMessage();
                LOG.debug(detail);
                // 发送错误日志 抛出异常
                throw new Exception( detail);
            }
        }
       
        return orderId;
    }
    
    public List<Order> queryListByparam(Map params )
        throws Exception {
        List<Order>  list = centerOrderService.queryListByParam(params);
       
        return list;
    }
    
}

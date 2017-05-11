package com.dozenx.web.module.timing.service;


import com.dozenx.web.module.timing.bean.Order;

import java.util.List;
import java.util.Map;



/**
 * 订单服务接口db层
 * @author zhouwx
 *
 */
public interface ICenterOrderService {
    /**
     * 新增订单
     * @param order 订单对象
     * @return int
     * 插入行数
     */
    int addOrder(Order order);
    /**
     * 更新订单
     * @param order 订单对象
     * @return
     * 影响行数
     */
    int updateOrder(Order order);
    /**
     * 根据条件查询订单
     * @param map
     * merchantId
     * packageId
     * status
     * @return
     * List<Order>
     */
    List<Order> queryListByParam(Map<String, Object> map);
    /**
     * 根据条件查询订单总记录数
     * @param map
     * merchantId
     * packageId
     * status
     * @return int
     * 总记录数
     */
    int queryCountByParam(Map<String, Object> map);
    /**
     * 订单完工
     * @param order 订单对象
     * @return int
     * 更新记录数
     */
    int finishOrder(Order order);
}

package com.dozenx.web.module.timing.dao;


import com.dozenx.web.module.timing.bean.Order;

import java.util.List;
import java.util.Map;




public interface CenterOrderMapper {
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
     * 更新记录数
     */
    int updateOrder(Order order);
    /**
     * 根据条件查询订单
     * @param map
     * merchantId
     * @return
     * list 订单对象
     */
    List<Order> queryListByParam(Map<String, Object> map);
    /**
     * 根据条件查询订单总记录数
     * @param map
     * merchantId
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

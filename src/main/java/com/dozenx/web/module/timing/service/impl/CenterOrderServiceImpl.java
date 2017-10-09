package com.dozenx.web.module.timing.service.impl;


import com.dozenx.web.module.buy.order.bean.Order;
import com.dozenx.web.module.timing.dao.CenterOrderMapper;
import com.dozenx.web.module.timing.service.ICenterOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service("CenterOrderService")
public class CenterOrderServiceImpl implements ICenterOrderService {
    /**
     * centerOrderMapper
     */
    @Autowired
    private CenterOrderMapper centerOrderMapper;
    
    @Override
    public int addOrder(Order order) {
        return centerOrderMapper.addOrder(order);
    }

    @Override
    public int updateOrder(Order order) {
        return centerOrderMapper.updateOrder(order);
    }

    @Override
    public List<Order> queryListByParam(Map<String, Object> map) {
        return centerOrderMapper.queryListByParam(map);
    }

    @Override
    public int queryCountByParam(Map<String, Object> map) {
        return centerOrderMapper.queryCountByParam(map);
    }

    @Override
    public int finishOrder(Order order) {
        return centerOrderMapper.finishOrder(order);
    }

}

package com.dozenx.web.module.buy.orderDetail.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.buy.orderDetail.bean.OrderDetail;

public interface OrderDetailMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(OrderDetail record);

   
    int insertSelective(OrderDetail record);

    
    OrderDetail  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param orderDetail
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(OrderDetail orderDetail);

    /**
     * 说明:根据主键修改record完整内容
     * @param orderDetail
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(OrderDetail orderDetail);

    /**
     * 说明:根据map查找bean结果集
     * @param orderDetail
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<OrderDetail> listByParams(Map orderDetail);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param orderDetail
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<OrderDetail> listByParams4Page(Map orderDetail);
    
    /**
     * 说明:根据map查找map结果集
     * @param orderDetail
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(OrderDetail orderDetail);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param orderDetail
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<OrderDetail> selectBeanByMap4Page(HashMap map);
    
    int countByBean(OrderDetail record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}

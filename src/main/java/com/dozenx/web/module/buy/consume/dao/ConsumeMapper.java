package com.dozenx.web.module.buy.consume.dao;

import com.dozenx.web.module.buy.consume.bean.Consume;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ConsumeMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(Consume record);

   
    int insertSelective(Consume record);

    
    Consume  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param consume
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(Consume consume);

    /**
     * 说明:根据主键修改record完整内容
     * @param consume
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(Consume consume);

    /**
     * 说明:根据map查找bean结果集
     * @param consume
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Consume> listByParams(Map consume);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param consume
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Consume> listByParams4Page(Map consume);
    
    /**
     * 说明:根据map查找map结果集
     * @param consume
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(Consume consume);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param consume
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<Consume> selectBeanByMap4Page(HashMap map);
    
    int countByBean(Consume record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}

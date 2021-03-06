package com.dozenx.web.system.portal.component.dao;

import com.dozenx.web.system.portal.component.bean.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ComponentMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(Component record);

   
    int insertSelective(Component record);

    
    Component  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param component
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(Component component);

    /**
     * 说明:根据主键修改record完整内容
     * @param component
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(Component component);

    /**
     * 说明:根据map查找bean结果集
     * @param component
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Component> listByParams(Map component);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param component
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Component> listByParams4Page(Map component);
    
    /**
     * 说明:根据map查找map结果集
     * @param component
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(Component component);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param component
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<Component> selectBeanByMap4Page(HashMap map);
    
    int countByBean(Component record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}

package com.dozenx.web.system.expertArtical.dao;

import com.dozenx.web.system.expertArtical.bean.ExpertArtical;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ExpertArticalMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(ExpertArtical record);

   
    int insertSelective(ExpertArtical record);

    
    ExpertArtical  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param expertArtical
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(ExpertArtical expertArtical);

    /**
     * 说明:根据主键修改record完整内容
     * @param expertArtical
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(ExpertArtical expertArtical);

    /**
     * 说明:根据map查找bean结果集
     * @param expertArtical
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<ExpertArtical> listByParams(Map expertArtical);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param expertArtical
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<ExpertArtical> listByParams4Page(Map expertArtical);
    
    /**
     * 说明:根据map查找map结果集
     * @param expertArtical
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(ExpertArtical expertArtical);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param expertArtical
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<ExpertArtical> selectBeanByMap4Page(HashMap map);
    
    int countByBean(ExpertArtical record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}

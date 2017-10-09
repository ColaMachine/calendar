package com.dozenx.web.system.expertDetail.dao;

import com.dozenx.web.system.expertDetail.bean.ExpertDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ExpertDetailMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(ExpertDetail record);

   
    int insertSelective(ExpertDetail record);

    
    ExpertDetail  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param expertDetail
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(ExpertDetail expertDetail);

    /**
     * 说明:根据主键修改record完整内容
     * @param expertDetail
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(ExpertDetail expertDetail);

    /**
     * 说明:根据map查找bean结果集
     * @param expertDetail
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<ExpertDetail> listByParams(Map expertDetail);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param expertDetail
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<ExpertDetail> listByParams4Page(Map expertDetail);
    
    /**
     * 说明:根据map查找map结果集
     * @param expertDetail
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(ExpertDetail expertDetail);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param expertDetail
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<ExpertDetail> selectBeanByMap4Page(HashMap map);
    
    int countByBean(ExpertDetail record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}

package com.dozenx.web.system.expert.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.system.expert.bean.Expert;

public interface ExpertMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(Expert record);

   
    int insertSelective(Expert record);

    
    Expert  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param expert
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(Expert expert);

    /**
     * 说明:根据主键修改record完整内容
     * @param expert
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(Expert expert);

    /**
     * 说明:根据map查找bean结果集
     * @param expert
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Expert> listByParams(Map expert);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param expert
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Expert> listByParams4Page(Map expert);
    
    /**
     * 说明:根据map查找map结果集
     * @param expert
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(Expert expert);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param expert
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<Expert> selectBeanByMap4Page(HashMap map);
    
    int countByBean(Expert record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}

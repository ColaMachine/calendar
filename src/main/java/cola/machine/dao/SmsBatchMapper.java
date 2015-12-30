package cola.machine.dao;
 import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cola.machine.bean.SmsBatch;

public interface SmsBatchMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(SmsBatch record);

   
    int insertSelective(SmsBatch  record);

    
    SmsBatch  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param SmsBatch  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SmsBatch record);

    /**
     * 说明:根据主键修改record完整内容
     * @param SmsBatch  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SmsBatch record);

    /**
     * 说明:根据map查找bean结果集
     * @param SmsBatch  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SmsBatch> selectBeanByBean(SmsBatch record);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param SmsBatch  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SmsBatch> selectBeanByMap(HashMap map);
    
    /**
     * 说明:根据map查找map结果集
     * @param SmsBatch  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Map> selectMapByBean4Page(SmsBatch record);
    
   
    /**
     * 说明:根据map查找map结果集
     * @param SmsBatch  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SmsBatch> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SmsBatch record);
    
    int countByMap(HashMap map);
      
      
}

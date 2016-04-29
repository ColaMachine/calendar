package cola.machine.dao;
 import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import cola.machine.bean.Configuration;

public interface ConfigurationMapper {
    
    int deleteByPrimaryKey(String key);

    
    int insert(Configuration record);

   
    int insertSelective(Configuration record);

    
    Configuration  selectByPrimaryKey(String id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param Configuration  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(Configuration record);

    /**
     * 说明:根据主键修改record完整内容
     * @param Configuration  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(Configuration record);

    /**
     * 说明:根据map查找bean结果集
     * @param Configuration  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Configuration> listByParams(Map map);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param Configuration  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Configuration> listByParams4Page(Map map);
    
    /**
     * 说明:根据map查找map结果集
     * @param Configuration  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(Configuration record);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param Configuration  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<Configuration> selectBeanByMap4Page(HashMap map);
    
    int countByBean(Configuration record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}

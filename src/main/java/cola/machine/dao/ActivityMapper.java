package cola.machine.dao;
 import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import cola.machine.bean.Activity;

public interface ActivityMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(Activity record);

   
    int insertSelective(Activity record);

    
    Activity  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param Activity  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(Activity record);

    /**
     * 说明:根据主键修改record完整内容
     * @param Activity  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(Activity record);

    /**
     * 说明:根据map查找bean结果集
     * @param Activity  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Activity> listByParams(Map map);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param Activity  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Activity> listByParams4Page(Map map);
    
    /**
     * 说明:根据map查找map结果集
     * @param Activity  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(Activity record);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param Activity  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<Activity> selectBeanByMap4Page(HashMap map);
    
    int countByBean(Activity record);*/
    
    int countByParams(HashMap map);
      
}

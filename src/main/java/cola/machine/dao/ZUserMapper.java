package cola.machine.dao;
 import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import cola.machine.bean.ZUser;

public interface ZUserMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(ZUser record);

   
    int insertSelective(ZUser  record);

    
    ZUser  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param ZUser  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(ZUser record);

    /**
     * 说明:根据主键修改record完整内容
     * @param ZUser  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(ZUser record);

    /**
     * 说明:根据map查找bean结果集
     * @param ZUser  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<ZUser> listByParams(Map map);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param ZUser  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<ZUser> listByParams4Page(Map map);
    
    /**
     * 说明:根据map查找map结果集
     * @param ZUser  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(ZUser record);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param ZUser  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<ZUser> selectBeanByMap4Page(HashMap map);
    
    int countByBean(ZUser record);*/
    
    int countByParams(HashMap map);
      
      
}

package cola.machine.dao;
 import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import cola.machine.bean.SmsEach;

public interface SmsEachMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(SmsEach record);

   
    int insertSelective(SmsEach  record);

    
    SmsEach  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param SmsEach  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SmsEach record);

    /**
     * 说明:根据主键修改record完整内容
     * @param SmsEach  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SmsEach record);

    /**
     * 说明:根据map查找bean结果集
     * @param SmsEach  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SmsEach> listByParms(Map map);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param SmsEach  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SmsEach> listByParms4Page(Map map);
    
    /**
     * 说明:根据map查找map结果集
     * @param SmsEach  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SmsEach record);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param SmsEach  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SmsEach> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SmsEach record);*/
    
    int countByParams(HashMap map);
      
      
}

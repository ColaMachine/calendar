package cola.machine.dao;
 import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import cola.machine.bean.SmsValidCode;

public interface SmsValidCodeMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(SmsValidCode record);

   
    int insertSelective(SmsValidCode  record);

    
    SmsValidCode  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param SmsValidCode  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SmsValidCode record);

    /**
     * 说明:根据主键修改record完整内容
     * @param SmsValidCode  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SmsValidCode record);

    /**
     * 说明:根据map查找bean结果集
     * @param SmsValidCode  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SmsValidCode> listByParams(Map map);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param SmsValidCode  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SmsValidCode> listByParams4Page(Map map);
    
    /**
     * 说明:根据map查找map结果集
     * @param SmsValidCode  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SmsValidCode record);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param SmsValidCode  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SmsValidCode> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SmsValidCode record);*/
    
    int countByParams(HashMap map);
      
      
}

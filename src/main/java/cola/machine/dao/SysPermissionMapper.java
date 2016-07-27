package cola.machine.dao;
 import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import cola.machine.bean.SysPermission;

public interface SysPermissionMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(SysPermission record);

   
    int insertSelective(SysPermission record);

    
    SysPermission  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param SysPermission  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysPermission record);

    /**
     * 说明:根据主键修改record完整内容
     * @param SysPermission  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysPermission record);

    /**
     * 说明:根据map查找bean结果集
     * @param SysPermission  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysPermission> listByParams(Map map);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param SysPermission  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysPermission> listByParams4Page(Map map);
    
    /**
     * 说明:根据map查找map结果集
     * @param SysPermission  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysPermission record);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param SysPermission  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysPermission> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysPermission record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}

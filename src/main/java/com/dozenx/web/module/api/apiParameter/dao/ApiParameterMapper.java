package com.dozenx.web.module.api.apiParameter.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.api.apiParameter.bean.ApiParameter;

public interface ApiParameterMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(ApiParameter record);

   
    int insertSelective(ApiParameter record);

    
    ApiParameter  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param apiParameter
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(ApiParameter apiParameter);

    /**
     * 说明:根据主键修改record完整内容
     * @param apiParameter
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(ApiParameter apiParameter);

    /**
     * 说明:根据map查找bean结果集
     * @param apiParameter
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<ApiParameter> listByParams(Map apiParameter);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param apiParameter
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<ApiParameter> listByParams4Page(Map apiParameter);
    
    /**
     * 说明:根据map查找map结果集
     * @param apiParameter
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(ApiParameter apiParameter);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param apiParameter
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<ApiParameter> selectBeanByMap4Page(HashMap map);
    
    int countByBean(ApiParameter record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}

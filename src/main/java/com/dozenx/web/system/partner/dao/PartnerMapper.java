package com.dozenx.web.system.partner.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.system.partner.bean.Partner;

public interface PartnerMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(Partner record);

   
    int insertSelective(Partner record);

    
    Partner  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param partner
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(Partner partner);

    /**
     * 说明:根据主键修改record完整内容
     * @param partner
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(Partner partner);

    /**
     * 说明:根据map查找bean结果集
     * @param partner
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Partner> listByParams(Map partner);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param partner
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Partner> listByParams4Page(Map partner);
    
    /**
     * 说明:根据map查找map结果集
     * @param partner
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(Partner partner);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param partner
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<Partner> selectBeanByMap4Page(HashMap map);
    
    int countByBean(Partner record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}

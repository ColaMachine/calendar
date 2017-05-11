package com.dozenx.web.module.merchant.merchantNews.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.merchant.merchantNews.bean.MerchantNews;

public interface MerchantNewsMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(MerchantNews record);

   
    int insertSelective(MerchantNews record);

    
    MerchantNews  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param merchantNews
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(MerchantNews merchantNews);

    /**
     * 说明:根据主键修改record完整内容
     * @param merchantNews
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(MerchantNews merchantNews);

    /**
     * 说明:根据map查找bean结果集
     * @param merchantNews
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<MerchantNews> listByParams(Map merchantNews);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param merchantNews
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<MerchantNews> listByParams4Page(Map merchantNews);
    
    /**
     * 说明:根据map查找map结果集
     * @param merchantNews
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(MerchantNews merchantNews);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param merchantNews
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<MerchantNews> selectBeanByMap4Page(HashMap map);
    
    int countByBean(MerchantNews record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}

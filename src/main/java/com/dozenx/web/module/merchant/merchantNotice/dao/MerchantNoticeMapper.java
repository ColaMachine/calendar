package com.dozenx.web.module.merchant.merchantNotice.dao;

import com.dozenx.web.module.merchant.merchantNotice.bean.MerchantNotice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MerchantNoticeMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(MerchantNotice record);

   
    int insertSelective(MerchantNotice record);

    
    MerchantNotice  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param merchantNotice
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(MerchantNotice merchantNotice);

    /**
     * 说明:根据主键修改record完整内容
     * @param merchantNotice
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(MerchantNotice merchantNotice);

    /**
     * 说明:根据map查找bean结果集
     * @param merchantNotice
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<MerchantNotice> listByParams(Map merchantNotice);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param merchantNotice
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<MerchantNotice> listByParams4Page(Map merchantNotice);
    
    /**
     * 说明:根据map查找map结果集
     * @param merchantNotice
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(MerchantNotice merchantNotice);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param merchantNotice
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<MerchantNotice> selectBeanByMap4Page(HashMap map);
    
    int countByBean(MerchantNotice record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}

package com.dozenx.web.module.merchant.merchantPic.dao;
import com.dozenx.web.module.merchant.merchantPic.bean.MerchantPic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MerchantPicMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(MerchantPic record);

   
    int insertSelective(MerchantPic record);

    
    MerchantPic  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param merchantPic
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(MerchantPic merchantPic);

    /**
     * 说明:根据主键修改record完整内容
     * @param merchantPic
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(MerchantPic merchantPic);

    /**
     * 说明:根据map查找bean结果集
     * @param merchantPic
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<MerchantPic> listByParams(Map merchantPic);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param merchantPic
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<MerchantPic> listByParams4Page(Map merchantPic);
    
    /**
     * 说明:根据map查找map结果集
     * @param merchantPic
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(MerchantPic merchantPic);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param merchantPic
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<MerchantPic> selectBeanByMap4Page(HashMap map);
    
    int countByBean(MerchantPic record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}

package com.dozenx.web.module.merchant.dao;

import com.dozenx.web.module.merchant.bean.SysMngMerchant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface MngMerchantMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(SysMngMerchant sysMng);

   
    int insertSelective(SysMngMerchant sysMng);

    
    SysMngMerchant selectByPrimaryKey(Integer id);
    

    /**
     * 说明:根据主键修改所存在属性内容
     * @param SysMngMerchant  
     * @return int 更新数量
     * @author 
     * @date 20160818
     */
    int updateByPrimaryKeySelective(SysMngMerchant sysMng);

    /**
     * 说明:根据主键修改record完整内容
     * @param SysMngMerchant  
     * @return int 更新数量
     * @author 
     * @date 20160818
     */
    int updateByPrimaryKey(SysMngMerchant sysMng);

    /**
     * 说明:根据map查找bean结果集
     * @param SysMngMerchant  
     * @return int 更新数量
     * @author 
     * @date 20160818
     */
    List<SysMngMerchant> listByParams(Map map);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param SysMngMerchant  
     * @return int 更新数量
     * @author 
     * @date 20160818
     */
    List<SysMngMerchant> listByParams4Page(Map map);
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}

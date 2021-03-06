package com.dozenx.web.system.partnerDetail.dao;

import com.dozenx.web.system.partnerDetail.bean.PartnerDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PartnerDetailMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(PartnerDetail record);

   
    int insertSelective(PartnerDetail record);

    
    PartnerDetail  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param partnerDetail
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(PartnerDetail partnerDetail);

    /**
     * 说明:根据主键修改record完整内容
     * @param partnerDetail
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(PartnerDetail partnerDetail);

    /**
     * 说明:根据map查找bean结果集
     * @param partnerDetail
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<PartnerDetail> listByParams(Map partnerDetail);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param partnerDetail
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<PartnerDetail> listByParams4Page(Map partnerDetail);
    
    /**
     * 说明:根据map查找map结果集
     * @param partnerDetail
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(PartnerDetail partnerDetail);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param partnerDetail
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<PartnerDetail> selectBeanByMap4Page(HashMap map);
    
    int countByBean(PartnerDetail record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}

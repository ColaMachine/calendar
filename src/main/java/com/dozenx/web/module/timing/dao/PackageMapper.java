package com.dozenx.web.module.timing.dao;

import com.dozenx.web.module.timing.bean.MerchantPackage;

import java.util.List;
import java.util.Map;



public interface PackageMapper {
    
    List<MerchantPackage> queryListByParam(Map map);
    
    MerchantPackage queryById(Long id);
    
    int add(MerchantPackage merchantPackage);
    
    int update(MerchantPackage merchantPackage);
    
    int delete(MerchantPackage merchantPackage);
     
    int queryCountByParam(Map map);
    
}

package com.dozenx.web.module.timing.service;

import com.dozenx.web.module.timing.bean.MerchantPackage;

import java.util.List;
import java.util.Map;


public interface IPackageService {
    
    List<MerchantPackage> queryListByParam(Map map)throws Exception;
     
     MerchantPackage queryById(Long id)throws Exception;
     
     int add(MerchantPackage merchantPackage)throws Exception;
     
     int update(MerchantPackage merchantPackage)throws Exception;
     
     int delete(Map map)throws Exception;
    
     int queryCountByParam(Map map)throws Exception;
    
}

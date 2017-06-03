package com.dozenx.web.module.timing.dao;

import com.dozenx.web.module.timing.bean.TimePackage;

import java.util.List;
import java.util.Map;



public interface PackageMapper {
    
    List<TimePackage> queryListByParam(Map map);
    
    TimePackage queryById(Long id);
    
    int add(TimePackage merchantPackage);
    
    int update(TimePackage merchantPackage);
    
    int delete(TimePackage merchantPackage);
     
    int queryCountByParam(Map map);
    
}

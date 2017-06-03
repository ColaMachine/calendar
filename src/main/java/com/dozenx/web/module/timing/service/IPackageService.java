package com.dozenx.web.module.timing.service;

import com.dozenx.web.module.timing.bean.TimePackage;

import java.util.List;
import java.util.Map;


public interface IPackageService {
    
    List<TimePackage> queryListByParam(Map map)throws Exception;
     
     TimePackage queryById(Long id)throws Exception;
     
     int add(TimePackage merchantPackage)throws Exception;
     
     int update(TimePackage merchantPackage)throws Exception;
     
     int delete(Map map)throws Exception;
    
     int queryCountByParam(Map map)throws Exception;
    
}

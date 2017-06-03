package com.dozenx.web.module.timing.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dozenx.web.module.timing.bean.TimePackage;
import com.dozenx.web.module.timing.dao.PackageMapper;
import com.dozenx.web.module.timing.service.IPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("packageService")
public class PackageServiceImpl implements IPackageService {

    @Autowired(required=true)
    private PackageMapper packageMapper;

    

    /**
     * 
     * 迭代4：TODO 根据条件查询套餐
     * 
     * @Description: queryListByParam
     * @param map
     * @return
     * @throws Exception
     * @return List<MerchantPackage> 返回类型
     * @author w049
     * @date 2016年11月30日 下午4:18:19
     */
    public List<TimePackage> queryListByParam(Map map) throws Exception {
        if (map == null) {
            throw new Exception("套餐查询参数为空");
        }
        int pageNum = 1;
        int pageSize = 10;
        try {
            if (map.get("pageNum") != null  ) {
                pageNum = Integer.valueOf(String.valueOf(map.get("pageNum")));
            }
            if(map.get("pageSize") !=null){
                pageSize = Integer.valueOf(String.valueOf(map.get("pageSize")));
            }
            
            if(map.get("pageNum") == null ||map.get("pageNum")==""|| map.get("pageSize")==null ||map.get("pageSize") == ""){
                pageNum = 1;
                pageSize = 10;
            }
            
        } catch (Exception e) {
            throw new Exception("根据分页条件查询套餐出错:" + e.getMessage());
        }
        if (pageNum < 0 || pageSize < 0) {
            throw new Exception("根据分页条件查询套餐出错:分页条件出错");
        }
        map.put("beginIndex", (pageNum - 1) * pageSize);
        map.put("pageSize",pageSize);
        return packageMapper.queryListByParam(map);
    }
    
    
    public int queryCountByParam (Map map)throws Exception{
        if (map == null) {
            throw new Exception("套餐查询参数为空");
        }
        return packageMapper.queryCountByParam(map);
    }
    

    /**
     * 
     * 迭代4：TODO 根据主键查询套餐
     * 
     * @Description: queryById
     * @param id
     * @return
     * @throws Exception
     * @return MerchantPackage 返回类型
     * @author w049
     * @date 2016年11月30日 下午4:48:54
     */
    public TimePackage queryById(Long id) throws Exception {

        if (id <= 0) {
            throw new Exception("根据主键查询套餐出错:主键参数错误");
        }
        return packageMapper.queryById(id);
    }
    /**
     * 增加套餐
     */
    public int add(TimePackage merchantPackage) throws Exception {
        if (merchantPackage == null) {
            throw new Exception("新增套餐出错:参数为空");
        }
        if (merchantPackage.getMerchantId() == null ||merchantPackage.getPackageType()==null||merchantPackage.getMerchantId() == 0 || merchantPackage.getPackageType() <= 0
                || merchantPackage.getPackageKey() <= 0 || merchantPackage.getPackageValue() < 0
               /* || merchantPackage.getEffectDatetime() == null || merchantPackage.getExpiredDatetime() == null*/
                || merchantPackage.getCreateDate() == null) {
            throw new Exception("新增套餐数据出错:缺少必要参数");

        }
        

        return packageMapper.add(merchantPackage);
    }
    
    /**
     * 
     */
    public int update(TimePackage merchantPackage) throws Exception {

        if (merchantPackage.getMerchantId() <= 0) {
            throw new Exception("更新套餐出错:缺少必要参数");
        }
        return packageMapper.update(merchantPackage);
    }
    /**
     * 
     */
    public int delete(Map map) throws Exception {
        if (map == null) {
            throw new Exception("删除套餐出错:参数为空");
        }
        Long id = 0L;
        if (map.get("id") == null) {
            throw new Exception("删除套餐出错:缺少必要参数");
        }
        try {
            id = Long.valueOf(String.valueOf(map.get("id")));
        } catch (Exception e) {
            throw new Exception("删除套餐出错:参数错误");
        }
        TimePackage merchantPackage = new TimePackage();
        merchantPackage.setId(id);
        merchantPackage.setStatus(9);
        merchantPackage.setStatusDate(new Date());
        merchantPackage.setRemarks((String)map.get("remarks"));
        return packageMapper.delete(merchantPackage);
    }

}

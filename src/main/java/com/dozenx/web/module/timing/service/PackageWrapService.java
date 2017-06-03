package com.dozenx.web.module.timing.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dozenx.web.module.timing.bean.TimePackage;
import com.dozenx.web.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class PackageWrapService {
    /** * 引入 IPackageService . */
    /** @Resource
    private IPackageService iPackageService;
     * 引入 MerchantServiceApi . */
    Logger logger =LoggerFactory.getLogger(PackageWrapService.class);
    @Autowired
    @Qualifier("packageService") //注释指定注入 Bean
    private IPackageService packageService;


    /**
     * 根据商户id 套餐类型 查询套餐
     * @param merchantId
     * @param packageType
     * @return
     * @throws Exception
     */
    public List<TimePackage> queryListByParam(Long merchantId , Integer packageType) throws Exception{
        Map<String, Object> parm = new HashMap<String, Object>();
        if (merchantId == null) {
            return null;
        }
        parm.put("merchantId", merchantId);
        parm.put("status", 1);
        parm.put("packageType", packageType);
        if (packageType == 2) {
            parm.put("packageKey", "201");
        }
        return packageService.queryListByParam(parm);
        
    }
    

    /**
     * 商户发售套餐查询
     * 
     * @param merchantId
     * @return String
     * @author xhb
     * @throws Exception
     */
    public List<TimePackage> packageListSearch(Long merchantId, Integer packageType) throws Exception {
        Map<String, Object> parm = new HashMap<String, Object>();
        if (merchantId == null) {
            return null;
        }
        parm.put("merchantId", merchantId);
        parm.put("status", 1);
        parm.put("packageType", packageType);
        if (packageType == 2) {
            parm.put("packageKey", "201");
        }
        List<TimePackage> pkg = packageService.queryListByParam(parm);
        List<TimePackage> result = new ArrayList<TimePackage>();
        for (TimePackage ret : pkg) {
            if (((new Date().getTime()) > (ret.getEffectDatetime().getTime()))
                    && ((ret.getExpiredDatetime().getTime()) > (new Date().getTime()))) {
                result.add(ret);
            }
        }

        if (result.size() == 0) {
            parm.put("merchantId", ConfigUtil.getConfig("free.pkg.id"));
            parm.put("status", 1);
            parm.put("packageType", packageType);
            if (packageType == 2) {
                parm.put("packageKey", "201");
            }
            pkg = packageService.queryListByParam(parm);
            for (TimePackage ret : pkg) {
               result.add(ret);
            }
        }

        return result;
    }

    /**
     * 套餐包查询
     * 
     * @param id
     *            编号
     * @return String
     * @author xhb
     * @throws Exception
     */
    public TimePackage packageSearch(Long id) throws Exception {
        return packageService.queryById(id);
    }

    /**
     * 商户套餐获得
     * 
     * @param merchantId
     * @return
     * @author xhb
     * @throws Exception
     */
   /* @Deprecated
    public String packageGet(Long merchantId) throws Exception {
        Map<String, Object> parm = new HashMap<String, Object>();
        parm.put("merchantId", merchantId);
        return JSON.toJSONString(packageService.queryListByParam(parm));
    }*/
    
    /**
     * 商户发售套餐查询
     * 
     * @param merchantId
     * @return String
     * @author xhb
     * @throws Exception
     */
    public TimePackage getFreePkg(Long merchantId) throws Exception {
        Map<String, Object> parm = new HashMap<String, Object>();
        if (merchantId == null) {
            return null;
        }
        parm.put("merchantId", merchantId);
        parm.put("status", 1);
        parm.put("packageType", 2);
        
        parm.put("packageKey", "201");
        List<TimePackage> pkgList = packageService.queryListByParam(parm);
        if(pkgList!=null && pkgList.size()>0){
            if(pkgList.size()>1){
               logger.error("merchantid:"+merchantId+"has more than one freepackage"); 
            }
            return pkgList.get(0);
        }

        return null;
    }

    public void update(TimePackage pkg) throws Exception {
        // TODO Auto-generated method stub
        this.packageService.update(pkg);
    }

    public void insert(TimePackage pkg) throws Exception {
        // TODO Auto-generated method stub
        this.packageService.add(pkg);
    }
    
    public int logicDelete(Long packageId)throws Exception{
        TimePackage pkg = null;
            pkg = packageService.queryById(packageId);
            if(pkg==null){
                throw new Exception("套餐不存在");
            }
            pkg.setStatus(9);
            pkg.setStatusDate(new Date());
            
            int i = packageService.update(pkg);
            
            if(i!=1){
                logger.error("packageId:"+packageId+":逻辑删除失败"); 
                throw new Exception("逻辑删除套餐失败");
            }else{
                return 1;
            }
        
        
    }
    
    
}

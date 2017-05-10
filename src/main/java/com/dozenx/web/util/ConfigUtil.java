package com.dozenx.web.util;

import com.dozenx.util.CacheUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.sysConfig.bean.SysConfig;
import com.dozenx.web.core.sysConfig.service.SysConfigService;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;

import java.util.Properties;


/**
 * Created by dozen.zhang on 2016/12/5.
 */
public class ConfigUtil {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ConfigUtil.class);

    public static Properties properties =new Properties();
    /**
     * 根据name获取配置项值
     * @param name
     * @return
     */
    public static String getConfig(String name) {
        if(StringUtil.isBlank(name)){

            logger.error("ConfigUtil.getConfig 参数不能为空");
            return null;
           // throw new Exception("config param is null");
        }

        String value =(String) properties.get(name);
        return value;
     /*   String value =(String) CacheUtil.getInstance().readCache(name,String.class);
        if(StringUtil.isBlank(value)){
          //  Object object = BeanUtil.getBean("sysUserRoleService");
            SysConfigService sysConfigService = (SysConfigService)BeanUtil.getBean("sysConfigService");
            SysConfig config = sysConfigService.selectByName(name);
            if(config!=null) {
                 value = config.getValue();
                CacheUtil.getInstance().writeCache(name,value);
                return value;
            }else {
                return null;
            }
        }else{

            return value;
        }*/

    }

}

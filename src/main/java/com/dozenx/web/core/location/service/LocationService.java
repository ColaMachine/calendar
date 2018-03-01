package com.dozenx.web.core.location.service;

import com.dozenx.util.MapUtils;
import com.dozenx.util.RedisUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.location.dao.LocationMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 19:16 2018/2/26
 * @Modified By:
 */
@Service("locationService")
public class LocationService {

    public static Map<Long ,String> idNameMap=null;
    @Resource
    private LocationMapper locationMapper;
    public   String getNameById(Long id){
       // if(idNameMap==null){
            cache();//重新进行地区缓存
     //   }
        return idNameMap.get(id);
    }

    /**
     * 缓存地区信息
     * @author 周颖
     * @throws Exception
     * @date 2017年1月22日 上午10:05:34
     */
    public void cache(){
        idNameMap = new HashMap <Long ,String>();
        List<Map<String,Object>> list= locationMapper.selectAll();
        for(Map<String,Object> map:list){
            Long id = MapUtils.getLong(map,"id");
            String name = MapUtils.getString(map,"area_name");
            idNameMap.put(id,name);

        }

    }
}

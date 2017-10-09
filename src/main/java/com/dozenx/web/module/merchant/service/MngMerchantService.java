/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.merchant.service;

import com.dozenx.util.ResultUtil;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.merchant.bean.SysMngMerchant;
import com.dozenx.web.module.merchant.dao.MngMerchantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;



@Service
public class MngMerchantService  {
    private static final Logger logger = LoggerFactory.getLogger(MngMerchantService.class);
    
    @Resource
    private MngMerchantMapper sysMngMapper;

    /**
     * 说明:list by page and params根据参数返回列表
     * 
     * @return List<HashMap>
     * @author 
     * @date 20160818
     */
    public List<SysMngMerchant> listByParams4Page(HashMap params) {
        return sysMngMapper.listByParams4Page(params);
    }

    public List<SysMngMerchant> listByParams(HashMap params) {
        return sysMngMapper.listByParams(params);
    }

    /**
     * 说明:countByParams 根据参数提取个数
     * 
     * @return int
     * @author 
     * @date 20160818
     */
    public int countByParams(HashMap params) {
        return sysMngMapper.countByParams(params);
    }

    /**
     * 说明:
     * 
     * @return object
     * @author 
     * @date 20160818
     */
    public ResultDTO save(SysMngMerchant sysMng) {
        if (sysMng.getId() == null) {
            sysMngMapper.insert(sysMng);
        } else {
        	sysMngMapper.updateByPrimaryKey(sysMng);
        }
        return ResultUtil.getSuccResult();
    }

    /**
     * 说明:根据主键删除数据 description:delete by key
     * 
     * @param id
     * @return void
     * @author 
     * @date 20160818
     */
    public void delete(Integer id) {
    	sysMngMapper.deleteByPrimaryKey(id);
    }

    /**
     * 说明:根据主键获取数据 description:delete by key
     * 
     * @param id
     * @return void
     * @author 
     * @date 20160818
     */
    public SysMngMerchant selectByPrimaryKey(Integer id) {
        return sysMngMapper.selectByPrimaryKey(id);
    }
    
    
    
    /**
     * 说明:根据主键获取数据 description:delete by key
     * 
     * @param uid
     * @return void
     * @author 
     * @date 20160818
     */
//    public SysMngMerchant selectByUid(Integer uid) {
//        return sysMngMapper.selectByPrimaryKey(uid);
//    }
    
    
    /**
     * 说明:根据主键获取数据 description:delete by key
     * 
     * @param mid
     * @return void
     * @author 
     * @date 20160818
     */
//    public SysMngMerchant selectByMid(Integer mid) {
//        return sysMngMapper.selectByPrimaryKey(mid);
//    }

    
    /**
     * 多id删除
     * 
     * @param idAry
     * @author 
     * @date 20160818
     */
    public ResultDTO multilDelete(Integer[] idAry) {
        for (int i = 0; i < idAry.length; i++) {
        	sysMngMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}

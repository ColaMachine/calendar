/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.api.apiCategory.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.api.apiCategory.bean.ApiCategory;
import com.dozenx.web.module.api.apiCategory.dao.ApiCategoryMapper;
import com.dozenx.util.CacheUtil;
import com.dozenx.util.ResultUtil;
import com.dozenx.util.UUIDUtil;
import com.dozenx.util.ValidateUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("apiCategoryService")
public class ApiCategoryService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(ApiCategoryService.class);
    @Resource
    private ApiCategoryMapper apiCategoryMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<ApiCategory> listByParams4Page(HashMap params) {
        return apiCategoryMapper.listByParams4Page(params);
    }
    public List<ApiCategory> listByParams(HashMap params) {
        return apiCategoryMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return apiCategoryMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param ApiCategory
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(ApiCategory apiCategory) {
        // 进行字段验证
      /* ValidateUtil<ApiCategory> vu = new ValidateUtil<ApiCategory>();
        ResultDTO result = vu.valid(apiCategory);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (apiCategory.getId()==null ||  this.selectByPrimaryKey(apiCategory.getId())==null) {

            apiCategoryMapper.insert(apiCategory);
        } else {
            apiCategory.setUpdatetime(new Timestamp(new Date().getTime()));
            apiCategoryMapper.updateByPrimaryKeySelective(apiCategory);
        }
        return ResultUtil.getSuccResult();
    }
    /**
    * 说明:根据主键删除数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public void delete(Long  id){
        apiCategoryMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public ApiCategory selectByPrimaryKey(Long id){
       return apiCategoryMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            apiCategoryMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}

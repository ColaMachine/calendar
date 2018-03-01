/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.api.apiParameter.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.api.apiParameter.bean.ApiParameter;
import com.dozenx.web.module.api.apiParameter.dao.ApiParameterMapper;
import com.dozenx.util.CacheUtil;
import com.dozenx.util.ResultUtil;
import com.dozenx.util.UUIDUtil;
import com.dozenx.util.ValidateUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("apiParameterService")
public class ApiParameterService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(ApiParameterService.class);
    @Resource
    private ApiParameterMapper apiParameterMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<ApiParameter> listByParams4Page(HashMap params) {
        return apiParameterMapper.listByParams4Page(params);
    }
    public List<ApiParameter> listByParams(HashMap params) {
        return apiParameterMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return apiParameterMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param ApiParameter
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(ApiParameter apiParameter) {
        // 进行字段验证
      /* ValidateUtil<ApiParameter> vu = new ValidateUtil<ApiParameter>();
        ResultDTO result = vu.valid(apiParameter);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (apiParameter.getId()==null ||  this.selectByPrimaryKey(apiParameter.getId())==null) {

            apiParameterMapper.insert(apiParameter);
        } else {
            apiParameter.setUpdatetime(new Timestamp(new Date().getTime()));
            apiParameterMapper.updateByPrimaryKeySelective(apiParameter);
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
        apiParameterMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public ApiParameter selectByPrimaryKey(Long id){
       return apiParameterMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            apiParameterMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}

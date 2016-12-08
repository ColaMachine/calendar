/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.system.portal.template.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.system.portal.template.bean.Template;
import com.dozenx.web.system.portal.template.dao.TemplateMapper;
import com.dozenx.util.CacheUtil;
import com.dozenx.util.ResultUtil;
import com.dozenx.util.UUIDUtil;
import com.dozenx.util.ValidateUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.message.ResultDTO;

@Service("templateService")
public class TemplateService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(TemplateService.class);
    @Resource
    private TemplateMapper templateMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<Template> listByParams4Page(HashMap params) {
        return templateMapper.listByParams4Page(params);
    }
    public List<Template> listByParams(HashMap params) {
        return templateMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return templateMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param Template
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(Template template) {
        // 进行字段验证
      /* ValidateUtil<Template> vu = new ValidateUtil<Template>();
        ResultDTO result = vu.valid(template);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (template.getId()==null ||  this.selectByPrimaryKey(template.getId())==null) {

            templateMapper.insert(template);
        } else {
            templateMapper.updateByPrimaryKeySelective(template);
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
        templateMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public Template selectByPrimaryKey(Long id){
       return templateMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            templateMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}

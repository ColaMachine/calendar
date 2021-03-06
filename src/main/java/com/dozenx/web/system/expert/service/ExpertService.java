/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.system.expert.service;

import com.dozenx.util.ResultUtil;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.system.expert.bean.Expert;
import com.dozenx.web.system.expert.dao.ExpertMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service("expertService")
public class ExpertService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(ExpertService.class);
    @Resource
    private ExpertMapper expertMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<Expert> listByParams4Page(HashMap params) {
        return expertMapper.listByParams4Page(params);
    }
    public List<Expert> listByParams(HashMap params) {
        return expertMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return expertMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param Expert
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(Expert expert) {
        // 进行字段验证
      /* ValidateUtil<Expert> vu = new ValidateUtil<Expert>();
        ResultDTO result = vu.valid(expert);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (expert.getId()==null ||  this.selectByPrimaryKey(expert.getId())==null) {

            expertMapper.insert(expert);
        } else {
            expert.setUpdatetime(new Timestamp(new Date().getTime()));
            expertMapper.updateByPrimaryKeySelective(expert);
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
        expertMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public Expert selectByPrimaryKey(Long id){
       return expertMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            expertMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}

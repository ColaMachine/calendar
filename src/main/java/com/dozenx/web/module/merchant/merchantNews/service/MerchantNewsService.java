/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.merchant.merchantNews.service;

import com.dozenx.util.ResultUtil;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.merchant.merchantNews.bean.MerchantNews;
import com.dozenx.web.module.merchant.merchantNews.dao.MerchantNewsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("merchantNewsService")
public class MerchantNewsService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(MerchantNewsService.class);
    @Resource
    private MerchantNewsMapper merchantNewsMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<MerchantNews> listByParams4Page(HashMap params) {
        return merchantNewsMapper.listByParams4Page(params);
    }
    public List<MerchantNews> listByParams(HashMap params) {
        return merchantNewsMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return merchantNewsMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param MerchantNews
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(MerchantNews merchantNews) {
        // 进行字段验证
      /* ValidateUtil<MerchantNews> vu = new ValidateUtil<MerchantNews>();
        ResultDTO result = vu.valid(merchantNews);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (merchantNews.getId()==null ||  this.selectByPrimaryKey(merchantNews.getId())==null) {

            merchantNewsMapper.insert(merchantNews);
        } else {
            merchantNewsMapper.updateByPrimaryKeySelective(merchantNews);
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
    public void delete(Integer  id){
        merchantNewsMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public MerchantNews selectByPrimaryKey(Integer id){
       return merchantNewsMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            merchantNewsMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}

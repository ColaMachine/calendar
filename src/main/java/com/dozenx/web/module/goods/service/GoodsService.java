/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.goods.service;

import com.dozenx.util.ResultUtil;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.goods.bean.Goods;
import com.dozenx.web.module.goods.dao.GoodsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service("goodsService")
public class GoodsService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(GoodsService.class);
    @Resource
    private GoodsMapper goodsMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<Goods> listByParams4Page(HashMap params) {
        return goodsMapper.listByParams4Page(params);
    }
    public List<Goods> listByParams(HashMap params) {
        return goodsMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return goodsMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param Goods
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(Goods goods) {
        // 进行字段验证
      /* ValidateUtil<Goods> vu = new ValidateUtil<Goods>();
        ResultDTO result = vu.valid(goods);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (goods.getId()==null ||  this.selectByPrimaryKey(goods.getId())==null) {

            goodsMapper.insert(goods);
        } else {
            goods.setUpdatetime(new Timestamp(new Date().getTime()));
            goodsMapper.updateByPrimaryKeySelective(goods);
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
        goodsMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public Goods selectByPrimaryKey(Long id){
       return goodsMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            goodsMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}

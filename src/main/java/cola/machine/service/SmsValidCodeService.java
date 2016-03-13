/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package cola.machine.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cola.machine.bean.SmsValidCode;
import cola.machine.dao.SmsValidCodeMapper;
import cola.machine.util.CacheUtil;
import cola.machine.util.ResultUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.StringUtil;
import core.page.Page;

import core.action.ResultDTO;

@Service("smsValidCodeService")
public class SmsValidCodeService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SmsValidCodeService.class);
    @Resource
    private SmsValidCodeMapper smsValidCodeMapper;
    /**
     * 说明:list by page and params
     * @param page
     * @return
     * @return List<Role>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SmsValidCode> listByParams4Page(HashMap params) {
        return smsValidCodeMapper.listByParams4Page(params);
    }
     public List<SmsValidCode> listByParams(HashMap params) {
        return smsValidCodeMapper.listByParams(params);
    }

    /*
     * 说明:
     * @param SmsValidCode
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SmsValidCode smsValidCode) {
        // 进行字段验证
       ValidateUtil<SmsValidCode> vu = new ValidateUtil<SmsValidCode>();
        ResultDTO result = vu.valid(smsValidCode);
        if (result.getR() != 1) {
            return result;
        }
         //逻辑业务判断判断
       
       //判断是更新还是插入
        if (smsValidCode.getId()==null) {
               
            smsValidCodeMapper.insert(smsValidCode);
        } else {
             smsValidCodeMapper.updateByPrimaryKey(smsValidCode);
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
        smsValidCodeMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SmsValidCode selectByPrimaryKey(Integer id){
       return smsValidCodeMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            smsValidCodeMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}

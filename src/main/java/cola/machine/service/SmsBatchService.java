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

import cola.machine.bean.SmsBatch;
import cola.machine.dao.SmsBatchMapper;
import cola.machine.util.CacheUtil;
import cola.machine.util.ResultUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.ValidateUtil;

import com.awifi.core.page.Page;
import com.awifi.util.StringUtils;

import core.action.ResultDTO;

@Service("smsBatchService")
public class SmsBatchService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SmsBatchService.class);
    @Resource
    private SmsBatchMapper smsBatchMapper;

    /**
     * 说明:list by page and params
     * @param page
     * @return
     * @return List<Role>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SmsBatch> list(HashMap params) {
        return smsBatchMapper.selectByPage(params);
    }
    
    /*
    *//**
     * 说明:
     * @param SmsBatch
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SmsBatch smsBatch) {
        // 进行字段验证
       ValidateUtil<SmsBatch> vu = new ValidateUtil<SmsBatch>();
        ResultDTO result = vu.valid(smsBatch);
        if (result.getR() != 1) {
            return result;
        }
         //逻辑业务判断判断
       
       //判断是更新还是插入
        if (smsBatch.getId()==null) {
            smsBatchMapper.insert(smsBatch);
        } else {
             smsBatchMapper.updateByPrimaryKey(smsBatch);
        }
        return new ResultDTO(1, "保存成功");
    }
 
    /**
    * 说明:根据主键删除数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public void delete(    Integer  id){
        permissionMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SmsBatch selectByPrimaryKey(Integer id){
       return smsBatchMapper.selectByPrimaryKey(id);
    }
}

/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
 <#assign abc="${table.name[0]?lower_case}${table.name[1..]}">
<#assign Abc="${table.name[0]?upper_case}${table.name[1..]}">
package cola.machine.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cola.machine.bean.${Abc};
import cola.machine.dao.${Abc}Mapper;
import cola.machine.dao.${Abc}Service;
import cola.machine.util.CacheUtil;
import cola.machine.util.ResultUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.ValidateUtil;

import com.awifi.core.page.Page;
import com.awifi.util.StringUtils;

import core.action.ResultDTO;

@Service("${abc}Service")
public class ${Abc}Service extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(${Abc}Service.class);
    @Resource
    private ${Abc}Mapper ${abc}Mapper;

    /**
     * 说明:list by page and params
     * @param page
     * @return
     * @return List<Role>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<${Abc}> list(HashMap params) {
        return ${abc}Mapper.selectByPage(params);
    }
    
    /*
    *//**
     * 说明:
     * @param ${Abc}
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(${Abc} ${abc}) {
        // 进行字段验证
       ValidateUtil<${Abc}> vu = new ValidateUtil<${Abc}>();
        ResultDTO result = vu.valid(${abc});
        if (result.getR() != 1) {
            return result;
        }
         //逻辑业务判断判断
       
       //判断是更新还是插入
        if (${abc}.get${table.pk.name[0]?upper_case}${table.pk.name[1..]}()==null) {
            <#if table.pk.type?starts_with("varchar")>
            <#else>
               ${abc}.set${table.pk.name[0]?upper_case}${table.pk.name[1..]}(UUIDUtil.getUUID());
            </#if>
            ${abc}Mapper.insert(${abc});
        } else {
             ${abc}Mapper.updateByPrimaryKey(${abc});
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
    public void delete(    <@javaType>${table.pk.type}</@javaType>  ${table.pk.name}){
        permissionMapper.deleteByPrimaryKey(${table.pk.name});
    }   
    /**
    * 说明:根据主键删除数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public ${Abc} selectByPrimaryKey(<@javaType>${table.pk.type}</@javaType> id){
       return ${abc}Mapper.selectByPrimaryKey(id);
    }
}

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

import cola.machine.bean.${Abc};
import cola.machine.dao.${Abc}Mapper;
import cola.machine.util.CacheUtil;
import cola.machine.util.ResultUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.StringUtil;
import core.page.Page;
<#if table.mapper??>
import java.util.StringTokenizer;
import cola.machine.bean.${table.mapper.mapper};
import cola.machine.dao.${table.mapper.mapper}Mapper;
</#if>

import core.action.ResultDTO;

@Service("${abc}Service")
public class ${Abc}Service extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(${Abc}Service.class);
    @Resource
    private ${Abc}Mapper ${abc}Mapper;
      <#if table.mapper??>
       @Resource
    private <@getAbc>${table.mapper.mapper}</@getAbc>Mapper <@getabc>${table.mapper.mapper}</@getabc>Mapper;
      </#if>
    /**
     * 说明:list by page and params
     * @param page
     * @return
     * @return List<Role>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<${Abc}> listByParams4Page(HashMap params) {
        return ${abc}Mapper.listByParams4Page(params);
    }
     public List<${Abc}> listByParams(HashMap params) {
        return ${abc}Mapper.listByParams(params);
    }

    /*
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
                ${abc}.set${table.pk.name[0]?upper_case}${table.pk.name[1..]}(UUIDUtil.getUUID());
            <#else>
               
            </#if>
            ${abc}Mapper.insert(${abc});
        } else {
             ${abc}Mapper.updateByPrimaryKey(${abc});
        }
        return ResultUtil.getSuccResult();
    }
    <#if table.mapper??>
     /*
     * 说明:和关联关系一起保存
     * @param ${Abc}
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO saveWithChilds(${Abc} ${abc},String childids) {
        // 进行字段验证
        ValidateUtil<${Abc}> vu = new ValidateUtil<${Abc}>();
        ResultDTO result = vu.valid(${abc});
        if (result.getR() != 1) {
            return result;
        }
         //逻辑业务判断判断
       
        //判断是更新还是插入
        boolean addFlag =false;

        if (${abc}.get${table.pk.name[0]?upper_case}${table.pk.name[1..]}()==null) {
            <#if table.pk.type?starts_with("varchar")>
                ${abc}.set${table.pk.name[0]?upper_case}${table.pk.name[1..]}(UUIDUtil.getUUID());
            <#else>
               
            </#if>
            ${abc}Mapper.insert(${abc});
            addFlag=true;
        } else {
             ${abc}Mapper.updateByPrimaryKey(${abc});
        }

         /***删除掉不在关系中的数据****/
        HashMap params =new HashMap();
        params.put("${table.mapper.parentid}", <@getabc>${abc}</@getabc>.get<@getAbc>${table.pk.name}</@getAbc>());
        List<${table.mapper.mapper}> list = <@getabc>${table.mapper.mapper}</@getabc>Mapper.listByParams(params);
        for(${table.mapper.mapper} <@getabc>${table.mapper.mapper}</@getabc> : list){
            String ${table.mapper.childid} = ""+<@getabc>${table.mapper.mapper}</@getabc>.get<@getAbc>${table.mapper.childid}</@getAbc>();
            if( !StringUtil.splitStrContains(childids,${table.mapper.childid})){
                <@getabc>${table.mapper.mapper}</@getabc>Mapper.deleteByPrimaryKey(<@getabc>${table.mapper.mapper}</@getabc>.getId());
            }
        }
        
        if(!StringUtil.isBlank(childids)){
            StringTokenizer st = new StringTokenizer(childids,",",false);
            while( st.hasMoreElements() ){
                String stNow=  st.nextToken(); 
                //System.out.println(stNow);
                //进行ids验证
                if(!StringUtil.checkNumeric(stNow)){
                    return ResultUtil.getResult(302,"子元素id格式不正确");
                }
                //查询是否有关联数据有的话就不用再插入了 如果是新增数据就不用判断了
                if(!addFlag){
                    params.clear();
                    params.put("${table.mapper.parentid}", ${abc}.getId());
                    params.put("${table.mapper.childid}", stNow);
                    int count = <@getabc>${table.mapper.mapper}</@getabc>Mapper.countByParams(params);
                    if(count>0){
                        continue;
                    }
                    
                }
                ${table.mapper.mapper} <@getabc>${table.mapper.mapper}</@getabc>=new ${table.mapper.mapper}();
              
                <@getabc>${table.mapper.mapper}</@getabc>.set<@getAbc>${table.mapper.parentid}</@getAbc>(${abc}.get<@getAbc>${table.pk.name}</@getAbc>());
                <@getabc>${table.mapper.mapper}</@getabc>.set<@getAbc>${table.mapper.childid}</@getAbc>(${serviceSaveWithChilds});
                <@getabc>${table.mapper.mapper}</@getabc>Mapper.insert( <@getabc>${table.mapper.mapper}</@getabc>);
            }
        }

        return ResultUtil.getSuccResult();
    }
    </#if>
    /**
    * 说明:根据主键删除数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public void delete(<@javaType>${table.pk.type}</@javaType>  ${table.pk.name}){
        ${abc}Mapper.deleteByPrimaryKey(${table.pk.name});
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public ${Abc} selectByPrimaryKey(<@javaType>${table.pk.type}</@javaType> id){
       return ${abc}Mapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(<@javaType>${table.pk.type}</@javaType>[] idAry) {
        for(int i=0;i<idAry.length;i++){
            ${abc}Mapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}

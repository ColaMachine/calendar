/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.system.portal.editorTempComp.service;

import com.dozenx.util.ResultUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.system.portal.component.bean.Component;
import com.dozenx.web.system.portal.component.dao.ComponentMapper;
import com.dozenx.web.system.portal.editorTempComp.bean.EditorTempComp;
import com.dozenx.web.system.portal.editorTempComp.dao.EditorTempCompMapper;
import com.dozenx.web.system.portal.template.bean.Template;
import com.dozenx.web.system.portal.template.dao.TemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("editorTempCompService")
public class EditorTempCompService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(EditorTempCompService.class);
    @Resource
    private EditorTempCompMapper editorTempCompMapper;
    @Resource
    private TemplateMapper templateMapper;
    @Resource
    private ComponentMapper componentMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<EditorTempComp> listByParams4Page(HashMap params) {
        return editorTempCompMapper.listByParams4Page(params);
    }
    public List<EditorTempComp> listByParams(HashMap params) {
        return editorTempCompMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return editorTempCompMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param EditorTempComp
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(EditorTempComp editorTempComp) {
        // 进行字段验证
      /* ValidateUtil<EditorTempComp> vu = new ValidateUtil<EditorTempComp>();
        ResultDTO result = vu.valid(editorTempComp);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (editorTempComp.getId()==null ||  this.selectByPrimaryKey(editorTempComp.getId())==null) {

            editorTempCompMapper.insert(editorTempComp);
        } else {
            editorTempCompMapper.updateByPrimaryKeySelective(editorTempComp);
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
        editorTempCompMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public EditorTempComp selectByPrimaryKey(Long id){
       return editorTempCompMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            editorTempCompMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }

    /**
     * 多项关联保存
     * @param templateIds
     * @param componentIds
     * @return
     */
        public ResultDTO msave(String templateIds,String componentIds) {
            if(StringUtil.isBlank(templateIds)){
                return ResultUtil.getResult(101,"参数错误");
            }

            String[] templateIdAry= templateIds.split(",");
            String[] componentIdAry=componentIds.split(",");
            Long[] templateIdAryReal =new  Long[templateIdAry.length];
            Long[] componentIdAryReal =new  Long[componentIdAry.length];
            for(int i=0;i<templateIdAry.length;i++){
                if(!StringUtil.checkNumeric(templateIdAry[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                templateIdAryReal[i]=Long.valueOf(templateIdAry[i]);
            }
            if(StringUtil.isBlank(componentIds)){
                componentIdAryReal=null;
                 componentIdAry=null;
            }
            if(componentIdAry!=null)
            for(int i=0;i<componentIdAry.length;i++){
                if(!StringUtil.checkNumeric(componentIdAry[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                componentIdAryReal[i]=Long.valueOf(componentIdAry[i]);
            }
            //验证父亲id 正确性 是否存在
             if(templateIdAryReal!=null)
            for(int i=0;i< templateIdAryReal.length;i++){
                //
                Template template = templateMapper.selectByPrimaryKey(templateIdAryReal[i]);
                if(template==null ){
                    return ResultUtil.getResult(101,"数据不存在");
                }
                //查询的数据不存在
            }
             if(componentIdAryReal!=null)
            for(int i=0;i<componentIdAryReal.length;i++){
                 Component component = componentMapper.selectByPrimaryKey(componentIdAryReal[i]);
                //查询的数据不存在
                if(component==null ){
                    return ResultUtil.getResult(101,"数据不存在");
                }
            }
             HashMap params =new HashMap();
            //验证子id 正确性 是否存在
             if(componentIdAryReal!=null)
            for(int i=0;i<templateIdAryReal.length;i++){
                for(int j=0;j<componentIdAryReal.length;j++){
                   EditorTempComp editorTempComp =new  EditorTempComp();
                    Long templateId =templateIdAryReal[i];
                    Long componentId =componentIdAryReal[j];
                    //查找是否已经有关联数据了

                    params.put("componentId",componentId);
                    params.put("templateId",templateId);
                    int count = editorTempCompMapper.countByParams(params);
                    if(count>0)continue;
                    editorTempComp.setComponentId(componentId);
                    editorTempComp.setTemplateId(templateId);
                    editorTempCompMapper.insert(editorTempComp);
                }
            }
            //删除多余的数据
            params.clear();
            params.put("componentIds",componentIdAryReal);
            params.put("templateIds",templateIdAryReal);
            editorTempCompMapper.deleteExtra(params);
            //delete from SysUserRole where uid in (1,2,3,4,5) and rid not in(1,2,3)
            return ResultUtil.getSuccResult();
        }


    /**
     * 多项关联保存
     * @param templateIdStr
     * @param list
     * @return
     */
    public ResultDTO msave1(String templateIdStr,List list) {

        if(StringUtil.isBlank(templateIdStr) || !StringUtil.checkNumeric(templateIdStr)){
            return ResultUtil.getResult(101,"参数错误");
        }
        Long templateId =Long.valueOf(templateIdStr);


        List<EditorTempComp> newList = new ArrayList<>();
        // 参数预处理
        Long[] componentIds =new Long[list.size()];

        Template template = templateMapper.selectByPrimaryKey(Long.valueOf(templateId));
        if (template == null) {
            return ResultUtil.getResult(101, "数据不存在");
        }
        HashMap params =new HashMap();
        params.put("templateId",templateId);



        if(list!=null)
            for(int i=0;i<list.size();i++){
                HashMap record =(HashMap)list.get(i);
                EditorTempComp editorTempComp = new EditorTempComp();
                Object idObj =  record.get("id");


                Integer index= (Integer)record.get("index");
                editorTempComp.setIndex(index);
                Long componentId =  Long.valueOf(record.get("componentId")+"");
                if(componentId == null ){
                    return ResultUtil.getResult(101,"参数错误");
                }
                Component component = componentMapper.selectByPrimaryKey(componentId);
                //查询的数据不存在
                if(component==null ){
                    return ResultUtil.getResult(101,"数据不存在");
                }
                componentIds[i]=Long.valueOf(componentId);

                editorTempComp.setComponentId(componentId);
                String config =(String) record.get("state");
                editorTempComp.setConfig(config);
                editorTempComp.setTemplateId(templateId);
                newList.add(editorTempComp);
                if(idObj!=null){
                    Long id= Long.valueOf(idObj+"");
                    //editorTempComp.setId(id);
                    EditorTempComp tempComp = editorTempCompMapper.selectByPrimaryKey(id);
                    if(tempComp== null ){
                        return ResultUtil.getResult(101,"数据不存在");
                    }
                    editorTempComp.setId(id);


                    editorTempCompMapper.updateByPrimaryKey(editorTempComp);
                }else{
                    editorTempCompMapper.insert(editorTempComp);
                }

            }


        List<EditorTempComp> oldList = editorTempCompMapper.listByParams(params);

        for(EditorTempComp oldTempComp : oldList){
            boolean exist = false;
            for(EditorTempComp newTempComp: newList){
                if(newTempComp.getId() == oldTempComp.getId()){
                    exist=true;
                    break;
                }


            }
            if(!exist){
                editorTempCompMapper.deleteByPrimaryKey(oldTempComp.getId());
            }
        }


        //delete from SysUserRole where uid in (1,2,3,4,5) and rid not in(1,2,3)
        return ResultUtil.getSuccResult();
    }
}

/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.system.portal.component.service;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.dozenx.core.Path.PathManager;
import com.dozenx.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.system.portal.component.bean.Component;
import com.dozenx.web.system.portal.component.dao.ComponentMapper;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("componentService")
public class ComponentService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(ComponentService.class);
    @Resource
    private ComponentMapper componentMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<Component> listByParams4Page(HashMap params) {

        List<Component> components=
         componentMapper.listByParams4Page(params);
        for(Component component:components){
            try {
                component.setHtml(FileUtil.readFile2Str(PathManager.getInstance().getHomePath().resolve("src/main/webapp/static/html/editor/component/source").resolve("entity" + component.getId() + ".html").toString()));

                component.setEntity(FileUtil.readFile2Str(PathManager.getInstance().getHomePath().resolve("src/main/webapp/static/js/editor/component/source").resolve("entity" + component.getId() + ".js").toString()));

                component.setSetting(FileUtil.readFile2Str(PathManager.getInstance().getHomePath().resolve("src/main/webapp/static/js/editor/component/source").resolve("setting" + component.getId() + ".js").toString()));

                component.setEntitySource(FileUtil.readFile2Str(PathManager.getInstance().getHomePath().resolve("static/js/editor/component/source").resolve("entity" + component.getId() + ".js").toString()));

                component.setSettingSource(FileUtil.readFile2Str(PathManager.getInstance().getHomePath().resolve("static/js/editor/component/source").resolve("setting" + component.getId() + ".js").toString()));


                component.setCss(FileUtil.readFile2Str(PathManager.getInstance().getTmpPath().resolve("component" + component.getId() + ".css").toString()));
                ;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return components;
    }
    public List<Component> listByParams(HashMap params) {
        return componentMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return componentMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param Component
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(Component component) {
        // 进行字段验证
      /* ValidateUtil<Component> vu = new ValidateUtil<Component>();
        ResultDTO result = vu.valid(component);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入

        if (component.getId()==null ||  this.selectByPrimaryKey(component.getId())==null) {

            componentMapper.insert(component);
        } else {
            componentMapper.updateByPrimaryKeySelective(component);
        }
        try {
        /*    if(component.getEntity().length()>0){
                FileUtil.writeFile(PathManager.getInstance().getTmpPath().resolve("").resolve("entity"+component.getId()+".js").toFile(),component.getEntity()*//*.replaceAll("&lt;","<").replaceAll("&gt;",">")*//*.replaceAll("_Entity_","Entity"+component.getId()));
              //  FileUtil.writeFile(PathManager.getInstance().getTmpPath().resolve("component"+component.getId()+".jshtml").toFile(),component.getJsHtml());
            }

            if(component.getSetting().length()>0){
                FileUtil.writeFile(PathManager.getInstance().getTmpPath().resolve("setting"+component.getId()+".js").toFile(),component.getSetting()*//*.replaceAll("&lt;","<").replaceAll("&gt;",">")*//*.replaceAll("_Setting_","Setting"+component.getId()));
                //  FileUtil.writeFile(PathManager.getInstance().getTmpPath().resolve("component"+component.getId()+".jshtml").toFile(),component.getJsHtml());
            }*/
            if(StringUtil.isNotBlank(component.getHtml())){
                FileUtil.writeFile(PathManager.getInstance().getHomePath().resolve("src/main/webapp/static/html/editor/component").resolve("source/entity"+component.getId()+".html").toString(),component.getHtml()/*.replaceAll("&lt;","<").replaceAll("&gt;",">")*/.replaceAll("_Entity_","Entity"+component.getId()));
                //  FileUtil.writeFile(PathManager.getInstance().getTmpPath().resolve("component"+component.getId()+".jshtml").toFile(),component.getJsHtml());
            }
            if(StringUtil.isNotBlank(component.getEntitySource())){
                FileUtil.writeFile(PathManager.getInstance().getHomePath().resolve("static/js/editor/component").resolve("source/entity"+component.getId()+".js").toString(),component.getEntitySource()/*.replaceAll("&lt;","<").replaceAll("&gt;",">")*/.replaceAll("_Entity_","Entity"+component.getId()));
                //  FileUtil.writeFile(PathManager.getInstance().getTmpPath().resolve("component"+component.getId()+".jshtml").toFile(),component.getJsHtml());
            }

            if(StringUtil.isNotBlank(component.getSettingSource())){
                FileUtil.writeFile(PathManager.getInstance().getHomePath().resolve("static/js/editor/component").resolve("source/setting"+component.getId()+".js").toString(),component.getSettingSource()/*.replaceAll("&lt;","<").replaceAll("&gt;",">")*/.replaceAll("_Setting_","Setting"+component.getId()));
                //  FileUtil.writeFile(PathManager.getInstance().getTmpPath().resolve("component"+component.getId()+".jshtml").toFile(),component.getJsHtml());
            }

            if(StringUtil.isNotBlank(component.getCss())){
                FileUtil.writeFile(PathManager.getInstance().getTmpPath().resolve("component"+component.getId()+".css").toString(),component.getCss()/*.replaceAll("&lt;","<").replaceAll("&gt;",">")*/);
                //FileUtil.writeFile(PathManager.getInstance().getTmpPath().resolve("component"+component.getId()+".csshtml").toFile(),component.getCssHtml());
            }

           new CmdUtil().execCommand("gulp build",PathManager.getInstance().getHomePath().resolve("gulp").toString());
        } catch (IOException e) {
            e.printStackTrace();
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
        componentMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public Component selectByPrimaryKey(Long id){
        Component component = componentMapper.selectByPrimaryKey(id);
        try {
            component.setHtml( FileUtil.readFile2Str(PathManager.getInstance().getHomePath().resolve("src/main/webapp/static/html/editor/component/source").resolve("entity"+component.getId()+".html").toString()));

            component.setEntity( FileUtil.readFile2Str(PathManager.getInstance().getHomePath().resolve("src/main/webapp/static/js/editor/component/source").resolve("entity"+component.getId()+".js").toString()));

            component.setSetting( FileUtil.readFile2Str(PathManager.getInstance().getHomePath().resolve("src/main/webapp/static/js/editor/component/source").resolve("setting"+component.getId()+".js").toString()));

            component.setEntitySource( FileUtil.readFile2Str(PathManager.getInstance().getHomePath().resolve("static/js/editor/component/source").resolve("entity"+component.getId()+".js").toString()));

            component.setSettingSource( FileUtil.readFile2Str(PathManager.getInstance().getHomePath().resolve("static/js/editor/component/source").resolve("setting"+component.getId()+".js").toString()));


            component.setCss( FileUtil.readFile2Str(PathManager.getInstance().getTmpPath().resolve("component"+component.getId()+".css").toString()));;

           // component.setJsHtml( FileUtil.readFile2Str(PathManager.getInstance().getTmpPath().resolve("component"+component.getId()+".js.html").toString()));
           // component.setCssHtml( FileUtil.readFile2Str(PathManager.getInstance().getTmpPath().resolve("component"+component.getId()+".css.html").toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;

       return component;
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            componentMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}

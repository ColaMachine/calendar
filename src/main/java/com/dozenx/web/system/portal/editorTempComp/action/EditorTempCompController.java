/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.system.portal.editorTempComp.action;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.dozenx.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.dozenx.web.system.portal.component.service.ComponentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.dozenx.web.system.portal.editorTempComp.service.EditorTempCompService;
import com.dozenx.web.system.portal.editorTempComp.bean.EditorTempComp;
import com.dozenx.util.ResultUtil;
import com.dozenx.util.ValidateUtil;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.util.StringUtil;
import com.dozenx.util.ValidateUtil;
import com.dozenx.web.util.RequestUtil;
import com.dozenx.web.message.ResultDTO;
import com.dozenx.util.DateUtil;

@Controller
@RequestMapping("/editorTempComp")
public class EditorTempCompController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(EditorTempCompController.class);
    /** 权限service **/
    @Autowired
    private EditorTempCompService editorTempCompService;
    @Autowired
    private ComponentService componentService;

    /**
     * 说明: 跳转到角色列表页面
     *
     * @return
     * @return String
     * @author dozen.zhang
     * @date 2015年11月15日下午12:30:45
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    public String list() {
        return "/static/html/editor/editorTempComp/EditorTempCompList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapper() {
        return "/static/html/editor/editorTempComp/EditorTempCompListMapper.html";
    }

    /**
     * 说明:ajax请求角色信息
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午12:31:55
     */
    @RequestMapping(value = "/list.json")
    @ResponseBody
    public Object list(HttpServletRequest request) {
        Page page = RequestUtil.getPage(request);
        if(page ==null){
            return this.getWrongResultFromCfg("err.param.page");
        }

        HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String templateId = request.getParameter("templateId");
        if(!StringUtil.isBlank(templateId)){
            params.put("templateId",templateId);
        }
        String componentId = request.getParameter("componentId");
        if(!StringUtil.isBlank(componentId)){
            params.put("componentId",componentId);
        }
        String config = request.getParameter("config");
        if(!StringUtil.isBlank(config)){
            params.put("config",config);
        }
        String configLike = request.getParameter("configLike");
        if(!StringUtil.isBlank(configLike)){
            params.put("configLike",configLike);
        }

        params.put("page",page);
        List<EditorTempComp> editorTempComps = editorTempCompService.listByParams4Page(params);
        for(EditorTempComp editorTempComp:editorTempComps){
            editorTempComp.setHtml(componentService.selectByPrimaryKey(editorTempComp.getComponentId()).getHtml());
        }
        return ResultUtil.getResult(editorTempComps, page);
    }

    /**
     * 说明:ajax请求角色信息 无分页版本
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午12:31:55
     */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public Object listAll(HttpServletRequest request) {
        HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String templateId = request.getParameter("templateId");
        if(!StringUtil.isBlank(templateId)){
            params.put("templateId",templateId);
        }
        String componentId = request.getParameter("componentId");
        if(!StringUtil.isBlank(componentId)){
            params.put("componentId",componentId);
        }
        String config = request.getParameter("config");
        if(!StringUtil.isBlank(config)){
            params.put("config",config);
        }
        String configLike = request.getParameter("configLike");
        if(!StringUtil.isBlank(configLike)){
            params.put("configLike",configLike);
        }

        List<EditorTempComp> editorTempComps = editorTempCompService.listByParams(params);
        return ResultUtil.getDataResult(editorTempComps);
    }

    /**
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/editor/editorTempComp/EditorTempCompEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/editor/editorTempComp/EditorTempCompView.html";
    }

    @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
        String id = request.getParameter("id");
        HashMap<String,Object> result =new HashMap<String,Object>();
        if(!StringUtil.isBlank(id)){
            EditorTempComp bean = editorTempCompService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        EditorTempComp bean = editorTempCompService.selectByPrimaryKey(Long.valueOf(id));
        HashMap<String,Object> result =new HashMap<String,Object>();
        result.put("bean", bean);
        return this.getResult(bean);*/
    }


    /**
     * 说明:保存角色信息
     *
     * @param request
     * @return
     * @throws Exception
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:00
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "/save.json")
    @ResponseBody
    public Object save(HttpServletRequest request) throws Exception {
        EditorTempComp editorTempComp =new  EditorTempComp();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            editorTempComp.setId(Long.valueOf(id)) ;
        }

        String templateId = request.getParameter("templateId");
        if(!StringUtil.isBlank(templateId)){
            editorTempComp.setTemplateId(Long.valueOf(templateId)) ;
        }

        String componentId = request.getParameter("componentId");
        if(!StringUtil.isBlank(componentId)){
            editorTempComp.setComponentId(Long.valueOf(componentId)) ;
        }

        String config = request.getParameter("config");
        if(!StringUtil.isBlank(config)){
            editorTempComp.setConfig(String.valueOf(config)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            editorTempComp.setId(Long.valueOf(id));
        }
        String templateId = request.getParameter("templateId");
        if(!StringUtil.isBlank(templateId)){
            editorTempComp.setTemplateId(Long.valueOf(templateId));
        }
        String componentId = request.getParameter("componentId");
        if(!StringUtil.isBlank(componentId)){
            editorTempComp.setComponentId(Long.valueOf(componentId));
        }
        String config = request.getParameter("config");
        if(!StringUtil.isBlank(config)){
            editorTempComp.setConfig(config);
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
        vu.add("templateId", templateId, "模板id",  new Rule[]{new Digits(15,0),new NotEmpty()});
        vu.add("componentId", componentId, "组件id",  new Rule[]{new Digits(15,0)});
        vu.add("config", config, "配置",  new Rule[]{new Length(200)});
        validStr = vu.validateString();
        if(StringUtil.isNotEmpty(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return editorTempCompService.save(editorTempComp);


    }
    @RequestMapping(value = "/msave.json")
    @ResponseBody
    public Object msave(HttpServletRequest request) throws Exception {

        String templateIds= request.getParameter("templateIds");
        String componentIds= request.getParameter("componentIds");
        return editorTempCompService.msave( templateIds, componentIds);
    }

    @RequestMapping(value = "/msave1.json")
    @ResponseBody
    public Object msave1(HttpServletRequest request) throws Exception {
       // HashMap map =MapUtils.request2Map(request);
        String templateId = (String)request.getParameter("templateId");

        String data = (String)request.getParameter("data");
       List<HashMap> list =  JSON.parseArray(data,HashMap.class);
        if(StringUtil.isBlank(templateId)){
            return getResult(1,"缺少参数");
        }
      //  Iterator<Map.Entry<String,String>> it = map.entrySet().iterator();
       /* List list =new ArrayList();
        while(it.hasNext()){
            Map.Entry entry = it.next();
            HashMap record =new HashMap();
            if(entry.getKey().equals("r") || entry.getKey().equals("templateId"))continue;
            record.put("componentId",entry.getKey());
            record.put("config",entry.getValue());
            list.add(record);
        }*/


        return editorTempCompService.msave1( templateId, list);
    }

    @RequestMapping(value = "/del.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Long id = Long.valueOf(idStr);
        editorTempCompService.delete(id);
        return this.getResult(SUCC);
    }
    /**
     * 多行删除
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/mdel.json")
    @ResponseBody
    public Object multiDelete(HttpServletRequest request) {
        String idStr = request.getParameter("ids");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        String idStrAry[]= idStr.split(",");
        Long idAry[]=new Long[idStrAry.length];
        for(int i=0,length=idAry.length;i<length;i++){
            ValidateUtil vu = new ValidateUtil();
            String validStr="";
            String id = idStrAry[i];
            vu.add("id", id, "编号",  new Rule[]{});

            try{
                validStr=vu.validateString();
            }catch(Exception e){
                e.printStackTrace();
                validStr="验证程序异常";
                return ResultUtil.getResult(302,validStr);
            }

            if(StringUtil.isNotEmpty(validStr)) {
                return ResultUtil.getResult(302,validStr);
            }
            idAry[i]=Long.valueOf(idStrAry[i]);
        }
        return  editorTempCompService.multilDelete(idAry);
    }

    /**
     * 导出
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/export.json")
    @ResponseBody
    public Object exportExcel(HttpServletRequest request){
        HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String templateId = request.getParameter("templateId");
        if(!StringUtil.isBlank(templateId)){
            params.put("templateId",templateId);
        }
        String componentId = request.getParameter("componentId");
        if(!StringUtil.isBlank(componentId)){
            params.put("componentId",componentId);
        }
        String config = request.getParameter("config");
        if(!StringUtil.isBlank(config)){
            params.put("config",config);
        }
        String configLike = request.getParameter("configLike");
        if(!StringUtil.isBlank(configLike)){
            params.put("configLike",configLike);
        }

        // 查询list集合
        List<EditorTempComp> list =editorTempCompService.listByParams(params);
        // 存放临时文件
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "list.xlsx");
        String folder = request.getSession().getServletContext()
                .getRealPath("/")
                + "xlstmp";
        File folder_file = new File(folder);
        if (!folder_file.exists()) {
            folder_file.mkdir();
        }
        String fileName = folder + File.separator
                + DateUtil.formatToString(new Date(), "yyyyMMddHHmmssSSS")
                + ".xlsx";
        // 得到导出Excle时清单的英中文map
        LinkedHashMap<String, String> colTitle = new LinkedHashMap<String, String>();
        colTitle.put("id", "编号");
        colTitle.put("templateId", "模板id");
        colTitle.put("componentId", "组件id");
        colTitle.put("config", "配置");
        List finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            EditorTempComp sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("templateId",  list.get(i).getTemplateId());
            map.put("componentId",  list.get(i).getComponentId());
            map.put("config",  list.get(i).getConfig());
            finalList.add(map);
        }
        try {
            if (ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
                return this.getResult(SUCC,fileName,"导出成功");
            }
            /*
             * return new ResponseEntity<byte[]>(
             * FileUtils.readFileToByteArray(new File(fileName)), headers,
             * HttpStatus.CREATED);
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.getResult(0, "数据为空，导出失败");

    }
    @RequestMapping(value = "/import.json")
    public void importExcel(){

    }
}

/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.system.portal.template.action;

import com.dozenx.util.*;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.system.portal.template.bean.Template;
import com.dozenx.web.system.portal.template.service.TemplateService;
import com.dozenx.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;
@Controller
@RequestMapping("/template")
public class TemplateController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(TemplateController.class);
    /** 权限service **/
    @Autowired
    private TemplateService templateService;
    
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
        return "/static/html/editor/template/TemplateList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapper() {
        return "/static/html/editor/template/TemplateListMapper.html";
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
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = request.getParameter("nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            params.put("face",face);
        }
        String faceLike = request.getParameter("faceLike");
        if(!StringUtil.isBlank(faceLike)){
            params.put("faceLike",faceLike);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                params.put("createtime",createtime);
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtime",new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = request.getParameter("createtimeBegin");
        if(!StringUtil.isBlank(createtimeBegin)){
            if(StringUtil.checkNumeric(createtimeBegin)){
                params.put("createtimeBegin",createtimeBegin);
            }else if(StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeBegin",new Timestamp( DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = request.getParameter("createtimeEnd");
        if(!StringUtil.isBlank(createtimeEnd)){
            if(StringUtil.checkNumeric(createtimeEnd)){
                params.put("createtimeEnd",createtimeEnd);
            }else if(StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeEnd",new Timestamp( DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page",page);
        List<Template> templates = templateService.listByParams4Page(params);
        return ResultUtil.getResult(templates, page);
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
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = request.getParameter("nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            params.put("face",face);
        }
        String faceLike = request.getParameter("faceLike");
        if(!StringUtil.isBlank(faceLike)){
            params.put("faceLike",faceLike);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                params.put("createtime",createtime);
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtime",new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = request.getParameter("createtimeBegin");
        if(!StringUtil.isBlank(createtimeBegin)){
            if(StringUtil.checkNumeric(createtimeBegin)){
                params.put("createtimeBegin",createtimeBegin);
            }else if(StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeBegin",new Timestamp( DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = request.getParameter("createtimeEnd");
        if(!StringUtil.isBlank(createtimeEnd)){
            if(StringUtil.checkNumeric(createtimeEnd)){
                params.put("createtimeEnd",createtimeEnd);
            }else if(StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeEnd",new Timestamp( DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        List<Template> templates = templateService.listByParams(params);
        return ResultUtil.getDataResult(templates);
    }
    
    /**
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/editor/template/TemplateEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/editor/template/TemplateView.html";
    }
   
    @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
            String id = request.getParameter("id");
        HashMap<String,Object> result =new HashMap<String,Object>();
        if(!StringUtil.isBlank(id)){
            Template bean = templateService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        Template bean = templateService.selectByPrimaryKey(Long.valueOf(id));
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
        Template template =new  Template();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            template.setId(Long.valueOf(id)) ;
        }
        
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            template.setName(String.valueOf(name)) ;
        }
        
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            template.setRemark(String.valueOf(remark)) ;
        }
        
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            template.setFace(String.valueOf(face)) ;
        }
        
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            template.setCreatetime(Timestamp.valueOf(createtime)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            template.setId(Long.valueOf(id));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            template.setName(name);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            template.setRemark(remark);
        }
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            template.setFace(face);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                template.setCreatetime(Timestamp.valueOf(createtime));
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                template.setCreatetime(new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
        vu.add("name", name, "模板名称",  new Rule[]{new Length(40),new NotEmpty()});
        vu.add("remark", remark, "描述",  new Rule[]{new Length(200)});
        vu.add("face", face, "图片",  new Rule[]{new Length(100)});
        vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return templateService.save(template);
       
    }

    @RequestMapping(value = "/del.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Long id = Long.valueOf(idStr);
        templateService.delete(id);
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
            
            if(StringUtil.isNotBlank(validStr)) {
                return ResultUtil.getResult(302,validStr);
            }
            idAry[i]=Long.valueOf(idStrAry[i]);
        }
       return  templateService.multilDelete(idAry);
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
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = request.getParameter("nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }
        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            params.put("face",face);
        }
        String faceLike = request.getParameter("faceLike");
        if(!StringUtil.isBlank(faceLike)){
            params.put("faceLike",faceLike);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                params.put("createtime",createtime);
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtime",new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = request.getParameter("createtimeBegin");
        if(!StringUtil.isBlank(createtimeBegin)){
            if(StringUtil.checkNumeric(createtimeBegin)){
                params.put("createtimeBegin",createtimeBegin);
            }else if(StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeBegin",new Timestamp( DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = request.getParameter("createtimeEnd");
        if(!StringUtil.isBlank(createtimeEnd)){
            if(StringUtil.checkNumeric(createtimeEnd)){
                params.put("createtimeEnd",createtimeEnd);
            }else if(StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createtimeEnd",new Timestamp( DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        // 查询list集合
        List<Template> list =templateService.listByParams(params);
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
        colTitle.put("name", "模板名称");
        colTitle.put("remark", "描述");
        colTitle.put("face", "图片");
        colTitle.put("createtime", "创建时间");
        List finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Template sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("name",  list.get(i).getName());
            map.put("remark",  list.get(i).getRemark());
            map.put("face",  list.get(i).getFace());
            map.put("createtime",  list.get(i).getCreatetime());
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

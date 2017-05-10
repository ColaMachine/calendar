/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysMenu.action;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;
import com.dozenx.util.*;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.dozenx.web.core.auth.sysMenu.service.SysMenuService;
import com.dozenx.web.core.auth.sysMenu.bean.SysMenu;
import com.dozenx.util.ResultUtil;
import com.dozenx.util.ValidateUtil;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.util.StringUtil;
import com.dozenx.web.util.RequestUtil;
import com.dozenx.util.DateUtil;
@Controller
@RequestMapping("/sysMenu")
public class SysMenuController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysMenuController.class);
    /** 权限service **/
    @Autowired
    private SysMenuService sysMenuService;
    
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
        return "/static/html/SysMenuList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapper() {
        return "/static/html/SysMenuListMapper.html";
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
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = request.getParameter("nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            params.put("code",code);
        }
        String codeLike = request.getParameter("codeLike");
        if(!StringUtil.isBlank(codeLike)){
            params.put("codeLike",codeLike);
        }
        String permission = request.getParameter("permission");
        if(!StringUtil.isBlank(permission)){
            params.put("permission",permission);
        }
        String permissionLike = request.getParameter("permissionLike");
        if(!StringUtil.isBlank(permissionLike)){
            params.put("permissionLike",permissionLike);
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = request.getParameter("urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
        }
        String order = request.getParameter("order");
        if(!StringUtil.isBlank(order)){
            params.put("order",order);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }

        params.put("page",page);
        List<SysMenu> sysMenus = sysMenuService.listByParams4Page(params);
        return ResultUtil.getResult(sysMenus, page);
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
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = request.getParameter("nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            params.put("code",code);
        }
        String codeLike = request.getParameter("codeLike");
        if(!StringUtil.isBlank(codeLike)){
            params.put("codeLike",codeLike);
        }
        String permission = request.getParameter("permission");
        if(!StringUtil.isBlank(permission)){
            params.put("permission",permission);
        }
        String permissionLike = request.getParameter("permissionLike");
        if(!StringUtil.isBlank(permissionLike)){
            params.put("permissionLike",permissionLike);
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = request.getParameter("urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
        }
        String order = request.getParameter("order");
        if(!StringUtil.isBlank(order)){
            params.put("order",order);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }

        List<SysMenu> sysMenus = sysMenuService.listByParams(params);
        return ResultUtil.getDataResult(sysMenus);
    }
    
    /**
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/SysMenuEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/SysMenuView.html";
    }
   
    @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
            String id = request.getParameter("id");
        HashMap<String,Object> result =new HashMap<String,Object>();
        if(!StringUtil.isBlank(id)){
            SysMenu bean = sysMenuService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        SysMenu bean = sysMenuService.selectByPrimaryKey(Long.valueOf(id));
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
        SysMenu sysMenu =new  SysMenu();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sysMenu.setId(Long.valueOf(id)) ;
        }
        
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            sysMenu.setPid(Long.valueOf(pid)) ;
        }
        
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            sysMenu.setName(String.valueOf(name)) ;
        }
        
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            sysMenu.setCode(String.valueOf(code)) ;
        }
        
        String permission = request.getParameter("permission");
        if(!StringUtil.isBlank(permission)){
            sysMenu.setPermission(String.valueOf(permission)) ;
        }
        
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            sysMenu.setUrl(String.valueOf(url)) ;
        }
        
        String order = request.getParameter("order");
        if(!StringUtil.isBlank(order)){
            sysMenu.setOrder(Integer.valueOf(order)) ;
        }
        
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            sysMenu.setStatus(Integer.valueOf(status)) ;
        }
        
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            sysMenu.setRemark(String.valueOf(remark)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sysMenu.setId(Long.valueOf(id));
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            sysMenu.setPid(Long.valueOf(pid));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            sysMenu.setName(name);
        }
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            sysMenu.setCode(code);
        }
        String permission = request.getParameter("permission");
        if(!StringUtil.isBlank(permission)){
            sysMenu.setPermission(permission);
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            sysMenu.setUrl(url);
        }
        String order = request.getParameter("order");
        if(!StringUtil.isBlank(order)){
            sysMenu.setOrder(Integer.valueOf(order));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            sysMenu.setStatus(Integer.valueOf(status));
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            sysMenu.setRemark(remark);
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(10,0)});
        vu.add("pid", pid, "父菜单",  new Rule[]{new Digits(10,0)});
        vu.add("name", name, "菜单名称",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("code", code, "菜单代码",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("permission", permission, "权限",  new Rule[]{new Length(255)});
        vu.add("url", url, "资源对应URL",  new Rule[]{new Length(255)});
        vu.add("order", order, "排序id",  new Rule[]{new Digits(11,0)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2"}),new NotEmpty()});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(20)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return sysMenuService.save(sysMenu);
       
    }

    @RequestMapping(value = "/del.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Long id = Long.valueOf(idStr);
        sysMenuService.delete(id);
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
       return  sysMenuService.multilDelete(idAry);
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
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = request.getParameter("nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            params.put("code",code);
        }
        String codeLike = request.getParameter("codeLike");
        if(!StringUtil.isBlank(codeLike)){
            params.put("codeLike",codeLike);
        }
        String permission = request.getParameter("permission");
        if(!StringUtil.isBlank(permission)){
            params.put("permission",permission);
        }
        String permissionLike = request.getParameter("permissionLike");
        if(!StringUtil.isBlank(permissionLike)){
            params.put("permissionLike",permissionLike);
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = request.getParameter("urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
        }
        String order = request.getParameter("order");
        if(!StringUtil.isBlank(order)){
            params.put("order",order);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }

        // 查询list集合
        List<SysMenu> list =sysMenuService.listByParams(params);
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
        colTitle.put("pid", "父菜单");
        colTitle.put("name", "菜单名称");
        colTitle.put("code", "菜单代码");
        colTitle.put("permission", "权限");
        colTitle.put("url", "资源对应URL");
        colTitle.put("order", "排序id");
        colTitle.put("status", "状态");
        colTitle.put("remark", "备注");
        List finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            SysMenu sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("pid",  list.get(i).getPid());
            map.put("name",  list.get(i).getName());
            map.put("code",  list.get(i).getCode());
            map.put("permission",  list.get(i).getPermission());
            map.put("url",  list.get(i).getUrl());
            map.put("order",  list.get(i).getOrder());
            map.put("status",  list.get(i).getStatus());
            map.put("remark",  list.get(i).getRemark());
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

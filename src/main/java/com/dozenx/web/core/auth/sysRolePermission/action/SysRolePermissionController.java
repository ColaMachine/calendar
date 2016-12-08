/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysRolePermission.action;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;
import com.dozenx.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

import com.dozenx.web.core.auth.sysRolePermission.service.SysRolePermissionService;
import com.dozenx.web.core.auth.sysRolePermission.bean.SysRolePermission;
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
@RequestMapping("/sysRolePermission")
public class SysRolePermissionController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysRolePermissionController.class);
    /** 权限service **/
    @Autowired
    private SysRolePermissionService sysRolePermissionService;
    

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
        return "/static/html/SysRolePermissionList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapper() {
        return "/static/html/SysRolePermissionListMapper.html";
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
        String rid = request.getParameter("rid");
        if(!StringUtil.isBlank(rid)){
            params.put("rid",rid);
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }

        params.put("page",page);
        List<SysRolePermission> sysRolePermissions = sysRolePermissionService.listByParams4Page(params);
        return ResultUtil.getResult(sysRolePermissions, page);
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
        String rid = request.getParameter("rid");
        if(!StringUtil.isBlank(rid)){
            params.put("rid",rid);
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }

        List<SysRolePermission> sysRolePermissions = sysRolePermissionService.listByParams(params);
        return ResultUtil.getDataResult(sysRolePermissions);
    }
    
    /**
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/SysRolePermissionEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/SysRolePermissionView.html";
    }
   
    @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
            String id = request.getParameter("id");
        HashMap<String,Object> result =new HashMap<String,Object>();
        if(!StringUtil.isBlank(id)){
            SysRolePermission bean = sysRolePermissionService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        SysRolePermission bean = sysRolePermissionService.selectByPrimaryKey(Long.valueOf(id));
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
        SysRolePermission sysRolePermission =new  SysRolePermission();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sysRolePermission.setId(Long.valueOf(id)) ;
        }
        
        String rid = request.getParameter("rid");
        if(!StringUtil.isBlank(rid)){
            sysRolePermission.setRid(Long.valueOf(rid)) ;
        }
        
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            sysRolePermission.setPid(Long.valueOf(pid)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sysRolePermission.setId(Long.valueOf(id));
        }
        String rid = request.getParameter("rid");
        if(!StringUtil.isBlank(rid)){
            sysRolePermission.setRid(Long.valueOf(rid));
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            sysRolePermission.setPid(Long.valueOf(pid));
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "主键",  new Rule[]{new Digits(15,0)});
        vu.add("rid", rid, "角色id",  new Rule[]{new Digits(15,0),new NotEmpty()});
        vu.add("pid", pid, "权限id",  new Rule[]{new Digits(15,0),new NotEmpty()});
        validStr = vu.validateString();
        if(StringUtil.isNotEmpty(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

          return sysRolePermissionService.save(sysRolePermission);

       
    }
    @RequestMapping(value = "/msave.json")
    @ResponseBody
    public Object msave(HttpServletRequest request) throws Exception {
        String rids= request.getParameter("rids");
        String pids= request.getParameter("pids");
        return sysRolePermissionService.msave( rids, pids);
    }

    @RequestMapping(value = "/del.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Long id = Long.valueOf(idStr);
        sysRolePermissionService.delete(id);
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
                    vu.add("id", id, "主键",  new Rule[]{});

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
       return  sysRolePermissionService.multilDelete(idAry);
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
        String rid = request.getParameter("rid");
        if(!StringUtil.isBlank(rid)){
            params.put("rid",rid);
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }

        // 查询list集合
        List<SysRolePermission> list =sysRolePermissionService.listByParams(params);
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
        colTitle.put("id", "主键");
        colTitle.put("rid", "角色id");
        colTitle.put("pid", "权限id");
        List finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            SysRolePermission sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("rid",  list.get(i).getRid());
            map.put("pid",  list.get(i).getPid());
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

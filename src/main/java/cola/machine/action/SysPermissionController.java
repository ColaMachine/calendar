/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package cola.machine.action;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;

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

import cola.machine.service.SysPermissionService;
import cola.machine.bean.SysPermission;
import cola.machine.util.ResultUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.rules.*;
import core.page.Page;

import cola.machine.util.StringUtil;
import cola.machine.util.ValidateUtil;
import core.util.RequestUtil;
import core.action.ResultDTO;
import cola.machine.util.DateUtil;
@Controller
@RequestMapping("/sysPermission")
public class SysPermissionController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysPermissionController.class);
    /** 权限service **/
    @Autowired
    private SysPermissionService sysPermissionService;
    
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
        return "/static/html/SysPermissionList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapper() {
        return "/static/html/SysPermissionListMapper.html";
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
        List<SysPermission> sysPermissions = sysPermissionService.listByParams4Page(params);
        return ResultUtil.getResult(sysPermissions, page);
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

        List<SysPermission> sysPermissions = sysPermissionService.listByParams(params);
        return ResultUtil.getResult(sysPermissions);
    }
    
    /**
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/SysPermissionEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/SysPermissionView.html";
    }
   
    @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
            String id = request.getParameter("id");
        HashMap<String,Object> result =new HashMap<String,Object>();
        if(!StringUtil.isBlank(id)){
            SysPermission bean = sysPermissionService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        SysPermission bean = sysPermissionService.selectByPrimaryKey(Long.valueOf(id));
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
        SysPermission sysPermission =new  SysPermission();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sysPermission.setId(Long.valueOf(id)) ;
        }
        
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            sysPermission.setPid(Long.valueOf(pid)) ;
        }
        
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            sysPermission.setName(String.valueOf(name)) ;
        }
        
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            sysPermission.setCode(String.valueOf(code)) ;
        }
        
        String order = request.getParameter("order");
        if(!StringUtil.isBlank(order)){
            sysPermission.setOrder(Integer.valueOf(order)) ;
        }
        
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            sysPermission.setStatus(Integer.valueOf(status)) ;
        }
        
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            sysPermission.setRemark(String.valueOf(remark)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sysPermission.setId(Long.valueOf(id));
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            sysPermission.setPid(Long.valueOf(pid));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            sysPermission.setName(name);
        }
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            sysPermission.setCode(code);
        }
        String order = request.getParameter("order");
        if(!StringUtil.isBlank(order)){
            sysPermission.setOrder(Integer.valueOf(order));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            sysPermission.setStatus(Integer.valueOf(status));
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            sysPermission.setRemark(remark);
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(10,0)});
        vu.add("pid", pid, "父主键",  new Rule[]{new Digits(10,0)});
        vu.add("name", name, "权限名称",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("code", code, "权限代码",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("order", order, "排序id",  new Rule[]{new Digits(11,0)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2"}),new NotEmpty()});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(20)});
        validStr = vu.validateString();
        if(StringUtil.isNotEmpty(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return sysPermissionService.save(sysPermission);
       
    }

    @RequestMapping(value = "/del.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Long id = Long.valueOf(idStr);
        sysPermissionService.delete(id);
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
       return  sysPermissionService.multilDelete(idAry);
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
        List<SysPermission> list =sysPermissionService.listByParams(params);
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
        colTitle.put("pid", "父主键");
        colTitle.put("name", "权限名称");
        colTitle.put("code", "权限代码");
        colTitle.put("order", "排序id");
        colTitle.put("status", "状态");
        colTitle.put("remark", "备注");
        List finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            SysPermission sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("pid",  list.get(i).getPid());
            map.put("name",  list.get(i).getName());
            map.put("code",  list.get(i).getCode());
            map.put("order",  list.get(i).getOrder());
            map.put("status",  list.get(i).getStatus());
            map.put("remark",  list.get(i).getRemark());
            finalList.add(map);
        }
        try {
            if (cola.machine.util.ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
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

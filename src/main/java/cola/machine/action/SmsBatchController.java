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
import java.util.LinkedHashMap;
import java.util.List;

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

import cola.machine.service.SmsBatchService;
import cola.machine.bean.SmsBatch;
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
@RequestMapping("/smsBatch")
public class SmsBatchController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SmsBatchController.class);
    /** 权限service **/
    @Autowired
    private SmsBatchService smsBatchService;
    

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
        return "/static/html/SmsBatchList.html";
    }

 
    /**
     * 说明:ajax请求角色信息
     * 
     * @param curPage
     * @param pageSize
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
        
        HashMap params= new HashMap();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
                params.put("id",id);
        }
        String total = request.getParameter("total");
        if(!StringUtil.isBlank(total)){
                params.put("total",total);
        }
        String succ = request.getParameter("succ");
        if(!StringUtil.isBlank(succ)){
                params.put("succ",succ);
        }
        String fail = request.getParameter("fail");
        if(!StringUtil.isBlank(fail)){
                params.put("fail",fail);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
                params.put("status",status);
        }
        String phone = request.getParameter("phone");
        if(!StringUtil.isBlank(phone)){
                params.put("phone",phone);
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
                params.put("content",content);
        }
        String sendTime = request.getParameter("sendTime");
        if(!StringUtil.isBlank(sendTime)){
            if(StringUtil.checkNumeric(sendTime)){
                params.put("sendTime",sendTime);
            }else if(StringUtil.checkDateStr(sendTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("sendTime",new Timestamp( DateUtil.parseToDate(sendTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String sendTimeBegin = request.getParameter("sendTimeBegin");
        if(!StringUtil.isBlank(sendTimeBegin)){
            if(StringUtil.checkNumeric(sendTimeBegin)){
                params.put("sendTimeBegin",sendTimeBegin);
            }else if(StringUtil.checkDateStr(sendTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("sendTimeBegin",new Timestamp( DateUtil.parseToDate(sendTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String sendTimeEnd = request.getParameter("sendTimeEnd");
        if(!StringUtil.isBlank(sendTimeEnd)){
            if(StringUtil.checkNumeric(sendTimeEnd)){
                params.put("sendTimeEnd",sendTimeEnd);
            }else if(StringUtil.checkDateStr(sendTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("sendTimeEnd",new Timestamp( DateUtil.parseToDate(sendTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                params.put("createTime",createTime);
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeBegin = request.getParameter("createTimeBegin");
        if(!StringUtil.isBlank(createTimeBegin)){
            if(StringUtil.checkNumeric(createTimeBegin)){
                params.put("createTimeBegin",createTimeBegin);
            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = request.getParameter("createTimeEnd");
        if(!StringUtil.isBlank(createTimeEnd)){
            if(StringUtil.checkNumeric(createTimeEnd)){
                params.put("createTimeEnd",createTimeEnd);
            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String excuteTime = request.getParameter("excuteTime");
        if(!StringUtil.isBlank(excuteTime)){
            if(StringUtil.checkNumeric(excuteTime)){
                params.put("excuteTime",excuteTime);
            }else if(StringUtil.checkDateStr(excuteTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("excuteTime",new Timestamp( DateUtil.parseToDate(excuteTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String excuteTimeBegin = request.getParameter("excuteTimeBegin");
        if(!StringUtil.isBlank(excuteTimeBegin)){
            if(StringUtil.checkNumeric(excuteTimeBegin)){
                params.put("excuteTimeBegin",excuteTimeBegin);
            }else if(StringUtil.checkDateStr(excuteTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("excuteTimeBegin",new Timestamp( DateUtil.parseToDate(excuteTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String excuteTimeEnd = request.getParameter("excuteTimeEnd");
        if(!StringUtil.isBlank(excuteTimeEnd)){
            if(StringUtil.checkNumeric(excuteTimeEnd)){
                params.put("excuteTimeEnd",excuteTimeEnd);
            }else if(StringUtil.checkDateStr(excuteTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("excuteTimeEnd",new Timestamp( DateUtil.parseToDate(excuteTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page",page);
        List<SmsBatch> smsBatchs = smsBatchService.listByParams4Page(params);
        return ResultUtil.getResult(smsBatchs, page);
    }
    
    
    
    /**
     * @param id 参数
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/SmsBatchEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/SmsBatchView.html";
    }
   
      @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
        String id = request.getParameter("id");
        SmsBatch bean = smsBatchService.selectByPrimaryKey(Integer.valueOf(id));
        return this.getResult(bean);
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
        SmsBatch smsBatch =new  SmsBatch();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            smsBatch.setId(Integer.valueOf(id)) ;
        }
        
        String total = request.getParameter("total");
        if(!StringUtil.isBlank(total)){
            smsBatch.setTotal(Integer.valueOf(total)) ;
        }
        
        String succ = request.getParameter("succ");
        if(!StringUtil.isBlank(succ)){
            smsBatch.setSucc(Integer.valueOf(succ)) ;
        }
        
        String fail = request.getParameter("fail");
        if(!StringUtil.isBlank(fail)){
            smsBatch.setFail(Integer.valueOf(fail)) ;
        }
        
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            smsBatch.setStatus(Integer.valueOf(status)) ;
        }
        
        String phone = request.getParameter("phone");
        if(!StringUtil.isBlank(phone)){
            smsBatch.setPhone(String.valueOf(phone)) ;
        }
        
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            smsBatch.setContent(String.valueOf(content)) ;
        }
        
        String sendTime = request.getParameter("sendTime");
        if(!StringUtil.isBlank(sendTime)){
            smsBatch.setSendTime(Timestamp.valueOf(sendTime)) ;
        }
        
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            smsBatch.setCreateTime(Timestamp.valueOf(createTime)) ;
        }
        
        String excuteTime = request.getParameter("excuteTime");
        if(!StringUtil.isBlank(excuteTime)){
            smsBatch.setExcuteTime(Timestamp.valueOf(excuteTime)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
                smsBatch.setId(Integer.valueOf(id));
        }
        String total = request.getParameter("total");
        if(!StringUtil.isBlank(total)){
                smsBatch.setTotal(Integer.valueOf(total));
        }
        String succ = request.getParameter("succ");
        if(!StringUtil.isBlank(succ)){
                smsBatch.setSucc(Integer.valueOf(succ));
        }
        String fail = request.getParameter("fail");
        if(!StringUtil.isBlank(fail)){
                smsBatch.setFail(Integer.valueOf(fail));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
                smsBatch.setStatus(Integer.valueOf(status));
        }
        String phone = request.getParameter("phone");
        if(!StringUtil.isBlank(phone)){
                smsBatch.setPhone(String.valueOf(phone));
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
                smsBatch.setContent(String.valueOf(content));
        }
        String sendTime = request.getParameter("sendTime");
        if(!StringUtil.isBlank(sendTime)){
            if(StringUtil.checkNumeric(sendTime)){
                smsBatch.setSendTime(Timestamp.valueOf(sendTime));
            }else if(StringUtil.checkDateStr(sendTime, "yyyy-MM-dd HH:mm:ss")){
                smsBatch.setSendTime(new Timestamp( DateUtil.parseToDate(sendTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                smsBatch.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                smsBatch.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String excuteTime = request.getParameter("excuteTime");
        if(!StringUtil.isBlank(excuteTime)){
            if(StringUtil.checkNumeric(excuteTime)){
                smsBatch.setExcuteTime(Timestamp.valueOf(excuteTime));
            }else if(StringUtil.checkDateStr(excuteTime, "yyyy-MM-dd HH:mm:ss")){
                smsBatch.setExcuteTime(new Timestamp( DateUtil.parseToDate(excuteTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "id",  new Rule[]{new Digits(10,0)});
        vu.add("total", total, "总发送数据",  new Rule[]{new Digits(10,0),new NotEmpty()});
        vu.add("succ", succ, "成功数量",  new Rule[]{new Digits(10,0),new NotEmpty()});
        vu.add("fail", fail, "失败数量",  new Rule[]{new Digits(10,0),new NotEmpty()});
        vu.add("status", status, "状态",  new Rule[]{new Digits(10,0),new CheckBox(new String[]{"1","2"}),new NotEmpty()});
        vu.add("phone", phone, "手机号码",  new Rule[]{new Length(100),new NotEmpty(),new Regex("^(\\d{11},)*\\d{11}$")});
        vu.add("content", content, "短信内容",  new Rule[]{new Length(140),new NotEmpty()});
        vu.add("sendTime", sendTime, "定时发送",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss"),new NotEmpty()});
        vu.add("excuteTime", excuteTime, "执行时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotEmpty(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return smsBatchService.save(smsBatch);
    }
    
    @RequestMapping(value = "/del.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Integer id = Integer.valueOf(idStr);
        smsBatchService.delete(id);
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
        Integer idAry[]=new Integer[idStrAry.length];
        for(int i=0,length=idAry.length;i<length;i++){
            ValidateUtil vu = new ValidateUtil();
            String validStr="";
                    vu.add("id", idStrAry[i], "id",  new Rule[]{new Digits(10,0)});

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
            idAry[i]=Integer.valueOf(idStrAry[i]);
        }
       return  smsBatchService.multilDelete(idAry);
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
        String total = request.getParameter("total");
        if(!StringUtil.isBlank(total)){
                params.put("total",total);
        }
        String succ = request.getParameter("succ");
        if(!StringUtil.isBlank(succ)){
                params.put("succ",succ);
        }
        String fail = request.getParameter("fail");
        if(!StringUtil.isBlank(fail)){
                params.put("fail",fail);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
                params.put("status",status);
        }
        String phone = request.getParameter("phone");
        if(!StringUtil.isBlank(phone)){
                params.put("phone",phone);
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
                params.put("content",content);
        }
        String sendTime = request.getParameter("sendTime");
        if(!StringUtil.isBlank(sendTime)){
            if(StringUtil.checkNumeric(sendTime)){
                params.put("sendTime",sendTime);
            }else if(StringUtil.checkDateStr(sendTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("sendTime",new Timestamp( DateUtil.parseToDate(sendTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String sendTimeBegin = request.getParameter("sendTimeBegin");
        if(!StringUtil.isBlank(sendTimeBegin)){
            if(StringUtil.checkNumeric(sendTimeBegin)){
                params.put("sendTimeBegin",sendTimeBegin);
            }else if(StringUtil.checkDateStr(sendTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("sendTimeBegin",new Timestamp( DateUtil.parseToDate(sendTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String sendTimeEnd = request.getParameter("sendTimeEnd");
        if(!StringUtil.isBlank(sendTimeEnd)){
            if(StringUtil.checkNumeric(sendTimeEnd)){
                params.put("sendTimeEnd",sendTimeEnd);
            }else if(StringUtil.checkDateStr(sendTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("sendTimeEnd",new Timestamp( DateUtil.parseToDate(sendTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                params.put("createTime",createTime);
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeBegin = request.getParameter("createTimeBegin");
        if(!StringUtil.isBlank(createTimeBegin)){
            if(StringUtil.checkNumeric(createTimeBegin)){
                params.put("createTimeBegin",createTimeBegin);
            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = request.getParameter("createTimeEnd");
        if(!StringUtil.isBlank(createTimeEnd)){
            if(StringUtil.checkNumeric(createTimeEnd)){
                params.put("createTimeEnd",createTimeEnd);
            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String excuteTime = request.getParameter("excuteTime");
        if(!StringUtil.isBlank(excuteTime)){
            if(StringUtil.checkNumeric(excuteTime)){
                params.put("excuteTime",excuteTime);
            }else if(StringUtil.checkDateStr(excuteTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("excuteTime",new Timestamp( DateUtil.parseToDate(excuteTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String excuteTimeBegin = request.getParameter("excuteTimeBegin");
        if(!StringUtil.isBlank(excuteTimeBegin)){
            if(StringUtil.checkNumeric(excuteTimeBegin)){
                params.put("excuteTimeBegin",excuteTimeBegin);
            }else if(StringUtil.checkDateStr(excuteTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("excuteTimeBegin",new Timestamp( DateUtil.parseToDate(excuteTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String excuteTimeEnd = request.getParameter("excuteTimeEnd");
        if(!StringUtil.isBlank(excuteTimeEnd)){
            if(StringUtil.checkNumeric(excuteTimeEnd)){
                params.put("excuteTimeEnd",excuteTimeEnd);
            }else if(StringUtil.checkDateStr(excuteTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("excuteTimeEnd",new Timestamp( DateUtil.parseToDate(excuteTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        // 查询list集合
        List<SmsBatch> list =smsBatchService.listByParams(params);
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
        String realName = DateUtil.formatToString(new Date(), "yyyyMMddHHmmssSSS")
                + ".xlsx";
        String fileName = folder + File.separator
                +realName;
        // 得到导出Excle时清单的英中文map
        LinkedHashMap<String, String> colTitle = new LinkedHashMap<String, String>();
        colTitle.put("id", "id");
        colTitle.put("total", "总发送数据");
        colTitle.put("succ", "成功数量");
        colTitle.put("fail", "失败数量");
        colTitle.put("status", "状态");
        colTitle.put("phone", "手机号码");
        colTitle.put("content", "短信内容");
        colTitle.put("sendTime", "定时发送");
        colTitle.put("createTime", "创建时间");
        colTitle.put("excuteTime", "执行时间");
        List finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            SmsBatch sm = list.get(i);
            HashMap map = new HashMap();
            map.put("id",  list.get(i).getId());
            map.put("total",  list.get(i).getTotal());
            map.put("succ",  list.get(i).getSucc());
            map.put("fail",  list.get(i).getFail());
            map.put("status",  list.get(i).getStatus());
            map.put("phone",  list.get(i).getPhone());
            map.put("content",  list.get(i).getContent());
            map.put("sendTime",  list.get(i).getSendTime());
            map.put("createTime",  list.get(i).getCreateTime());
            map.put("excuteTime",  list.get(i).getExcuteTime());
            finalList.add(map);
        }
        try {
            if (cola.machine.util.ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
                return this.getResult(SUCC, "xlstmp/"+realName, "导出成功");
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

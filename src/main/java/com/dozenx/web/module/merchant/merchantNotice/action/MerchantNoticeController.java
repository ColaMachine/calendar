/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2017-3-31 16:18:45
 * 文件说明:
 */

package com.dozenx.web.module.merchant.merchantNotice.action;

import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
import com.dozenx.util.*;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.Digits;
import com.dozenx.web.core.rules.Length;
import com.dozenx.web.core.rules.NotEmpty;
import com.dozenx.web.core.rules.Rule;
import com.dozenx.web.module.merchant.merchantNotice.bean.MerchantNotice;
import com.dozenx.web.module.merchant.merchantNotice.service.MerchantNoticeService;
import com.dozenx.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;
@APIs(description = "商户滚动消息")
@Controller
@RequestMapping("/merchantNotice")
public class MerchantNoticeController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(MerchantNoticeController.class);
    /** 权限service **/
    @Autowired
    private MerchantNoticeService merchantNoticeService;



    /**
     * 说明:ajax请求MerchantNotice信息
     * @author dozen.zhang
     * @date 2017-3-31 16:18:45
     * @return String
     */
    @API(summary="商户滚动消息列表接口",
            description="商户滚动消息列表接口",
            parameters={
                    @Param(name="pageSize", description="分页大小", dataType= DataType.PHONE,required = true),
                    @Param(name="curPage", description="当前页", dataType= DataType.CAPTCHA,required = true),
                    @Param(name="id" , description="编号",dataType = DataType.INTEGER,required = false),
                    @Param(name="slot" , description="槽位",dataType = DataType.INTEGER,required = true),
                    @Param(name="content" , description="消息",dataType = DataType.STRING,required = false),
                    @Param(name="merid" , description="商户id",dataType = DataType.LONG,required = false),
            })
    @RequestMapping(value = "/list" , method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO list(HttpServletRequest request) throws Exception{
        Page page = RequestUtil.getPage(request);
        if(page ==null){
            return this.getWrongResultFromCfg("err.param.page");
        }

        HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String slot = request.getParameter("slot");
        if(!StringUtil.isBlank(slot)){
            params.put("slot",slot);
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = request.getParameter("contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
        }
        String merid = request.getParameter("merid");
        if(!StringUtil.isBlank(merid)){
            params.put("merid",merid);
        }

        params.put("page",page);
        List<MerchantNotice> merchantNotices = merchantNoticeService.listByParams4Page(params);
        return ResultUtil.getResult(merchantNotices, page);
    }

    /**
     * 说明:ajax请求MerchantNotice信息 无分页版本
     * @return ResultDTO 返回结果
     * @author dozen.zhang
     * @date 2017-3-31 16:18:45
     */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public ResultDTO listAll(HttpServletRequest request) {
        HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String slot = request.getParameter("slot");
        if(!StringUtil.isBlank(slot)){
            params.put("slot",slot);
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = request.getParameter("contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
        }
        String merid = request.getParameter("merid");
        if(!StringUtil.isBlank(merid)){
            params.put("merid",merid);
        }

        List<MerchantNotice> merchantNotices = merchantNoticeService.listByParams(params);
        return ResultUtil.getDataResult(merchantNotices);
    }

    /**
     * 说明:查看单条信息
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2017-3-31 16:18:45
     */
    @API( summary="根据id查询单个商户滚动消息信息",
            description = "根据id查询单个商户滚动消息信息",
            parameters={
                    @Param(name="id" , description="id",dataType= DataType.INTEGER,required = true),
            })
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(@PathVariable Integer id,HttpServletRequest request) {
        HashMap<String,Object> result =new HashMap</*String,Object*/>();
        if(id > 0){
            MerchantNotice bean = merchantNoticeService.selectByPrimaryKey(Integer.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        MerchantNotice bean = merchantNoticeService.selectByPrimaryKey(Integer.valueOf(id));
        HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
        result.put("bean", bean);
        return this.getResult(bean);*/
    }


    /**
     * 说明:更新MerchantNotice信息
     *
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2017-3-31 16:18:45
     */
    @API( summary="根据id更新单个商户滚动消息信息",
            description = "根据id更新单个商户滚动消息信息",
            parameters={
                    @Param(name="id" , description="编号",type="int(11)",required = true),
                    @Param(name="slot" , description="槽位",type="int(2)",required = false),
                    @Param(name="content" , description="消息",type="VARCHAR(250)",required = false),
                    @Param(name="merid" , description="商户id",type="bigint(11)",required = false),
            })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "{id}",method = RequestMethod.PUT,produces="application/json")
    @ResponseBody
    public ResultDTO update(@PathVariable Integer id,  @RequestBody(required=true) Map<String, Object> bodyParam,HttpServletRequest request) throws Exception {
        MerchantNotice merchantNotice =new  MerchantNotice();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            merchantNotice.setId(Integer.valueOf(id)) ;
        }

        String slot = request.getParameter("slot");
        if(!StringUtil.isBlank(slot)){
            merchantNotice.setSlot(Integer.valueOf(slot)) ;
        }

        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            merchantNotice.setContent(String.valueOf(content)) ;
        }

        String merid = request.getParameter("merid");
        if(!StringUtil.isBlank(merid)){
            merchantNotice.setMerid(Long.valueOf(merid)) ;
        }
        */

        merchantNotice.setId(id);

        String slot = request.getParameter("slot");
        if(!StringUtil.isBlank(slot)){
            merchantNotice.setSlot(Integer.valueOf(slot));
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            merchantNotice.setContent(content);
        }
        String merid = request.getParameter("merid");
        if(!StringUtil.isBlank(merid)){
            merchantNotice.setMerid(Long.valueOf(merid));
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
       // vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("slot", slot, "槽位",  new Rule[]{new Digits(2,0),new NotEmpty()});
        vu.add("content", content, "消息",  new Rule[]{new Length(250)});
        vu.add("merid", merid, "商户id",  new Rule[]{new Digits(11,0)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return merchantNoticeService.save(merchantNotice);

    }


    /**
     * 说明:添加MerchantNotice信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2017-3-31 16:18:45
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @API( summary="添加单个商户滚动消息信息",
            parameters={
                    @Param(name="content" , description="消息",dataType = DataType.STRING,required = false),
                    @Param(name="merid" , description="商户id",dataType = DataType.LONG,required = false),
            })
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO add(HttpServletRequest request) throws Exception {
        MerchantNotice merchantNotice =new  MerchantNotice();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                merchantNotice.setId(Integer.valueOf(id)) ;
            }

            String slot = request.getParameter("slot");
            if(!StringUtil.isBlank(slot)){
                merchantNotice.setSlot(Integer.valueOf(slot)) ;
            }

            String content = request.getParameter("content");
            if(!StringUtil.isBlank(content)){
                merchantNotice.setContent(String.valueOf(content)) ;
            }

            String merid = request.getParameter("merid");
            if(!StringUtil.isBlank(merid)){
                merchantNotice.setMerid(Long.valueOf(merid)) ;
            }
            */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            merchantNotice.setId(Integer.valueOf(id));
        }
        String slot = request.getParameter("slot");
        if(!StringUtil.isBlank(slot)){
            merchantNotice.setSlot(Integer.valueOf(slot));
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            merchantNotice.setContent(content);
        }
        String merid = request.getParameter("merid");
        if(!StringUtil.isBlank(merid)){
            merchantNotice.setMerid(Long.valueOf(merid));
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("slot", slot, "槽位",  new Rule[]{new Digits(2,0),new NotEmpty()});
        vu.add("content", content, "消息",  new Rule[]{new Length(250)});
        vu.add("merid", merid, "商户id",  new Rule[]{new Digits(11,0)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return merchantNoticeService.save(merchantNotice);

    }
    /**
     * 说明:删除MerchantNotice信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2017-3-31 16:18:45
     */
    @API( summary="根据id删除单个商户滚动消息信息",
            description = "根据id删除单个商户滚动消息信息",
            parameters={
                    @Param(name="id" , description="编号",dataType= DataType.INTEGER,required = true),
            })
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public ResultDTO delete(@PathVariable Integer id,HttpServletRequest request) {
        merchantNoticeService.delete(id);
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
    public ResultDTO multiDelete(HttpServletRequest request) {
        String idStr = request.getParameter("ids");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        String idStrAry[]= idStr.split(",");
        Integer idAry[]=new Integer[idStrAry.length];
        for(int i=0,length=idAry.length;i<length;i++){
            ValidateUtil vu = new ValidateUtil();
            String validStr="";
            String id = idStrAry[i];
            vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});

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
            idAry[i]=Integer.valueOf(idStrAry[i]);
        }
        return  merchantNoticeService.multilDelete(idAry);
    }

    /**
     * 导出
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/export.json")
    @ResponseBody
    public ResultDTO exportExcel(HttpServletRequest request){
        HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String slot = request.getParameter("slot");
        if(!StringUtil.isBlank(slot)){
            params.put("slot",slot);
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = request.getParameter("contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
        }
        String merid = request.getParameter("merid");
        if(!StringUtil.isBlank(merid)){
            params.put("merid",merid);
        }

        // 查询list集合
        List<MerchantNotice> list =merchantNoticeService.listByParams(params);
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
        colTitle.put("slot", "槽位");
        colTitle.put("content", "消息");
        colTitle.put("merid", "商户id");
        List<Map> finalList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            MerchantNotice sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("slot",  list.get(i).getSlot());
            map.put("content",  list.get(i).getContent());
            map.put("merid",  list.get(i).getMerid());
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


    /**
     * 说明: 跳转到MerchantNotice列表页面
     *
     * @return
     * @return String
     * @author dozen.zhang
     * @date 2015年11月15日下午12:30:45
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    public String listHtml() {
        return "/static/html/MerchantNoticeList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapperHtml() {
        return "/static/html/MerchantNoticeListMapper.html";
    }


    /**
     * 说明:跳转编辑页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2017-3-31 16:18:45
     */
    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
    public String editHtml( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/MerchantNoticeEdit.html";
    }
    /**
     * 说明:跳转查看页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2017-3-31 16:18:45
     */
    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
    public String viewHtml( HttpServletRequest request) {
        return "/static/html/MerchantNoticeView.html";
    }
}

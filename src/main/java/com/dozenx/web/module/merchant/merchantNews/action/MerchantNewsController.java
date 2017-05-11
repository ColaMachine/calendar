/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2017-3-31 15:53:25
 * 文件说明:
 */

package com.dozenx.web.module.merchant.merchantNews.action;
import java.io.File;
import java.util.*;

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
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
import com.dozenx.web.module.merchant.merchantNews.service.MerchantNewsService;
import com.dozenx.web.module.merchant.merchantNews.bean.MerchantNews;
import com.dozenx.util.ResultUtil;
import com.dozenx.util.ValidateUtil;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.util.StringUtil;
import com.dozenx.web.util.RequestUtil;
import org.springframework.web.bind.annotation.*;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.util.DateUtil;
@APIs(description = "商户滚动新闻")
@Controller
@RequestMapping("/merchantNews")
public class MerchantNewsController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(MerchantNewsController.class);
    /** 权限service **/
    @Autowired
    private MerchantNewsService merchantNewsService;



    /**
     * 说明:ajax请求MerchantNews信息
     * @author dozen.zhang
     * @date 2017-3-31 15:53:25
     * @return String
     */
    @API(summary="MerchantNews列表接口",
            description="MerchantNews列表接口",
            parameters={
                    @Param(name="pageSize", description="分页大小", dataType= DataType.PHONE,required = true),
                    @Param(name="curPage", description="当前页", dataType= DataType.CAPTCHA,required = true),
                    @Param(name="id" , description="编号",dataType = DataType.INTEGER,required = false),
                    @Param(name="content" , description="内容",dataType = DataType.STRING,required = false),
                    @Param(name="merid" , description="商户id",dataType = DataType.LONG,required = false),
            })
    @RequestMapping(value = "/list" , method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO list(HttpServletRequest request) throws Exception{
        Page page = RequestUtil.getPage(request);
        if(page ==null){
            return this.getWrongResultFromCfg("err.param.page");
        }

        HashMap<String,Object> params= new HashMap<>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
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
        List<MerchantNews> merchantNewss = merchantNewsService.listByParams4Page(params);
        return ResultUtil.getResult(merchantNewss, page);
    }

    /**
     * 说明:ajax请求MerchantNews信息 无分页版本
     * @return ResultDTO 返回结果
     * @author dozen.zhang
     * @date 2017-3-31 15:53:25
     */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public ResultDTO listAll(HttpServletRequest request) {
        HashMap<String,Object> params= new HashMap<>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
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

        List<MerchantNews> merchantNewss = merchantNewsService.listByParams(params);
        return ResultUtil.getDataResult(merchantNewss);
    }

    /**
     * 说明:查看单条信息
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2017-3-31 15:53:25
     */
    @API( summary="根据id查询单个MerchantNews信息",
            description = "根据id查询单个MerchantNews信息",
            parameters={
                    @Param(name="id" , description="id",dataType= DataType.INTEGER,required = true),
            })
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(@PathVariable Integer id,HttpServletRequest request) {
       // String id = request.getParameter("id");
        HashMap<String,Object> result =new HashMap<>();
        if(id>0){
            MerchantNews bean = merchantNewsService.selectByPrimaryKey(Integer.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        MerchantNews bean = merchantNewsService.selectByPrimaryKey(Integer.valueOf(id));
        HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
        result.put("bean", bean);
        return this.getResult(bean);*/
    }


    /**
     * 说明:更新MerchantNews信息
     *
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2017-3-31 15:53:25
     */
    @API( summary="根据id更新单个MerchantNews信息",
            description = "根据id更新单个MerchantNews信息",
            parameters={
                    @Param(name="id" , description="编号",type="int(11)",required = false),
                    @Param(name="content" , description="内容",type="VARCHAR(150)",required = false),
                    @Param(name="merid" , description="商户id",type="bigint(11)",required = false),
            })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO update(@PathVariable Integer id,HttpServletRequest request) throws Exception {
        MerchantNews merchantNews =new  MerchantNews();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            merchantNews.setId(Integer.valueOf(id)) ;
        }

        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            merchantNews.setContent(String.valueOf(content)) ;
        }

        String merid = request.getParameter("merid");
        if(!StringUtil.isBlank(merid)){
            merchantNews.setMerid(Long.valueOf(merid)) ;
        }
        */

            merchantNews.setId(Integer.valueOf(id));

        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            merchantNews.setContent(content);
        }
        String merid = request.getParameter("merid");
        if(!StringUtil.isBlank(merid)){
            merchantNews.setMerid(Long.valueOf(merid));
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        //vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("content", content, "内容",  new Rule[]{new Length(150)});
        vu.add("merid", merid, "商户id",  new Rule[]{new Digits(11,0)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return merchantNewsService.save(merchantNews);

    }


    /**
     * 说明:添加MerchantNews信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2017-3-31 15:53:25
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @API( summary="添加单个MerchantNews信息",
            description = "添加单个MerchantNews信息",
            parameters={
                    @Param(name="id" , description="编号",dataType = DataType.INTEGER,required = false),
                    @Param(name="content" , description="内容",dataType = DataType.STRING,required = false),
                    @Param(name="merid" , description="商户id",dataType = DataType.LONG,required = false),
            })
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO add(HttpServletRequest request) throws Exception {
        MerchantNews merchantNews =new  MerchantNews();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                merchantNews.setId(Integer.valueOf(id)) ;
            }

            String content = request.getParameter("content");
            if(!StringUtil.isBlank(content)){
                merchantNews.setContent(String.valueOf(content)) ;
            }

            String merid = request.getParameter("merid");
            if(!StringUtil.isBlank(merid)){
                merchantNews.setMerid(Long.valueOf(merid)) ;
            }
            */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            merchantNews.setId(Integer.valueOf(id));
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            merchantNews.setContent(content);
        }
        String merid = request.getParameter("merid");
        if(!StringUtil.isBlank(merid)){
            merchantNews.setMerid(Long.valueOf(merid));
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(11,0)});
        vu.add("content", content, "内容",  new Rule[]{new Length(150)});
        vu.add("merid", merid, "商户id",  new Rule[]{new Digits(11,0)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return merchantNewsService.save(merchantNews);

    }
    /**
     * 说明:删除MerchantNews信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2017-3-31 15:53:25
     */
    @API( summary="根据id删除单个MerchantNews信息",
            description = "根据id删除单个MerchantNews信息",
            parameters={
                    @Param(name="id" , description="编号",dataType= DataType.INTEGER,required = true),
            })
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public ResultDTO delete(@PathVariable Integer id,HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }

        merchantNewsService.delete(id);
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
        return  merchantNewsService.multilDelete(idAry);
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
        List<MerchantNews> list =merchantNewsService.listByParams(params);
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
        colTitle.put("content", "内容");
        colTitle.put("merid", "商户id");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            MerchantNews sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
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
     * 说明: 跳转到MerchantNews列表页面
     *
     * @return
     * @return String
     * @author dozen.zhang
     * @date 2015年11月15日下午12:30:45
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    public String listHtml() {
        return "/static/html/MerchantNewsList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapperHtml() {
        return "/static/html/MerchantNewsListMapper.html";
    }


    /**
     * 说明:跳转编辑页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2017-3-31 15:53:25
     */
    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
    public String editHtml( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/MerchantNewsEdit.html";
    }
    /**
     * 说明:跳转查看页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2017-3-31 15:53:25
     */
    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
    public String viewHtml( HttpServletRequest request) {
        return "/static/html/MerchantNewsView.html";
    }
}

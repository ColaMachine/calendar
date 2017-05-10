/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.goods.action;
import java.io.File;
import java.sql.Timestamp;
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

import com.dozenx.web.module.goods.service.GoodsService;
import com.dozenx.web.module.goods.bean.Goods;
import com.dozenx.util.ResultUtil;
import com.dozenx.util.ValidateUtil;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.util.StringUtil;
import com.dozenx.web.util.RequestUtil;
import com.dozenx.util.DateUtil;
@Controller
@RequestMapping("/goods")
public class GoodsController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(GoodsController.class);
    /** 权限service **/
    @Autowired
    private GoodsService goodsService;
    
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
        return "/static/html/GoodsList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapper() {
        return "/static/html/GoodsListMapper.html";
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
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            params.put("title",title);
        }
        String titleLike = request.getParameter("titleLike");
        if(!StringUtil.isBlank(titleLike)){
            params.put("titleLike",titleLike);
        }
        String subtitle = request.getParameter("subtitle");
        if(!StringUtil.isBlank(subtitle)){
            params.put("subtitle",subtitle);
        }
        String subtitleLike = request.getParameter("subtitleLike");
        if(!StringUtil.isBlank(subtitleLike)){
            params.put("subtitleLike",subtitleLike);
        }
        String src = request.getParameter("src");
        if(!StringUtil.isBlank(src)){
            params.put("src",src);
        }
        String srcLike = request.getParameter("srcLike");
        if(!StringUtil.isBlank(srcLike)){
            params.put("srcLike",srcLike);
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = request.getParameter("contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
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
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            params.put("creator",creator);
        }
        String pic = request.getParameter("pic");
        if(!StringUtil.isBlank(pic)){
            params.put("pic",pic);
        }
        String picLike = request.getParameter("picLike");
        if(!StringUtil.isBlank(picLike)){
            params.put("picLike",picLike);
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = request.getParameter("urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
        }
        String creatorname = request.getParameter("creatorname");
        if(!StringUtil.isBlank(creatorname)){
            params.put("creatorname",creatorname);
        }
        String creatornameLike = request.getParameter("creatornameLike");
        if(!StringUtil.isBlank(creatornameLike)){
            params.put("creatornameLike",creatornameLike);
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
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                params.put("updatetime",updatetime);
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetime",new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeBegin = request.getParameter("updatetimeBegin");
        if(!StringUtil.isBlank(updatetimeBegin)){
            if(StringUtil.checkNumeric(updatetimeBegin)){
                params.put("updatetimeBegin",updatetimeBegin);
            }else if(StringUtil.checkDateStr(updatetimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeBegin",new Timestamp( DateUtil.parseToDate(updatetimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeEnd = request.getParameter("updatetimeEnd");
        if(!StringUtil.isBlank(updatetimeEnd)){
            if(StringUtil.checkNumeric(updatetimeEnd)){
                params.put("updatetimeEnd",updatetimeEnd);
            }else if(StringUtil.checkDateStr(updatetimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeEnd",new Timestamp( DateUtil.parseToDate(updatetimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page",page);
        List<Goods> goodss = goodsService.listByParams4Page(params);
        return ResultUtil.getResult(goodss, page);
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
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            params.put("title",title);
        }
        String titleLike = request.getParameter("titleLike");
        if(!StringUtil.isBlank(titleLike)){
            params.put("titleLike",titleLike);
        }
        String subtitle = request.getParameter("subtitle");
        if(!StringUtil.isBlank(subtitle)){
            params.put("subtitle",subtitle);
        }
        String subtitleLike = request.getParameter("subtitleLike");
        if(!StringUtil.isBlank(subtitleLike)){
            params.put("subtitleLike",subtitleLike);
        }
        String src = request.getParameter("src");
        if(!StringUtil.isBlank(src)){
            params.put("src",src);
        }
        String srcLike = request.getParameter("srcLike");
        if(!StringUtil.isBlank(srcLike)){
            params.put("srcLike",srcLike);
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = request.getParameter("contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
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
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            params.put("creator",creator);
        }
        String pic = request.getParameter("pic");
        if(!StringUtil.isBlank(pic)){
            params.put("pic",pic);
        }
        String picLike = request.getParameter("picLike");
        if(!StringUtil.isBlank(picLike)){
            params.put("picLike",picLike);
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = request.getParameter("urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
        }
        String creatorname = request.getParameter("creatorname");
        if(!StringUtil.isBlank(creatorname)){
            params.put("creatorname",creatorname);
        }
        String creatornameLike = request.getParameter("creatornameLike");
        if(!StringUtil.isBlank(creatornameLike)){
            params.put("creatornameLike",creatornameLike);
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
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                params.put("updatetime",updatetime);
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetime",new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeBegin = request.getParameter("updatetimeBegin");
        if(!StringUtil.isBlank(updatetimeBegin)){
            if(StringUtil.checkNumeric(updatetimeBegin)){
                params.put("updatetimeBegin",updatetimeBegin);
            }else if(StringUtil.checkDateStr(updatetimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeBegin",new Timestamp( DateUtil.parseToDate(updatetimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeEnd = request.getParameter("updatetimeEnd");
        if(!StringUtil.isBlank(updatetimeEnd)){
            if(StringUtil.checkNumeric(updatetimeEnd)){
                params.put("updatetimeEnd",updatetimeEnd);
            }else if(StringUtil.checkDateStr(updatetimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeEnd",new Timestamp( DateUtil.parseToDate(updatetimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        List<Goods> goodss = goodsService.listByParams(params);
        return ResultUtil.getDataResult(goodss);
    }
    
    /**
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/GoodsEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/GoodsView.html";
    }
   
    @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
            String id = request.getParameter("id");
        HashMap<String,Object> result =new HashMap<String,Object>();
        if(!StringUtil.isBlank(id)){
            Goods bean = goodsService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        Goods bean = goodsService.selectByPrimaryKey(Long.valueOf(id));
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
        Goods goods =new  Goods();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            goods.setId(Long.valueOf(id)) ;
        }
        
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            goods.setTitle(String.valueOf(title)) ;
        }
        
        String subtitle = request.getParameter("subtitle");
        if(!StringUtil.isBlank(subtitle)){
            goods.setSubtitle(String.valueOf(subtitle)) ;
        }
        
        String src = request.getParameter("src");
        if(!StringUtil.isBlank(src)){
            goods.setSrc(String.valueOf(src)) ;
        }
        
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            goods.setContent(String.valueOf(content)) ;
        }
        
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            goods.setStatus(Integer.valueOf(status)) ;
        }
        
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            goods.setRemark(String.valueOf(remark)) ;
        }
        
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            goods.setCreator(Long.valueOf(creator)) ;
        }
        
        String pic = request.getParameter("pic");
        if(!StringUtil.isBlank(pic)){
            goods.setPic(String.valueOf(pic)) ;
        }
        
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            goods.setUrl(String.valueOf(url)) ;
        }
        
        String creatorname = request.getParameter("creatorname");
        if(!StringUtil.isBlank(creatorname)){
            goods.setCreatorname(String.valueOf(creatorname)) ;
        }
        
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            goods.setCreatetime(Timestamp.valueOf(createtime)) ;
        }
        
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            goods.setUpdatetime(Timestamp.valueOf(updatetime)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            goods.setId(Long.valueOf(id));
        }
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            goods.setTitle(title);
        }
        String subtitle = request.getParameter("subtitle");
        if(!StringUtil.isBlank(subtitle)){
            goods.setSubtitle(subtitle);
        }
        String src = request.getParameter("src");
        if(!StringUtil.isBlank(src)){
            goods.setSrc(src);
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            goods.setContent(content);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            goods.setStatus(Integer.valueOf(status));
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            goods.setRemark(remark);
        }
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            goods.setCreator(Long.valueOf(creator));
        }
        String pic = request.getParameter("pic");
        if(!StringUtil.isBlank(pic)){
            goods.setPic(pic);
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            goods.setUrl(url);
        }
        String creatorname = request.getParameter("creatorname");
        if(!StringUtil.isBlank(creatorname)){
            goods.setCreatorname(creatorname);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                goods.setCreatetime(Timestamp.valueOf(createtime));
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                goods.setCreatetime(new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                goods.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                goods.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "主键",  new Rule[]{new Digits(15,0)});
        vu.add("title", title, "标题",  new Rule[]{new Length(40),new NotEmpty()});
        vu.add("subtitle", subtitle, "副标题",  new Rule[]{new Length(40),new NotEmpty()});
        vu.add("src", src, "来源",  new Rule[]{new Length(40),new NotEmpty()});
        vu.add("content", content, "正文",  new Rule[]{new Length(10000),new NotEmpty()});
        vu.add("status", status, "状态",  new Rule[]{new Digits(11,0),new CheckBox(new String[]{"1","2","3","4","5"})});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(200)});
        vu.add("creator", creator, "创建人",  new Rule[]{new Digits(11,0)});
        vu.add("pic", pic, "封面",  new Rule[]{new Length(40)});
        vu.add("url", url, "商品链接",  new Rule[]{new Length(200),new NotEmpty()});
        vu.add("creatorname", creatorname, "创建人姓名",  new Rule[]{new Length(20)});
        vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return goodsService.save(goods);
       
    }

    @RequestMapping(value = "/del.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Long id = Long.valueOf(idStr);
        goodsService.delete(id);
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
            
            if(StringUtil.isNotBlank(validStr)) {
                return ResultUtil.getResult(302,validStr);
            }
            idAry[i]=Long.valueOf(idStrAry[i]);
        }
       return  goodsService.multilDelete(idAry);
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
        String title = request.getParameter("title");
        if(!StringUtil.isBlank(title)){
            params.put("title",title);
        }
        String titleLike = request.getParameter("titleLike");
        if(!StringUtil.isBlank(titleLike)){
            params.put("titleLike",titleLike);
        }
        String subtitle = request.getParameter("subtitle");
        if(!StringUtil.isBlank(subtitle)){
            params.put("subtitle",subtitle);
        }
        String subtitleLike = request.getParameter("subtitleLike");
        if(!StringUtil.isBlank(subtitleLike)){
            params.put("subtitleLike",subtitleLike);
        }
        String src = request.getParameter("src");
        if(!StringUtil.isBlank(src)){
            params.put("src",src);
        }
        String srcLike = request.getParameter("srcLike");
        if(!StringUtil.isBlank(srcLike)){
            params.put("srcLike",srcLike);
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = request.getParameter("contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
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
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            params.put("creator",creator);
        }
        String pic = request.getParameter("pic");
        if(!StringUtil.isBlank(pic)){
            params.put("pic",pic);
        }
        String picLike = request.getParameter("picLike");
        if(!StringUtil.isBlank(picLike)){
            params.put("picLike",picLike);
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = request.getParameter("urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
        }
        String creatorname = request.getParameter("creatorname");
        if(!StringUtil.isBlank(creatorname)){
            params.put("creatorname",creatorname);
        }
        String creatornameLike = request.getParameter("creatornameLike");
        if(!StringUtil.isBlank(creatornameLike)){
            params.put("creatornameLike",creatornameLike);
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
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                params.put("updatetime",updatetime);
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetime",new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeBegin = request.getParameter("updatetimeBegin");
        if(!StringUtil.isBlank(updatetimeBegin)){
            if(StringUtil.checkNumeric(updatetimeBegin)){
                params.put("updatetimeBegin",updatetimeBegin);
            }else if(StringUtil.checkDateStr(updatetimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeBegin",new Timestamp( DateUtil.parseToDate(updatetimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeEnd = request.getParameter("updatetimeEnd");
        if(!StringUtil.isBlank(updatetimeEnd)){
            if(StringUtil.checkNumeric(updatetimeEnd)){
                params.put("updatetimeEnd",updatetimeEnd);
            }else if(StringUtil.checkDateStr(updatetimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("updatetimeEnd",new Timestamp( DateUtil.parseToDate(updatetimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        // 查询list集合
        List<Goods> list =goodsService.listByParams(params);
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
        colTitle.put("title", "标题");
        colTitle.put("subtitle", "副标题");
        colTitle.put("src", "来源");
        colTitle.put("content", "正文");
        colTitle.put("status", "状态");
        colTitle.put("remark", "备注");
        colTitle.put("creator", "创建人");
        colTitle.put("pic", "封面");
        colTitle.put("url", "商品链接");
        colTitle.put("creatorname", "创建人姓名");
        colTitle.put("createtime", "创建时间");
        colTitle.put("updatetime", "更新时间");
        List finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Goods sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("title",  list.get(i).getTitle());
            map.put("subtitle",  list.get(i).getSubtitle());
            map.put("src",  list.get(i).getSrc());
            map.put("content",  list.get(i).getContent());
            map.put("status",  list.get(i).getStatus());
            map.put("remark",  list.get(i).getRemark());
            map.put("creator",  list.get(i).getCreator());
            map.put("pic",  list.get(i).getPic());
            map.put("url",  list.get(i).getUrl());
            map.put("creatorname",  list.get(i).getCreatorname());
            map.put("createtime",  list.get(i).getCreatetime());
            map.put("updatetime",  list.get(i).getUpdatetime());
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
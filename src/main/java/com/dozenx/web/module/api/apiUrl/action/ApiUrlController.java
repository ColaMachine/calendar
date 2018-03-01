/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2017-10-25 15:43:04
 * 文件说明: 
 */

package com.dozenx.web.module.api.apiUrl.action;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;

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
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
import com.dozenx.web.module.api.apiUrl.service.ApiUrlService;
import com.dozenx.web.module.api.apiUrl.bean.ApiUrl;
import com.dozenx.util.ResultUtil;
import com.dozenx.util.ValidateUtil;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.util.StringUtil;
import com.dozenx.util.ValidateUtil;
import com.dozenx.web.util.RequestUtil;
import org.springframework.web.bind.annotation.*;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.util.DateUtil;
@APIs(description = "api分类")
@Controller
@RequestMapping("/api/url/")
public class ApiUrlController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(ApiUrlController.class);
    /** 权限service **/
    @Autowired
    private ApiUrlService apiUrlService;
    


    /**
     * 说明:ajax请求ApiUrl信息
     * @author dozen.zhang
     * @date 2017-10-25 15:43:04
     * @return String
     */
       @API(summary="api分类列表接口",
                 description="api分类列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
                    @Param(name="name" , description="名称",dataType = DataType.STRING,required = false),
                    @Param(name="pid" , description="父id",dataType = DataType.LONG,required = false),
                    @Param(name="summary" , description="概要",dataType = DataType.STRING,required = false),
                    @Param(name="description" , description="备注",dataType = DataType.STRING,required = false),
                    @Param(name="consumes" , description="允许的请求类型",dataType = DataType.STRING,required = false),
                    @Param(name="produces" , description="响应MIME",dataType = DataType.STRING,required = false),
                    @Param(name="createUser" , description="创建者",dataType = DataType.STRING,required = false),
                    @Param(name="deprecated" , description="是否废弃",dataType = DataType.INTEGER,required = false),
                    @Param(name="createtime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
                    @Param(name="updatetime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
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
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = request.getParameter("nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }
        String summary = request.getParameter("summary");
        if(!StringUtil.isBlank(summary)){
            params.put("summary",summary);
        }
        String summaryLike = request.getParameter("summaryLike");
        if(!StringUtil.isBlank(summaryLike)){
            params.put("summaryLike",summaryLike);
        }
        String description = request.getParameter("description");
        if(!StringUtil.isBlank(description)){
            params.put("description",description);
        }
        String descriptionLike = request.getParameter("descriptionLike");
        if(!StringUtil.isBlank(descriptionLike)){
            params.put("descriptionLike",descriptionLike);
        }
        String consumes = request.getParameter("consumes");
        if(!StringUtil.isBlank(consumes)){
            params.put("consumes",consumes);
        }
        String consumesLike = request.getParameter("consumesLike");
        if(!StringUtil.isBlank(consumesLike)){
            params.put("consumesLike",consumesLike);
        }
        String produces = request.getParameter("produces");
        if(!StringUtil.isBlank(produces)){
            params.put("produces",produces);
        }
        String producesLike = request.getParameter("producesLike");
        if(!StringUtil.isBlank(producesLike)){
            params.put("producesLike",producesLike);
        }
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            params.put("createUser",createUser);
        }
        String createUserLike = request.getParameter("createUserLike");
        if(!StringUtil.isBlank(createUserLike)){
            params.put("createUserLike",createUserLike);
        }
        String deprecated = request.getParameter("deprecated");
        if(!StringUtil.isBlank(deprecated)){
            params.put("deprecated",deprecated);
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
        List<ApiUrl> apiUrls = apiUrlService.listByParams4Page(params);
        return ResultUtil.getResult(apiUrls, page);
    }
    
   /**
    * 说明:ajax请求ApiUrl信息 无分页版本
    * @return ResultDTO 返回结果
    * @author dozen.zhang
    * @date 2017-10-25 15:43:04
    */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public ResultDTO listAll(HttpServletRequest request) {
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
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }
        String summary = request.getParameter("summary");
        if(!StringUtil.isBlank(summary)){
            params.put("summary",summary);
        }
        String summaryLike = request.getParameter("summaryLike");
        if(!StringUtil.isBlank(summaryLike)){
            params.put("summaryLike",summaryLike);
        }
        String description = request.getParameter("description");
        if(!StringUtil.isBlank(description)){
            params.put("description",description);
        }
        String descriptionLike = request.getParameter("descriptionLike");
        if(!StringUtil.isBlank(descriptionLike)){
            params.put("descriptionLike",descriptionLike);
        }
        String consumes = request.getParameter("consumes");
        if(!StringUtil.isBlank(consumes)){
            params.put("consumes",consumes);
        }
        String consumesLike = request.getParameter("consumesLike");
        if(!StringUtil.isBlank(consumesLike)){
            params.put("consumesLike",consumesLike);
        }
        String produces = request.getParameter("produces");
        if(!StringUtil.isBlank(produces)){
            params.put("produces",produces);
        }
        String producesLike = request.getParameter("producesLike");
        if(!StringUtil.isBlank(producesLike)){
            params.put("producesLike",producesLike);
        }
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            params.put("createUser",createUser);
        }
        String createUserLike = request.getParameter("createUserLike");
        if(!StringUtil.isBlank(createUserLike)){
            params.put("createUserLike",createUserLike);
        }
        String deprecated = request.getParameter("deprecated");
        if(!StringUtil.isBlank(deprecated)){
            params.put("deprecated",deprecated);
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

        List<ApiUrl> apiUrls = apiUrlService.listByParams(params);
        return ResultUtil.getDataResult(apiUrls);
    }

   /**
    * 说明:查看单条信息
    * @param request 发请求
    * @return String
    * @author dozen.zhang
    * @date 2017-10-25 15:43:04
    */
  @API( summary="根据id查询单个api分类信息",
           description = "根据id查询单个api分类信息",
           parameters={
                   @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
           })
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(@PathVariable Long id,HttpServletRequest request) {
            HashMap<String,Object> result =new HashMap</*String,Object*/>();
        if(id > 0){
            ApiUrl bean = apiUrlService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        ApiUrl bean = apiUrlService.selectByPrimaryKey(Long.valueOf(id));
        HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
        result.put("bean", bean);
        return this.getResult(bean);*/
    }

    
    /**
     * 说明:更新ApiUrl信息
     * 
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2017-10-25 15:43:04
     */
      @API( summary="根据id更新单个api分类信息",
        description = "根据id更新单个api分类信息",
        parameters={
           @Param(name="id" , description="编号",type="LONG",required = false),
           @Param(name="name" , description="名称",type="STRING",required = false),
           @Param(name="pid" , description="父id",type="LONG",required = false),
           @Param(name="summary" , description="概要",type="STRING",required = false),
           @Param(name="description" , description="备注",type="STRING",required = false),
           @Param(name="consumes" , description="允许的请求类型",type="STRING",required = false),
           @Param(name="produces" , description="响应MIME",type="STRING",required = false),
           @Param(name="createUser" , description="创建者",type="STRING",required = false),
           @Param(name="deprecated" , description="是否废弃",type="INTEGER",required = false),
           @Param(name="createtime" , description="创建时间",type="TIMESTAMP",required = false),
           @Param(name="updatetime" , description="更新时间",type="TIMESTAMP",required = false),
        })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO update(@PathVariable Long id,HttpServletRequest request) throws Exception {
        ApiUrl apiUrl =new  ApiUrl();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            apiUrl.setId(Long.valueOf(id)) ;
        }
        
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            apiUrl.setName(String.valueOf(name)) ;
        }
        
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            apiUrl.setPid(Long.valueOf(pid)) ;
        }
        
        String summary = request.getParameter("summary");
        if(!StringUtil.isBlank(summary)){
            apiUrl.setSummary(String.valueOf(summary)) ;
        }
        
        String description = request.getParameter("description");
        if(!StringUtil.isBlank(description)){
            apiUrl.setDescription(String.valueOf(description)) ;
        }
        
        String consumes = request.getParameter("consumes");
        if(!StringUtil.isBlank(consumes)){
            apiUrl.setConsumes(String.valueOf(consumes)) ;
        }
        
        String produces = request.getParameter("produces");
        if(!StringUtil.isBlank(produces)){
            apiUrl.setProduces(String.valueOf(produces)) ;
        }
        
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            apiUrl.setCreateUser(String.valueOf(createUser)) ;
        }
        
        String deprecated = request.getParameter("deprecated");
        if(!StringUtil.isBlank(deprecated)){
            apiUrl.setDeprecated(Integer.valueOf(deprecated)) ;
        }
        
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            apiUrl.setCreatetime(Timestamp.valueOf(createtime)) ;
        }
        
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            apiUrl.setUpdatetime(Timestamp.valueOf(updatetime)) ;
        }
        */
       // String id = request.getParameter("id");
//        if(!StringUtil.isBlank(id)){
//            apiUrl.setId(Long.valueOf(id));
//        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            apiUrl.setName(name);
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            apiUrl.setPid(Long.valueOf(pid));
        }
        String summary = request.getParameter("summary");
        if(!StringUtil.isBlank(summary)){
            apiUrl.setSummary(summary);
        }
        String description = request.getParameter("description");
        if(!StringUtil.isBlank(description)){
            apiUrl.setDescription(description);
        }
        String consumes = request.getParameter("consumes");
        if(!StringUtil.isBlank(consumes)){
            apiUrl.setConsumes(consumes);
        }
        String produces = request.getParameter("produces");
        if(!StringUtil.isBlank(produces)){
            apiUrl.setProduces(produces);
        }
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            apiUrl.setCreateUser(createUser);
        }
        String deprecated = request.getParameter("deprecated");
        if(!StringUtil.isBlank(deprecated)){
            apiUrl.setDeprecated(Integer.valueOf(deprecated));
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                apiUrl.setCreatetime(Timestamp.valueOf(createtime));
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                apiUrl.setCreatetime(new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                apiUrl.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                apiUrl.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id+"", "编号",  new Rule[]{new Digits(15,0)});
        vu.add("name", name, "名称",  new Rule[]{new Length(50)});
        vu.add("pid", pid, "父id",  new Rule[]{new Digits(15,0)});
        vu.add("summary", summary, "概要",  new Rule[]{new Length(200)});
        vu.add("description", description, "备注",  new Rule[]{new Length(200)});
        vu.add("consumes", consumes, "允许的请求类型",  new Rule[]{new Length(50)});
        vu.add("produces", produces, "响应MIME",  new Rule[]{new Length(50)});
        vu.add("createUser", createUser, "创建者",  new Rule[]{new Length(200)});
        vu.add("deprecated", deprecated, "是否废弃",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","0"})});
        vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return apiUrlService.save(apiUrl);
       
    }


        /**
         * 说明:添加ApiUrl信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2017-10-25 15:43:04
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
        @API( summary="添加单个api分类信息",
            description = "添加单个api分类信息",
            parameters={
               @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
               @Param(name="name" , description="名称",dataType = DataType.STRING,required = false),
               @Param(name="pid" , description="父id",dataType = DataType.LONG,required = false),
               @Param(name="summary" , description="概要",dataType = DataType.STRING,required = false),
               @Param(name="description" , description="备注",dataType = DataType.STRING,required = false),
               @Param(name="consumes" , description="允许的请求类型",dataType = DataType.STRING,required = false),
               @Param(name="produces" , description="响应MIME",dataType = DataType.STRING,required = false),
               @Param(name="createUser" , description="创建者",dataType = DataType.STRING,required = false),
               @Param(name="deprecated" , description="是否废弃",dataType = DataType.INTEGER,required = false),
               @Param(name="createtime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
               @Param(name="updatetime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
            })
        @RequestMapping(value = "add",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO add(HttpServletRequest request) throws Exception {
            ApiUrl apiUrl =new  ApiUrl();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                apiUrl.setId(Long.valueOf(id)) ;
            }
            
            String name = request.getParameter("name");
            if(!StringUtil.isBlank(name)){
                apiUrl.setName(String.valueOf(name)) ;
            }
            
            String pid = request.getParameter("pid");
            if(!StringUtil.isBlank(pid)){
                apiUrl.setPid(Long.valueOf(pid)) ;
            }
            
            String summary = request.getParameter("summary");
            if(!StringUtil.isBlank(summary)){
                apiUrl.setSummary(String.valueOf(summary)) ;
            }
            
            String description = request.getParameter("description");
            if(!StringUtil.isBlank(description)){
                apiUrl.setDescription(String.valueOf(description)) ;
            }
            
            String consumes = request.getParameter("consumes");
            if(!StringUtil.isBlank(consumes)){
                apiUrl.setConsumes(String.valueOf(consumes)) ;
            }
            
            String produces = request.getParameter("produces");
            if(!StringUtil.isBlank(produces)){
                apiUrl.setProduces(String.valueOf(produces)) ;
            }
            
            String createUser = request.getParameter("createUser");
            if(!StringUtil.isBlank(createUser)){
                apiUrl.setCreateUser(String.valueOf(createUser)) ;
            }
            
            String deprecated = request.getParameter("deprecated");
            if(!StringUtil.isBlank(deprecated)){
                apiUrl.setDeprecated(Integer.valueOf(deprecated)) ;
            }
            
            String createtime = request.getParameter("createtime");
            if(!StringUtil.isBlank(createtime)){
                apiUrl.setCreatetime(Timestamp.valueOf(createtime)) ;
            }
            
            String updatetime = request.getParameter("updatetime");
            if(!StringUtil.isBlank(updatetime)){
                apiUrl.setUpdatetime(Timestamp.valueOf(updatetime)) ;
            }
            */
            String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            apiUrl.setId(Long.valueOf(id));
        }
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            apiUrl.setName(name);
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            apiUrl.setPid(Long.valueOf(pid));
        }
        String summary = request.getParameter("summary");
        if(!StringUtil.isBlank(summary)){
            apiUrl.setSummary(summary);
        }
        String description = request.getParameter("description");
        if(!StringUtil.isBlank(description)){
            apiUrl.setDescription(description);
        }
        String consumes = request.getParameter("consumes");
        if(!StringUtil.isBlank(consumes)){
            apiUrl.setConsumes(consumes);
        }
        String produces = request.getParameter("produces");
        if(!StringUtil.isBlank(produces)){
            apiUrl.setProduces(produces);
        }
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            apiUrl.setCreateUser(createUser);
        }
        String deprecated = request.getParameter("deprecated");
        if(!StringUtil.isBlank(deprecated)){
            apiUrl.setDeprecated(Integer.valueOf(deprecated));
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                apiUrl.setCreatetime(Timestamp.valueOf(createtime));
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                apiUrl.setCreatetime(new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                apiUrl.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                apiUrl.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

            //valid
            ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
        vu.add("name", name, "名称",  new Rule[]{new Length(50)});
        vu.add("pid", pid, "父id",  new Rule[]{new Digits(15,0)});
        vu.add("summary", summary, "概要",  new Rule[]{new Length(200)});
        vu.add("description", description, "备注",  new Rule[]{new Length(200)});
        vu.add("consumes", consumes, "允许的请求类型",  new Rule[]{new Length(50)});
        vu.add("produces", produces, "响应MIME",  new Rule[]{new Length(50)});
        vu.add("createUser", createUser, "创建者",  new Rule[]{new Length(200)});
        vu.add("deprecated", deprecated, "是否废弃",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","0"})});
        vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

            return apiUrlService.save(apiUrl);

        }
    /**
     * 说明:删除ApiUrl信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2017-10-25 15:43:04
     */
     @API( summary="根据id删除单个api分类信息",
        description = "根据id删除单个api分类信息",
        parameters={
         @Param(name="id" , description="编号",dataType= DataType.LONG,required = true),
        })
    @RequestMapping(value = "delete/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public ResultDTO delete(@PathVariable Long id,HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
       // Long id = Long.valueOf(idStr);
        apiUrlService.delete(id);
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
       return  apiUrlService.multilDelete(idAry);
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
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = request.getParameter("nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }
        String summary = request.getParameter("summary");
        if(!StringUtil.isBlank(summary)){
            params.put("summary",summary);
        }
        String summaryLike = request.getParameter("summaryLike");
        if(!StringUtil.isBlank(summaryLike)){
            params.put("summaryLike",summaryLike);
        }
        String description = request.getParameter("description");
        if(!StringUtil.isBlank(description)){
            params.put("description",description);
        }
        String descriptionLike = request.getParameter("descriptionLike");
        if(!StringUtil.isBlank(descriptionLike)){
            params.put("descriptionLike",descriptionLike);
        }
        String consumes = request.getParameter("consumes");
        if(!StringUtil.isBlank(consumes)){
            params.put("consumes",consumes);
        }
        String consumesLike = request.getParameter("consumesLike");
        if(!StringUtil.isBlank(consumesLike)){
            params.put("consumesLike",consumesLike);
        }
        String produces = request.getParameter("produces");
        if(!StringUtil.isBlank(produces)){
            params.put("produces",produces);
        }
        String producesLike = request.getParameter("producesLike");
        if(!StringUtil.isBlank(producesLike)){
            params.put("producesLike",producesLike);
        }
        String createUser = request.getParameter("createUser");
        if(!StringUtil.isBlank(createUser)){
            params.put("createUser",createUser);
        }
        String createUserLike = request.getParameter("createUserLike");
        if(!StringUtil.isBlank(createUserLike)){
            params.put("createUserLike",createUserLike);
        }
        String deprecated = request.getParameter("deprecated");
        if(!StringUtil.isBlank(deprecated)){
            params.put("deprecated",deprecated);
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
        List<ApiUrl> list =apiUrlService.listByParams(params);
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
        colTitle.put("name", "名称");
        colTitle.put("pid", "父id");
        colTitle.put("summary", "概要");
        colTitle.put("description", "备注");
        colTitle.put("consumes", "允许的请求类型");
        colTitle.put("produces", "响应MIME");
        colTitle.put("createUser", "创建者");
        colTitle.put("deprecated", "是否废弃");
        colTitle.put("createtime", "创建时间");
        colTitle.put("updatetime", "更新时间");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            ApiUrl sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("name",  list.get(i).getName());
            map.put("pid",  list.get(i).getPid());
            map.put("summary",  list.get(i).getSummary());
            map.put("description",  list.get(i).getDescription());
            map.put("consumes",  list.get(i).getConsumes());
            map.put("produces",  list.get(i).getProduces());
            map.put("createUser",  list.get(i).getCreateUser());
            map.put("deprecated",  list.get(i).getDeprecated());
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


      /**
         * 说明: 跳转到ApiUrl列表页面
         *
         * @return
         * @return String
         * @author dozen.zhang
         * @date 2015年11月15日下午12:30:45
         */
        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
        public String listHtml() {
            return "/static/html/api/ApiUrlList.html";
        }

        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
        public String listMapperHtml() {
            return "/static/html/ApiUrlListMapper.html";
        }


    /**
     * 说明:跳转编辑页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2017-10-25 15:43:04
     */
    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
    public String editHtml( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/api/ApiUrlEdit.html";
    }
    /**
         * 说明:跳转查看页面
         * @param request 发请求
         * @return String
         * @author dozen.zhang
         * @date 2017-10-25 15:43:04
         */
    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
    public String viewHtml( HttpServletRequest request) {
        return "/static/html/api/ApiUrlView.html";
    }
}

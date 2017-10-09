/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2017-5-15 15:30:02
 * 文件说明: 
 */

package com.dozenx.web.module.buy.consume.action;

import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
import com.dozenx.util.*;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.DateValue;
import com.dozenx.web.core.rules.Digits;
import com.dozenx.web.core.rules.NotEmpty;
import com.dozenx.web.core.rules.Rule;
import com.dozenx.web.module.buy.consume.bean.Consume;
import com.dozenx.web.module.buy.consume.service.ConsumeService;
import com.dozenx.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;
@APIs(description = "消费记录")
@Controller
@RequestMapping("/buy/consume/")
public class ConsumeController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(ConsumeController.class);
    /** 权限service **/
    @Autowired
    private ConsumeService consumeService;
    


    /**
     * 说明:ajax请求Consume信息
     * @author dozen.zhang
     * @date 2017-5-15 15:30:02
     * @return String
     */
       @API(summary="消费记录列表接口",
                 description="消费记录列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小", dataType= DataType.PHONE,required = true),
                 @Param(name="curPage", description="当前页", dataType= DataType.CAPTCHA,required = true),
                    @Param(name="id" , description="主键",dataType = DataType.LONG,required = false),
                    @Param(name="merId" , description="商户id",dataType = DataType.LONG,required = true),
                    @Param(name="userId" , description="用户id",dataType = DataType.LONG,required = true),
                    @Param(name="money" , description="实付金额",dataType = DataType.INTEGER,required = true),
                    @Param(name="orderId" , description="订单id",dataType = DataType.LONG,required = true),
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
        String merId = request.getParameter("merId");
        if(!StringUtil.isBlank(merId)){
            params.put("merId",merId);
        }
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            params.put("userId",userId);
        }
        String money = request.getParameter("money");
        if(!StringUtil.isBlank(money)){
            params.put("money",money);
        }
        String orderId = request.getParameter("orderId");
        if(!StringUtil.isBlank(orderId)){
            params.put("orderId",orderId);
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
        List<Consume> consumes = consumeService.listByParams4Page(params);
        return ResultUtil.getResult(consumes, page);
    }
    
   /**
    * 说明:ajax请求Consume信息 无分页版本
    * @return ResultDTO 返回结果
    * @author dozen.zhang
    * @date 2017-5-15 15:30:02
    */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public ResultDTO listAll(HttpServletRequest request) {
                HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String merId = request.getParameter("merId");
        if(!StringUtil.isBlank(merId)){
            params.put("merId",merId);
        }
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            params.put("userId",userId);
        }
        String money = request.getParameter("money");
        if(!StringUtil.isBlank(money)){
            params.put("money",money);
        }
        String orderId = request.getParameter("orderId");
        if(!StringUtil.isBlank(orderId)){
            params.put("orderId",orderId);
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

        List<Consume> consumes = consumeService.listByParams(params);
        return ResultUtil.getDataResult(consumes);
    }

   /**
    * 说明:查看单条信息
    * @param request 发请求
    * @return String
    * @author dozen.zhang
    * @date 2017-5-15 15:30:02
    */
  @API( summary="根据id查询单个消费记录信息",
           description = "根据id查询单个消费记录信息",
           parameters={
                   @Param(name="id" , description="id",dataType= DataType.INTEGER,required = true),
           })
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(@PathVariable Long id,HttpServletRequest request) {
            HashMap<String,Object> result =new HashMap</*String,Object*/>();
        if(id > 0){
            Consume bean = consumeService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        Consume bean = consumeService.selectByPrimaryKey(Long.valueOf(id));
        HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
        result.put("bean", bean);
        return this.getResult(bean);*/
    }

    
    /**
     * 说明:更新Consume信息
     * 
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2017-5-15 15:30:02
     */
      @API( summary="根据id更新单个消费记录信息",
        description = "根据id更新单个消费记录信息",
        parameters={
           @Param(name="id" , description="主键",type="bigint(15)",required = false),
           @Param(name="merId" , description="商户id",type="bigint(15)",required = true),
           @Param(name="userId" , description="用户id",type="bigint(15)",required = true),
           @Param(name="money" , description="实付金额",type="int(15)",required = true),
           @Param(name="orderId" , description="订单id",type="bigint(15)",required = true),
           @Param(name="createtime" , description="创建时间",type="timestamp",required = false),
           @Param(name="updatetime" , description="更新时间",type="timestamp",required = false),
        })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO update(@PathVariable Long id,HttpServletRequest request) throws Exception {
        Consume consume =new  Consume();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            consume.setId(Long.valueOf(id)) ;
        }
        
        String merId = request.getParameter("merId");
        if(!StringUtil.isBlank(merId)){
            consume.setMerId(Long.valueOf(merId)) ;
        }
        
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            consume.setUserId(Long.valueOf(userId)) ;
        }
        
        String money = request.getParameter("money");
        if(!StringUtil.isBlank(money)){
            consume.setMoney(Integer.valueOf(money)) ;
        }
        
        String orderId = request.getParameter("orderId");
        if(!StringUtil.isBlank(orderId)){
            consume.setOrderId(Long.valueOf(orderId)) ;
        }
        
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            consume.setCreatetime(Timestamp.valueOf(createtime)) ;
        }
        
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            consume.setUpdatetime(Timestamp.valueOf(updatetime)) ;
        }
        */

        String merId = request.getParameter("merId");
        if(!StringUtil.isBlank(merId)){
            consume.setMerId(Long.valueOf(merId));
        }
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            consume.setUserId(Long.valueOf(userId));
        }
        String money = request.getParameter("money");
        if(!StringUtil.isBlank(money)){
            consume.setMoney(Integer.valueOf(money));
        }
        String orderId = request.getParameter("orderId");
        if(!StringUtil.isBlank(orderId)){
            consume.setOrderId(Long.valueOf(orderId));
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                consume.setCreatetime(Timestamp.valueOf(createtime));
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                consume.setCreatetime(new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                consume.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                consume.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", ""+id, "主键",  new Rule[]{new Digits(15,0)});
        vu.add("merId", merId, "商户id",  new Rule[]{new Digits(15,0),new NotEmpty()});
        vu.add("userId", userId, "用户id",  new Rule[]{new Digits(15,0),new NotEmpty()});
        vu.add("money", money, "实付金额",  new Rule[]{new Digits(15,0),new NotEmpty()});
        vu.add("orderId", orderId, "订单id",  new Rule[]{new Digits(15,0),new NotEmpty()});
        vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return consumeService.save(consume);
       
    }


        /**
         * 说明:添加Consume信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2017-5-15 15:30:02
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
        @API( summary="添加单个消费记录信息",
            description = "添加单个消费记录信息",
            parameters={
               @Param(name="id" , description="主键",dataType = DataType.LONG,required = false),
               @Param(name="merId" , description="商户id",dataType = DataType.LONG,required = true),
               @Param(name="userId" , description="用户id",dataType = DataType.LONG,required = true),
               @Param(name="money" , description="实付金额",dataType = DataType.INTEGER,required = true),
               @Param(name="orderId" , description="订单id",dataType = DataType.LONG,required = true),
               @Param(name="createtime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
               @Param(name="updatetime" , description="更新时间",dataType = DataType.DATE_TIME,required = false),
            })
        @RequestMapping(value = "",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO add(HttpServletRequest request) throws Exception {
            Consume consume =new  Consume();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                consume.setId(Long.valueOf(id)) ;
            }
            
            String merId = request.getParameter("merId");
            if(!StringUtil.isBlank(merId)){
                consume.setMerId(Long.valueOf(merId)) ;
            }
            
            String userId = request.getParameter("userId");
            if(!StringUtil.isBlank(userId)){
                consume.setUserId(Long.valueOf(userId)) ;
            }
            
            String money = request.getParameter("money");
            if(!StringUtil.isBlank(money)){
                consume.setMoney(Integer.valueOf(money)) ;
            }
            
            String orderId = request.getParameter("orderId");
            if(!StringUtil.isBlank(orderId)){
                consume.setOrderId(Long.valueOf(orderId)) ;
            }
            
            String createtime = request.getParameter("createtime");
            if(!StringUtil.isBlank(createtime)){
                consume.setCreatetime(Timestamp.valueOf(createtime)) ;
            }
            
            String updatetime = request.getParameter("updatetime");
            if(!StringUtil.isBlank(updatetime)){
                consume.setUpdatetime(Timestamp.valueOf(updatetime)) ;
            }
            */
            String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            consume.setId(Long.valueOf(id));
        }
        String merId = request.getParameter("merId");
        if(!StringUtil.isBlank(merId)){
            consume.setMerId(Long.valueOf(merId));
        }
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            consume.setUserId(Long.valueOf(userId));
        }
        String money = request.getParameter("money");
        if(!StringUtil.isBlank(money)){
            consume.setMoney(Integer.valueOf(money));
        }
        String orderId = request.getParameter("orderId");
        if(!StringUtil.isBlank(orderId)){
            consume.setOrderId(Long.valueOf(orderId));
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                consume.setCreatetime(Timestamp.valueOf(createtime));
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                consume.setCreatetime(new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            if(StringUtil.checkNumeric(updatetime)){
                consume.setUpdatetime(Timestamp.valueOf(updatetime));
            }else if(StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")){
                consume.setUpdatetime(new Timestamp( DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

            //valid
            ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "主键",  new Rule[]{new Digits(15,0)});
        vu.add("merId", merId, "商户id",  new Rule[]{new Digits(15,0),new NotEmpty()});
        vu.add("userId", userId, "用户id",  new Rule[]{new Digits(15,0),new NotEmpty()});
        vu.add("money", money, "实付金额",  new Rule[]{new Digits(15,0),new NotEmpty()});
        vu.add("orderId", orderId, "订单id",  new Rule[]{new Digits(15,0),new NotEmpty()});
        vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

            return consumeService.save(consume);

        }
    /**
     * 说明:删除Consume信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2017-5-15 15:30:02
     */
     @API( summary="根据id删除单个消费记录信息",
        description = "根据id删除单个消费记录信息",
        parameters={
         @Param(name="id" , description="主键",dataType= DataType.LONG,required = true),
        })
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public ResultDTO delete(@PathVariable Long id,HttpServletRequest request) {
       // String idStr = request.getParameter("id");
       /* if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }*/
       // Long id = Long.valueOf(idStr);
        consumeService.delete(id);
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
       return  consumeService.multilDelete(idAry);
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
        String merId = request.getParameter("merId");
        if(!StringUtil.isBlank(merId)){
            params.put("merId",merId);
        }
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            params.put("userId",userId);
        }
        String money = request.getParameter("money");
        if(!StringUtil.isBlank(money)){
            params.put("money",money);
        }
        String orderId = request.getParameter("orderId");
        if(!StringUtil.isBlank(orderId)){
            params.put("orderId",orderId);
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
        List<Consume> list =consumeService.listByParams(params);
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
        colTitle.put("merId", "商户id");
        colTitle.put("userId", "用户id");
        colTitle.put("money", "实付金额");
        colTitle.put("orderId", "订单id");
        colTitle.put("createtime", "创建时间");
        colTitle.put("updatetime", "更新时间");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            Consume sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("merId",  list.get(i).getMerId());
            map.put("userId",  list.get(i).getUserId());
            map.put("money",  list.get(i).getMoney());
            map.put("orderId",  list.get(i).getOrderId());
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
         * 说明: 跳转到Consume列表页面
         *
         * @return
         * @return String
         * @author dozen.zhang
         * @date 2015年11月15日下午12:30:45
         */
        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
        public String listHtml() {
            return "/static/html/ConsumeList.html";
        }

        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
        public String listMapperHtml() {
            return "/static/html/ConsumeListMapper.html";
        }


    /**
     * 说明:跳转编辑页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2017-5-15 15:30:02
     */
    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
    public String editHtml( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/ConsumeEdit.html";
    }
    /**
         * 说明:跳转查看页面
         * @param request 发请求
         * @return String
         * @author dozen.zhang
         * @date 2017-5-15 15:30:02
         */
    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
    public String viewHtml( HttpServletRequest request) {
        return "/static/html/ConsumeView.html";
    }
}

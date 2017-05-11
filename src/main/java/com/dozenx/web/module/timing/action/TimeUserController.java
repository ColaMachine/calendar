package com.dozenx.web.module.timing.action;

import com.cpj.swagger.annotation.*;
import com.dozenx.core.exception.ParamException;
import com.dozenx.util.JsonUtils;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.LogType;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.merchant.bean.SessionDTO;
import com.dozenx.web.module.merchant.bean.SessionUser;
import com.dozenx.web.module.timing.bean.UserConsume;
import com.dozenx.web.module.timing.bean.UserCutoff;
import com.dozenx.web.module.timing.service.UserConsumeWrapService;
import com.dozenx.web.module.timing.service.UserCutoffWrapService;
import com.dozenx.web.module.timing.service.impl.UserConsumeService;
import org.codehaus.jackson.map.deser.ValueInstantiators;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by dozen.zhang on 2017/3/27.
 */
@Controller
@APIs(description = "用户时长")
@RequestMapping("/time/usertime")
public class TimeUserController extends BaseController{


    @Resource
    private UserCutoffWrapService userCutoffWrapService;

    @Resource
    private UserConsumeWrapService userConsumeWrapService;

    @Resource
    private UserConsumeService userConsumeService;

    private static final String key="gfds89u0gh562dsfg3qwce%sw23H%@1&asr#Fsdw";//用于接口校验

    private static final String appId = "1000029";//用于接口校验


    /**
     * 用户服务显示接口
     * @param request 请求
     * @return 赔付导入时长
     * @author zhangzw
     * @date 2017年3月27日20:45:31
     */
    @API(/*value="usertimes",*/ summary="赔付时长导入",parameters={
            @Param(name="file" , description="导入的excel文件",dataType= DataType.FILE,required = true),
    })
    @APIResponse("{r:0/1,msg:'失败原因'}")
    @RequestMapping(value = "usertimes" ,method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO batchAdd(HttpServletRequest request) {
        //TODO　
        return getResult(null);

    }

    /**
     *
     * @param request
     * @param response
     */

    /**
     * 用户服务显示接口
     * @param request 请求
     * @return 赔付导入时长
     * @author zhangzw
     * @date 2017年3月27日20:45:31
     */
    @API(/*value="/usertime",*/ summary="单条时长添加",parameters={
            @Param(name="merchantId" , description="商户Id",dataType= DataType.LONG,required = true),
            @Param(name="userId" , description="用户Id",dataType= DataType.LONG,required = true),
            @Param(name="days" , description="添加的天数",dataType= DataType.LONG,required = true),
            @Param(name="remarks" , description="备注(操作人名称)",dataType= DataType.STRING,required = true),
    })
    @APIResponse("{r:0/1,msg:'失败原因'}")
    @RequestMapping(value= "/usertime",method = RequestMethod.POST)
    @ResponseBody
    //id":66460,"merchantid":205628,"userid":25785130,"consumetype":2,"packageid":322,"packagenum":1,"totalnum":0.0,"orderid":"orderid","paynum":0.0,"addday":79,"begindate":1484029950000,"enddate":1490855550000,"createdate":1484029950000,"remarks":"","pkgprice":"0.00"
    //http://127.0.0.1:8080/yuanqu/jiekou/addCompConsume?merchantId=205628&userId=25785130&days=2&remarks=admin&orderId=123&consumeType=2&packageId=322&packageNum=1&totalNum=3&payNum=3
    public ResultDTO singleAdd(HttpServletRequest request){
        Map<String, Object> result = new HashMap<String,Object>();
        result.put("resultCode",0);//如果成功则返回resultCode=0
        Map<String, Object> consume = new HashMap<String, Object>();
        //必须的参数
        String merchantIdStr = (String)request.getParameter("merchantId");
        String userIdStr = (String)request.getParameter("userId");
        String daysStr = (String)request.getParameter("days");
        String remarks = (String)request.getParameter("remarks");
        long merchantId = 0;
        long userId = 0;
        int days = 0;
        //可选的参数
        String orderId = (String)request.getParameter("orderId");
        String consumeTypeStr = (String)request.getParameter("consumeType");
        String packageIdStr = (String)request.getParameter("packageId");
        String packageNumStr = (String)request.getParameter("packageNum");
        String totalNumStr = (String)request.getParameter("totalNum");
        String payNumStr = (String)request.getParameter("payNum");
        int consumeType = 2;//默认为免费礼包
        long packageId = 201;//免费礼包ID
        int packageNum = 1;//免费礼包数
        double totalNum = 0;//套餐价格
        double payNum = 0;//实付金额
        try {
            //token校验
//			if(!checkToken(request)){
//				result.put("resultCode",-1);
//				result.put("msg","token校验失败");
//				throw new Exception("token校验失败");
//			}
            if (merchantIdStr == null || userIdStr == null ||daysStr == null) {
                result.put("resultCode",-1);
                result.put("msg","缺少必要的参数");
                throw new Exception("缺少必要的参数");
            }else{
                try {
                    merchantId = Long.parseLong(merchantIdStr);
                    userId = Long.parseLong(userIdStr);
                    days = Integer.parseInt(daysStr);
                } catch (NumberFormatException e) {
                    logger.error("字符串转数字出错：", e);
                    result.put("resultCode",-1);
                    result.put("msg","传入的参数格式有问题");
                    throw e;
                }
            }
            if(consumeTypeStr!=null){
                consumeType = Integer.parseInt(consumeTypeStr);
            }
            if(packageIdStr!=null){
                packageId = Long.parseLong(packageIdStr);
            }
            if(packageNumStr!=null){
                packageNum = Integer.parseInt(packageNumStr);
            }
            if(totalNumStr!=null){
                totalNum = Double.parseDouble(totalNumStr);
            }
            if(payNumStr!=null){
                payNum = Double.parseDouble(payNumStr);
            }
            Date nowtime = new Date();
            Date startime = nowtime;
            consume.put("merchantId", merchantId);
            consume.put("userId", userId);
            // 查询用户在商家下的上网时长
            UserCutoff userCutoff = null;
            try {
                userCutoff = userCutoffWrapService.merchantUserCutoffDate(merchantId,userId);
            } catch (Exception e) {
                logger.error("error when iUserCutoffService.merchantUserCutoffDate", e);
                result.put("resultCode",-1);
                result.put("msg","获取用户时长失败");
                throw e;
            }
            Calendar calendar = new GregorianCalendar();
            consume.put("packageId", packageId);
            consume.put("packageNum", packageNum);
            consume.put("totalNum", totalNum);
            consume.put("orderId", orderId);
            consume.put("payNum", payNum);
            consume.put("addDay", days);
            consume.put("consumeType", consumeType);
            consume.put("remarks", remarks);
            if (userCutoff!=null) {
                if (userCutoff.getCutoffDate() != null && userCutoff.getCutoffDate().getTime() > nowtime.getTime()) {
                    // 如果还有上网时长则增加时长
                    startime = userCutoff.getCutoffDate();
                    calendar.setTime(userCutoff.getCutoffDate());
                    calendar.add(Calendar.DATE, Integer.valueOf(days));

                } else {
                    // 如果时长过期则当前时间起算
                    calendar.setTime(nowtime);
                    calendar.add(Calendar.DATE, Integer.valueOf(days));
                }
                consume.put("beginDate", startime);
                consume.put("endDate", calendar.getTime());
                consume.put("cutoffDate", calendar.getTime());
            } else {
                // 新增用户在商家下的上网时长
                calendar.setTime(nowtime);
                calendar.add(Calendar.DATE, days);
                consume.put("beginDate", startime);
                consume.put("endDate", calendar.getTime());
                consume.put("cutoffDate", calendar.getTime());
            }
            try {
                UserConsume userConsume = JsonUtils.toJavaBean(JsonUtils.toJsonString(consume), UserConsume.class);
                userConsumeWrapService.addCompConsume(userConsume);
            } catch (Exception e) {
                logger.error("iUserConsumeService.addCompConsume fail", e);
                result.put("resultCode",-1);
                result.put("msg","新增消费记录失败");
                throw e;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
       return this.getResult(SUCC);
    }
    /**
     * 用户消费列表查询 供报表统计使用
     * @param request 请求
     * @return 赔付导入时长
     * @author zhangzw
     * @date 2017年3月27日20:45:31
     */
    @API(/*value="/usertimes",*/ summary="用户消费列表查询 供报表统计使用",parameters={
            @Param(name="merchantId" , description="商户Id",dataType= DataType.LONG,required = true),
            @Param(name="userId" , description="用户Id",dataType= DataType.LONG,required = true),
            @Param(name="days" , description="添加的天数",dataType= DataType.LONG,required = true),
            @Param(name="remarks" , description="备注(操作人名称)",dataType= DataType.STRING,required = true),
    })
    @APIResponse("{r:0/1,data:'{[]}',msg:'失败原因'}")
    @RequestMapping(value="usertimes",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO queryListByParam(HttpServletRequest request){
        List<UserConsume> consumeList = null;
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("resultCode", 0);//返回成功
        Map<String,Object> map = new HashMap<String,Object>();
        String id = request.getParameter("id");
        String merchantId = request.getParameter("merchantId");
        String userId = request.getParameter("userId");
        String consumeType = request.getParameter("consumeType");
        String orderId = request.getParameter("orderId");
        String packageId = request.getParameter("packageId");
        if(id!=null&&Long.parseLong(id)>0){
            map.put("id", id);
        }
        if(merchantId!=null&&Long.parseLong(merchantId)>0){
            map.put("merchantId", merchantId);
        }
        if(userId!=null&&Long.parseLong(userId)>0){
            map.put("userId", userId);
        }
        if(consumeType!=null&&Integer.parseInt(consumeType)>0){
            map.put("consumeType",consumeType);
        }
        if(orderId!=null){
            map.put("orderId",orderId);
        }
        if(packageId!=null&&Long.parseLong(packageId)>0){
            map.put("packageId", packageId);
        }
        try {
            consumeList = userConsumeService.queryListByParam(map);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("resultCode", -1);
            result.put("msg", "调用服务查询消费记录数量出错");
        }

       return this.getDataResult(consumeList);
    }
    /**
     * 用户消费列表查询 供报表统计使用
     * @param request 请求
     * @return 赔付导入时长
     * @author zhangzw
     * @date 2017年3月27日20:45:31
     */
    @API(/*value="/usertimes",*/ summary="用户消费列表查询 供报表统计使用",parameters={
            @Param(name="merchantId" , description="商户Id",dataType= DataType.LONG,required = true),
            @Param(name="userId" , description="用户Id",dataType= DataType.LONG,required = true),
            @Param(name="days" , description="添加的天数",dataType= DataType.LONG,required = true),
            @Param(name="remarks" , description="备注(操作人名称)",dataType= DataType.STRING,required = true),
    })
    @APIResponse("{r:0/1,data:'{[]}',msg:'失败原因'}")
    @RequestMapping(value="queryCountByParam",method = RequestMethod.GET)
    @ResponseBody

    public ResultDTO queryCountByParam(HttpServletRequest request){
        int count = 0;
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("resultCode", 0);//成功返回
        Map<String,Object> map = new HashMap<String,Object>();
        String id = request.getParameter("id");
        String merchantId = request.getParameter("merchantId");
        String userId = request.getParameter("userId");
        String consumeType = request.getParameter("consumeType");
        String orderId = request.getParameter("orderId");
        String packageId = request.getParameter("packageId");
        if(id!=null&&Long.parseLong(id)>0){
            map.put("id", id);
        }
        if(merchantId!=null&&Long.parseLong(merchantId)>0){
            map.put("merchantId", merchantId);
        }
        if(userId!=null&&Long.parseLong(userId)>0){
            map.put("userId", userId);
        }
        if(consumeType!=null&&Integer.parseInt(consumeType)>0){
            map.put("consumeType",consumeType);
        }
        if(orderId!=null){
            map.put("orderId",orderId);
        }
        if(packageId!=null&&Long.parseLong(packageId)>0){
            map.put("packageId", packageId);
        }
        try {
            count = userConsumeService.queryCountByParam(map);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("resultCode", -1);
            result.put("msg", "调用服务查询消费记录数量出错");
        }
        result.put("count", count);
        return this.getResult(result);
    }

    public boolean checkToken(HttpServletRequest request){
        String timestamp = request.getParameter("timestamp");
        String token = request.getParameter("token");
        String token1 = StringUtil.encryptByDes(timestamp + appId, key);
        if(token!=null&&token1.equals(token)){
            return true;
        }
        return false;
    }
}

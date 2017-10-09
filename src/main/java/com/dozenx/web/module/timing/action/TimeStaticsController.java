package com.dozenx.web.module.timing.action;

import com.cpj.swagger.annotation.*;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.timing.service.UserConsumeWrapService;
import com.dozenx.web.module.timing.service.UserCutoffWrapService;
import com.dozenx.web.module.timing.service.impl.UserConsumeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dozen.zhang on 2017/3/27.
 */
@Controller
//@APITag(/*value = "用户时长信息",*/description = "用户时长信息")
@APIs(description = "用户时长信息")
@RequestMapping("/time/static")
public class TimeStaticsController extends BaseController{


    @Resource
    private UserCutoffWrapService userCutoffWrapService;

    @Resource
    private UserConsumeWrapService userConsumeWrapService;

    @Resource
    private UserConsumeService userConsumeService;

    private static final String key="gfds89u0gh562dsfg3qwce%sw23H%@1&asr#Fsdw";//用于接口校验

    private static final String appId = "1000029";//用于接口校验


    /**
     * 用户消费列表查询 供报表统计使用
     * @param request 请求
     * @return 赔付导入时长
     * @author zhangzw
     * @date 2017年3月27日20:45:31
     */
    @API(/*value="/usertime/consume/page",*/ summary="用户消费列表查询 供报表统计使用",parameters={
            @Param(name="merchantId" , description="商户Id",dataType= DataType.LONG,required = true),
            @Param(name="userId" , description="用户Id",dataType= DataType.LONG,required = true),
            @Param(name="days" , description="添加的天数",dataType= DataType.LONG,required = true),

            @Param(name="remarks" , description="备注(操作人名称)",dataType= DataType.STRING,required = true),
            @Param(name="pageSize" , description="分页大小",dataType= DataType.INTEGER,required = true),
            @Param(name="pageNum" , description="分页位置",dataType= DataType.INTEGER,required = true),
    })
    @APIResponse("{r:0/1,data:'{[]}',msg:'失败原因'}")
    @RequestMapping(value="queryCountByParam",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO queryPageListByParam(HttpServletRequest request){
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
            List list = userConsumeService.queryListByParam(map);
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

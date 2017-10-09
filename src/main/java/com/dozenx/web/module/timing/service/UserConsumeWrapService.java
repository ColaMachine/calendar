package com.dozenx.web.module.timing.service;

import com.dozenx.web.module.timing.bean.TimeConsume;
import com.dozenx.web.module.timing.service.impl.UserConsumeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserConsumeWrapService {
    
    @Autowired
    @Qualifier(value="localUserConsumeService")
    private UserConsumeService iUserConsumeService;
    /** * 日志打印 */
    private static Logger logger = LoggerFactory.getLogger(UserConsumeWrapService.class);
    
    /**
     * 消费记录数量查询
     * @param merchantId 商户id
     * @param userId 用户id
     * @return int
     * @author xhb 
     * @throws DCLogException 
     */
/*    public  int freeTimeAuth(Long merchantId,Long userId, Integer consumeType) throws DCLogException {
        if(merchantId==null){
            return 0;
        }
        Map<String, Object> parm = new HashMap<String, Object>();
        parm.put("merchantId", merchantId);
        parm.put("userId", userId);
        parm.put("consumeType", consumeType);
        return iUserConsumeService.queryCountByParam(JSON.toJSONString(parm));
    }*/
    
    /**
     * . 用是否有时长消费认证
     * 
     * @param userId
     *            用户id
     * @param merchantId
     *            商户id
     * @return Boolean
     * @throws Exception
     * @author xhb 2016-4-20 免费礼包
     */
    public Boolean canGetFreePkg(Long userId, Long merchantId) throws Exception {
        Map<String, Object> parm = new HashMap<String, Object>();
        if (userId == null || merchantId == null)
            return false;
        parm.put("userId", userId);
        parm.put("merchantId", merchantId);
        parm.put("consumeType", 2);
        parm.put("packageKey", 201);
        if (iUserConsumeService.queryCountByParam(parm) > 0) {
            return false;
        } else {
            return true;
        }
    }

    public void addCompConsume(TimeConsume consume) throws Exception {
        // TODO Auto-generated method stub
        iUserConsumeService.addCompConsume(consume);
    }
}

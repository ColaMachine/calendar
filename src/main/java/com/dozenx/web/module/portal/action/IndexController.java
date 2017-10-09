package com.dozenx.web.module.portal.action;

import com.cpj.swagger.annotation.APIs;
import com.dozenx.web.core.log.ServiceCode;
import com.dozenx.web.module.merchant.service.MerchantPictureService;
import com.dozenx.web.third.dbcenter.service.MerchantDeviceBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by dozen.zhang on 2017/3/30.
 */
@APIs(description = "商户接口")
@Controller
@RequestMapping("/index")
public class IndexController {

    /**日志**/
    private Logger logger = LoggerFactory.getLogger(IndexController.class);
    /**日志码**/
    private ServiceCode serviceCode = ServiceCode.INDEX;

    @Resource
    private MerchantPictureService merchantPictureService;
    @Resource
    private MerchantDeviceBaseService merchantDeviceBaseService;

}

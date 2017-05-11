package com.dozenx.web.module.merchant.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dozen.zhang on 2017/2/28.
 * 商户图片服务 用于展现轮播图片
 */
@Service
public class MerchantPictureService {
    /**
     * 根据商户id查询图片服务 由于 数据中心暂停服务 此接口待补充
     * @param merchantId 商户id
     * @return List<String> 图片路径
     * @author dozhangzheng
     * @2017-2-28 15:21:34
     */
    public List<String> getSliderList(Long merchantId){
        //TODO 未编写代码
        return new ArrayList<String>();
    }
}

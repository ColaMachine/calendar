package com.dozenx.web.module.timing.action;

import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
import com.dozenx.util.RedisUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.annotation.RequiresRoles;
import com.dozenx.web.core.annotation.RequiresUser;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.log.ServiceCode;
import com.dozenx.web.core.log.service.LogUtilService;
import com.dozenx.web.module.merchant.bean.SessionDTO;
import com.dozenx.web.module.merchant.bean.SessionUser;
import com.dozenx.web.module.timing.bean.MerchantPackage;
import com.dozenx.web.module.timing.service.PackageWrapService;
import com.dozenx.web.module.timing.service.TimeBusService;
import com.dozenx.web.third.access.service.AccessAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@APIs(description = "套餐接口")
@Controller
@RequestMapping("/time/pkg")
public class TimePackageController extends BaseController {
	ServiceCode serviceCode =ServiceCode.PackageMngController;
	/** * 引入 packageWrapService . */
    @Resource
	private PackageWrapService packageWrapService;
	/** * 引入 Order . */
	
	/** * 引入 UserWifiTime . */
	@Resource
	private TimeBusService userWifiTime;
	/** * 引入AccessAuth . */
	@Resource
	private AccessAuth accessAuth;
	/** * Logger 引入 . */
	private static Logger logger = LoggerFactory
			.getLogger(TimePackageController.class);
    @Resource
    private LogUtilService LogUtil;


	/**
	 * . 商户免费套餐领取接口
	 * @return ResultDTO
	 * @author zhangzw
	 * @date  2017-3-30 14:48:01
	 */
	@API(summary="领取商户免费套餐",description = "根据存入在session中的商户id 用户id去判断是否领取过免费礼包 如果领取过就不再领取")
	@RequestMapping(value = "get",method = RequestMethod.GET)
	@RequiresUser
	@ResponseBody
	public ResultDTO getFreePackage(HttpServletRequest request) throws Exception {

		SessionDTO sessionDTO =  (SessionDTO) request.getSession().getAttribute(
				Constants.SESSION_DTO);
		Long merchantId =sessionDTO.getMerchant().getMerchantId();
		SessionUser sessionUser = sessionDTO.getSessionUser();
		Long userId = sessionUser.getId();
		String phone = sessionUser.getPhone();
		String deviceId =sessionDTO.getDeviceId();
		String terMac = sessionDTO.getUserMac();
		String apMac = sessionDTO.getMerchant().getMacAddr();

		//String callback = sessionDTO.getCallback();
	
		if (merchantId == null || userId == null) {
			return this.getWrongResultFromCfg("err.logic.notlogging");
		}
		LogUtil.init(phone);

		Map<String, Object> ret = new HashMap<String, Object>();
		// 验证是否领过


		    String free_pkg_get_auth = RedisUtil.hget(Constants.REDIS_PKG_GET, userId+""+merchantId);
		    if(!"ok".equals(free_pkg_get_auth) || !sessionDTO.getTimeInfo().isCanGetFreePkg()){
		        return getWrongResultFromCfg("err.pkg.get.again");
		    }
			/*Object free_pkg_get_auth = request.getSession().getAttribute(
					"free_pkg_get_auth");
			if (free_pkg_get_auth == null) {
			    return getWrongResultFromCfg("err.pkg.get.again");
			} */
			// 套餐包id获取
			List<MerchantPackage> freePkg = packageWrapService.packageListSearch(
					merchantId, 2);
			if (freePkg.size() < 1) {
				return getWrongResultFromCfg("err.pkg.null");
			}
//			Long id = freePkg.get(0).getId();
			MerchantPackage merchantPackage = freePkg.get(0);
//					packageWrapService.packageSearch(id);
			 LogUtil.track(serviceCode,105, ""+ merchantId+ userId
                     +":"+(int) (merchantPackage.getPackageValue().floatValue())+":"+ merchantPackage.getId(), "begin get package ",null);
         
			if (userWifiTime.update(merchantId, userId,
					(int) (merchantPackage.getPackageValue().floatValue()), merchantPackage.getId(),
					1, (float) 0, "", (float) 0, 1, 2) == 1) {
			    RedisUtil.hdel(Constants.REDIS_PKG_GET, userId+""+merchantId);
				sessionDTO.getTimeInfo().setCanGetFreePkg(false);
				//request.getSession().removeAttribute("free_pkg_get_auth");
				// 上网时长
				ret.put("add_day",
						(int) (merchantPackage.getPackageValue().floatValue()));
				// 认证上网
				ret.put("access_auth", accessAuth.accessAccountAuth4MD5(
						sessionDTO));
			} else {
			    LogUtil.track(serviceCode,105, ""+ merchantId+ userId
			            +":"+(int) (merchantPackage.getPackageValue().floatValue())+":"+ merchantPackage.getId(), "userWifiTime.update fail ",null);
				return getWrongResultFromCfg("err.update.user.cutoff.date");
			}
			sessionDTO.setTimeInfo(userWifiTime.getUserTimeInfo(sessionUser.getId(),sessionUser.getPhone(),sessionDTO.getMerchant().getMerchantId()));

		return getResult(ret);
	}


	/**
	 * . 商户免费套餐保存接口
	 * @return ResultDTO
	 * @author zzw
	 * @date  2017-3-30 14:48:01
	 */
	@API(summary="商户免费套餐保存接口",
			description = "管理系统设置商户免费套餐",
			parameters={
		@Param(name="merchantId", description="商户id",dataType = DataType.LONG,required = true),
		@Param(name="day", description="天数", dataType = DataType.INTEGER,required = true),
	})
	@RequiresRoles("admin")
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO saveMerchantFreePackage(HttpServletRequest request) throws Exception {

            Long merchantId = Long.valueOf( request.getParameter("merchantId"));
            Integer days = Integer.valueOf(request.getParameter("day"));
            MerchantPackage pkg = packageWrapService.getFreePkg(merchantId);
            if(pkg!=null &&  pkg.getId()!=null && pkg.getId()!=0){//更新
                pkg.setPackageValue(days.floatValue());
                packageWrapService.update(pkg);
            }else{//新增
                MerchantPackage merPac = new MerchantPackage();
                merPac.setMerchantId(merchantId);
                merPac.setCreateDate(new Date());
                merPac.setPackageType(2);
                merPac.setPackageKey(201);
                merPac.setStatus(1);
                merPac.setStatusDate(new Date());
                merPac.setPackageValue(days.floatValue());
                packageWrapService.insert(merPac);
            }
            return getResult(0);

    }


  /**
   *
  * 迭代11：查询套餐
  * @Description: queryListByParam
  * @param request
  * @return ResultDTO    返回类型
  * @author zhangzw
  * @date     2016年12月21日 下午3:40:35
   */
 	 @API( summary="查询套餐",
		  description="根据查询显示该商户底下免费礼包套餐 PackageController.java:queryListByParam  ",
		  parameters={
				  @Param(name="merchantId", description="商户id", type="long",required = true),
				  @Param(name="packageType", description="套餐类型", type="int",dataType= DataType.ARRAY,items="1,2,3",required = true),
		  })
    @RequestMapping(value = "",method = RequestMethod.GET)
 	@RequiresUser
    @ResponseBody
    public ResultDTO queryListByParam(HttpServletRequest request) throws Exception {

            Long merchantId = Long.valueOf( request.getParameter("merchantId"));
            Integer packageType = Integer.valueOf(request.getParameter("packageType"));
            List<MerchantPackage> list = packageWrapService.queryListByParam(merchantId, packageType);
            return getResult(0,list,null,null);

    }

	/**
	 * 逻辑删除套餐
	 * @param request
	 * @return
	 * @throws Exception
	 * @author zhangzw
	 * @date  2017-3-30 14:48:01
     */
	@API(summary="删除套餐",
			description="根据套餐id删除 应用场景 商户不再设置免费礼包",
			parameters={
					@Param(name="packageId", description="套餐id", type="long",required = true),
			})
	@RequiresRoles("admin")
    @RequestMapping(value = "",method = RequestMethod.DELETE)
    @ResponseBody
    public ResultDTO logicDelete(HttpServletRequest request) throws Exception {

            Long packageId = Long.valueOf( request.getParameter("packageId"));
            packageWrapService.logicDelete(packageId);
            return this.getResult(0, "删除套餐成功");

        
    }
}

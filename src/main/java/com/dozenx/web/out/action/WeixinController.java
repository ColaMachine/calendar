/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年9月11日
 * 文件说明: 
 */
package com.dozenx.web.out.action;

import com.dozenx.util.StringUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


@Controller
@Scope("prototype")
@RequestMapping("/share")
public class WeixinController {
	@RequestMapping(value = "/qq", method = RequestMethod.GET)
	public String  qq(HttpServletRequest request){
		String access_tocken ="I8DfWtWrTMlMqEnkS-0eobGLzkyPYxf4JV0LqqY1N0tVbQB8jKWLZUG-aOREFQ6DOxyUkLjki7gH9F2A_dhBUJS30-N-n1K_Tadfjq54Q7w";
		String ticket ="kgt8ON7yVITDhtdwci0qecxIYYsD2jPTf9FbI1SBbQXn0tvmcEnn9454E8lcSNRHgkp-G-NEdbZyULcFOaPOrQ";
		request.setAttribute("ticket", ticket);
		String timestamp = System.currentTimeMillis()/1000+"";//(new Date().getTime()+"");//.substring(0,10);
		
		String appid ="wxcfc589b7bf5662e3";
		String appsecret="e12106e8a1f70c82b9cb7ff05d3341c4";
		
		request.setAttribute("timestamp", timestamp);
		
		request.setAttribute("appid", appid);
		
		String nonceStr = StringUtil.getRandomAlphaString(16);
		
		request.setAttribute("nonceStr", nonceStr);
		
		System.out.println("nonceStr:"+nonceStr);
		System.out.println("timestamp:"+timestamp);
		String url ="http://60.191.109.203:8181/share/qq";
		 url ="http://192.168.0.100:8080/calendar/share/qq";
		String originStr= "jsapi_ticket="+ticket+"&noncestr="+nonceStr+"&timestamp="+timestamp+"&url="+url;
		System.out.println("originStr:"+originStr);  
		String signature ="";//SHA1.getDigestOfString(originStr.getBytes()).toLowerCase(); 
		System.out.println("signature:"+signature);
		request.setAttribute("signature", signature);
		return "/share.jsp";
	}
}
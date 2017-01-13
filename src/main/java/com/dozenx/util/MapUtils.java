package com.dozenx.util;

import com.dozenx.core.config.SysConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MapUtils {
	/*public static HashMap createResultMap(boolean flag ,String msg){
		HashMap returnMap =new HashMap();
		returnMap .put("result", flag);
		returnMap.put("msg",msg);
		return returnMap;
	}*/
	public static HashMap createResultMap(boolean flag ,String msg){
		HashMap returnMap =new HashMap();
		returnMap .put(SysConfig.AJAX_RESULT,flag?SysConfig.AJAX_SUCC:SysConfig.AJAX_FAIL);
		returnMap.put(SysConfig.AJAX_MSG,msg);
		return returnMap;
	}
	
	public static void push2Request(Map map,HttpServletRequest request){
		Iterator it = map.keySet().iterator();
		while(it.hasNext()){
//			java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
//			request.setAttribute((String)entry.getKey(),entry.getValue());
			String key = (String)it.next();
			Object value=map.get(key);
			request.setAttribute(key,value);
		}
	}
	public static String join(HashMap map ,String seprator){
		StringBuffer sb =new StringBuffer();

		Iterator it = map.keySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it.next();
			String key = entry.getKey();
			String value =entry.getValue()+"";
			sb.append(key+"="+value+"&");
		}
	return sb.toString();

	}
	public static HashMap request2Map(HttpServletRequest request) {
		HashMap map = new HashMap();
	     Enumeration paramNames = request.getParameterNames();
	    while (paramNames.hasMoreElements()) {
	      String paramName = (String) paramNames.nextElement();

	      String[] paramValues = request.getParameterValues(paramName);
	      if (paramValues.length == 1) {
	        String paramValue = paramValues[0];
	        if (paramValue.length() != 0) {
	         // System.out.println("参数：" + paramName + "=" + paramValue);
	          map.put(paramName, paramValue);
	        }
	      }
	    }
	    return map;
	}

	public static Float getFloatValue(Map map , String name){
		Float value =(Float)map.get(name);
		return value;
	}
	public static String getStringValue(Map map , String name){
		String value =(String)map.get(name);
		return value;
	}
	public static Integer getIntValue(Map map , String name){
		Integer value =(Integer)map.get(name);
		return value;
	}
	
}

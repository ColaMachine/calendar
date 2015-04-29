/**   
* @Title: ActivityAjax
* @Package cola.machine.。calendar。activity。ajax
* @Description: 活动service接口实现类
* @author: 371452875@qq.com
* @date: 2014年4月24日 23:12:32
* @version: V1.0   
*/
package cola.machine.calendar.activity.ajax;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.MapUtils;

import cola.machine.calendar.activity.bean.Activity;
import cola.machine.calendar.activity.service.ActivityService;
import cola.machine.util.StringUtil;

public class ActivityAjax {
	private ActivityService activityService;

	public void setActivityService(ActivityService activityService) {
		this.activityService = activityService;
	}
	
	/** 
	* @Title: getActivity 
	* @Description: 根据起始时间和结束时间获取活动
	* @param: 
	* @return HashMap    返回类型 
	* @author 371452875@qq.com
	* @date 2014年4月17日
	*/
	public HashMap getActivities(HashMap inputMap){
		HashMap returnMap =new HashMap();
		int startDate = MapUtils.getIntValue(inputMap, "STARTDATE");
		int endDate = MapUtils.getIntValue(inputMap, "ENDDATE");
		List activities =activityService.getActivities( startDate, endDate);
		returnMap.put("LIST", activities);
		return returnMap;
		
	}
	
	
	public HashMap saveActivity(HashMap inputMap){
		HashMap returnMap =new HashMap();
		int startDate = MapUtils.getIntValue(inputMap, "STARTDATE");
		int endDate = MapUtils.getIntValue(inputMap, "ENDDATE");
		Activity activity =new Activity();
		
		
		activity.setStartDate(startDate);
		activity.setEndDate(endDate);
		activity.setTitle(MapUtils.getString(inputMap, "TITLE"));
		
		
		if(StringUtil.isEmpty(MapUtils.getString(inputMap, "ID"))){
			activity.setActivityId(UUID.randomUUID().toString());
			activityService.saveActivity(activity);
		}else{
			activity.setActivityId(MapUtils.getString(inputMap, "ID"));
			activityService.updateActivity(activity);
		}
		
		
		returnMap.put("RESULT", true);
		return returnMap;
		
	}
}

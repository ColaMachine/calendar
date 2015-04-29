/**   
* @Title: ActivityServiceImpl
* @Package cola.machine。calendar。activity。service.impl
* @Description: 活动service接口实现类
* @author: 371452875@qq.com
* @date: 2014年2月24日 17:15:37
* @version: V1.0   
*/
package cola.machine.calendar.activity.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cola.machine.calendar.activity.bean.Activity;
import cola.machine.calendar.activity.dao.ActivityDao;
import cola.machine.calendar.activity.service.ActivityService;



public class ActivityServiceImpl implements ActivityService{
	/** 
	* @Fields activityDao :用spring代理的dao接口 具体由ibatis实现mapper
	*/ 
	private ActivityDao activityDao;
	/** 
	* @Title: setActivityDao 
	* @Description: spring代理
	* @param: 
	* @return void    返回类型 
	* @author zhangzw
	* @date 2014年4月9日
	* @throws 
	*/
	public void setActivityDao(ActivityDao activityDao) {
		this.activityDao = activityDao;
	}

	/* (non-Javadoc)
	 * @see cola.machine.calendar.activity.service.ActivityService#saveActivity(cola.machine.calendar.activity.bean.Activity)
	 */
	public boolean saveActivity(Activity activity) {
		if(activity!=null){
			activityDao.insertActivity(activity);
			return true;
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see cola.machine.calendar.activity.service.ActivityService#selecActivityByActivityId(java.lang.String)
	 */
	public Activity selecActivityByActivityId(String activityId) {
		return activityDao.selecActivityByActivityId(activityId);
		 
	}

	/* (non-Javadoc)
	 * @see cola.machine.calendar.activity.service.ActivityService#deleteActivityById(java.lang.String)
	 */
	public boolean deleteActivityById(String activityId) {
		activityDao.deleteActivity(activityId);
		return true;
	}

	public List<HashMap> getActivities(int startDate, int endDate) {
		Map map =new HashMap();
		map.put("STARTDATE",startDate);
		map.put("ENDDATE",endDate);
		return activityDao.selectActivityBetween2Date(map);
	}
	
	public List<HashMap> getActivities(long startDate, long endDate) {
		return getActivities((int)(startDate/60000),(int)(endDate/60000));
	}

	public boolean updateActivity(Activity activity) {
		 activityDao.updateActivity(activity);
		 return true;
	}

}

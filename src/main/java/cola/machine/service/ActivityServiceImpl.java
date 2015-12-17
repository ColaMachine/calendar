/**   
* @Title: ActivityServiceImpl
* @Package cola.machine。calendar。activity。service.impl
* @Description: 活动service接口实现类
* @author: 371452875@qq.com
* @date: 2014年2月24日 17:15:37
* @version: V1.0   
*/
package cola.machine.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cola.machine.bean.Activity;
import cola.machine.dao.ActivityDao;
import cola.machine.util.UUIDUtil;


@Transactional
@Service("activityService")
public class ActivityServiceImpl implements ActivityService{
	/** 
	* @Fields activityDao :用spring代理的dao接口 具体由ibatis实现mapper
	*/ 
	@Resource(name = "activityDao")
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
	 * 保存日历活动 arrangements
	 * @see cola.machine.calendar.activity.service.ActivityService#saveActivity(cola.machine.calendar.activity.bean.Activity)
	 */
	public boolean saveActivity(Activity activity) {
		
		//
		if(activity!=null){
			if(StringUtils.isNotBlank(activity.getActivityId())){
				Activity activityFromDb =activityDao.getActivityById(activity.getActivityId());
				if(activityFromDb!=null){
					activityDao.updateActivity(activity);
					return true;
				}
			}
			activity.setActivityId(UUIDUtil.getUUID());
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

	public List<HashMap> getActivities(long startDate, long endDate,String userid) {
		Map map =new HashMap();
		map.put("STARTTIME",startDate);//23885280
		map.put("ENDTIME",endDate);//23893920
		map.put("USERID",userid);//23893920
		return activityDao.selectActivityBetween2Date(map);
	}
	
/*	public List<HashMap> getActivities(long startDate, long endDate) {
		return getActivities((startDate/60000),(endDate/60000));
	}*/

	public boolean updateActivity(Activity activity) {
		 activityDao.updateActivity(activity);
		 return true;
	}

	/* (non-Javadoc)
	 * @see cola.machine.calendar.activity.service.ActivityService#saveActivitys(java.util.List)
	 * @author colamachine
	 */
	@Override
	public void saveActivitys(List<Activity> list) {
		for(Activity activity:list){
			if(activity.isdel){
				this.deleteActivityById(activity.getActivityId());
			}else
			this.saveActivity(activity);
		}
		
	}

}

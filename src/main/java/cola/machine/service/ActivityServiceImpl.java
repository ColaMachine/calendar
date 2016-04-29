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
	 * @see cola.machine.calendar.activity.service.ActivityService#saveActivity(cola.machine.calendar.activity.bean.Activity.cfg)
	 */
	public boolean saveActivity(Activity activity) {
		
		//
		if(activity!=null){
			if(activity.getId()!=null && activity.getId()!=0){
				Activity activityFromDb =activityDao.selectByPrimaryKey(activity.getId());
				if(activityFromDb!=null){
					activityDao.updateByPrimaryKey(activity);
					return true;
				}
			}
			//activity.setActivityId(UUIDUtil.getUUID());
			activityDao.insert(activity);
			return true;
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see cola.machine.calendar.activity.service.ActivityService#selecActivityByActivityId(java.lang.String)
	 */
	public Activity selecActivityByActivityId(Long activityId) {
		return activityDao.selectByPrimaryKey(activityId);
		 
	}

	/* (non-Javadoc)
	 * @see cola.machine.calendar.activity.service.ActivityService#deleteActivityById(java.lang.String)
	 */
	public boolean deleteActivityById(Long activityId) {
		activityDao.deleteByPrimaryKey(activityId);
		return true;
	}

	public List<HashMap> getActivities(long startDate, long endDate,Long userid) {
		Map map =new HashMap();
		map.put("startTime",startDate);//23885280
		map.put("endTime",endDate);//23893920
		map.put("userId",userid);//23893920
		return activityDao.selectActivityBetween2Date(map);
	}
	
/*	public List<HashMap> getActivities(long startDate, long endDate) {
		return getActivities((startDate/60000),(endDate/60000));
	}*/

	public boolean updateActivity(Activity activity) {
		 activityDao.updateByPrimaryKey(activity);
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
				this.deleteActivityById(activity.getId());
			}else
			this.saveActivity(activity);
		}
		
	}

}

/**   
* @Title: ActivityService  
* @Package cola.machine。calendar。activity。service
* @Description: 活动service接口
* @author: 371452875@qq.com
* @date: 2014年2月24日 17:15:37
* @version: V1.0   
*/
package cola.machine.service;

import java.util.HashMap;
import java.util.List;

import cola.machine.bean.Activity;





public interface ActivityService {
	
	
	/** 
	* @Title: saveActivity 
	* @Description: 保存活动
	* @param @param activity
	* @return boolean    返回类型 
	* @throws 
	*/
	public boolean saveActivity(Activity activity);
	
	/** 
	* @Title: selecActivityByActivityId 
	* @Description: 根据id查询活动
	* @param @param id
	* @return Activity    返回类型 
	* @throws 
	*/
	public Activity  selecActivityByActivityId(String id);
	
	/** 
	* @Title: deleteActivityById 
	* @Description: delete the bean by it's primary key
	* @param  id
	* @return boolean    
	* @author zhangzw
	* @throws 
	*/
	public boolean  deleteActivityById(String id);
	
	/** 
	* @Title: getActivities 
	* @Description:根据开始时间结束时间获得区间内的活动
	* @param: 
	* @return List<HashMap>    返回类型 
	* @author zhangzw
	* @date 2014年4月17日
	* @throws 
	*/
	public List<HashMap> getActivities(long startDate,long endDate,String userid);

	/** 
	* @Title: updagteActivitys 
	* @Description: 保存活动
	* @param @param activity
	* @return boolean    返回类型 
	* @throws 
	*/
	public boolean updateActivity(Activity activity);

	/**
	 * 说明:
	 * @param list
	 * @return void
	 * @author dozen.zhang
	 * @date 2015年7月2日下午2:31:20
	 */
	public void saveActivitys(List<Activity> list);
}

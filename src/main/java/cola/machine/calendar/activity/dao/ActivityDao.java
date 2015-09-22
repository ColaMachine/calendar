/**   
* @Title: ActivityDao  
* @Package cola.machine.calendar.activity.dao
* @Description: ActivityDao接口
* @author: 371452875@qq.com
* @date: 2014年2月24日 19:03:34
* @version: V1.0   
*/
package cola.machine.calendar.activity.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cola.machine.calendar.activity.bean.Activity;



public interface ActivityDao {

	
	
	/** 
	* @Title: selecActivityByActivityId 
	* @Description: 根据id查询活动
	* @param String activityId
	* @return Activity    返回类型 
	* @author 371452875@qq.com
	* @throws 
	*/
	public Activity selecActivityByActivityId(String activityId);

	/** 
	* @Title: insertActivity 
	* @Description: 插入一条新的活动 
	* @param @param activity    设定文件 
	* @return void    返回类型 
	* @author 371452875@qq.com
	* @throws 
	*/
	public void insertActivity(Activity activity);
	
	/** 
	* @Title: updateActivity 
	* @Description: 更新活动
	* @param @param activity    设定文件 
	* @return void    返回类型 
	* @author 371452875@qq.com
	* @throws 
	*/
	public void updateActivity(Activity activity);
	
	
	/**
	 * 说明:根据id得到activity
	 * @param activityId
	 * @return
	 * @author dozen.zhang
	 * @date 2015年5月28日下午5:29:26
	 */
	public Activity getActivityById(String activityId);
	/** 
	* @Title: deleteActivity 
	* @Description: 删除活动
	* @param @param activityId    设定文件 
	* @return void    返回类型 
	* @author 371452875@qq.com
	* @throws 
	*/
	public void deleteActivity(String activityId);

	/** 
	* @Title: selectActivityBetween2Date 
	* @Description: 根据两个日期 开始时间和结束时间查询到活动列表
	* @param String startDate
	* @param String endDate
	* @return List<Activity>    返回类型 
	* @author 371452875@qq.com
	* @throws 
	*/
	//public List<HashMap> selectActivityBetween2Date(int startDate,int endDate);
	
	public List<HashMap> selectActivityBetween2Date(Map map);
	

}

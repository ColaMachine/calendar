package cola.machine.calendar.activity;

import com.dozenx.util.DateTime;
import com.dozenx.util.UUIDUtil;
import com.dozenx.web.module.calendar.bean.Activity;
import com.dozenx.web.module.calendar.service.ActivityService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;


public class ActivityTest{
	@Autowired
	ActivityService activityService;

	@Before
	public void init() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"\\config\\xml\\applicationContext.xml");
		activityService = (ActivityService) context.getBean("activityService");
	}
	//@Test
    public void updateActivity() {
		DateTime startDate =DateTime.parseDateTime("2014-05-29T01:30:00");
		DateTime endDate =DateTime.parseDateTime("2014-07-19T14:30:00");
		List list = activityService.getActivities(startDate.getValue(),endDate.getValue(),123l);
		if(list.size()>0){
			Activity activity =(Activity)list.get(0);
		
			activityService.updateActivity(activity);
		
		}
		
		System.out.println("list的总数是:"+list.size());
    }
  //  @Test
	public void saveActivity() {
		Activity activity=new Activity();
		activity.setAddress("上海浦东机场陆边的12号弄3楼301");
		activity.setBusyLevel(activity.BUSYLEVEL_IDLE);
		activity.setDescription("描述信息");
		DateTime startDate =DateTime.parseDateTime("2014-04-24T12:30:00");
		DateTime endDate =DateTime.parseDateTime("2014-04-24T12:31:00");
		activity.setStartTime((int)(startDate.getValue()/(60*1000)));//);
		activity.setEndTime((int)(endDate.getValue()/(60*1000)));//"2014-02-24 21:40:00");
		activity.setPrivacy((byte)1);
		activity.setTitle("标题");
		activity.setType((byte)1);
		activity.setUserId(123l);
		boolean flag=activityService.saveActivity(activity);
		assert flag:"save activity failed!";
	}
   // @Test
    public void getActivities() {
		DateTime startDate =DateTime.parseDateTime("2014-04-19T01:30:00");
		DateTime endDate =DateTime.parseDateTime("2014-05-19T14:30:00");
		List list = activityService.getActivities(startDate.getValue(),endDate.getValue(),123l);
		System.out.println("list的总数是:"+list.size());
    }
    
    
	public Activity selecActivityByActivityId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteActivityById(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static void main(String args[]){
		/*Date date =new Date();
		date.setTime(1405780200000L);
		System.out.println(date.getYear());
		System.out.println(date.getMonth()+1);
		System.out.println(date.getDate());
	System.out.println(date.toLocaleString());*/
		
	}
	

}

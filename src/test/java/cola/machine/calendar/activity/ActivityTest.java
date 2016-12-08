package cola.machine.calendar.activity;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;




public class ActivityTest{
//	@Autowired
//	ActivityService activityService;
//
//	@Before
//	public void init() {
//		ApplicationContext context = new ClassPathXmlApplicationContext(
//				"\\config\\xml\\applicationContext.xml");
//		activityService = (ActivityService) context.getBean("activityService");
//	}
/*	@Test
    public void updateActivity() {
		DateTime startDate =DateTime.parseDateTime("2014-05-29T01:30:00");
		DateTime endDate =DateTime.parseDateTime("2014-07-19T14:30:00");
		List list = activityService.getActivities(startDate.getValue(),endDate.getValue());
		if(list.size()>0){
			Activity.cfg activity =(Activity.cfg)list.get(0);
		
			activityService.updateActivity(activity);
		
		}
		
		System.out.println("list的总数是:"+list.size());
    }
    @Test
	public void saveActivity() {
		Activity.cfg activity=new Activity.cfg();
		activity.setAddress("上海浦东机场陆边的12号弄3楼301");
		activity.setBusyLevel(activity.BUSYLEVEL_IDLE);
		activity.setDescription("描述信息");
		DateTime startDate =DateTime.parseDateTime("2014-04-24T12:30:00");
		DateTime endDate =DateTime.parseDateTime("2014-04-24T12:31:00");
		activity.setStartDate((int)(startDate.getValue()/(60*1000)));//);
		activity.setEndDate((int)(endDate.getValue()/(60*1000)));//"2014-02-24 21:40:00");
		activity.setPrivacy("1");
		activity.setTitle("标题");
		activity.setType("1");
		activity.setUserId("userid");
		activity.setActivityId(UUID.randomUUID().toString());
		boolean flag=activityService.saveActivity(activity);
		assert flag:"save activity failed!";
	}
    @Test
    public void getActivities() {
		DateTime startDate =DateTime.parseDateTime("2014-04-19T01:30:00");
		DateTime endDate =DateTime.parseDateTime("2014-05-19T14:30:00");
		List list = activityService.getActivities(startDate.getValue(),endDate.getValue());
		System.out.println("list的总数是:"+list.size());
    }
    
    
	public Activity.cfg selecActivityByActivityId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteActivityById(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static void main(String args[]){
		Date date =new Date();
		date.setTime(1405780200000L);
		System.out.println(date.getYear());
		System.out.println(date.getMonth()+1);
		System.out.println(date.getDate());
	System.out.println(date.toLocaleString());
		
	}
	
*/
}

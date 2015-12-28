import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.tools.ant.util.DateUtils;

import cola.machine.util.DateUtil;



public class test {
	/**
	 * 说明:
	 * @param args
	 * @return void
	 * @author dozen.zhang
	 * @date 2015年12月15日下午11:15:17
	 */
	public static void main(String[] args)
	{
	    Calendar cal = Calendar.getInstance();
	    TimeZone timeZone = cal.getTimeZone();
	    System.out.println(timeZone.getID());
	    System.out.println(timeZone.getDisplayName());
	    Date now = new Date();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式


	    String hehe = dateFormat.format( now );
	    System.out.println(hehe); 
		System.out.println(new Date().getTime());
		Long vl=24167130*60000l;//new Date().getTime();// date.getTime();
		
		System.out.println("123"+DateUtil.formatToString(new Date(vl), "yyyy-MM-dd HH:mm:ss"));
		System.out.println(vl /60000/24/60);
		
	}
}

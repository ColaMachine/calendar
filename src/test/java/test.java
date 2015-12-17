import java.util.Date;

import org.apache.tools.ant.util.DateUtils;



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
		
		Date date = new Date();
		Long vl=24167250l;// date.getTime();
		System.out.println("124333"+DateUtils.format(vl*6000, "yyyy-MM-dd HH:mm:ss"));
		System.out.println((vl %(24*60))/60);
		
	}
}

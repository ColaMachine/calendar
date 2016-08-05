import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import cola.machine.bean.AppExceptionLog;
import cola.machine.bean.AppExceptionLogExample;
import cola.machine.service.LogService;

import core.page.Page;

/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月8日
 * 文件说明: 
 */

public class LogTest {
	LogService logService;


	public void init() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"\\config\\xml\\applicationContext.xml");
		logService = (LogService) context.getBean("logService");
	}
	public void listExceptionLog(){
		Page page =new Page();
		page.setPageSize(1);
		page.setCurPage(0);
		AppExceptionLogExample example =new AppExceptionLogExample();
		example.setPage(page);
		List<AppExceptionLog> log =logService.listExceptionLog(example);
		Assert.isTrue(log.size()>=0);

	}
}

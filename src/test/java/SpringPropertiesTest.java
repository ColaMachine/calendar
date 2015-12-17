import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月11日
 * 文件说明: 
 */

public class SpringPropertiesTest {
	public void test(){
		
	
		         XmlBeanFactory factory = new XmlBeanFactory(new FileSystemResource("conf.properties"));
		         // 如果要在BeanFactory中使用，bean factory post-processor必须手动运行:
		         PropertyPlaceholderConfigurer cfg = new PropertyPlaceholderConfigurer();
		         cfg.setLocation(new FileSystemResource("src/com/zsw/config/jdbc.properties"));
		         cfg.postProcessBeanFactory(factory);
		         DriverManagerDataSource dataSource = (DriverManagerDataSource) factory.getBean("dataSource");
//		         System.out.println(dataSource.getDriverClassName());
		         
		         
		         System.out.println(dataSource.getUsername());
		         // 注意，ApplicationContext能够自动辨认和应用在其上部署的实现了BeanFactoryPostProcessor的bean。这就意味着，当使用ApplicationContext的时候应用PropertyPlaceholderConfigurer会非常的方便。由于这个原因，建议想要使用这个或者其他bean
		         // factory postprocessor的用户使用ApplicationContext代替BeanFactroy。
		         ApplicationContext context = new ClassPathXmlApplicationContext("com/zsw/config/conf.xml");
		         DriverManagerDataSource dataSource2 = (DriverManagerDataSource) context.getBean("dataSource");
		         System.out.println(dataSource2.getUsername());
	}
}

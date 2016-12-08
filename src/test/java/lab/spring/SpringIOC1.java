package lab.spring;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by dozen.zhang on 2016/3/9.
 */
public class SpringIOC1{

public static void main(String args[]) {
    ApplicationContext context = new ClassPathXmlApplicationContext(
            "\\config\\xml\\applicationContext.xml");
    Object object = context.getBean("helloWorld");
    System.out.println(object);
}
}

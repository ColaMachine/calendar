package lab.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dozen.zhang on 2016/3/10.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})//指定该注解使用的用处：用在class上和用在方法体上。
public @interface HelloAnnotation {
    //返回值是String类型

    String color() default "蓝色";//方法，但是相当于注解的属性，即：当成属性赋值，当成方法调用。
    //默认value
    String value();

    String author() default "默认给定了属性";
    //返回值是数组对象
    int[] arrayAttr() default {1};

}

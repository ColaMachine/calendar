package com.dozenx.test.rpc.anotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by dozen.zhang on 2017/1/13.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component // 表明可被 Spring 扫描
public @interface RpcService {
    String value() default "";
    Class<?> clazz();
}
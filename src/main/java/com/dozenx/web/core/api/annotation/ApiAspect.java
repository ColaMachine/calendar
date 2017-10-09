package com.dozenx.web.core.api.annotation;

import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
import com.dozenx.util.ResultUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.util.ValidateUtil;
import com.dozenx.web.core.rules.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by dozen.zhang on 2017/3/20.
 */
@Component
@Aspect
public class ApiAspect {


    /*@Pointcut("execution(public * com.itsoft.action..*.*(..))")
    public void recordLog(){}*/
        /**
         * 定义缓存逻辑
         */
        @Around("execution(* *.*(..)) && @annotation(com.cpj.swagger.annotation.API)")
        public Object cache(ProceedingJoinPoint pjp ) throws Throwable {
            Object result=null;
            Method method=getMethod(pjp);
            API api=method.getAnnotation(com.cpj.swagger.annotation.API.class);
            RequestMapping methodUrl = method.getAnnotation(RequestMapping.class);
            Param[] params = api.parameters();
           Object[] objectAry =  pjp.getArgs();
            HttpServletRequest request =null;
            for ( int i=0;i<objectAry.length;i++){
                if(objectAry[i] instanceof HttpServletRequest){
                    request = (HttpServletRequest)objectAry[i];

                }
            }
            if(request==null){
                throw new Exception( "request 不能为空");
            }


            ValidateUtil vu = new ValidateUtil();
            String validStr="";

            if(StringUtil.isNotBlank(validStr)) {
                return ResultUtil.getResult(302,validStr);
            }

            int startIndex=  methodUrl.value()[0].indexOf("{");
            int endIndex=  methodUrl.value()[0].indexOf("}");
            String restfulParam=null;
            if(startIndex>=0){
                restfulParam = methodUrl.value()[0].substring(startIndex+1,endIndex-1);
            }

            for(int i=0;i<params.length;i++){
                String name =params[i].name();
                if(restfulParam!=null && restfulParam.equals(name)){
                    continue;
                }

                String value =request.getParameter(name);
                DataType type = params[i].dataType();
                List<Rule> rules =new ArrayList<Rule>();



                if(type == DataType.INTEGER){
                    rules.add(new Numeric());
                }else if(type == DataType.LONG){
                    rules.add(new Numeric());
                }else if(type == DataType.FLOAT){
                    rules.add(new Numeric());
                }else if(type == DataType.DOUBLE){
                    rules.add(new Numeric());
                }else if(type == DataType.STRING){
                    //rules.add(new Numeric());
                }else if(type == DataType.ARRAY){
                    String items = params[i].items();
                    rules.add(new CheckBox(items.split(",")));
                }else if(type == DataType.IP){
                    String items = params[i].items();
                    rules.add(new IpRule());
                }else if(type == DataType.PORT){
                    rules.add(new Numeric());
                }else if(type == DataType.MAC_SHORT){
                    rules.add(new MacShortRule());
                }else if(type == DataType.PHONE){
                    rules.add(new PhoneRule());
                }
                if(params[i].required()){
                    rules.add(new Required());
                }
                Rule[] ruleAry = new Rule[rules.size()];
                vu.add(name, value, params[i].description(),rules.toArray(ruleAry));
            }
            validStr= vu.validateString();
            if(StringUtil.isNotBlank(validStr)) {
                return ResultUtil.getResult(302,validStr);
            }
            return pjp.proceed();

        }

    /**
     *  获取被拦截方法对象
     *
     *  MethodSignature.getMethod() 获取的是顶层接口或者父类的方法对象
     *    而缓存的注解在实现类的方法上
     *  所以应该使用反射获取当前对象的方法对象
     */
    public Method getMethod(ProceedingJoinPoint pjp){
        //获取参数的类型
        Object [] args=pjp.getArgs();
        Class [] argTypes=new Class[pjp.getArgs().length];
        for(int i=0;i<args.length;i++){
            String name = args[i].getClass().getName();
            //System.out.println(args[i].getClass().getName());
            //System.out.println(args[i].getClass().toString());

            argTypes[i]=args[i].getClass();
            if(args[i].getClass().getName().equals("org.apache.catalina.connector.RequestFacade")){
                argTypes[i]=HttpServletRequest.class;
            }
        }
        Method method=null;
        try {

            Class[] parameterTypes = ((MethodSignature)pjp.getSignature()).getMethod().getParameterTypes();
            method=pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(),parameterTypes);
         //   method=pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(),argTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return method;

    }

}

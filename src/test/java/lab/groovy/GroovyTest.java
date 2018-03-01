package lab.groovy;

import groovy.lang.Binding;
import java.util.Date;
import groovy.lang.GroovyShell;

/**
 * Created by dozen.zhang on 2017/8/28.
 */
public class GroovyTest {

    public static void evalScriptText() throws Exception{

        //groovy.lang.Binding
        Binding binding = new Binding();
        GroovyShell shell = new GroovyShell(binding);

        binding.setVariable("name", "zhangsan");
        shell.evaluate("println 'Hello World! I am ' + name;");
        //在script中,声明变量,不能使用def,否则scrope不一致.
        shell.evaluate("date = new Date();");
        Date date = (Date)binding.getVariable("date");
        System.out.println("Date:" + date.getTime());
        //以返回值的方式,获取script内部变量值,或者执行结果
        //一个shell实例中,所有变量值,将会在此"session"中传递下去."date"可以在此后的script中获取
        Long time = (Long)shell.evaluate("def time = date.getTime(); return time;");
        System.out.println("Time:" + time);
        binding.setVariable("list", new String[]{"A","B","C"});
        //invoke method
        String joinString = (String)shell.evaluate("def call(){return list.join(' - ')};call();");
        System.out.println("Array join:" + joinString);
        shell = null;
        binding = null;
    }

    public static void main(String[] args){
        try {
            GroovyTest. evalScriptText();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package lab.annotation;


/**
 * Created by dozen.zhang on 2016/3/10.
 */
/***
 * 注解类：
 * 1、相当于一种标记,加上注解就等于为程序打上了某种标记,没加,则等于没加某种标记,
 * 2、以后javac编译器、开发工具或其他应用程序可以通过反射来了解你的类,以及各种元素上有无何种标记，
 *   看你有什么标记，就去执行相应的命令和干相应的事。
 * 3、标记用处地方：
 *   加在包、类、字段、方法、方法的参数、局部变量
 * @author huawei
 *
 */
//自定义注解
@HelloAnnotation(color="红色",
        value="如果只有value属性！可以不写属性名和等于号，直接写值即可！",
        arrayAttr={1,2,3}
)
public class AnnotationTest {
    @SuppressWarnings("deprecation")//阻止警告
    @HelloAnnotation("当为value属性时，可以省掉属性名和等于号。")

    public static void main(String[] args) throws Exception {
        System.runFinalizersOnExit(true);
        if (AnnotationTest.class.isAnnotationPresent(HelloAnnotation.class)) {
            HelloAnnotation helloAnnotation =
                    (HelloAnnotation) AnnotationTest.class.getAnnotation(HelloAnnotation.class);
            System.out.println("color(): " + helloAnnotation.color());
            System.out.println("value(): " + helloAnnotation.value());
            System.out.println("author(): " + helloAnnotation.author());
            System.out.println("arrayAttr(): " + helloAnnotation.arrayAttr().length);
        }
    }

    @Deprecated//自定义：备注过时的方法信息
    public static void sayHello() {
        System.out.println("hello,world");
    }
}
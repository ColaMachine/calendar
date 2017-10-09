import com.dozenx.core.config.Config;
import com.dozenx.util.DateUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;


public class test {
	/**
	 * 说明:
	 * @param args
	 * @return void
	 * @author dozen.zhang
	 * @date 2015年12月15日下午11:15:17
	 */

    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }
    public static void main1(String args[]) throws Exception {
        setFinalStatic(Boolean.class.getField("FALSE"), true);
        System.out.format("Everything is %s", false); // "Everything is true"
    }

    public static void main(String[] args) {
        try {

            System.out.println(URLEncoder.encode("http://192.168.3.139:8080/devsrv/device/upgrade/alone/tasks?params={%22pageNo%22:$221%22}"));
            float total = 380000;
            System.out.println(DateUtil.daysBetween("2015-08-04","2016-04-13"));
            System.out.println(total*0.085/365);
            total+= (DateUtil.daysBetween("2015-08-04","2016-04-13")*total*0.085/365);
/*for(int i=0;i<8;i++){
   total+=total*0.08;
}*/
System.out.println(total);
      System.out.println("123");
        Class x = Config.class;
        Field[] fields = x.getDeclaredFields();
            setFinalStatic(Config.class.getField("a"), 1);
        for(int i=0;i<fields.length;i++){
            fields[i].setAccessible(true);


            Field modifiersField = null;
                modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);

                modifiersField.setInt(fields[i], fields[i].getModifiers() & ~Modifier.FINAL);


            if((fields[i].getModifiers() & 8) == 8){

                    System.out.println(fields[i].getName());
                    System.out.println(fields[i].get(null));
                    fields[i].set(null,1);

            }
        }

        } catch (Exception e) {
            e.printStackTrace();
        }

      //  System.out.println(Config.a);

        System.out.println("所有任务执行完毕");
    }
}
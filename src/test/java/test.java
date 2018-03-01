import com.dozenx.core.config.Config;
import com.dozenx.util.DateUtil;
import com.dozenx.util.HttpRequestUtil;
import com.dozenx.util.MD5Util;
import com.dozenx.util.UUIDUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;


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
//        setFinalStatic(Boolean.class.getField("FALSE"), true);
//        System.out.format("Everything is %s", false); // "Everything is true"
//        List<Number> ary = (ArrayList<Number>)bodyParam.get("roleIds");//bodyoaran 只不过的参数是 arryList<Double>格式的
//        Long[] roleIdAry = new Long[ary.size()];
//        for(int i=0;i<ary.size();i++){
//            Number number = ary.get(i);
//            roleIdAry[i] = ary.get(i).longValue();
//        }

        Integer obj = new Integer(1);
        Long a  =obj.longValue();



        String url ="https://wx.hz-hospital.com/weiwenvote/index?voteusername=cf1be1c0-d46a-4d3f-9bf4-4668dbde24e9";
        String otherurl ="https://wx.hz-hospital.com/";
        String id ="21";
        String voteusername ="";

        for(int i=0;i<1000;i++){
            voteusername=UUID.randomUUID().toString();
            String suffix = "https://wx.hz-hospital.com/weiwenvote/voteup" + "?id=" + id + "&voteusername=" + voteusername;
           HttpRequestUtil.sendGet  (suffix);
            try {
                Thread.sleep((int) (Math.random() * 13000));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        System.out.println("");
        System.out.println(MD5Util.getStringMD5String("hello"));
        System.out.println(UUID.randomUUID().toString());
    }

    public static void main2(String[] args) {
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

    public static void main(String args[]) throws Exception {
        System.out.println(MD5Util.getStringMD5String("123456"));
        System.out.println(URLDecoder.decode("http://hainan.51iwifi.com:84/hotmapsrv/authed/getAuthedDetail?access_token=AT_NP_BIZ_E8AD5F6E7A024357AC1CAA849B892C23&params=%7B%22start%22%3A%222018-02-24%22%2C%22messageFrom%22%3A%2206%22%2C%22end%22%3A%222018-02-24%22%2C%22pageNo%22%3A1%2C%22pageSize%22%3A20%7D"));
        long timestamp= System.currentTimeMillis();
        String sign= MD5Util.getStringMD5String("18368729738"+"|"+timestamp+"|awifi").toLowerCase();
        System.out.print(timestamp+"\n");
        System.out.print("signsignsignsign+++++++"+sign+"\n");
        Map<String, String> paramsMap=new HashMap<>();
        paramsMap.put("mac","00:0F:E8:52:88:42");
        paramsMap.put("account","18368729738");
        paramsMap.put("shopPhone","18368729738");
        paramsMap.put("fullName","杭州Awifi测试");
        paramsMap.put("pIndustryName","餐饮");
        paramsMap.put("industryName","中餐");
        paramsMap.put("povince","江苏省");
        paramsMap.put("city","南京市");
        paramsMap.put("district","玄武区");
        paramsMap.put("address","江苏南京玄武区118号");
        paramsMap.put("devid","5684569");
        paramsMap.put("timestamp",timestamp+"");
        paramsMap.put("sign",sign);


      HttpRequestUtil.sendPost("http://127.0.0.1:8080/loginPost.json",paramsMap);
       // System.out.print(result);
        /*String a ="mac:00:0F:E9:92:30:42\n" +
                "account:18368729738\n" +
                "shopPhone:18368729738\n" +
                "fullName:杭州Awifi测试\n" +
                "pIndustryName:餐饮\n" +
                "povince:江苏省\n" +
                "city:南京市\n" +
                "district:玄武区\n" +
                "address:江苏南京玄武区118号\n" +
                "devid:5684569\n" +
                "timestamp:"+timestamp+"\n" +
                "sign:"+sign+"\n" +
                "industryName:中餐";
        //推送过去

        HttpResult result= HttpUtil.postBody("http://portal.awifi.cn/shopunion/awifi/bss/openRegisterAp.api", a);
        return this.successMsg(result);*/

    }


}
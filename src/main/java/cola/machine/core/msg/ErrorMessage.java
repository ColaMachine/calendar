package cola.machine.core.msg;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import cola.machine.util.FilePath;

/**
 * 系统错误信息实现类
 * @author liuhualuo
 */
public class ErrorMessage {
    private static Properties msgProp;

    /**
     * 载入配置文件
     */
    private static void loadMsgProp(){
        if(msgProp != null){
            return ;
        }
        try {
            msgProp = new Properties();
            msgProp.load(new FileInputStream(FilePath.getAbsolutePathWithClass() + "/properties/message.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取错误消息
     * @param key
     * @return
     */
    public static String getErrorMsg(String key){
        loadMsgProp();
        Object obj=msgProp.get(key + ".msg");
        if(obj!=null){
        	return obj.toString();
        }else{
        	return null;
        }
    }

    /**
     * 获取错误码
     * @param key
     * @return
     */
    public static String getErrorMsgCode(String key){
        loadMsgProp();
        Object obj=msgProp.get(key + ".code");
        if(obj!=null){
        	return obj.toString();
        }else{
        	return null;
        }
    }

}

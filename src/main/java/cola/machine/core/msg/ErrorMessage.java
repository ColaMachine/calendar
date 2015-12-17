package cola.machine.core.msg;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import cola.machine.util.FilePath;

/**
 * 系统错误信息实现类
 * @author 
 */
public class ErrorMessage {
	/** 系统错误 **/
	public static final int SYSTEM=1;
	/** 应用级别错误(前端参数错误) **/
	public static final int PARAM=2;
	/** 业务级别错误(service自身处理出错) **/
	public static final int SERVICE=3;
	/** 依赖级别错误(service调用第三方服务出错) **/
	public static final int THIRD=4;
	/** 交互级别错误(正常业务逻辑,非错误,需要通知用户,如角色名重复) **/
	public static final int NOTIFI=5;
	/** 未知异常 **/
	public static final int EXCEPTION=99; 
	
	
	
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

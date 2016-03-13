package cola.machine.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mchange.lang.StringUtils;

public class StringUtil {
	public static boolean  isEmpty(String str){
		if(str ==null ||  str.length()==0){
			return true;
		}
		return false;
	}
	public static boolean  isBlank(String str){
		if(str ==null ||  str.length()==0){
			return true;
		}
		return false;
	}
	public static boolean  isNotEmpty(String str){
		
		return !isEmpty(str);
	}
	
	public static boolean checkEmailValid(String email){
		String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.find();
	}
	
	public static boolean checkNumeric(String str){
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
	
	public static boolean checkDateStr(String str,String format){
	    format=format.replaceAll("[yMdhHms]", "\\\\d");
	   return str.matches(format);
	}

	public static boolean checkUserNameValid(String username){
		String regex = "/^[0-9A-Za-z]*[a-zA-Z]+[0-9A-Za-z]*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(username);
		return matcher.find();
	}
	public static boolean checkFloat(String value,int integer,int fraction){
	    String regex= String.format("^[+|-]?\\d{1,%d}(\\.\\d{1,%d})?$", integer,fraction);
	    if(fraction==0){
	        regex= String.format("^[+|-]?\\d{1,%d}$", integer);
	    }
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		return matcher.find();
	}
	public static String getAbc(String abc){
	    return abc.substring(0, 1).toUpperCase()+abc.substring(1);
	}
	public static String getabc(String abc){
        return abc.substring(0, 1).toLowerCase()+abc.substring(1);
    }
	
	public  static String join(String join,Object [] strAry){
		StringBuffer sb=new StringBuffer();
        for(int i=0;i<strAry.length;i++){
             if(i==(strAry.length-1)){
                 sb.append(strAry[i]);
             }else{
                 sb.append(strAry[i]).append(join);
             }
        }
        return new String(sb);
	}
	
	public static String getContentBetween(String content,String a,String b){
		int index = content.indexOf(a)+a.length();
		int last = content.lastIndexOf(b);
		return content.substring(index, last);
	}
    public static boolean isPhone(String value) {
        String regex = "^[1][3578][0-9]{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }
    public static boolean isEmail(String value) {
        String regex = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }
    public static boolean splitStrContains(String longStr,String bean){
        if(longStr.indexOf(","+bean+",")>=0 || longStr.startsWith(bean+",") || longStr.endsWith(","+bean) || longStr.equals(bean)){
            return true;
        }
        return false;
    }
    public static void main(String[] args) {
        System.out.println(StringUtil.isPhone("14958173965"));;
    }

	public static String randDigitString = "0123456789";//随机产生的字符串
	public static String randAlphaString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";//随机产生的字符串
	public static String getRandomString(int len){
		String code ="";
		for(int i=0;i<len;i++){
			code+=randAlphaString.charAt((int)(Math.random()*10));
		}
		return code;
	}
	public static String getRandomAlphaString(int len){
		String code ="";
		for(int i=0;i<len;i++){
			code+=randAlphaString.charAt((int)(Math.random()*randAlphaString.length()));
		}
		return code;
	}
	public static String getRandomDigitString(int len){
		String code ="";
		for(int i=0;i<len;i++){
			code+=randDigitString.charAt((int)(Math.random()*randDigitString.length()));
		}
		return code;
	}
	public static boolean checkAlphaNumeric(String str){
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isAlphabetic(str.charAt(i))==false&&Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
	   public static boolean checkAlpha(String str){
	        if (str == null) {
	            return false;
	        }
	        int sz = str.length();
	        for (int i = 0; i < sz; i++) {
	            if (Character.isAlphabetic(str.charAt(i))==false) {
	                return false;
	            }
	        }
	        return true;
	    }
	public static String getYMDStr(String type){
        String ymd ="";
        if(type.startsWith("date")){
            ymd="yyyy-MM-dd";
        }else{
            ymd="yyyy-MM-dd HH:mm:ss";
        }
return ymd;
    }
}

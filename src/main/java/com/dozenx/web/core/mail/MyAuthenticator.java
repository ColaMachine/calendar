package com.dozenx.web.core.mail;


import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;

public class MyAuthenticator extends Authenticator{   
    String userName=null;   
    String password=null;   
        
    public MyAuthenticator(){   
    }   
    public MyAuthenticator(String username, String password) {    
        this.userName = username;    
        this.password = password;    
    }    
    protected PasswordAuthentication getPasswordAuthentication(){   
        return new PasswordAuthentication(userName, password);   
    }   
    
    public static void main(String[] args){   
        //这个类主要是设置邮件   
     MailSenderInfo mailInfo = new MailSenderInfo();
     mailInfo.setMailServerHost("smtp.163.com");    
     mailInfo.setMailServerPort("25");    
     mailInfo.setValidate(true);    
     mailInfo.setUserName("likegadfly");    
     mailInfo.setPassword("wangyi216568");//您的邮箱密码
     mailInfo.setFromAddress("likegadfly@163.com");    
     mailInfo.setToAddress("371452875@qq.com");    
     mailInfo.setSubject("1设置邮箱标题 如http://www.guihua.org 中国桂花网");
     mailInfo.setContent("1设置邮箱内容 如http://www.guihua.org 中国桂花网 是中国最大桂花网站==");
        //这个类主要来发送邮件   
     SimpleMailSender sms = new SimpleMailSender();
         sms.sendTextMail(mailInfo);//发送文体格式
        try {
            sms.sendHtmlMail(mailInfo);//发送html格式
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        System.out.println("发送完毕");
   }  

}   
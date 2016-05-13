package cola.machine.util;

import cola.machine.bean.Active;
import cola.machine.bean.SysRole;
import cola.machine.bean.SysUserRole;
import cola.machine.util.mail.MailSenderInfo;
import cola.machine.util.mail.SimpleMailSender;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dozen.zhang on 2016/5/13.
 */
public class EmailUtil {
    public static void send(String email,String content){
        if(StringUtil.isNotEmpty(user.getEmail())) {
            // 插入激活数据
            Active active = new Active();
            active.setUserid(user.getId());
            active.setActiveid(UUIDUtil.getUUID());
            this.activeMapper.insertActive(active);


            //TODO assign guest role
            SysUserRole sysUserRole=new SysUserRole();
            sysUserRole.setUid(user.getId());
            HashMap params =new HashMap();
            params.put("code","guest");
            List<SysRole> sysUserRoles=  roleMapper.listByParams(params);
            if(sysUserRoles==null || sysUserRoles.size()==0){
                return ResultUtil.getResult("");
            }
            sysUserRole.setRoleid(sysUserRoles.get(0).getId());
            sysUserRoleMapper.insert(sysUserRole);
            // 发送激活邮件
            MailSenderInfo mailInfo = new MailSenderInfo();
            mailInfo.setMailServerHost("smtp.163.com");
            mailInfo.setMailServerPort("25");
            mailInfo.setValidate(true);
            mailInfo.setUserName("likegadfly");
            mailInfo.setPassword("wangyi216568");// 您的邮箱密码
            mailInfo.setFromAddress("likegadfly@163.com");
            mailInfo.setToAddress("371452875@qq.com");
            mailInfo.setSubject("帐号激活");
            //mailInfo.setContent("请点击下面的链接进行激活</br><a href=''>http://127.0.0.1:8080/calendar/active.htm?activeid="
            //	+ active.getActiveid() + "</a>");
            mailInfo.setContent("你的重置密码验证码:"+ active.getActiveid()+" 请复制至密码重置验证码处进行后续操作");
            // 这个类主要来发送邮件
            SimpleMailSender sms = new SimpleMailSender();
            // sms.sendTextMail(mailInfo);//发送文体格式
            try {
                sms.sendHtmlMail(mailInfo);// 发送html格式
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return ResultUtil.getSuccResult();
        // }
    }
    }
}

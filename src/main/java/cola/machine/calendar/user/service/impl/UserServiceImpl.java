package cola.machine.calendar.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cola.machine.calendar.base.service.BaseService;
import cola.machine.calendar.user.bean.Active;
import cola.machine.calendar.user.bean.User;
import cola.machine.calendar.user.dao.ActiveMapper;
import cola.machine.calendar.user.dao.UserMapper;
import cola.machine.calendar.user.service.UserService;
import cola.machine.util.MD5Utils;
import cola.machine.util.StringUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.mail.MailSenderInfo;
import cola.machine.util.mail.SimpleMailSender;

public class UserServiceImpl implements UserService,BaseService{
	Logger log = (Logger) LoggerFactory.getLogger(UserServiceImpl.class);
	private UserMapper userMapper;
	public ActiveMapper getActiveMapper() {
		return activeMapper;
	}

	public void setActiveMapper(ActiveMapper activeMapper) {
		this.activeMapper = activeMapper;
	}

	public UserMapper getUserMapper() {
		return userMapper;
	}
	private ActiveMapper activeMapper;
	public User getUserByLoginName(String loginname) {
		User user = userMapper.selectUserByLoginName(loginname);
		return user;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper=userMapper;
	}

	public User saveUser(User user) {
		if(user!=null ){
			if(StringUtil.isEmpty(user.getUserid())){
				  UUID uuid = UUID.randomUUID();
				user.setUserid(uuid.toString());
			}
			userMapper.insertUser(user);
		}
		return user;
	}
	

	public User getUserByEmail(String email) {
		User user = userMapper.selectUserByEmail(email);
		return user;
	}

	public User getUserByUserid(String id) {
		User user = userMapper.selectUserByUserId(id);
		return user;
	}

	public int countAll() {
		return userMapper.countAll();
	}


	public java.util.List<HashMap> getUsersByParam(HashMap map) {
		List<HashMap> list =  userMapper.getUsersByParam(map);
		return list;
	}

	public User addUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 登录验证
	 */
	public HashMap loginValid(String email,String UnencryptedPwd ) {
	///	this.userMapper.getUsersByParam(map)
		String result="";
		String msg="";
		String pwd = MD5Utils.MD5Encode(UnencryptedPwd);
		HashMap<String,String> returnMap=new HashMap<String,String>();
		if (StringUtil.isEmpty(email)  || 
				StringUtil.isEmpty(UnencryptedPwd) ){
				result=FAIL;
				msg="用户名或密码不能为空";
		}else{
			HashMap<String,String> params =new HashMap<String,String>();
			params.put("email",email);
			params.put("pwd",pwd);
			List list =userMapper.getUsersByParam(params);
			if(list!=null && list.size()>0){
				HashMap userMap = (HashMap)list.get(0);
			
				returnMap.putAll(userMap);
				result=SUCCESS;msg="登录成功";
			}else{
				result=FAIL;msg="用户名或密码错误";
			}
		}
		returnMap.put(RESULT, result);
		returnMap.put(MSG, msg);
		return returnMap;
	}
	/**
	 * 注册
	 */
	public HashMap saveRegisterUser(User user) {
		// / this.userMapper.getUsersByParam(map)
		// 校验数据
		String result="";
		String msg="";
		HashMap errors=new HashMap();
		HashMap returnMap = new HashMap();
		user.setUserid(UUIDUtil.getUUID());
	
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		// validator.validateValue(User.class,"email",user.getEmail(),
		// javax.validation.groups.Default.class);
		Set<ConstraintViolation<User>> constraintViolations = validator
				.validate(user);
		if (!constraintViolations.isEmpty()) {
			HashMap errorMap = new HashMap();
			for (ConstraintViolation<User> constraintViolation : constraintViolations) {
				errorMap.put(constraintViolation.getPropertyPath(),
						constraintViolation.getMessage());
			}
			result = FAIL;
			msg = "注册失败";
			errors=errorMap;
			
		}else{
			
			//检测是否有冲突的用户
			
			User sameEmailUser = this.getUserByEmail(user.getEmail());
			if (sameEmailUser != null) {
				result = FAIL;
				msg = "此邮箱已注册";
			} else {
				user.setLoginname(user.getEmail());
				user.setPwd(MD5Utils.MD5Encode(user.getPwd()));
				this.userMapper.insertUser(user);
				result = SUCCESS;
				msg = "注册成功";

		/*		 MailSenderInfo mailInfo = new MailSenderInfo();   
				 mailInfo.setMailServerHost("smtp.163.com");   
				 mailInfo.setMailServerPort("25");    
				 mailInfo.setValidate(true);    
				 mailInfo.setUserName("likegadfly");    
				 mailInfo.setPassword("21656886026774");//您的邮箱密码    
				 mailInfo.setFromAddress("likegadfly@163.com");    
				 mailInfo.setToAddress("371452875@qq.com");    
				 mailInfo.setSubject("帐号激活");    
				 mailInfo.setContent("请点击下面的链接进行激活</br><a href=''>http://127.0.0.1:8080/calendar/active/</a>"); 
				 
				    //这个类主要来发送邮件   
					 SimpleMailSender sms = new SimpleMailSender();   
				   //  sms.sendTextMail(mailInfo);//发送文体格式    
				     sms.sendHtmlMail(mailInfo);//发送html格式   
*/				     
				     //插入激活数据
				     Active active =new Active();
				     active .setUserid(user.getUserid());
				     active.setActiveid(UUIDUtil.getUUID());
				     this.activeMapper.insertActive(active);
			}
		}
		//开始发送验证邮件
		
	     
	     //System.out.println("发送完毕");
		//
		returnMap.put(RESULT, result);
		returnMap.put(MSG, msg);
		returnMap.put(ERRORS, errors);
		return returnMap;
	}
	
}

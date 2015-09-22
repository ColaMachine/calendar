package cola.machine.calendar.user.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cola.machine.calendar.base.service.BaseService;
import cola.machine.calendar.user.bean.Active;
import cola.machine.calendar.user.bean.User;
import cola.machine.calendar.user.bean.Pwdrst;
import cola.machine.calendar.user.dao.ActiveMapper;
import cola.machine.calendar.user.dao.PwdrstMapper;
import cola.machine.calendar.user.dao.UserMapper;
import cola.machine.calendar.user.service.UserService;
import cola.machine.common.msgbox.MsgReturn;
import cola.machine.constants.SysConfig;
import cola.machine.util.MD5Utils;
import cola.machine.util.MapUtil;
import cola.machine.util.RegexUtil;
import cola.machine.util.StringUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.mail.MailSenderInfo;
import cola.machine.util.mail.SimpleMailSender;

/**
 * @author colamachine
 *
 */
public class UserServiceImpl implements UserService,BaseService{
	Logger log = (Logger) LoggerFactory.getLogger(UserServiceImpl.class);
	private UserMapper userMapper;
	private ActiveMapper activeMapper;
	private PwdrstMapper pwdrstMapper;
	public void setPwdrstMapper(PwdrstMapper pwdrstMapper) {
		this.pwdrstMapper = pwdrstMapper;
	}

	public ActiveMapper getActiveMapper() {
		return activeMapper;
	}

	public void setActiveMapper(ActiveMapper activeMapper) {
		this.activeMapper = activeMapper;
	}

	public UserMapper getUserMapper() {
		return userMapper;
	}
	
	public User getUserByLoginName(String loginname) {
		User user = userMapper.selectUserByLoginName(loginname);
		return user;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper=userMapper;
	}

	public User saveUser(User user) {
		if(user!=null ){
			if(StringUtils.isEmpty(user.getUserid())){
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
		if (StringUtils.isEmpty(email)  || 
				StringUtils.isEmpty(UnencryptedPwd) ){
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
				result=SUCC;msg="登录成功";
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
		user.setActive(false);
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
				result = SUCC;
				msg = "注册成功";

		    
				     //插入激活数据
				     Active active =new Active();
				     active .setUserid(user.getUserid());
				     active.setActiveid(UUIDUtil.getUUID());
				     this.activeMapper.insertActive(active);
				     
				     //发送激活邮件
				 	 MailSenderInfo mailInfo = new MailSenderInfo();   
					 mailInfo.setMailServerHost("smtp.163.com");   
					 mailInfo.setMailServerPort("25");    
					 mailInfo.setValidate(true);    
					 mailInfo.setUserName("likegadfly");    
					 mailInfo.setPassword("21656886026774");//您的邮箱密码    
					 mailInfo.setFromAddress("likegadfly@163.com");    
					 mailInfo.setToAddress("371452875@qq.com");    
					 mailInfo.setSubject("帐号激活");    
					 mailInfo.setContent("请点击下面的链接进行激活</br><a href=''>http://127.0.0.1:8080/calendar/active/"+active.getActiveid()+"</a>"); 
					    //这个类主要来发送邮件   
						 SimpleMailSender sms = new SimpleMailSender();   
					   //  sms.sendTextMail(mailInfo);//发送文体格式    
					     sms.sendHtmlMail(mailInfo);//发送html格式   
	
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

	@Override
	public HashMap saveSenPwdrstEmail(String email) {
		HashMap returnMap =new HashMap();
	
		//格式判断
		MsgReturn msgBox = RegexUtil.isEmail(email);
		if(!msgBox.isRight()){
			return MapUtil.createResultMap(false, msgBox.getMsg());
		}
		//数据判断
		User user = userMapper.selectUserByEmail(email);
		if(user==null||StringUtils.isBlank(user.getUserid())){
			returnMap.put(RESULT, FAIL);
			returnMap.put(MSG, "此邮箱没有注册过");
			return returnMap;
		}
		//判断是否有未使用的邮件重置数据
		//根据used=0 和 userid 来判断
		List  list= pwdrstMapper.selectUnusedPwdrstByUserId(user.getUserid());
		Pwdrst pwdrst =new Pwdrst();
		if(list!=null && list.size()>0){
			 pwdrst= (Pwdrst) list.get(0);
			 //防止重复发送激活邮件
		} else{
//		List list= 
		//保存数据
		pwdrst = new Pwdrst();		
		pwdrst .setUserid(user.getUserid());
		pwdrst.setUsed(false);
		pwdrst.setCreatetime(new Timestamp((new Date()).getTime()));
		pwdrst.setIdpwdrst(UUIDUtil.getUUID());
		pwdrstMapper.insertPwdrst(pwdrst);
		//发送邮件
		}
	     //发送激活邮件
	 	 MailSenderInfo mailInfo = new MailSenderInfo();   
		 mailInfo.setMailServerHost("smtp.163.com");   
		 mailInfo.setMailServerPort("25");    
		 mailInfo.setValidate(true);    
		 mailInfo.setUserName("likegadfly");    
		 mailInfo.setPassword("21656886026774");//您的邮箱密码    
		 mailInfo.setFromAddress("likegadfly@163.com");    
		 mailInfo.setToAddress("371452875@qq.com");    
		 mailInfo.setSubject("密码重置");    
		 mailInfo.setContent("请点击下面的链接进行密码重置</br><a href=''>http://127.0.0.1:8080/calendar/active/"+pwdrst.getIdpwdrst()+"</a>"); 
	    //这个类主要来发送邮件   
		 SimpleMailSender sms = new SimpleMailSender();   
	   //  sms.sendTextMail(mailInfo);//发送文体格式    
	     sms.sendHtmlMail(mailInfo);//发送html格式 
		
			//发送邮件
			//是否已经发送过重置邮件了
			//如果有没有用过的重置信息 重复利用
			
		return MapUtil.createResultMap(true,"发送成功");
	}
	
	/* (non-Javadoc)
	 * @see cola.machine.calendar.user.service.UserService#updateUserActive(java.lang.String)
	 * @author colamachine
	 * 涉及两张表 user 表 和 active 表 user表有active 字段标记账户是否激活状态 active 表记录激活过程和激活码
	 */
	@Override
	public HashMap updateUserActive(String activeid) {
		
		Active active= this.activeMapper.selectActiveById(activeid);
		if(active==null || StringUtils.isBlank(active.getActiveid())){
			return MapUtil.createResultMap(false, "激活url无效");
		}
		if(active.isActived()){
			return MapUtil.createResultMap(false, "该账户已激活");
		}
		active.setActived(true);
		active.setActivedtime(new Timestamp((new Date()).getTime()));
		this.activeMapper.updateActive(active);
		User user = this.userMapper.selectUserByUserId(active.getUserid());
		user.setActive(true);
		this.userMapper.updateUser(user);
		HashMap returnMap =new HashMap();
		returnMap.put("user", user);
		returnMap.put(SysConfig.AJAX_RESULT, SysConfig.AJAX_SUCC);
		returnMap.put(SysConfig.AJAX_MSG, "激活成功");
		return returnMap;
	}

	@Override
	public HashMap savePwdrst(String pwd, String code) {
		//数据格式校验
		MsgReturn msgBox =RegexUtil.isPwd(pwd);
		if(!msgBox.isRight()){
			return MapUtil.createResultMap(false, msgBox.getMsg());
		}
		if(StringUtils.isBlank(code) ){
			return MapUtil.createResultMap(false, "参数错误");
		}
		
		//db校验
		Pwdrst pwdrst = pwdrstMapper.selectPwdrstById(code);
		if(pwdrst==null){
			return MapUtil.createResultMap(false, "无效的激活码");
		}
		if(pwdrst.isUsed()){
			return MapUtil.createResultMap(false, "已使用国的激活码");
		}
		pwdrst.setUsed(true);
		pwdrstMapper.used(code);
		String userid = pwdrst.getUserid();
		User user =new User();
		user.setUserid(userid);
		user.setPwd(MD5Utils.MD5Encode(pwd));
		userMapper.restPwd(user);
		return  MapUtil.createResultMap(true, "密码重置成功");
	}
	
}

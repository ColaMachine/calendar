package cola.machine.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import core.action.ResultDTO;
import cola.machine.bean.Active;
import cola.machine.bean.Pwdrst;
import cola.machine.bean.User;
import cola.machine.common.msgbox.MsgReturn;
import cola.machine.constants.SysConfig;
import cola.machine.dao.ActiveMapper;
import cola.machine.dao.PwdrstMapper;
import cola.machine.dao.UserMapper;
import cola.machine.util.MD5Utils;
import cola.machine.util.MapUtil;
import cola.machine.util.RegexUtil;
import cola.machine.util.ResultUtil;
import cola.machine.util.StringUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.mail.MailSenderInfo;
import cola.machine.util.mail.SimpleMailSender;

/**
 * @author colamachine
 *
 */
@Service("userService")
public class UserService {

    private static final Logger logger = LoggerFactory
            .getLogger(UserService.class);
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ActiveMapper activeMapper;
	@Autowired
	private PwdrstMapper pwdrstMapper;

	/*
	 * public void setPwdrstMapper(PwdrstMapper pwdrstMapper) {
	 * this.pwdrstMapper = pwdrstMapper; }
	 * 
	 * public ActiveMapper getActiveMapper() { return activeMapper; }
	 * 
	 * public void setActiveMapper(ActiveMapper activeMapper) {
	 * this.activeMapper = activeMapper; }
	 * 
	 * public UserMapper getUserMapper() { return userMapper; }
	 */

	public User getUserByLoginName(String loginname) {
		User user = userMapper.selectUserByLoginName(loginname);
		return user;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public User saveUser(User user) {
		if (user != null) {
			if (StringUtils.isEmpty(user.getUserid())) {
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

	public java.util.List<Map> getUsersByParam(Map map) {
		List<Map> list = userMapper.getUsersByParam(map);
		return list;
	}

	public User addUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 登录验证
	 */
	public ResultDTO loginValid(String email, String UnencryptedPwd) {
		// / this.userMapper.getUsersByParam(map)
		String pwd = MD5Utils.MD5Encode(UnencryptedPwd);
		if (StringUtil.isBlank(email) || StringUtil.isBlank(UnencryptedPwd)) {
			return ResultUtil.getWrongResultFromCfg("err.account.empty");
		}
		
			HashMap<String, String> params = new HashMap<String, String>();
			if(StringUtil.isEmail(email)){
	            //是手机号码
			    params.put("email", email);
	        }else if(StringUtil.isPhone(email)){
	            params.put("telno", email);
	        }else{
	            return ResultUtil.getResult(ResultUtil.fail,"既不是手机号也不是邮箱");
	        }
			
			params.put("pwd", pwd);
			List list = userMapper.getUsersByParam(params);
			if (list != null && list.size() > 0) {
				HashMap userMap = (HashMap) list.get(0);
//				returnMap.putAll(userMap);
				
				 User user =new User();
				  user.setEmail(MapUtils.getString(userMap, "email"));
				  user.setTelno(MapUtils.getString(userMap, "telno"));
				  user.setUsername(MapUtils.getString(userMap, "username"));
				  user.setUserid(MapUtils.getString(userMap, "userid"));
				  user.setStatus(MapUtils.getByteValue(userMap, "active"));
				  
				ResultDTO result = ResultUtil.getSuccResult();
				result.setData(user);
				return result;
			} else {
				return ResultUtil
						.getWrongResultFromCfg("err.accountorpwd.wrong");
			}
	}

	/**
	 * 注册
	 */
	public ResultDTO saveRegisterUser(User user) {
		user.setStatus((byte)1);
		// / this.userMapper.getUsersByParam(map)
		// 校验数据
		user.setUserid(UUIDUtil.getUUID());
		ValidateUtil<User> valid = new ValidateUtil<User>();
		/*
		 * ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		 * Validator validator = factory.getValidator(); //
		 * validator.validateValue(User.class,"email",user.getEmail(), //
		 * javax.validation.groups.Default.class);
		 * Set<ConstraintViolation<User>> constraintViolations = validator
		 * .validate(user);
		 */

		/*ResultDTO result = valid.valid(user);
		if (result.getR() != 1) {
			return result;
		}*/
		/*
		 * if (!constraintViolations.isEmpty()) { HashMap errorMap = new
		 * HashMap(); for (ConstraintViolation<User> constraintViolation :
		 * constraintViolations) {
		 * errorMap.put(constraintViolation.getPropertyPath(),
		 * constraintViolation.getMessage()); } result = FAIL; msg = "注册失败";
		 * errors=errorMap;
		 * 
		 * }else{
		 */

		// 检测是否有冲突的用户
		// TODO 改成int 数
		//检查是否有相同账号用户
		//TODO 改成count 就可以了
		User sameEmailUser = this.getUserByEmail(user.getEmail());
		if (sameEmailUser != null) {
			return ResultUtil.getWrongResultFromCfg("mail.has.owner");
		} else {
			user.setLoginname(user.getEmail());
			user.setPwd(MD5Utils.MD5Encode(user.getPwd()));
			this.userMapper.insertUser(user);
			// 插入激活数据
			Active active = new Active();
			active.setUserid(user.getUserid());
			active.setActiveid(UUIDUtil.getUUID());
			this.activeMapper.insertActive(active);

			// 发送激活邮件
			MailSenderInfo mailInfo = new MailSenderInfo();
			mailInfo.setMailServerHost("smtp.163.com");
			mailInfo.setMailServerPort("25");
			mailInfo.setValidate(true);
			mailInfo.setUserName("likegadfly");
			mailInfo.setPassword("21656886026774");// 您的邮箱密码
			mailInfo.setFromAddress("likegadfly@163.com");
			mailInfo.setToAddress("371452875@qq.com");
			mailInfo.setSubject("帐号激活");
			mailInfo.setContent("请点击下面的链接进行激活</br><a href=''>http://127.0.0.1:8080/calendar/active.htm?activeid="
					+ active.getActiveid() + "</a>");
			// 这个类主要来发送邮件
			SimpleMailSender sms = new SimpleMailSender();
			// sms.sendTextMail(mailInfo);//发送文体格式
			sms.sendHtmlMail(mailInfo);// 发送html格式
			return ResultUtil.getSuccResult();
			// }
		}
	}

/*	String MSG = "msg";
	String ERRORS = "errrors";
	String RESULT = "result";*/

	/* (non-Javadoc)
	 * @see cola.machine.service.UserService#saveSenPwdrstEmail(java.lang.String)
	 * @author colamachine
	 */
	public ResultDTO saveSenPwdrstEmail(String email) {

		// 格式判断
		ResultDTO result = RegexUtil.email(email);
		if (result.getR() != 1) {
			return result;
		}
		// 数据判断
		User user = userMapper.selectUserByEmail(email);
		if (user == null || StringUtils.isBlank(user.getUserid())) {
			return ResultUtil.getWrongResultFromCfg("mail.not.register");
		}
		// 判断是否有未使用的邮件重置数据
		// 根据used=0 和 userid 来判断
		List list = pwdrstMapper.selectUnusedPwdrstByUserId(user.getUserid());
		Pwdrst pwdrst = new Pwdrst();
		if (list != null && list.size() > 0) {
			pwdrst = (Pwdrst) list.get(0);
			// 防止重复发送激活邮件
		} else {
			// List list=
			// 保存数据
			pwdrst = new Pwdrst();
			pwdrst.setUserid(user.getUserid());
			pwdrst.setUsed(false);
			pwdrst.setCreatetime(new Timestamp((new Date()).getTime()));
			pwdrst.setIdpwdrst(UUIDUtil.getUUID());
			pwdrstMapper.insertPwdrst(pwdrst);
			// 发送邮件
		}
		// 发送激活邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("likegadfly");
		mailInfo.setPassword("21656886026774");// 您的邮箱密码
		mailInfo.setFromAddress("likegadfly@163.com");
		mailInfo.setToAddress("371452875@qq.com");
		mailInfo.setSubject("密码重置");
		mailInfo.setContent("请点击下面的链接进行密码重置</br><a href=''>http://127.0.0.1:8080/calendar/active/"
				+ pwdrst.getIdpwdrst() + "</a>");
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		// sms.sendTextMail(mailInfo);//发送文体格式
		sms.sendHtmlMail(mailInfo);// 发送html格式

		// 发送邮件
		// 是否已经发送过重置邮件了
		// 如果有没有用过的重置信息 重复利用

		return ResultUtil.getSuccResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cola.machine.calendar.user.service.UserService#updateUserActive(java.
	 * lang.String)
	 * 
	 * @author colamachine 涉及两张表 user 表 和 active 表 user表有active 字段标记账户是否激活状态
	 * active 表记录激活过程和激活码
	 */
	public ResultDTO updateUserActive(String activeid) {

		Active active = this.activeMapper.selectActiveById(activeid);
		if (active == null || StringUtils.isBlank(active.getActiveid())) {
			return ResultUtil.getWrongResultFromCfg("active.url.not.valid");
		}
		if (active.isActived()) {
			return ResultUtil.getWrongResultFromCfg("active.url.used");
		}
		active.setActived(true);
		active.setActivedtime(new Timestamp((new Date()).getTime()));
		this.activeMapper.updateActive(active);
		User user = this.userMapper.selectUserByUserId(active.getUserid());
		user.setStatus((byte)2);
		this.userMapper.updateUser(user);
		
		return ResultUtil.getSuccResult();
		/*HashMap returnMap = new HashMap();
		returnMap.put("user", user);
		returnMap.put(SysConfig.AJAX_RESULT, SysConfig.AJAX_SUCC);
		returnMap.put(SysConfig.AJAX_MSG, "激活成功");
		return returnMap;*/
	}

	/* (non-Javadoc)
	 * @see cola.machine.service.UserService#savePwdrst(java.lang.String, java.lang.String)
	 * @author colamachine
	 */
	public ResultDTO savePwdrst(String pwd, String code) {
		// 数据格式校验
		ResultDTO msgBox = RegexUtil.pwd(pwd);
		if (msgBox.getR() != 1) {
			return msgBox;
		}
		if (StringUtils.isBlank(code)) {
			return ResultUtil.getWrongResultFromCfg("pwd.reset.code.wrong");
		}

		// db校验
		Pwdrst pwdrst = pwdrstMapper.selectPwdrstById(code);
		if (pwdrst == null) {
			return ResultUtil.getWrongResultFromCfg("pwd.reset.code.wrong");
		}
		if (pwdrst.isUsed()) {
			return ResultUtil.getWrongResultFromCfg( "pwd.reset.code.used");
		}
		pwdrst.setUsed(true);
		pwdrstMapper.used(code);
		String userid = pwdrst.getUserid();
		User user = new User();
		user.setUserid(userid);
		user.setPwd(MD5Utils.MD5Encode(pwd));
		userMapper.restPwd(user);
		return ResultUtil.getSuccResult( "pwd.reset.code.succ");
	}
	

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    /**
     * 说明:list by page and params
     * @param page
     * @return
     * @return List<Role>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<User> listByParams4Page(HashMap params) {
        return userMapper.listByParams4Page(params);
    }
     public List<User> listByParams(HashMap params) {
        return userMapper.listByParams(params);
    }

    /*
     * 说明:
     * @param User
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(User user) {
        // 进行字段验证
      /* ValidateUtil<User> vu = new ValidateUtil<User>();
        ResultDTO result = vu.valid(user);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       
       //判断是更新还是插入
        if (user.getUserid()==null) {
                user.setUserid(UUIDUtil.getUUID());
            userMapper.insert(user);
        } else {
             userMapper.updateByPrimaryKey(user);
        }
        return ResultUtil.getSuccResult();
    }
    /**
    * 说明:根据主键删除数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public void delete(String  userid){
        userMapper.deleteByPrimaryKey(userid);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public User selectByPrimaryKey(String id){
       return userMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(String[] idAry) {
        for(int i=0;i<idAry.length;i++){
            userMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }


}

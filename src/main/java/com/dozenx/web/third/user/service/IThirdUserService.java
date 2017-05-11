package com.dozenx.web.third.user.service;

import com.dozenx.web.module.merchant.bean.SessionDTO;
import com.dozenx.web.module.merchant.bean.SessionUser;

/**
 * Created by dozen.zhang on 2017/3/10.
 */
public interface IThirdUserService {
    public SessionUser getUserByPhone(String phone)throws  Exception;

    public SessionUser getUserById(Long id);

    public void updateUser(SessionUser sessionUser) throws Exception;
    public void addUser(SessionUser sessionUser) throws Exception;
    public void loginByPhoneAndSMS(SessionDTO sessionDTO, String phone , String captcha) throws Exception;
}

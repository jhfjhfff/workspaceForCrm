package com.jane.crm.settings.service;

import com.jane.crm.exception.LoginException;
import com.jane.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User login(String loginAct, String loginPwd ,String ip) throws LoginException;


    List<User> getUserList();

}

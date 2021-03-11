package com.bjpowernode.crm.setting.service;

import com.bjpowernode.crm.exception.LoginException;
import com.bjpowernode.crm.setting.dao.UserDao;
import com.bjpowernode.crm.setting.domain.User;
import com.bjpowernode.crm.util.DateTimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl {
    @Resource
    private UserDao userDao;


    public User login(String loginAct, String loginPwd,String ip) throws LoginException {
        User user = userDao.login(loginAct,loginPwd);
        if(user == null){
            throw new LoginException("账号密码错误");
        }
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if(expireTime.compareTo(currentTime) < 0){
            throw new LoginException("账号已失效");
        }
        String loackState = user.getLockState();
        if("0".equals(loackState)){
            throw new LoginException("账号已锁定");
        }
        String allowIps = user.getAllowIps();
        if(!allowIps.contains(ip)){
            throw new LoginException("ip地址受限");
        }
        return user;
    }

    public List<User> getUserList() {
        List<User> list = userDao.getUserList();
        return list;
    }
}

package com.bjpowernode.crm.controller;

import com.bjpowernode.crm.exception.LoginException;
import com.bjpowernode.crm.setting.domain.User;
import com.bjpowernode.crm.setting.service.UserServiceImpl;
import com.bjpowernode.crm.util.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/setting/user")
public class UserController {
    @Resource
    private UserServiceImpl service;

    @RequestMapping("/login.do")
    @ResponseBody
    public Map<String,Object> login(String loginAct, String loginPwd, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        loginPwd = MD5Util.getMD5(loginPwd);
        String ip = request.getRemoteAddr();
        User user = null;
        try {
            user = service.login(loginAct,loginPwd,ip);
            request.getSession().setAttribute("user",user);
            map.put("success",true);
        } catch (LoginException e) {
            map.put("success",false);
            map.put("msg",e.getMessage());
        }
        return map;
    }

    @RequestMapping("/login.go")
    public String logingo(){
        return "/index";
    }
    @RequestMapping("/main")
    public String main(){
        return "/main/index";
    }

    @RequestMapping("/getUserList")
    @ResponseBody
    public List<User> getUserList(){
        List<User> list = service.getUserList();
        return list;
    }
}

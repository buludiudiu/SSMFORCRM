package com.bjpowernode.crm.handle;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();
        if("/setting/user/login.do".equals(path)||"/login.jsp".equals(path)){
            return true;
        }
        Object user = request.getSession(false).getAttribute("user");
        if(user != null){
            return true;
        }
        if(user == null){
            response.sendRedirect(request.getContextPath()+"/login.jsp");
            return false;
        }
       return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

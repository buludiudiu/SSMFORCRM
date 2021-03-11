package com.bjpowernode.crm.controller;

import com.bjpowernode.crm.setting.domain.User;
import com.bjpowernode.crm.util.DateTimeUtil;
import com.bjpowernode.crm.util.UUIDUtil;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/workbench/Activity")
public class ActivityController {

    @Resource
    private ActivityServiceImpl service;

    @RequestMapping("/activity.go")
    public String goActivity(){
        return "activity/index";
    }

    @RequestMapping("/saveActivity")
    @ResponseBody
    public boolean saveActivity(Activity activity, HttpServletRequest request){
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        User user = (User) request.getSession().getAttribute("user");
        activity.setId(id);
        activity.setCreateTime(createTime);
        activity.setCreateBy(user.getName());
        boolean flag = service.saveActivity(activity);
        return flag;
    }


    @RequestMapping("/pageList")
    @ResponseBody
    public PaginationVO<Activity> pageList( Integer pageNo, Integer pageSize,String name,String owner,String startDate,String endDate){
        System.out.println(name+owner+startDate+endDate);
        Map<String,Object> map = new HashMap<>();
        //略过的条数
        int skipCount = (pageNo - 1) * pageSize ;
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        PaginationVO<Activity> vo = service.pageList(map);
        return vo;
    }

    @RequestMapping("/deleteActivity")
    @ResponseBody
    public boolean deleteActivity(String[] ids){
        return service.deleteActivity(ids);
    }

    @RequestMapping("/searchActivityByid")
    @ResponseBody
    public Map<String,Object> searchActivityByid(String id){
        return service.searchActivityByid(id);
    }

    @RequestMapping("/update")
    @ResponseBody
    public boolean update(Activity activity,HttpServletRequest request){
        System.out.println(".................................................");
        String editTime = DateTimeUtil.getSysTime();
        User user = (User) request.getSession().getAttribute("user");
        activity.setEditBy(user.getName());
        activity.setEditTime(editTime);
        return service.update(activity);
    }

    @RequestMapping("/detail")
    public ModelAndView detail(String id){
        Activity activity = service.detail(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("a",activity);
        mv.setViewName("/activity/detail");
        return mv;
    }
}

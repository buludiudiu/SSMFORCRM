package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.setting.dao.UserDao;
import com.bjpowernode.crm.setting.domain.User;
import com.bjpowernode.crm.util.DateTimeUtil;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl {
    @Resource
    private UserDao userDao;
    @Resource
    private ActivityDao activityDao;

    @Resource
    private ActivityRemarkDao activityRemarkDao;


    public boolean saveActivity(Activity activity) {
        boolean flag = true;
        int count = activityDao.saveActivity(activity);
        if(count != 1){
            flag = false;
        }
        return flag;
    }

    public PaginationVO<Activity> pageList(Map<String, Object> map) {
        PaginationVO<Activity> vo = new PaginationVO<>();
        int total = activityDao.getTotalByCondition(map);
        List<Activity> datalist = activityDao.getActivityListByCondition(map);
        vo.setTotal(total);
        vo.setDataList(datalist);
        return vo;
    }


    public boolean deleteActivity(String[] ids) {
        boolean flag = true;
        int count1 = activityRemarkDao.getCountByIds(ids);
        int count2 = activityRemarkDao.deleteByIds(ids);
        if(count1 != count2){
            flag = false;
        }
        int count3 = activityDao.deleteActivity(ids);
        if(count3 != ids.length){
            flag = false;
        }
        return flag;
    }

    public Map<String,Object> searchActivityByid(String id) {
        Map<String,Object> map = new HashMap<>();
        Activity activity =  activityDao.searchActivityByid(id);
        List<User> userList = userDao.getUserList();
        map.put("a",activity);
        map.put("uList",userList);
        return map;
    }

    public boolean update(Activity activity) {
        boolean flag = true;
        int count = activityDao.update(activity);
        if(count != 1){
            flag = false;
        }
        return flag;
    }

    public Activity detail(String id) {
        return activityDao.detail(id);
    }
}

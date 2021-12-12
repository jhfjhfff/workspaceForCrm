package com.jane.crm.workbench.service.Impl;

import com.jane.crm.settings.dao.UserDao;
import com.jane.crm.settings.domain.User;
import com.jane.crm.utils.SqlSessionUtil;
import com.jane.crm.vo.PaginationVO;
import com.jane.crm.workbench.dao.ActivityDao;
import com.jane.crm.workbench.dao.ActivityRemarkDao;
import com.jane.crm.workbench.domain.Activity;
import com.jane.crm.workbench.domain.ActivityRemark;
import com.jane.crm.workbench.service.ActivityService;
import jdk.nashorn.internal.ir.Flags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {

    private UserDao userDao = SqlSessionUtil.getSession().getMapper(UserDao.class);
    private ActivityDao activityDao = SqlSessionUtil.getSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSession().getMapper(ActivityRemarkDao.class);

    public boolean saveActivity(String id, String owner, String name, String startDate, String endDate, String cost ,String description, String createTime, String createBy) {
        System.out.println("添加用户的业务层");

        boolean flag = true;
        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);

        int count = activityDao.saveActivity(activity);
        if (count!= 1){
            flag = false;
        }

        return flag;
    }

    public PaginationVO<Activity> pageList(Map<String, Object> map) {

        //取得total，
        int total = activityDao.getTotalByCondition(map);

        //取得dataList
        List<Activity> dataList = activityDao.getActivityByCondition(map);

        //创建一个vo,将total和dataList封装到vo中
        PaginationVO<Activity> vo = new PaginationVO<Activity>();
        vo.setDataList(dataList);
        vo.setTotal(total);

        return vo;
    }

    public boolean delete(String[] ids) {

        boolean flag = true;

        //查询出需要删除的备注的数量
        int count1 = activityRemarkDao.getCountByAids(ids);

        //删除备注，返回受到影响的条数（实际删除的数量）
        int count2 = activityRemarkDao.deleteByAdis(ids);

        if (count1!=count2){
            flag =false;
        }
        //删除市场活动
        int count3 = activityDao.delete(ids);
        if (count3!=ids.length){
            flag = false;
        }

        return flag;
    }

    public Map getUserListAndActivity(String id) {

        List<User> uList = userDao.getUserList();
        Activity activity = activityDao.getActivity(id);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("uList",uList);
        map.put("a",activity);

        return map;
    }

    public boolean update(String id, String owner, String name, String startDate, String endDate, String cost, String description,String editTime,String editBy) {
        System.out.println("修改用户的业务层");

        boolean flag = true;
        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);

        int count = activityDao.update(activity);
        if (count!= 1){
            flag = false;
        }

        return flag;
    }

    public Activity detail(String id) {

        Activity activity = activityDao.detail(id);

        return activity;
    }

    public List<ActivityRemark> getRemarkListByAid(String activityId) {

        List<ActivityRemark> arList = activityRemarkDao.getRemarkListByAid(activityId);

        return arList;
    }

    public boolean deleteRemark(String id) {

        boolean flag = true;

        int count = activityRemarkDao.deleteById(id);

        if (count!=1){
            flag = false;
        }

        return flag;
    }

    public boolean saveRemark(ActivityRemark ar) {

        boolean flag = true;
        int count = activityRemarkDao.saveRemark(ar);
        if(count!=1){
            flag = false;
        }

        return flag;
    }

    public boolean updateRemark(ActivityRemark ar) {
        boolean flag = true;
        int count = activityRemarkDao.updateRemark(ar);
        if(count!=1){
            flag = false;
        }

        return flag;
    }

}

package com.jane.crm.workbench.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jane.crm.vo.PaginationVO;
import com.jane.crm.workbench.domain.Activity;
import com.jane.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    //boolean saveActivity(String id, String s, String date, String owner, String name, String startDate, String endDate, String description,String createTime,String createBy);

    boolean saveActivity(String id, String owner, String name, String startDate, String endDate, String cost,String description, String createTime, String createBy);

    PaginationVO<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] ids);

    Map getUserListAndActivity(String id);

    boolean update(String id, String owner, String name, String startDate, String endDate, String cost, String description,String editTime,String editBy);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    boolean deleteRemark(String id);

    boolean saveRemark(ActivityRemark ar);

    boolean updateRemark(ActivityRemark ar);
}

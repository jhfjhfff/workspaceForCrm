package com.jane.crm.workbench.dao;

import com.jane.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int saveActivity(Activity activity);

    int getTotalByCondition(Map<String, Object> map);

    List<Activity> getActivityByCondition(Map<String, Object> map);

    int delete(String[] ids);

    Activity getActivity(String id);

    int update(Activity activity);

    Activity detail(String id);
}

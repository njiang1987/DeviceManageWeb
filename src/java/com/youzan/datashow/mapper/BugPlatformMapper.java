package com.youzan.datashow.mapper;

import com.youzan.datashow.domain.BugPlatformObject;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by adrian on 16/5/17.
 */
public interface BugPlatformMapper {


        @Select("SELECT js.pname as bugResolutionStatus, COUNT(js.pname)  as bugNum FROM jiraissue ji INNER JOIN project pr ON pr.ID = ji.PROJECT LEFT JOIN issuestatus js ON ji.issuestatus = js.ID where pr.ID in (#{2})  and issuetype = 1 AND CREATED between #{0} and #{1} group by js.pname")
        List<BugPlatformObject> getList(String startDate,String endDate,String project);
}

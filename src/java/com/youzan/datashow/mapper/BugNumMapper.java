package com.youzan.datashow.mapper;

import com.youzan.datashow.domain.BugNumObject;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by adrian on 4/18/16.
 */
public interface BugNumMapper {

         @Select("select last_name as project ,COUNT(REPORTER) as bugNum FROM jiraissue ji INNER JOIN project pr ON pr.ID = ji.PROJECT LEFT JOIN cwd_user cu ON ji.REPORTER = cu.user_name  where pr.ID in (#{2})  and issuetype = 1  AND CREATED between #{0} and #{1} group by REPORTER")
         List<BugNumObject> getList(String startDate,String endDate,String project);

}

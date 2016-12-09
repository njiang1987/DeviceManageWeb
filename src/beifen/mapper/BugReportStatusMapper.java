package com.youzan.datashow.mapper;

import com.youzan.datashow.domain.BugReportStatusObject;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by adrian on 16/5/17.
 */
public interface BugReportStatusMapper {


        @Select("select last_name as bugReportStatus,count(ASSIGNEE) as bugNum " +
                "FROM jiraissue ji INNER JOIN project pr ON pr.ID = ji.PROJECT LEFT JOIN cwd_user cu ON ji.ASSIGNEE = cu.user_name  where pr.ID in (#{2}) and issuestatus in(10012,1,4) and issuetype = 1 and ASSIGNEE is not NULL AND CREATED between #{0} and #{1} group by ASSIGNEE\n" +
                "UNION ALL\n" +
                "select last_name as bugReportStatus,count(*) as bugNum " +
                "FROM jiraissue ji INNER JOIN project pr ON pr.ID = ji.PROJECT LEFT JOIN cwd_user cu ON ji.ASSIGNEE = cu.user_name  where pr.ID in (#{2}) and issuestatus in(10012,1,4) and issuetype = 1 and ASSIGNEE is NULL AND CREATED between #{0} and #{1} group by ASSIGNEE")
//        @Select("select last_name as bugReportStatus,count(ASSIGNEE) as bugNum " +
//                "FROM jiraissue ji INNER JOIN project pr ON pr.ID = ji.PROJECT LEFT JOIN cwd_user cu ON ji.ASSIGNEE = cu.user_name  where pr.ID in (10032,10031,10034)and issuestatus in(10012,1,4) and issuetype = 1 and ASSIGNEE is not NULL AND CREATED between #{0} and #{1} group by ASSIGNEE\n" +
//                "UNION ALL\n" +
//                "select last_name as bugReportStatus,count(*) as bugNum " +
//                "FROM jiraissue ji INNER JOIN project pr ON pr.ID = ji.PROJECT LEFT JOIN cwd_user cu ON ji.ASSIGNEE = cu.user_name  where pr.ID in (10032,10031,10034) and issuestatus in(10012,1,4) and issuetype = 1 and ASSIGNEE is NULL AND CREATED between #{0} and #{1} group by ASSIGNEE")

        List<BugReportStatusObject> getList(String startDate,String endDate,String project);
}

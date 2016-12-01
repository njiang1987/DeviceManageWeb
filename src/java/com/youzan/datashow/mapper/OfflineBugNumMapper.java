package com.youzan.datashow.mapper;

import com.youzan.datashow.domain.BugReportRankObject;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by sunwenting on 16/5/26.
 */
public interface OfflineBugNumMapper {

  @Select("select REPORTER as bugReporter,count(*) as bugNum from jiraissue a where REPORTER "
          + "in(select child_name from cwd_membership where parent_id=10131) and "
          + "CREATED >= DATE_SUB(DATE(NOW()),INTERVAL 30 DAY)  and issuetype =1 and"
          + " issuestatus in (1,3,4,5,6) and project in (10031,10032,10034) "
          + "group by REPORTER order by count(*) desc limit 10")
  List<BugReportRankObject> getQAList();

  @Select("select ASSIGNEE as bugReporter,count(*) as bugNum from jiraissue  WHERE issuetype=1 and "
          + "issuestatus in(1,3,4,5,6) and   CREATED >= DATE_SUB(DATE(NOW()),INTERVAL 30 DAY) and "
          + "project in (10031,10032,10034)"
          + "group by ASSIGNEE order by count(*) desc limit 10")
  List<BugReportRankObject> getDevList();

}

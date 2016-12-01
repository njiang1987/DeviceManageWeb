package com.youzan.datashow.mapper;

import com.youzan.datashow.domain.BugNumObject;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by sunwenting on 16/5/25.
 */
public interface OfflineBussnissBugNumMapper {

    @Select("select b.pname as project,count(*) as bugNum from jiraissue a,project b where a.REPORTER "
            + "in(select child_name from cwd_membership where parent_id=10031) and"
            + " a.project in (10031,10032,10034) and a.project = b.id  and "
            + "a.CREATED >= DATE_SUB(DATE(NOW()),INTERVAL 30 DAY)  and a.issuetype =1 "
            + "and a.issuestatus in (1,3,4,5,6)  group by a.project order by count(*) desc")
    List<BugNumObject> getBussnissList();

  @Select("select b.pname as project,count(*) as bugNum from jiraissue a,project b where a.REPORTER "
          + "in(select child_name from cwd_membership where parent_id=10031) and"
          + " a.project in (10031,10032,10034) and a.project = b.id  and "
          + "a.CREATED >= #{0} and a.CREATED <= #{1}  and a.issuetype =1 "
          + "and a.issuestatus in (1,3,4,5,6)  group by a.project order by count(*) desc")
  List<BugNumObject> getBussnissListByDate(String startDate,String endDate);

    @Select("select #{projectName} as project,count(*) as bugNum from jiraissue a where REPORTER "
            + "in(select child_name from cwd_membership where parent_id=10031) and "
            + "project in (10031,10032,10034)  and "
            + "issuetype =1 and issuestatus in (1,3,4,5,6) and summary like CONCAT(\'%\',#{projectName},\'%\') "
            + "order by count(*) desc")
    List<BugNumObject> getProjectList(String projectName);

}

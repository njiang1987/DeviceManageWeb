package com.youzan.datashow.mapper;

import com.youzan.datashow.domain.BugCheckObject;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by adrian on 16/5/17.
 */
public interface BugCheckMapper {


        @Select("select last_name as bugReporter,count(REPORTER) as bugNum FROM jiraissue ji INNER JOIN project pr ON pr.ID = ji.PROJECT LEFT JOIN cwd_user cu ON ji.REPORTER = cu.user_name  where pr.ID in (#{2}) and issuestatus in(10014,5,10017,10018,10019,10005) and issuetype = 1  AND UPDATED between #{0} and #{1} group by REPORTER")
//          @Select("select last_name as bugReporter,count(REPORTER) as bugNum FROM jiraissue ji INNER JOIN project pr ON pr.ID = ji.PROJECT LEFT JOIN cwd_user cu ON ji.REPORTER = cu.user_name  where pr.pkey in ('YYPC','SJPC','CARUAPP') and issuestatus in(10014,5,10017,10018,10019,10005) and issuetype = 1 AND UPDATED between #{0} and #{1}  group by REPORTER")
                List<BugCheckObject> getList(String startDate, String endDate,String project);

}

package com.youzan.datashow.mapper;

import com.youzan.datashow.domain.BugCheckObject;
import com.youzan.datashow.domain.BugFixedObject;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by adrian on 16/5/17.
 */
public interface BugFixedMapper {


        @Select("select datediff as bugFixed,count(datediff) as bugDate from (SELECT ji.ID, DATEDIFF(ji.UPDATED,ji.CREATED) as datediff ,UPDATED,CREATED  FROM jiraissue ji INNER JOIN project pr ON pr.ID = ji.PROJECT  where pr.ID in (#{2})  and issuetype =1 and issuestatus in (5,10005,10018,10014,10011,6) AND CREATED between #{0} and #{1}) t group by datediff order by bugDate DESC limit 20")
//@Select("select datediff as bugFixed,count(datediff) as bugDate from (SELECT ji.ID, DATEDIFF(ji.UPDATED,ji.CREATED) as datediff ,UPDATED,CREATED  FROM jiraissue ji INNER JOIN project pr ON pr.ID = ji.PROJECT LEFT JOIN issuestatus js ON ji.issuestatus = js.ID where pr.ID in (10032,10031,10034)  and issuetype =1 and issuestatus in (5,10005,10018,10014,10011)) t group by datediff \n")
                List<BugFixedObject> getList(String startDate, String endDate,String project);

}

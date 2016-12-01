package com.youzan.datashow.mapper;

import com.youzan.datashow.domain.BugCheckObject;
import com.youzan.datashow.domain.BugDevObject;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by adrian on 16/5/17.
 */
public interface BugDevMapper {


          @Select("select last_name as dev ,count(ASSIGNEE) as bugNum FROM jiraissue ji INNER JOIN project pr ON pr.ID = ji.PROJECT LEFT JOIN cwd_user cu ON ji.ASSIGNEE = cu.user_name  where pr.ID in (#{2}) and issuetype =1 AND CREATED between #{0} and #{1} group by ASSIGNEE  order by bugNum DESC limit 10")
                List<BugDevObject> getList(String startDate, String endDate, String project);

}

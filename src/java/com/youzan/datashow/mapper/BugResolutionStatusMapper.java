package com.youzan.datashow.mapper;

import com.youzan.datashow.domain.BugResolutionStatusObject;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by adrian on 16/5/17.
 */
public interface BugResolutionStatusMapper {


           @Select("select Date(CREATED) as bugNewDate, COUNT(Date(CREATED))  as bugNum from jiraissue ji INNER JOIN project pr ON pr.ID = ji.PROJECT  where pr.ID in (#{2}) and issuetype = 1  and CREATED between #{0} and #{1} group by Date(CREATED) ")

         List<BugResolutionStatusObject> getList(String startDate, String endDate,String project);
}

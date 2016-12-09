package com.youzan.datashow.mapper;

import com.youzan.datashow.domain.BugReportStatusObject;
import com.youzan.datashow.domain.BugSeverityObject;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by adrian on 16/5/17.
 */
public interface BugSeverityMapper {

        @Select("select cfo.customvalue as bugSeverity ,count(cfo.customvalue) as bugNum FROM jiraissue ji INNER JOIN project pr ON pr.ID = ji.PROJECT LEFT JOIN customfieldvalue cfv ON cfv.ISSUE =  ji.ID LEFT JOIN customfieldoption cfo ON cfv.STRINGVALUE = cfo.ID where pr.ID in (#{2}) and cfo.CUSTOMFIELD =10104 and issuetype = 1 and issuestatus in(10012,1,4) AND CREATED between #{0} and #{1} group by cfo.customvalue")
        List<BugSeverityObject> getList(String startDate, String endDate,String project);
}

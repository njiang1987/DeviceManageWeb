package com.youzan.datashow.mapper;

import com.youzan.datashow.domain.TickNumTrendObject;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by adrian on 4/21/16.
 */
public interface TickNumMapper {
    @Select("SELECT \t DATE(ji.CREATED) AS yearMonth,\n" +
            "\t\tCOUNT(ji.CREATED) AS createdTicketNo,\n" +
            "\t\tT.CreatedTicketNo AS resolvedTciektNo\n" +
            "FROM jiraissue ji\n" +
            "INNER JOIN project pr ON pr.ID = ji.PROJECT\n" +
            "LEFT JOIN (\n" +
            "\tSELECT\n" +
            "\t\tDATE(ji.RESOLUTIONDATE) AS YearMonth,\n" +
            "\t\tCOUNT(ji.RESOLUTIONDATE) AS CreatedTicketNo\n" +
            "\tFROM\n" +
            "\t\tjiraissue ji\n" +
            "\tINNER JOIN project pr ON pr.ID = ji.PROJECT\n" +
            "\tWHERE\n" +
            "\t\tpr.ID in (#{2})\n" +
            "\tAND ji.RESOLUTIONDATE between #{0} and #{1} and issuetype =1" +
            "\t and issuestatus not in(1,4,10012) GROUP BY\n" +
            "\t\tDATE(ji.RESOLUTIONDATE)\n" +
            "\t) T ON T.YearMonth = DATE(ji.CREATED)\n" +
            "WHERE pr.ID in (#{2})\n" +
            "\tAND ji.CREATED between #{0} and #{1} and issuetype =1\n" +
            "GROUP BY DATE(ji.CREATED);")
    List<TickNumTrendObject> getList(String startDate,String endDate,String project);
}

package com.youzan.datashow.service;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.AxisLabel;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.data.LineData;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.youzan.datashow.domain.BugResolutionStatusObject;
import com.youzan.datashow.domain.TickNumTrendObject;
import com.youzan.datashow.mapper.BugResolutionStatusMapper;
import com.youzan.datashow.mapper.TickNumMapper;
import com.youzan.datashow.util.TimeUtil;
//import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by adrian on 16/5/17.
 */
@Service
public class BugResolutionStatusService {

    @Autowired
    BugResolutionStatusMapper BugResolutionStatusMapper;

    private   List<BugResolutionStatusObject> getList(String startDate, String endDate, String project){
                   return  BugResolutionStatusMapper.getList(startDate,endDate,project); };

    public BugResolutionStatusService() {
    }

    public Option selectLinePicture(String startDate, String endDate,String project){
        Option option = new Option();
        List<String> yearMonthList=new ArrayList<String>();
        List<LineData>  CreatedTicketNo=new ArrayList<LineData>();
//        List<LineData>  ResolvedTciektNo=new ArrayList<LineData>();
        int createdTicketNum=0;
//        int resolvedTicketNum=0;

        List<BugResolutionStatusObject> bugResolutionStatusObjectList;

        bugResolutionStatusObjectList=this.getList(startDate,endDate,project);
        for(BugResolutionStatusObject bugResolutionStatus:bugResolutionStatusObjectList){
            yearMonthList.add(bugResolutionStatus.getBugNewDate());

            createdTicketNum=bugResolutionStatus.getBugNum();
            CreatedTicketNo.add(new LineData(createdTicketNum, Symbol.star, 5));

//            resolvedTicketNum=resolvedTicketNum+tickNumTrendObject.getResolvedTciektNo();
//            ResolvedTciektNo.add(new LineData(resolvedTicketNum, Symbol.star, 5));
        }

        option.tooltip().trigger(Trigger.axis);
        option.title().text("");
//        option.legend("新增");
        option.toolbox().show(true);
        option.calculable(true);
        CategoryAxis xaxis = new CategoryAxis().data(yearMonthList.toArray());
        AxisLabel axisLabel = new AxisLabel();
        axisLabel.setInterval("auto");
        axisLabel.setRotate(30);
        xaxis.axisLabel(axisLabel);
        option.xAxis(xaxis);
        option.yAxis(new ValueAxis());
        option.series(new Line().smooth(true).name("每日新增").symbol("image://http://echarts.baidu.com/doc/asset/ico/favicon.png").data(CreatedTicketNo.toArray()));
//        option.series(new Line().smooth(true).name("已解决").symbol("image://http://echarts.baidu.com/doc/asset/ico/favicon.png").data(ResolvedTciektNo.toArray()));
        Option optionNull = new Option();

        if(yearMonthList.isEmpty()){
            return optionNull;

        }else {
            return option;
        }
    }


}

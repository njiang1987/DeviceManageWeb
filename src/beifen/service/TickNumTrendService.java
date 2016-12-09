package com.youzan.datashow.service;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.AxisLabel;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Symbol;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.LineData;
import com.github.abel533.echarts.series.Line;
import com.youzan.datashow.domain.TickNumTrendObject;
import com.youzan.datashow.mapper.TickNumMapper;
import com.youzan.datashow.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by adrian on 4/21/16.
 */

@Service
public class TickNumTrendService {

    @Autowired
    TickNumMapper tickNumMapper;

  private   List<TickNumTrendObject> getList(String startDate,String endDate,String project){return  tickNumMapper.getList(startDate,endDate,project); };

    public TickNumTrendService() {
    }

    public Option selectLinePicture(String startDate, String endDate,String project){
        Option option = new Option();
        List<String> yearMonthList=new ArrayList<String>();
        List<LineData>  CreatedTicketNo=new ArrayList<LineData>();
        List<LineData>  ResolvedTciektNo=new ArrayList<LineData>();
        int createdTicketNum=0;
        int resolvedTicketNum=0;

        List<TickNumTrendObject> tickNumTrendObjectList;

        tickNumTrendObjectList=this.getList(startDate,endDate,project);
        for(TickNumTrendObject tickNumTrendObject:tickNumTrendObjectList){
            yearMonthList.add(tickNumTrendObject.getYearMonth());

            createdTicketNum=createdTicketNum+tickNumTrendObject.getCreatedTicketNo();
            CreatedTicketNo.add(new LineData(createdTicketNum, Symbol.star, 5));

            resolvedTicketNum=resolvedTicketNum+tickNumTrendObject.getResolvedTciektNo();
            ResolvedTciektNo.add(new LineData(resolvedTicketNum, Symbol.star, 5));
        }

        option.tooltip().trigger(Trigger.axis);
        option.title().text("已创建与已解决问题对比");
        option.legend("已创建", "已解决");
        option.toolbox().show(true);
        option.calculable(true);
        CategoryAxis xaxis = new CategoryAxis().data(yearMonthList.toArray());
        AxisLabel axisLabel = new AxisLabel();
        axisLabel.setInterval("auto");
        axisLabel.setRotate(30);
        xaxis.axisLabel(axisLabel);
        option.xAxis(xaxis);
        option.yAxis(new ValueAxis());
        option.series(new Line().smooth(true).name("已创建").symbol("image://http://echarts.baidu.com/doc/asset/ico/favicon.png").data(CreatedTicketNo.toArray()));
        option.series(new Line().smooth(true).name("已解决").symbol("image://http://echarts.baidu.com/doc/asset/ico/favicon.png").data(ResolvedTciektNo.toArray()));

        Option optionNull = new Option();

        if(yearMonthList.isEmpty()){
            return optionNull;

        }else {
            return option;
        }
    }


}

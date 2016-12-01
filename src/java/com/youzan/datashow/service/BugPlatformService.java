package com.youzan.datashow.service;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.AxisLabel;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Bar;
import com.youzan.datashow.domain.BugPlatformObject;
import com.youzan.datashow.domain.BugProjectObject;
import com.youzan.datashow.mapper.BugPlatformMapper;
import com.youzan.datashow.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by adrian on 16/5/17.
 */
@Service
public class BugPlatformService {

    @Autowired
    public BugPlatformMapper bugPlatformMapper;

    List<BugPlatformObject> getList(String startDate, String endDate,String project){
        return bugPlatformMapper.getList(startDate,endDate,project);
    };

    public HashMap GetBugList(String startDate, String endDate, String project)  {
        List<BugPlatformObject> ProjectList;

        ProjectList=this.getList(startDate,endDate,project);

        HashMap map=new HashMap<String, String>();
        for (BugPlatformObject bugStatus:ProjectList){
            map.put(bugStatus.getBugResolutionStatus(),bugStatus.getBugNum());
            System.out.println(bugStatus.getBugResolutionStatus()+"="+bugStatus.getBugNum());
        }
        return map;
    }


//    public HashMap GetBugList(String startDate, String endDate, String project)  {
////        List<BugPlatformObject> ProjectList;
////
////        ProjectList=this.getList(startDate,endDate,project);
//
//        HashMap map=new HashMap<String, String>();
////        for (BugPlatformObject bugStatus:ProjectList){
//            map.put("key1","value1");
//        map.put("key2","value2");
//
////        }
//        return map;
//    };

    public Option selectBarPicture(String startDate,String endDate,String project)  {
        List<BugPlatformObject> bugStatusList;

        bugStatusList=this.getList(startDate,endDate,project);
        List yList=new ArrayList<>();
        List xList=new ArrayList<>();

        for (BugPlatformObject bugStatus:bugStatusList){
            yList.add(bugStatus.getBugNum());
            xList.add(bugStatus.getBugResolutionStatus());
        }
        //定义Option对象
        Option option = new Option();
        option.legend().x(X.left).data("BUG量");
        //标题栏
        //option.title().text("业务BUG量").subtext("初步统计");
        option.tooltip().trigger(Trigger.axis);
        option.toolbox().show(true).feature(Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true), Tool.saveAsImage);
        option.calculable(true);
        CategoryAxis xaxis = new CategoryAxis().data(xList.toArray());
        AxisLabel axisLabel = new AxisLabel();
        axisLabel.setInterval(0);
        axisLabel.setRotate(30);
        xaxis.axisLabel(axisLabel);
        option.xAxis(xaxis);
        option.yAxis(new ValueAxis());
        Bar bar = new Bar("BUG量");
        bar.setData(yList);
        bar.itemStyle().normal().color("#1780C7").label().show(true).textStyle().color("#DD002C");
        bar.markPoint().data(new PointData().type(MarkType.max).name("最大值"), new PointData().type(MarkType.min).name("最小值"));
        bar.markLine().data(new PointData().type(MarkType.average).name("平均值"));
        option.series(bar);

        Option optionNull = new Option();

        if(xList.isEmpty()){
            return optionNull;

        }else {
            return option;
        }
    }
}

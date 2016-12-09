package com.youzan.datashow.service;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.AxisLabel;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Bar;
import com.youzan.datashow.domain.BugNumObject;
import com.youzan.datashow.mapper.BugNumMapper;
import com.youzan.datashow.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by adrian on 4/18/16.
 */

@Service
public class BugNumService {

    @Autowired
    private BugNumMapper   bugNumMapper ;

    List <BugNumObject> getList(String startDate,String endDate,String project){
        return bugNumMapper.getList(startDate,endDate,project);
    };

    public Option selectBarPicture(String startDate,String endDate,String project)  {
        List<BugNumObject> bugNumPerMonthList;

        bugNumPerMonthList=this.getList(startDate, endDate,project);

        List yList=new ArrayList<>();
        List xList=new ArrayList<>();

        for (BugNumObject bugNumPerMonth:bugNumPerMonthList){
            yList.add(bugNumPerMonth.getBugNum());
            xList.add(bugNumPerMonth.getProject());
        }
        //定义Option对象
        Option option = new Option();
        //标题栏
        //option.title().text("业务BUG量").subtext("初步统计");
        option.legend().x(X.left).data("BUG量");
        option.tooltip().trigger(Trigger.axis);
        option.toolbox().show(true).feature(Tool.dataView, new MagicType(Magic.bar,Magic.line).show(true), Tool.saveAsImage);
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

//此为饼图,暂未使用,故注释掉
//    public Option  selectPiePicture()  {
//        List<BugNumObject> bugNumPerMonthList=this.getList();
//        List yList=new ArrayList<>();
//        List xList=new ArrayList<>();
//
//        for (BugNumObject bugNumPerMonth:bugNumPerMonthList){
//            yList.add(bugNumPerMonth.getBugNum());
//            xList.add(bugNumPerMonth.getProject());
//        }
//
//        Option option = new Option();
//        Option basic = new Option();
//        option.timeline().autoPlay(false);
//        basic.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");
//        basic.legend().left("left");
//        basic.legend().data(xList.toArray());
//        Pie pie = getPie();
//        pie.label().normal().show(true);
//        basic.series(pie);
//        option.options(basic);
//        return option;
//    }
//
//    private Pie getPie() {
//        List<BugNumObject> bugNumPerMonthList=this.getList();
//        List dataArray=new ArrayList<>();
//
//        for (BugNumObject bugNumPerMonth:bugNumPerMonthList){
//            dataArray.add(new PieData(bugNumPerMonth.getProject(),bugNumPerMonth.getBugNum()));}
//
//        return new Pie().name("业务BUG数").data(dataArray.toArray());
//    }

    }

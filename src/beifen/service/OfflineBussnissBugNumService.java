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
import com.youzan.datashow.mapper.OfflineBussnissBugNumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunwenting on 16/5/26.
 */
@Service
public class OfflineBussnissBugNumService {

  @Autowired
  private OfflineBussnissBugNumMapper offlineBugNumMapper ;

  List<BugNumObject> getBussnissList(){
    return offlineBugNumMapper.getBussnissList();
  };

  List<BugNumObject> getBussnissList(String startDate,String endDate){
    return offlineBugNumMapper.getBussnissListByDate(startDate, endDate);
  };

  List<BugNumObject> getProjectList(String projectName){
    return offlineBugNumMapper.getProjectList(projectName);
  };

  public Option selectBussnissBarPicture(String startDate,String endDate)  {
    List<BugNumObject> olbugNumPerMonthList;

    if(startDate!=null&&endDate!=null)
      olbugNumPerMonthList=this.getBussnissList(startDate,endDate);
    else
      olbugNumPerMonthList=this.getBussnissList();
    List yList=new ArrayList<>();
    List xList=new ArrayList<>();

    for (BugNumObject bugNumPerMonth:olbugNumPerMonthList){
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
//    CategoryAxis xaxis = new CategoryAxis().data("微商城测试bug","交测试bug","微小店测试bug","测试bug","分销测试bug","商品测试bug","店铺测试bug");
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
//    bar.markPoint().data(new PointData().type(MarkType.max).name("最大值"), new PointData().type(MarkType.min).name("最小值"));
//    bar.markPoint().data(new PointData(yList));
    bar.markLine().data(new PointData().type(MarkType.average).name("平均值"));
    option.series(bar);
    return option;
  }

  public Option selectProjectBarPicture(String[] project)  {
    List<BugNumObject> olbugNumPerMonthList = new ArrayList<>();
    for(int i=0;i<project.length;i++){
      olbugNumPerMonthList.addAll(this.getProjectList(project[i]));
    }

    List yList=new ArrayList<>();
    List xList=new ArrayList<>();

    for (BugNumObject bugNumPerMonth:olbugNumPerMonthList){
      yList.add(bugNumPerMonth.getBugNum());
      xList.add(bugNumPerMonth.getProject());
    }
    //定义Option对象
    Option option = new Option();
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
    bar.itemStyle().normal().color("#1780C7").label().show(true).textStyle().color("#DD002C");
    bar.setData(yList);
    bar.markLine().data(new PointData().type(MarkType.average).name("平均值"));
    option.series(bar);
    return option;
  }


}

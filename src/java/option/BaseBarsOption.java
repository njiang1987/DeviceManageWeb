package com.youzan.datashow.option;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.MarkType;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Bar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adrian on 16/5/19.l
 */
public  class BaseBarsOption {


    Option initOption(Option option) {
        option.tooltip().trigger(Trigger.axis);
        option.toolbox().show(true).feature(Tool.dataView, new MagicType(Magic.bar, Magic.line).show(true), Tool.saveAsImage);
        option.calculable(true);
        option.yAxis(new ValueAxis());
        return option;
    }



    Option popUpOption(Option option, ArrayList<String> barNameList,ArrayList<ArrayList> yList) {
        for(int i = 0;i < yList.size(); i ++) {
            Bar bar = new Bar(barNameList.get(i));
            bar.setData(yList.get(i));
            bar.markPoint().data(new PointData().type(MarkType.max).name("最大值"), new PointData().type(MarkType.min).name("最小值"));
            bar.markLine().data(new PointData().type(MarkType.average).name("平均值"));
            option.series(bar);
        }
        return option;
    }

}

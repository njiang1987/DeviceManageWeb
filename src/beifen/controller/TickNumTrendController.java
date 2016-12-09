package com.youzan.datashow.controller;

import com.alibaba.fastjson.JSON;
import com.github.abel533.echarts.Option;
import com.youzan.datashow.service.TickNumTrendService;
import com.youzan.datashow.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

/**
 * Created by adrian on 4/21/16.
 */
@Controller
public class TickNumTrendController {

    @Autowired
    private TickNumTrendService tickNumTrendService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView showTicksTrend(@RequestParam(value = "startDate")  String startDate,
                                       @RequestParam(value = "endDate")  String endDate,
                                       @RequestParam(value = "project",required = false)  String project,
                                       HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView("index");

        if((startDate==null || startDate.isEmpty())&&(endDate==null || endDate.isEmpty())){
            endDate = TimeUtil.getCurrentTime();
            startDate= TimeUtil.getAdjustTime(Calendar.DATE, -700).substring(0,10)+" 00:00:00";
        }
        else{
            startDate = startDate+" 00:00:00";
            endDate = endDate + " 23:59:59";
        }


        if((project==null || project.isEmpty())){
            project = "10032";

        }

        Option optionLine = tickNumTrendService.selectLinePicture(startDate,endDate,project);
        String optLine = JSON.toJSONString(optionLine);
        mv.addObject("optLine", optLine);
        return mv;
    }
}

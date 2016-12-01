package com.youzan.datashow.controller;

import com.github.abel533.echarts.Option;
import com.youzan.datashow.service.*;
import com.youzan.datashow.util.TimeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;



/**
 * Created by adrian on 4/18/16.
 */

@Controller
public class BugNumController {

    @Autowired
    private BugNumService bugNumService;

    @Autowired
    private TickNumTrendService tickNumTrendService;


    @Autowired
    private BugReportStatusService bugReportStatusService;

    @Autowired
    private BugResolutionStatusService bugResolutionStatusService;

    @Autowired
    private BugPlatformService bugPlatformService;

    @Autowired
    private TaskDisposeService taskDisposeService;

    @Autowired
    private BugCheckService bugCheckService;

    @Autowired
    private BugSeverityService bugSeverityService;

    @Autowired
    private BugFixedService bugFixedService;

    @Autowired
    private BugProjectService bugProjectService;

    @Autowired
    private BugDevService bugDevService;

    @RequestMapping(value = "/mass")
    public ModelAndView showBugPerProject(@RequestParam(value = "startDate" ,required = false)  String startDate,
                                          @RequestParam(value = "endDate",required = false)  String endDate,
                                          @RequestParam(value = "project",required = false)  String project,
                                          HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView("index");

//        Logger logger = Logger.getLogger(BugNumController.class);


        if(startDate==null || startDate.isEmpty() || startDate==""){
            startDate= TimeUtil.getAdjustTime(Calendar.DATE, -365).substring(0,10)+" 00:00:00";
        }
        else{
            startDate = startDate+" 00:00:00";
        }

        if(endDate==null || endDate.isEmpty() || endDate==""){
            endDate = TimeUtil.getCurrentTime();
        }
        else{
            endDate = endDate + " 23:59:59";
        }


        String projectName ="";

        if((project==null || project.isEmpty())){
            project = "10032";

        }

//        logger.info(project);
//        logger.info(bugStatus.getBugResolutionStatus());

        //各业务线bug
        Option optionBar = bugNumService.selectBarPicture(startDate,endDate,project);
        String optBar = JSON.toJSONString(optionBar);

        //待验证BUG
        Option optionCheck=bugCheckService.selectBarPicture(startDate,endDate,project);
        String optCheck=JSON.toJSONString(optionCheck);

        //任务提交与解决数跟踪
        Option optionTrend=tickNumTrendService.selectLinePicture(startDate,endDate,project);
        String optTrend=JSON.toJSONString(optionTrend);

        //bug提交状态
        Option optionReport=bugReportStatusService.selectBarPicture(startDate,endDate,project);
        String optReport=JSON.toJSONString(optionReport);

        //未解决BUG平台分布
        Option optionPlatform=bugPlatformService.selectBarPicture(startDate,endDate,project);
        String optPlatform=JSON.toJSONString(optionPlatform);

         //bug解决状况
        Option optionResolution=bugResolutionStatusService.selectLinePicture(startDate,endDate,project);
        String optResolution=JSON.toJSONString(optionResolution);

        //任务响应时间
        Option optionDispose=taskDisposeService.selectBarPicture(startDate,endDate,project);
        String optDispose=JSON.toJSONString(optionDispose);

        //BUG严重程度
        Option optionSeverity=bugSeverityService.selectBarPicture(startDate,endDate,project);
        String optSeverity=JSON.toJSONString(optionSeverity);

        //BUG解决天数
        Option optionFixed=bugFixedService.selectBarPicture(startDate,endDate,project);
        String optFixed=JSON.toJSONString(optionFixed);

        //哪个开发最辛苦
        Option optionDev=bugDevService.selectBarPicture(startDate,endDate,project);
        String optDev=JSON.toJSONString(optionDev);

        //项目列表
        HashMap<String, String>  optionProject=bugProjectService.selectBarPicture();

        projectName =optionProject.get(project);




//        logger.info(startDate);
//        logger.info(endDate);
//        logger.info(optResolution);

        //把所选项目和所选日期传到前端
        mv.addObject("projectName",projectName);
        mv.addObject("startdate_new",startDate);
        mv.addObject("enddate_new",endDate);

        mv.addObject("optCheck", optCheck);
        mv.addObject("optionBar", optBar);
        mv.addObject("optTrend",optTrend);
        mv.addObject("optReport",optReport);
        mv.addObject("optPlatform",optPlatform);
        mv.addObject("optResolution",optResolution);
        mv.addObject("optDispose",optDispose);
        mv.addObject("optSeverity",optSeverity);
        mv.addObject("optFixed",optFixed);
        mv.addObject("optProject",optionProject);
        mv.addObject("optDev",optDev);

        //取消饼图
        /*
        Option optionPie = bugNumService.selectPiePicture();
        String optPie = JSON.toJSONString(optionPie);
        mv.addObject("optionPie", optPie);*/

        //取项目名
        packageData doPackage =new packageData();
        HashMap<String,String> projectlist = new HashMap<String,String>();
        projectlist = doPackage.selectMachineModifyInfo("select id,projectname from project order by id DESC");
        mv.addObject("projectlist", projectlist);

        return mv;
    }


}
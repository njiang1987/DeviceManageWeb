package com.youzan.datashow.controller;

import com.alibaba.fastjson.JSON;
import com.github.abel533.echarts.Option;
import com.youzan.datashow.service.OfflineBugNumService;
import com.youzan.datashow.service.OfflineBussnissBugNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

/**
 * Created by sunwenting on 16/5/26.
 */

@Controller
public class SendMailSuccessController {


  @RequestMapping(value = "/success", method = RequestMethod.GET)
  public ModelAndView showBugPerProject() throws Exception {
    ModelAndView mv = new ModelAndView("success");

    //取项目名
    packageData doPackage =new packageData();
    HashMap<String,String> projectlist = new HashMap<String,String>();
    projectlist = doPackage.selectMachineModifyInfo("select id,projectname from project order by id DESC");
    mv.addObject("projectlist", projectlist);


    return mv;
  }



}

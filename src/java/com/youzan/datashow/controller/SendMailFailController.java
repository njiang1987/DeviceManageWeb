package com.youzan.datashow.controller;

import com.alibaba.fastjson.JSON;
import com.github.abel533.echarts.Option;
import com.youzan.datashow.service.OfflineBugNumService;
import com.youzan.datashow.service.OfflineBussnissBugNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sunwenting on 16/5/26.
 */

@Controller
public class SendMailFailController {

  @RequestMapping(value = "/fail", method = RequestMethod.GET)
  public ModelAndView showBugPerProject(@RequestParam(value = "platform",required = false)  String platform,
                                        @RequestParam(value = "projectname",required = false)  String projectname,
                                        @RequestParam(value = "svn",required = false)  String svn,
                                        @RequestParam(value = "packagetype",required = false)  String packagetype,
                                        @RequestParam(value = "alias",required = false)  String alias,
                                        @RequestParam(value = "aliasExist",required = false)  String aliasExist,
                                        @RequestParam(value = "mobilePlatform",required = false)  String mobilePlatform,
                                        @RequestParam(value = "model",required = false)  String model,
                                        @RequestParam(value = "user",required = false)  String user,
                                        @RequestParam(value = "os",required = false)  String os,
                                        @RequestParam(value = "NO",required = false)  String NO,
                                        @RequestParam(value = "NOexist",required = false)  String NOexist,
                                        @RequestParam(value = "projectName",required = false)  String projectName,
                                        @RequestParam(value = "password",required = false)  String password,
                                        @RequestParam(value = "fromAddress",required = false)  String fromAddress,
                                        @RequestParam(value = "toAddress",required = false)  String toAddress,
                                        @RequestParam(value = "ccList",required = false)  String ccList,
                                        HttpServletRequest request) throws Exception {
    ModelAndView mv = new ModelAndView("fail");

    mv.addObject("platform", platform);
    mv.addObject("projectname", projectname);
    mv.addObject("svn", svn);
    mv.addObject("packagetype", packagetype);
    mv.addObject("alias", alias);
    mv.addObject("aliasExist", aliasExist);

    mv.addObject("mobilePlatform", mobilePlatform);
    mv.addObject("model", model);
    mv.addObject("user", user);
    mv.addObject("os", os);
    mv.addObject("NO", NO);
    mv.addObject("NOexist", NOexist);

    mv.addObject("projectName", projectName);
    mv.addObject("password", password);
    mv.addObject("fromAddress", fromAddress);
    mv.addObject("toAddress", toAddress);
    mv.addObject("ccList", ccList);

    //取项目名
    packageData doPackage =new packageData();
    HashMap<String,String> projectlist = new HashMap<String,String>();
    projectlist = doPackage.selectMachineModifyInfo("select id,projectname from project order by id DESC");
    mv.addObject("projectlist", projectlist);


    return mv;
  }
}

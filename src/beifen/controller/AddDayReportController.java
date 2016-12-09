package com.youzan.datashow.controller;

import com.youzan.datashow.service.BugProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sunwenting on 16/5/26.
 */

@Controller
public class AddDayReportController {

     @Autowired
     private BugProjectService bugProjectService;

  @RequestMapping(value = "/addDayReport", method = RequestMethod.GET)
  public ModelAndView showBugPerProject() throws Exception {
    ModelAndView mv = new ModelAndView("addDayReport");

//项目列表
    HashMap<String, String>  optionProject=bugProjectService.selectBarPicture();
    HashMap<String, String>  UserList=bugProjectService.selectUserName();
    HashMap<String, String>  EmailList=bugProjectService.selectUserEmail();



    mv.addObject("optProject", optionProject);
      mv.addObject("UserList", UserList);
      mv.addObject("EmailList", EmailList);

    //取项目名
    packageData doPackage =new packageData();
    HashMap<String,String> projectlist = new HashMap<String,String>();
    projectlist = doPackage.selectMachineModifyInfo("select id,projectname from project order by id DESC");
    mv.addObject("projectlist", projectlist);

      return mv;
  }


}

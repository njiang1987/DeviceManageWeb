package com.youzan.datashow.controller;

import com.alibaba.fastjson.JSON;
import com.youzan.datashow.service.PackageListService;
import com.youzan.datashow.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;


/**
 * Created by shenzhaohua on 16/8/10.
 */

@Controller
public class MachineListController {
  String Sql ="";

  @Autowired
  private PackageListService packageListService;

  @RequestMapping(value = "/machinelist")
  @ResponseBody
  public String showlist() throws SQLException,IOException {
    return JSON.toJSONString(packageListService.getlist("select * from tel_information  order by id DESC"));
  }
  @RequestMapping(value = "/machine")
  public ModelAndView showPackageListProject() throws Exception {
    ModelAndView mv = new ModelAndView("machine");


    packageData doPackage =new packageData();
    //取得总记录数
    int totalPageSize = doPackage.selectPackage("select count(*) from tel_information");
    mv.addObject("totalPageSize",totalPageSize);
//    mv.addObject("jobExist",jobExist);

    //取项目名
    HashMap<String,String> projectlist = new HashMap<String,String>();
    projectlist = doPackage.selectMachineModifyInfo("select id,projectname from project order by id DESC");
    mv.addObject("projectlist", projectlist);

    return mv;
  }



}

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
import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by shenzhaohua on 16/8/10.
 */

@Controller
public class AndroidHistoryController {
  String Sql ="";
  static final  String JENKINS_ADDRESS ="http://192.168.12.4:8081/";
  static final  String LOCATION_ADDRESS ="http://192.168.12.4:8080/";
  @Autowired
  private PackageListService packageListService;

  @RequestMapping(value = "/historylist")
  @ResponseBody
  public String showlist(@RequestParam(value = "apkKeyWord",required = false)  String apkKeyWord) throws SQLException,IOException {
    return JSON.toJSONString(packageListService.getlist("select * from app_package where branches  ='"+apkKeyWord+"' order by id DESC"));
  }
  @RequestMapping(value = "/androidHistory")
  public ModelAndView showPackageListProject(@RequestParam(value = "historypackaging" ,required = false)  boolean historypackaging,
                                             @RequestParam(value = "pid",required = false)  String pid,
                                        HttpServletRequest request) throws Exception {
    ModelAndView mv = new ModelAndView("androidHistory");

    packageData doPackage =new packageData();
    String apkKeyWord ="";
    apkKeyWord =doPackage.selectStatus("select alias from project where id ="+pid);

    String WEB_ROOT= "E:\\mass\\mass\\src\\main\\webapp\\";
    String APK_DIRECTORY = WEB_ROOT+"app_"+apkKeyWord+"\\";
    String LOG_DIRECTORY = WEB_ROOT+"log_"+apkKeyWord+"\\";
    String JENKINS_DIRECTORY = "E:\\Jenkins\\jobs\\app_"+apkKeyWord+"\\builds\\";
    String JENKINS_config_DIRECTORY = "E:\\Jenkins\\jobs\\app_"+apkKeyWord+"\\";
    String BUILD_NUMBER = JENKINS_ADDRESS+"job/app_"+apkKeyWord+"/lastBuild/buildNumber --user shenzh:shenzh";
    String BUILD_JOB = JENKINS_ADDRESS+"job/app_"+apkKeyWord+"/build?token=12345 --user shenzh:shenzh";


    //取得总记录数
    int totalPageSize = doPackage.selectPackage("select count(*) from app_package where branches ='"+apkKeyWord+"'");
    String curl = "curl  "+BUILD_NUMBER+"";
    int buildNumber = doPackage.getBuildNumber(curl)+1;
    int count = 0;
    String buildSuccesSql ="";
    String Status ="";
    String buildFailSql ="";
    int jobExist =0;
    String keyword ="Car";
    String projectInfo ="";

    for(int i =buildNumber-totalPageSize;i<=buildNumber;i++){
      String path =APK_DIRECTORY+i;
      String apkPath =APK_DIRECTORY+i+"\\apk";
      Sql ="select count(*) from app_package where branches ='"+apkKeyWord+"' and job ="+i+"";
      Status="select jobstatus from app_package where branches ='"+apkKeyWord+"' and job ="+i+"";
      count = doPackage.selectPackage(Sql);
      String logPath = JENKINS_DIRECTORY+i+"\\log";
      if(count !=0&&doPackage.selectStatus(Status).equals("正在打包")){
          String copylog = "cmd.exe /c copy "+JENKINS_DIRECTORY + i + "\\log  "+LOG_DIRECTORY + i;
          Runtime.getRuntime().exec(copylog);

        if(doPackage.buildStatus(logPath)==1){
          String apkName = doPackage.getApkName(apkPath,keyword);
          String apklink="/app_"+apkKeyWord+"/"+i+"/apk/"+apkName;
          String erweima ="/app_"+apkKeyWord+"/"+i+"/"+apkName+".png";
          String url =LOCATION_ADDRESS+"app_"+apkKeyWord+"/";
          buildSuccesSql ="update app_package set jobstatus ='打包成功',apk =\'"+apkName+"\',erweima =\'"+erweima+"\',apklink =\'"+apklink+"\' where branches ='"+apkKeyWord+"' and job ="+i+"";
          doPackage.executePackage(buildSuccesSql);
          doPackage.apkEncode(apkName, path, i,url);
        }
        if(doPackage.buildStatus(logPath)==2){
          buildFailSql ="update app_package set jobstatus ='打包失败',apk ='' where branches ='"+apkKeyWord+"' and job ="+i+"";
          doPackage.executePackage(buildFailSql);

        }

      }

    }

    String Date = TimeUtil.getCurrentTime();
    if(historypackaging) {
      String packagingSql = "select count(*) from app_package where branches ='"+apkKeyWord+"' and job =" + buildNumber;
      if (doPackage.selectPackage(packagingSql) == 1 ) {
//        if (doPackage.selectPackage(packagingSql) == 1 || apkVersion.length ==1) {
          jobExist =1;

      } else {
        Runtime.getRuntime().exec("curl -X GET "+BUILD_JOB+"");
        String logadreass = "/log_"+apkKeyWord+"/" + buildNumber;
        Sql = "insert into app_package(job,apk,createtime,jobstatus,erweima,apklink,log,branches) values(" + buildNumber + ",'<div class=\"loading\" onclick=\"location.reload()\"></div>','" + Date + "','正在打包','','','" + logadreass + "','"+apkKeyWord+"')";
        doPackage.executePackage(Sql);
      }
    }

    mv.addObject("totalPageSize",totalPageSize);
    mv.addObject("jobExist",jobExist);

    //取项目数据
    projectInfo = doPackage.selectMachineInfo("select platform,svn,task,projectname,alias,modify,id from project where id =" + pid + "");
    //取值
    String [] projectInfoSplit = projectInfo.split("\\|");
    mv.addObject("platform", projectInfoSplit[0]);
    mv.addObject("svn", projectInfoSplit[1].split("mobile")[1]);
    mv.addObject("task", projectInfoSplit[2]);
    mv.addObject("projectname", projectInfoSplit[3]);
    mv.addObject("alias", projectInfoSplit[4]);
    mv.addObject("modify", projectInfoSplit[5]);
    mv.addObject("pid", pid);

    //取项目名
    HashMap<String,String> projectlist = new HashMap<String,String>();
    projectlist = doPackage.selectMachineModifyInfo("select id,projectname from project order by id DESC");
    mv.addObject("projectlist", projectlist);

    return mv;
  }



}

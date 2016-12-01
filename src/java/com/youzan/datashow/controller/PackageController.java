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
public class PackageController {
  String Sql ="";
  static final  String WEB_ROOT= "E:\\mass\\mass\\src\\main\\webapp\\";
  static final  String APK_DIRECTORY = WEB_ROOT+"android_apk\\";
  static final  String LOG_DIRECTORY = WEB_ROOT+"log\\";
  static final  String JENKINS_DIRECTORY = "E:\\Jenkins\\jobs\\SteCreate\\builds\\";
  static final  String JENKINS_ADDRESS ="http://192.168.12.4:8081/";
  static final  String LOCATION_ADDRESS ="http://192.168.12.4:8080/";
  static final  String BUILD_NUMBER = JENKINS_ADDRESS+"job/SteCreate/lastBuild/buildNumber --user shenzh:shenzh";
  static final  String BUILD_JOB = JENKINS_ADDRESS+"job/SteCreate/build?token=12345 --user shenzh:shenzh";
  @Autowired
  private PackageListService packageListService;

  @RequestMapping(value = "/packagelist")
  @ResponseBody
  public String showlist() throws SQLException,IOException {
    return JSON.toJSONString(packageListService.getlist("select * from android_list where branches ='dev' order by id DESC"));
  }
  @RequestMapping(value = "/package")
  public ModelAndView showPackageListProject(@RequestParam(value = "packaging" ,required = false)  boolean packaging,
                                        HttpServletRequest request) throws Exception {
    ModelAndView mv = new ModelAndView("package");


    packageData doPackage =new packageData();
    //取得总记录数
    int totalPageSize = doPackage.selectPackage("select count(*) from android_list where branches ='dev'");
    String curl = "curl  "+BUILD_NUMBER+"";
    int buildNumber = doPackage.getBuildNumber(curl)+1;
    int count = 0;
    String buildSuccesSql ="";
    String Status ="";
    String buildFailSql ="";
    int jobExist =0;
    String Date = TimeUtil.getCurrentTime();
    String keyword ="Carme";

    for(int i =buildNumber-totalPageSize;i<=buildNumber;i++){
      String path =APK_DIRECTORY+i;
      String apkPath =APK_DIRECTORY+i+"\\apk";
      Sql ="select count(*) from android_list where branches ='dev' and job =\'"+i+"\'";
      Status="select jobstatus from android_list where branches ='dev' and job ="+i+"";
      count = doPackage.selectPackage(Sql);
      String logPath = JENKINS_DIRECTORY+i+"\\log";
      if(count !=0&&doPackage.selectStatus(Status).equals("正在打包")){
          String copylog = "cmd.exe /c copy "+JENKINS_DIRECTORY + i + "\\log  "+LOG_DIRECTORY + i;
          Runtime.getRuntime().exec(copylog);

        if(doPackage.buildStatus(logPath)==1){
          String apkName = doPackage.getApkName(apkPath,keyword);
          String apklink="/android_apk/"+i+"/apk/"+apkName;
          String erweima ="/android_apk/"+i+"/"+apkName+".png";
          String url =LOCATION_ADDRESS+"android_apk/";
          buildSuccesSql ="update android_list set jobstatus ='打包成功',apk =\'"+apkName+"\',erweima =\'"+erweima+"\',apklink =\'"+apklink+"\' where branches ='dev' and job ="+i+"";
          doPackage.executePackage(buildSuccesSql);
            doPackage.apkEncode(apkName, path, i,url);
        }
        if(doPackage.buildStatus(logPath)==2){
          buildFailSql ="update android_list set jobstatus ='打包失败',apk ='' where branches ='dev' and job ="+i+"";
          doPackage.executePackage(buildFailSql);

        }

      }

    }

    if(packaging) {
      String packagingSql = "select count(*) from android_list where branches ='dev' and job =" + buildNumber;
      if (doPackage.selectPackage(packagingSql) == 1) {
        jobExist =1;

      } else {
        Runtime.getRuntime().exec("curl -X GET "+BUILD_JOB+"");
        String logadreass = "/log/" + buildNumber;
        Sql = "insert into android_list(job,apk,createtime,jobstatus,erweima,apklink,log,branches) values(" + buildNumber + ",'<div class=\"loading\" onclick=\"location.reload()\"></div>','" + Date + "','正在打包','','','" + logadreass + "','dev')";
        doPackage.executePackage(Sql);
      }
    }
    mv.addObject("totalPageSize",totalPageSize);
    mv.addObject("jobExist",jobExist);

    //取项目名
    HashMap<String,String> projectlist = new HashMap<String,String>();
    projectlist = doPackage.selectMachineModifyInfo("select id,projectname from project order by id DESC");
    mv.addObject("projectlist", projectlist);

    return mv;
  }



}

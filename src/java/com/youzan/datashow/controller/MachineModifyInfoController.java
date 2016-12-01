package com.youzan.datashow.controller;

import com.alibaba.fastjson.JSON;
import com.youzan.datashow.domain.BugProjectObject;
import com.youzan.datashow.service.PackageListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;


/**
 * Created by shenzhaohua on 16/8/10.
 */

@Controller
public class MachineModifyInfoController {

  @RequestMapping(value = "/machineModifyInfo", method = RequestMethod.GET)
  public ModelAndView showMachineList(@RequestParam(value = "mid" ,required = false)   String mid,
                                             HttpServletRequest request) throws Exception {
    ModelAndView mv = new ModelAndView("machineModifyInfo");


    packageData doPackage =new packageData();
    HashMap<String,String> machineInfo = new HashMap<String,String>();
    String machineModify ="";
    if(mid !=null) {
      machineInfo = doPackage.selectMachineModifyInfo("select modifytime,info from machine_modify_info mmi LEFT JOIN machine ma ON ma.id = mmi.mid where mmi.mid =" + mid + "");
//      Iterator iter = machineInfo.keySet().iterator();
//      while (iter.hasNext()) {
//        Object key = iter.next();
//        Object val = machineInfo.get(key);
//        }


      machineModify = doPackage.selectMachineInfo("select platform,model,nub,user,modify,id,os from machine where id =" + mid + "");
      //取值
      String [] machineModifySplit = machineModify.split("\\|");
      mv.addObject("platform", machineModifySplit[0]);
      mv.addObject("model", machineModifySplit[1]);
      mv.addObject("nub", machineModifySplit[2]);
      mv.addObject("user", machineModifySplit[3]);
      mv.addObject("os", machineModifySplit[6]);
      mv.addObject("machineInfo", machineInfo);
    }

    //取项目名
    HashMap<String,String> projectlist = new HashMap<String,String>();
    projectlist = doPackage.selectMachineModifyInfo("select id,projectname from project order by id DESC");
    mv.addObject("projectlist", projectlist);

    return mv;
  }



}

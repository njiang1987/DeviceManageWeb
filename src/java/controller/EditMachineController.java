package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by sunwenting on 16/5/26.
 */

@Controller
public class EditMachineController {

     @Autowired
     private BugProjectService bugProjectService;

  @RequestMapping(value = "/editMachine", method = RequestMethod.GET)
  public ModelAndView  EditMachine(@RequestParam(value = "edit" ,required = false)  String edit,
                                        HttpServletRequest request) throws Exception {
    ModelAndView mv = new ModelAndView("editMachine");


      packageData doPackage =new packageData();
      String machineInfo ="";
      if(edit !=null) {
          machineInfo = doPackage.selectMachineInfo("select id,platform,model,nub,user,modify,os from machine where id =" + edit + "");
          //取值
          String [] machineInfoSplit = machineInfo.split("\\|");
          mv.addObject("id", machineInfoSplit[0]);
          mv.addObject("platform", machineInfoSplit[1]);
          mv.addObject("model", machineInfoSplit[2]);
          mv.addObject("nub", machineInfoSplit[3]);
          mv.addObject("user", machineInfoSplit[4]);
          mv.addObject("modify", machineInfoSplit[5]);
          mv.addObject("os", machineInfoSplit[6]);
      }

      //取项目名
      HashMap<String,String> projectlist = new HashMap<String,String>();
      projectlist = doPackage.selectMachineModifyInfo("select id,projectname from project order by id DESC");
      mv.addObject("projectlist", projectlist);

      return mv;
  }


}

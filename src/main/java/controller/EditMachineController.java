package controller;

//import service.BugProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import service.packageData;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by sunwenting on 16/5/26.
 */

@Controller
public class EditMachineController {

     @Autowired
//     private BugProjectService bugProjectService;

  @RequestMapping(value = "/editMachine", method = RequestMethod.GET)
  public ModelAndView  EditMachine(HttpServletRequest request) throws Exception {
    ModelAndView mv = new ModelAndView("editMachine");


      packageData doPackage =new packageData();
      String machineInfo ="";
    
          machineInfo = doPackage.selectMachineInfo("select * from tel_information");
          
//          //取值
//          String [] machineInfoSplit = machineInfo.split("\\|");
//          mv.addObject("id", machineInfoSplit[0]);
//          mv.addObject("create_time", machineInfoSplit[1]);
//          mv.addObject("plantform", machineInfoSplit[2]);
//          mv.addObject("imei", machineInfoSplit[3]);
//          mv.addObject("tel_name", machineInfoSplit[4]);
//          mv.addObject("tel_version", machineInfoSplit[5]);
//          mv.addObject("borrow_name", machineInfoSplit[6]);
//          mv.addObject("borrow_time", machineInfoSplit[7]);
//          mv.addObject("operation", machineInfoSplit[8]);
//      
//
//      //取项目名
////      HashMap<String,String> projectlist = new HashMap<String,String>();
//      projectlist = doPackage.selectMachineModifyInfo("select id,projectname from project order by id DESC");
//        mv.addObject("projectlist", machineInfo);
        return mv;
  }


}

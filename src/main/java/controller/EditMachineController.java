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
          

        return mv;
  }


}

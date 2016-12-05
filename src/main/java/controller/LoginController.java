package controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import model.LoginForm;

@Controller
public class LoginController {
    @RequestMapping(value="login")
    public ModelAndView login(HttpServletRequest request,HttpServletResponse response,LoginForm command ){
        String username = command.getUsername();
        ModelAndView mv = new ModelAndView("index/index","command","LOGIN SUCCESS, " + username);
        return mv;
    }
    @RequestMapping(value="add")
    public ModelAndView getAddTel(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView("device/add");
        return mv;
    }
}
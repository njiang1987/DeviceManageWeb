package service;

/**
 * Created by wanghua on 16/7/16.
 */

import util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Enumeration;



public class EditMachineServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置字符编码
    	
    	String create_time=null;
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        create_time = TimeUtil.getCurrentTime();
        
//      System.out.println("当前时间"+create_time);
        
        //从页面获取参数
        String id = request.getParameter("id");
        String plantform = request.getParameter("plantform");
        
        String imei = request.getParameter("imei");
        String tel_version = request.getParameter("tel_version");
        String borrow_name = request.getParameter("borrow_name");
        String borrow_time = request.getParameter("borrow_time");
        String tel_name=request.getParameter("tel_name");
        
        packageData doPackage = new packageData(); 
        
        doPackage.executePackage("insert into tel_information(plantform,imei,tel_version,borrow_name,tel_name,borrow_time) values('"+ plantform + "','" + imei + "','" + tel_version + "','" + borrow_name + "','" + tel_name + "','" + borrow_time + "')");
        
        System.out.println("插入成功。");
        response.sendRedirect("success");
//        int NOexist = doPackage.selectPackage("select count(*) from monitor where tel_version ='" + tel_version + "'");
//
//        if (plantform == ""  || imei.length() == 0 || borrow_name.length() == 0 ) {
//            System.out.println("平台、机型、使用人均不能为空。");
//            response.sendRedirect("fail");
//        } else if (NOexist >= 1&& id.length()== 0&&tel_version.length() != 0 ) {
//            System.out.println("编号已经存在。");
//            response.sendRedirect("fail");
//        } else if(id.length()== 0){
//            //插入数据
//            doPackage.executePackage("insert into machine(plantform,imei,tel_version,borrow_name,borrow_time) values('" + plantform + "','" + imei + "','" + create_time + "','" + create_time + "','" + borrow_name + "','" + borrow_name + "','" + tel_version + "')");
//            System.out.println("插入成功。");
//            response.sendRedirect("success");
//        }
//        else {
//            //更新数据
//            doPackage.executePackage("update machine set platform ='" + platform + "',model='" + model + "',modify='" + modify + "',user='" + user + "',nub='" + bianhao + "' where id=" + id + "");
//            doPackage.executePackage("insert into machine_modify_info(mid,modifytime,info) values('" + id + "','" + Date + "','借用人->" + user + ",借用日期->" + modify + "')");
//            System.out.println("更新成功。");
//            response.sendRedirect("success  

    }
}

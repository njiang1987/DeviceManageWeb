package com.youzan.datashow.controller;

/**
 * Created by shenzhaohua on 16/7/16.
 */

import com.youzan.datashow.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.util.Calendar;



public class EditMachineServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }


        public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置字符编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String Date = TimeUtil.getCurrentTime();
        //从页面获取参数
        String id = request.getParameter("id");
        String platform = request.getParameter("platform");
        String model = request.getParameter("model");
        String bianhao = request.getParameter("NO").trim();
        String user = request.getParameter("user");
        String modify = request.getParameter("modify");

        packageData doPackage = new packageData();
        int NOexist = doPackage.selectPackage("select count(*) from machine where nub ='" + bianhao + "'");

        if (platform == ""  || model.length() == 0 || user.length() == 0 ) {
            System.out.println("平台、机型、使用人均不能为空。");
            response.sendRedirect("fail");
        } else if (NOexist >= 1&& id.length()== 0&&bianhao.length() != 0 ) {
            System.out.println("编号已经存在。");
            response.sendRedirect("fail");
        } else if(id.length()== 0){
            //插入数据
            doPackage.executePackage("insert into machine(platform,model,createtime,modify,owner,user,nub) values('" + platform + "','" + model + "','" + Date + "','" + Date + "','" + user + "','" + user + "','" + bianhao + "')");
            System.out.println("插入成功。");
            response.sendRedirect("success");
        }else {
            //更新数据
            doPackage.executePackage("update machine set platform ='" + platform + "',model='" + model + "',modify='" + modify + "',user='" + user + "',nub='" + bianhao + "' where id=" + id + "");
            doPackage.executePackage("insert into machine_modify_info(mid,modifytime,info) values('" + id + "','" + Date + "','借用人->" + user + ",借用日期->" + modify + "')");
            System.out.println("更新成功。");
            response.sendRedirect("success");
        }
        }




}

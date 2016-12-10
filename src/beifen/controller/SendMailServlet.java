package com.youzan.datashow.controller;

/**
 * Created by shenzhaohua on 16/7/16.
 */

import com.youzan.datashow.util.TimeUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;



public class SendMailServlet extends HttpServlet {

      String sql ="";
      String Graph_Title ="";

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
        String TwoWeek= TimeUtil.getAdjustTime(Calendar.DATE, -23).substring(0,10)+" 00:00:00";

        String content_Graph="";


        //从页面获取参数
        String userNameEmail = request.getParameter("username");
        String password =request.getParameter("password");
        String toAddressInputTxt =request.getParameter("toaddressInput").trim();
        String toAddressInput =request.getParameter("toaddressInput").trim().replace(" ","");
        String[] toAddressSelect =request.getParameterValues("toaddressSelect");
        String[] project =request.getParameterValues("project");
        String total =request.getParameter("total");
        String jindu =request.getParameter("jindu");
        String description =request.getParameter("description");
        String startDate =request.getParameter("startdate");
        String ifcc =request.getParameter("tester");
        String test1 =request.getParameter("test1");
        String test2 =request.getParameter("test2");
        String test3 =request.getParameter("test3");
        String test4 =request.getParameter("test4");

        String projectid ="";
        String projectName ="";
        String toAddressTmp ="";
        String toAddressTotal="";

        if(toAddressSelect!=null) {
            for (int i = 0; i < toAddressSelect.length; i++) {
                toAddressTmp = toAddressTmp + toAddressSelect[i] + ",";
            }
        }

        if(project!=null) {
            for (int i = 0; i < project.length; i++) {
                String projectTmp[] = project[i].split("\\|");
                if (i == 0) {
                    projectName = projectTmp[0];
                    projectid = projectTmp[1];
                } else {
                    projectName = projectName + "," + projectTmp[0];
                    projectid = projectid + "," + projectTmp[1];
                }
            }
        }


        String userName[] = userNameEmail.split("\\|");

        String Graph_Platform="";
        String Graph_Dev="";
        String Graph_Severity="";
        String Graph_Everyday="";
        String[] toAddress = new String[]{};
        String[] AddressInput  =toAddressInput.split(",");
        String[] ccList =new String[]{};

        if(startDate.length() ==0){
            startDate ="2016-03-01";
        }
        startDate =startDate+" 00:00:00";

        //拼接发送邮件地址
        if(toAddressInput.length()==0){

                toAddress = toAddressSelect;
            toAddressTotal =toAddressTmp;

        }else {
                toAddress =AddressInput;
            toAddressTotal =toAddressInputTxt;
        }



        //各平台未解决bug分布图生成
        sql="SELECT cfo.customvalue as project ,count(cfo.customvalue) as bugNum FROM jiraissue ji INNER JOIN project pr ON pr.ID = ji.PROJECT LEFT JOIN customfieldvalue cfv ON cfv.ISSUE =  ji.ID LEFT JOIN customfieldoption cfo ON cfv.STRINGVALUE = cfo.ID where pr.ID in("+projectid+") and cfo.CUSTOMFIELD =10106 and issuetype = 1 and issuestatus in(10012,1,4) AND CREATED between '"+startDate+"' and '"+Date+"' group by cfo.customvalue";
        Graph_Title ="※各平台未解决bug分布※";
        CreateGraph Graph_add = new CreateGraph();
        Graph_Platform = Graph_add.CreateGraphDo(sql, Graph_Title);

        //未解决bug归属分布图生成
        sql = "select last_name as bugReportStatus,count(ASSIGNEE) as bugNum " +
                "FROM jiraissue ji INNER JOIN project pr ON pr.ID = ji.PROJECT LEFT JOIN cwd_user cu ON ji.ASSIGNEE = cu.user_name  where pr.ID in ("+projectid+") and issuestatus in(10012,1,4) and issuetype = 1 and ASSIGNEE is not NULL AND CREATED between '"+startDate+"' and '"+Date+"' group by ASSIGNEE " +
                "UNION ALL\n" +
                "select last_name as bugReportStatus,count(*) as bugNum " +
                "FROM jiraissue ji INNER JOIN project pr ON pr.ID = ji.PROJECT LEFT JOIN cwd_user cu ON ji.ASSIGNEE = cu.user_name  where pr.ID in ("+projectid+") and issuestatus in(10012,1,4) and issuetype = 1 and ASSIGNEE is NULL AND CREATED between '"+startDate+"' and '"+Date+"' group by ASSIGNEE";
        Graph_Title ="※未解决bug归属分布※";
        Graph_Dev = Graph_add.CreateGraphDo(sql, Graph_Title);

        //未解决bug严重程度分布图生成
        sql = "select cfo.customvalue as bugSeverity ,count(cfo.customvalue) as bugNum FROM jiraissue ji INNER JOIN project pr ON pr.ID = ji.PROJECT LEFT JOIN customfieldvalue cfv ON cfv.ISSUE =  ji.ID LEFT JOIN customfieldoption cfo ON cfv.STRINGVALUE = cfo.ID where pr.ID in ("+projectid+") and cfo.CUSTOMFIELD =10104 and issuetype = 1 and issuestatus in(10012,1,4) AND CREATED between '"+startDate+"' and '"+Date+"' group by cfo.customvalue";
        Graph_Title ="※未解决bug严重程度分布※";
        Graph_Severity = Graph_add.CreateGraphDo(sql, Graph_Title);

        //每日新增bug分布图生成
        sql = "select Date(CREATED) as bugNewDate, COUNT(Date(CREATED))  as bugNum from jiraissue ji INNER JOIN project pr ON pr.ID = ji.PROJECT  where pr.ID in ("+projectid+") and issuetype = 1  and CREATED between '"+TwoWeek+"' and '"+Date+"' group by Date(CREATED)";
        Graph_Title ="※每日新增bug分布图(最近一个月)※";
        Graph_Everyday = Graph_add.CreateGraphDo(sql, Graph_Title);


//生成日报

        String subject =projectName+"_项目日报_"+Date+"_"+userName[1];
        String content ="";
        String content_Description="<table width=800 border=\"1\" align=\"left\"><tr style=\"background:#e1e1e1;\" align=\"center\"><td><b><font color=\"#FF0000\">"+subject+"</font></b></td></tr>\n" +
                "<tr  align=\"left\"><td>\n" +
                "<table width=800>\n" +
                "<tr style=\"background:#e1e1e1;\"><td width=\"100\">测试进度</td><td width=\"700\">"+jindu+"</td></tr>\n" +
                "<tr style=\"background:#e1e1e1;\"><td width=\"100\">质量打分</td><td width=\"700\">"+total+"</td></tr>\n" +
                "<tr style=\"background:#e1e1e1;\"><td width=\"100\">质量描述</td><td width=\"700\">"+description+"</td></tr></table>\n" +
                "</tr>";


        if(test1!= null && Graph_Platform !=""){
            content_Graph = content_Graph + Graph_Platform;
        }
        if(test2!= null&& Graph_Dev !=""){
            content_Graph = content_Graph + Graph_Dev;
        }
        if(test3!= null&& Graph_Severity !=""){
            content_Graph = content_Graph + Graph_Severity;
        }
        if(test4!= null && Graph_Everyday !=""){
            content_Graph = content_Graph + Graph_Everyday;
        }

        //是否抄送
        sql ="SELECT cwdu.email_address as email from cwd_membership cwdm LEFT  JOIN cwd_user cwdu ON cwdm.child_id =cwdu.ID where parent_name ='jira-tester' and email_address not in ('duanzy@car-me.com','qinnn@car-me.com')";

        if(ifcc != null){
            ccList = Graph_add.ccList(sql).split(",");
        }

        content=content_Description+content_Graph+"</table>";


        //发送方的邮箱地址
        String fromAddress = userName[0];

        MultiMailSenderInfo mailInfo = new MultiMailSenderInfo();
        mailInfo.setMailServerHost("smtphz.qiye.163.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName(fromAddress);
        mailInfo.setPassword(password);
        mailInfo.setFromAddress(fromAddress);
        mailInfo.setToAddress(userName[0]);
        mailInfo.setSubject(subject);
        mailInfo.setContent(content);

        mailInfo.setReceivers(toAddress);

        mailInfo.setCcs(ccList);
        boolean flag = false;
        flag =MultiMailsender.sendMailtoMultiCC(mailInfo);

        //如果邮件发送成功，则跳转到success.jsp页面；否则，跳转到fail.jsp页面
        if(flag&&project!=null)
        {
            System.out.println("发送html格式mail 成功！");
            response.sendRedirect("success");
        }
        else
        {
            System.out.println("发送html格式mail 失败！");
            response.sendRedirect("fail?projectName="+projectName+"&password="+password+"&fromAddress="+fromAddress+"&toAddress="+toAddressTotal+"&ccList="+Graph_add.ccList(sql)+"");
        }

    }

}

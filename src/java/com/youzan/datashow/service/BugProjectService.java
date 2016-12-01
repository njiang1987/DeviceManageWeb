package com.youzan.datashow.service;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.AxisLabel;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Map;
import com.youzan.datashow.domain.BugCheckObject;
import com.youzan.datashow.domain.BugProjectObject;
import com.youzan.datashow.domain.UserEmailObject;
import com.youzan.datashow.domain.UserNameObject;
import com.youzan.datashow.mapper.BugCheckMapper;
import com.youzan.datashow.mapper.BugProjectMapper;
import com.youzan.datashow.mapper.UserEmailMapper;
import com.youzan.datashow.mapper.UserNameMapper;
import com.youzan.datashow.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by adrian on 16/5/17.
 */
@Service
public class BugProjectService {

    @Autowired
    private BugProjectMapper bugProjectMapper;
    @Autowired
    private UserNameMapper userNameMapper;
    @Autowired
    private UserEmailMapper userEmailMapper;

    List<BugProjectObject> getList(){
        return bugProjectMapper.getList();
    };
    List<UserNameObject> getUserList(){
        return userNameMapper.getList();
    };
    List<UserEmailObject> getEmailList(){
        return userEmailMapper.getList();
    };



    public HashMap selectBarPicture()  {
        List<BugProjectObject> ProjectList;

        ProjectList=this.getList();

        HashMap map=new HashMap<String, String>();
        for (BugProjectObject bugStatus:ProjectList){
            map.put(bugStatus.getProjectKey(),bugStatus.getProjectName());
        }

        return map;

    }

    public HashMap selectUserName()  {
        List<UserNameObject> UserList;

        UserList=this.getUserList();
        HashMap map=new HashMap<String, String>();
        for (UserNameObject Users:UserList){
            map.put(Users.getUserName(),Users.getEngName());
        }

        return map;

    }

    public HashMap selectUserEmail()  {
        List<UserEmailObject> EmailList;

        EmailList=this.getEmailList();
        HashMap map=new HashMap<String, String>();
        for (UserEmailObject Emails:EmailList){
            map.put(Emails.getUserName(),Emails.getUserEmail());
        }

        return map;

    }
}

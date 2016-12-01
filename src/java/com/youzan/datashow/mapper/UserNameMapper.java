package com.youzan.datashow.mapper;

import com.youzan.datashow.domain.BugProjectObject;
import com.youzan.datashow.domain.UserNameObject;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by adrian on 16/5/17.
 */
public interface UserNameMapper {


        @Select("SELECT cwdu.last_name as userName , cwdu.email_address as engName from cwd_membership cwdm LEFT  JOIN cwd_user cwdu ON cwdm.child_id =cwdu.ID where parent_name ='jira-tester' and email_address !='duanzy@car-me.com' ")
        List<UserNameObject> getList();

}

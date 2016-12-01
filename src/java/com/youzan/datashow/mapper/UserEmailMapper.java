package com.youzan.datashow.mapper;

import com.youzan.datashow.domain.UserEmailObject;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by adrian on 16/5/17.
 */
public interface UserEmailMapper {


        @Select("SELECT cwdu.last_name as userName , cwdu.email_address as userEmail from cwd_membership cwdm LEFT  JOIN cwd_user cwdu ON cwdm.child_id =cwdu.ID where last_name not in('管理员','理员','付伟伟','彭金艳','王林焱','张洋','liyue','秦娜娜','王敏','孟岍') and parent_name in ('jira-developers','jira-PM') and parent_name not in ('jira-tester')")
        List<UserEmailObject> getList();

}

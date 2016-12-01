package com.youzan.datashow.mapper;

import com.youzan.datashow.domain.BugCheckObject;
import com.youzan.datashow.domain.BugProjectObject;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by adrian on 16/5/17.
 */
public interface BugProjectMapper {


        @Select("select pname as projectName,ID as projectKey from project where ID >10030 ORDER BY pname DESC ")
                List<BugProjectObject> getList();

}

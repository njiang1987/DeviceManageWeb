package com.youzan.datashow.domain;

import lombok.Data;

/**
 * Created by adrian on 16/5/17.
 */
@Data
public class TaskDisposeObject {
    private String project;
    private Integer bugNum;
    private float firstResponseTime;
    private float averageResoultionTime;
}

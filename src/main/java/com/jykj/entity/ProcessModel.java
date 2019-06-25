package com.jykj.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ProcessModel {

    private String id;

    private String rev;

    private String name;

    private String key1;

    private Date createTime;

    private Date lastUpdateTime;

}

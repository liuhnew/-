package com.jykj.entity;

import java.util.Date;

public class Log {

    public final static String UPDATE_ACTION="更新";
    public final static String ADD_ACTION="添加";
    public final static String IMPORT_ACTION="导入";
    public final static String EXPORT_ACTION="导出";
    public final static String DELETE_ACTION="删除";

    private Integer id;

    private String content;

    private Date time;

    private String type;

    private String userId;

    public Log(String type, String content) {
        this.content = content;
        this.type = type;
    }

    public Log(Integer id, String content, Date time, String type, String userId) {
        this.id = id;
        this.content = content;
        this.time = time;
        this.type = type;
        this.userId = userId;
    }

    public Log() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}
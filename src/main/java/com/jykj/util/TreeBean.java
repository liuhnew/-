package com.jykj.util;

import lombok.Data;

import java.util.List;

@Data
public class TreeBean<T> {

    private String id;

    private String text;

    private String pid;

    private T data;

    private List<TreeBean<T>> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<TreeBean<T>> getChildren() {
        return children;
    }

    public void setChildren(List<TreeBean<T>> children) {
        this.children = children;
    }
}

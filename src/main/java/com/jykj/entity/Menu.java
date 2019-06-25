package com.jykj.entity;

import org.bson.types.ObjectId;

public class Menu {
    private ObjectId id;

    private ObjectId app;

    private String title;

    private Integer idx;

    private Integer level;

    private String uri;

    private ObjectId parent;

    private Integer method;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getApp() {
        return app;
    }

    public void setApp(ObjectId app) {
        this.app = app;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public ObjectId getParent() {
        return parent;
    }

    public void setParent(ObjectId parent) {
        this.parent = parent;
    }

    public Integer getMethod() {
        return method;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }
}

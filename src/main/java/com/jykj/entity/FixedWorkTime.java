package com.jykj.entity;

import java.util.Date;

public class FixedWorkTime {
    private String id;

    private String num;

    private String autoType;

    private String name;

    private String serviceType;

    private String itemsType;

    private Float repairItemsTime;

    private Float price;

    private Integer state;

    private String detail;

    private Date createTime;

    private Date updateTime;

    public FixedWorkTime(String id, String num, String autoType, String name, String serviceType, String itemsType, Float repairItemsTime, Float price, Integer state, String detail, Date createTime, Date updateTime) {
        this.id = id;
        this.num = num;
        this.autoType = autoType;
        this.name = name;
        this.serviceType = serviceType;
        this.itemsType = itemsType;
        this.repairItemsTime = repairItemsTime;
        this.price = price;
        this.state = state;
        this.detail = detail;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public FixedWorkTime() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
    }

    public String getAutoType() {
        return autoType;
    }

    public void setAutoType(String autoType) {
        this.autoType = autoType == null ? null : autoType.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType == null ? null : serviceType.trim();
    }

    public String getItemsType() {
        return itemsType;
    }

    public void setItemsType(String itemsType) {
        this.itemsType = itemsType == null ? null : itemsType.trim();
    }

    public Float getRepairItemsTime() {
        return repairItemsTime;
    }

    public void setRepairItemsTime(Float repairItemsTime) {
        this.repairItemsTime = repairItemsTime;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
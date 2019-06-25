package com.jykj.entity;

import java.util.Date;

public class OrderItems {
    private String id;

    private String repairId;

    private String type;

    private String num;

    private String name;

    private String serviceType;

    private String itemType;

    private Float repairItemsTime;

    private Float price;

    private String detail;

    private Date createTime;

    public OrderItems(String id, String repairId, String type, String num, String name, String serviceType, String itemType, Float repairItemsTime, Float price, String detail, Date createTime) {
        this.id = id;
        this.repairId = repairId;
        this.type = type;
        this.num = num;
        this.name = name;
        this.serviceType = serviceType;
        this.itemType = itemType;
        this.repairItemsTime = repairItemsTime;
        this.price = price;
        this.detail = detail;
        this.createTime = createTime;
    }

    public OrderItems() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId == null ? null : repairId.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
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

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType == null ? null : itemType.trim();
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
}
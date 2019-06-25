package com.jykj.entity;

import java.util.Date;

public class Overflow {
    private Integer id;

    private Date overflowDate;

    private String overflowNumber;

    private String remarks;

    private Integer userId;

    public Overflow(Integer id, Date overflowDate, String overflowNumber, String remarks, Integer userId) {
        this.id = id;
        this.overflowDate = overflowDate;
        this.overflowNumber = overflowNumber;
        this.remarks = remarks;
        this.userId = userId;
    }

    public Overflow() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOverflowDate() {
        return overflowDate;
    }

    public void setOverflowDate(Date overflowDate) {
        this.overflowDate = overflowDate;
    }

    public String getOverflowNumber() {
        return overflowNumber;
    }

    public void setOverflowNumber(String overflowNumber) {
        this.overflowNumber = overflowNumber == null ? null : overflowNumber.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
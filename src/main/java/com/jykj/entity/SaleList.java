package com.jykj.entity;

import java.util.Date;

public class SaleList {
    private Integer id;

    private Float amountPaid;

    private Float amountPayable;

    private String remarks;

    private Date saleDate;

    private String saleNumber;

    private Integer state;

    private Integer customerId;

    private Integer userId;

    public SaleList(Integer id, Float amountPaid, Float amountPayable, String remarks, Date saleDate, String saleNumber, Integer state, Integer customerId, Integer userId) {
        this.id = id;
        this.amountPaid = amountPaid;
        this.amountPayable = amountPayable;
        this.remarks = remarks;
        this.saleDate = saleDate;
        this.saleNumber = saleNumber;
        this.state = state;
        this.customerId = customerId;
        this.userId = userId;
    }

    public SaleList() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Float getAmountPayable() {
        return amountPayable;
    }

    public void setAmountPayable(Float amountPayable) {
        this.amountPayable = amountPayable;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public String getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(String saleNumber) {
        this.saleNumber = saleNumber == null ? null : saleNumber.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
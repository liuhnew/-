package com.jykj.entity;

import java.util.Date;

public class Purchase {
    private Integer id;

    private Float amountPaid;

    private Float amountPayable;

    private Date purchaseDate;

    private String purchaseNumber;

    private String remarks;

    private Integer state;

    private Integer supplierId;

    private Integer userId;

    public Purchase(Integer id, Float amountPaid, Float amountPayable, Date purchaseDate, String purchaseNumber, String remarks, Integer state, Integer supplierId, Integer userId) {
        this.id = id;
        this.amountPaid = amountPaid;
        this.amountPayable = amountPayable;
        this.purchaseDate = purchaseDate;
        this.purchaseNumber = purchaseNumber;
        this.remarks = remarks;
        this.state = state;
        this.supplierId = supplierId;
        this.userId = userId;
    }

    public Purchase() {
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

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(String purchaseNumber) {
        this.purchaseNumber = purchaseNumber == null ? null : purchaseNumber.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
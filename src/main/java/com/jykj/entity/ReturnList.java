package com.jykj.entity;

import java.util.Date;

public class ReturnList {
    private Integer id;

    private Float amountPaid;

    private Float amountPayable;

    private String remarks;

    private Date returnDate;

    private String returnNumber;

    private Integer state;

    private Integer supplierId;

    private Integer userId;

    public ReturnList(Integer id, Float amountPaid, Float amountPayable, String remarks, Date returnDate, String returnNumber, Integer state, Integer supplierId, Integer userId) {
        this.id = id;
        this.amountPaid = amountPaid;
        this.amountPayable = amountPayable;
        this.remarks = remarks;
        this.returnDate = returnDate;
        this.returnNumber = returnNumber;
        this.state = state;
        this.supplierId = supplierId;
        this.userId = userId;
    }

    public ReturnList() {
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

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnNumber() {
        return returnNumber;
    }

    public void setReturnNumber(String returnNumber) {
        this.returnNumber = returnNumber == null ? null : returnNumber.trim();
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
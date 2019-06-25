package com.jykj.entity;

import java.util.Date;

public class CustomerReturn {
    private Integer id;

    private Float amountPaid;

    private Float amountPayable;

    private Date customerReturnDate;

    private String customerReturnNumber;

    private String remarks;

    private Integer state;

    private Integer customerId;

    private Integer userId;

    public CustomerReturn(Integer id, Float amountPaid, Float amountPayable, Date customerReturnDate, String customerReturnNumber, String remarks, Integer state, Integer customerId, Integer userId) {
        this.id = id;
        this.amountPaid = amountPaid;
        this.amountPayable = amountPayable;
        this.customerReturnDate = customerReturnDate;
        this.customerReturnNumber = customerReturnNumber;
        this.remarks = remarks;
        this.state = state;
        this.customerId = customerId;
        this.userId = userId;
    }

    public CustomerReturn() {
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

    public Date getCustomerReturnDate() {
        return customerReturnDate;
    }

    public void setCustomerReturnDate(Date customerReturnDate) {
        this.customerReturnDate = customerReturnDate;
    }

    public String getCustomerReturnNumber() {
        return customerReturnNumber;
    }

    public void setCustomerReturnNumber(String customerReturnNumber) {
        this.customerReturnNumber = customerReturnNumber == null ? null : customerReturnNumber.trim();
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
package com.jykj.entity;

import java.util.Date;

public class Damage {
    private Integer id;

    private Date damageDate;

    private String damageNumber;

    private String remarks;

    private Integer userId;

    public Damage(Integer id, Date damageDate, String damageNumber, String remarks, Integer userId) {
        this.id = id;
        this.damageDate = damageDate;
        this.damageNumber = damageNumber;
        this.remarks = remarks;
        this.userId = userId;
    }

    public Damage() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDamageDate() {
        return damageDate;
    }

    public void setDamageDate(Date damageDate) {
        this.damageDate = damageDate;
    }

    public String getDamageNumber() {
        return damageNumber;
    }

    public void setDamageNumber(String damageNumber) {
        this.damageNumber = damageNumber == null ? null : damageNumber.trim();
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
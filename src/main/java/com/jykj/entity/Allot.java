package com.jykj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Allot {
    private Integer id;

    private String goodsName;

    private String orgId;

    private String orgName;

    private String goodsModel;

    private Float price;

    private Integer num;

    private Integer state;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Integer createUserId;

    private String trueName;

    private String remark;

    private String inRepo;

    private String outRepo;

    private String confirmUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmDate;

    private String allotNum;

   public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInRepo() {
        return inRepo;
    }

    public void setInRepo(String inRepo) {
        this.inRepo = inRepo;
    }

    public String getOutRepo() {
        return outRepo;
    }

    public void setOutRepo(String outRepo) {
        this.outRepo = outRepo;
    }

    public String getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(String confirmUser) {
        this.confirmUser = confirmUser;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getAllotNum() {
        return allotNum;
    }

    public void setAllotNum(String allotNum) {
        this.allotNum = allotNum;
    }
}
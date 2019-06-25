package com.jykj.entity;

import java.util.Date;

/**
 *  入库单实体
 *  @created 20190524
 *  @author chengzhao
 */

public class StockOdd {

    private Integer id;

    private String purchaseGoodsNum;//采购收货单号

    private String purchaseNum;//采购单号

    private String purchaseOrg; //采购组织

    private Integer supplierId;

    private String supplierName;

    private String docType; //单据类型

    private Integer docState; //单据状态

    private Date createTime; //制单日期

    private String remark;

    private Integer userId;

    private String trueName;

    private String repository;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPurchaseGoodsNum() {
        return purchaseGoodsNum;
    }

    public void setPurchaseGoodsNum(String purchaseGoodsNum) {
        this.purchaseGoodsNum = purchaseGoodsNum;
    }

    public String getPurchaseNum() {
        return purchaseNum;
    }

    public void setPurchaseNum(String purchaseNum) {
        this.purchaseNum = purchaseNum;
    }

    public String getPurchaseOrg() {
        return purchaseOrg;
    }

    public void setPurchaseOrg(String purchaseOrg) {
        this.purchaseOrg = purchaseOrg;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public Integer getDocState() {
        return docState;
    }

    public void setDocState(Integer docState) {
        this.docState = docState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }
}

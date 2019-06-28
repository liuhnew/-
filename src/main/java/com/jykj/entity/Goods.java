package com.jykj.entity;

public class Goods {
    private Integer id;

    private String code;

    private Integer inventoryQuantity;

    private Float lastPurchasingPrice;

    private Integer minNum;

    private String model;

    private String name;

    private String producer;

    private Float purchasingPrice;

    private String remarks;

    private Integer saleTotal;

    private Float sellingPrice;

    private Integer state;

    private String unit;

    private Integer typeId;

    private String tenantId;

    private String tenantName;

    private String url;

    public Goods(Integer id, String code, Integer inventoryQuantity, Float lastPurchasingPrice, Integer minNum, String model, String name, String producer, Float purchasingPrice, String remarks, Float sellingPrice, Integer state, String unit, Integer typeId, String tenantId, String url) {
        this.id = id;
        this.code = code;
        this.inventoryQuantity = inventoryQuantity;
        this.lastPurchasingPrice = lastPurchasingPrice;
        this.minNum = minNum;
        this.model = model;
        this.name = name;
        this.producer = producer;
        this.purchasingPrice = purchasingPrice;
        this.remarks = remarks;
        this.sellingPrice = sellingPrice;
        this.state = state;
        this.unit = unit;
        this.typeId = typeId;
        this.setTenantId(tenantId);
        this.url = url;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public Integer getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(Integer saleTotal) {
        this.saleTotal = saleTotal;
    }

    public Goods() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(Integer inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public Float getLastPurchasingPrice() {
        return lastPurchasingPrice;
    }

    public void setLastPurchasingPrice(Float lastPurchasingPrice) {
        this.lastPurchasingPrice = lastPurchasingPrice;
    }

    public Integer getMinNum() {
        return minNum;
    }

    public void setMinNum(Integer minNum) {
        this.minNum = minNum;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer == null ? null : producer.trim();
    }

    public Float getPurchasingPrice() {
        return purchasingPrice;
    }

    public void setPurchasingPrice(Float purchasingPrice) {
        this.purchasingPrice = purchasingPrice;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
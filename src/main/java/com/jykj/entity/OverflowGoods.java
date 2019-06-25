package com.jykj.entity;

public class OverflowGoods {
    private Integer id;

    private String code;

    private Integer goodsId;

    private String model;

    private String name;

    private Integer num;

    private Float price;

    private Float total;

    private String unit;

    private Integer overflowListId;

    private Integer typeId;

    public OverflowGoods(Integer id, String code, Integer goodsId, String model, String name, Integer num, Float price, Float total, String unit, Integer overflowListId, Integer typeId) {
        this.id = id;
        this.code = code;
        this.goodsId = goodsId;
        this.model = model;
        this.name = name;
        this.num = num;
        this.price = price;
        this.total = total;
        this.unit = unit;
        this.overflowListId = overflowListId;
        this.typeId = typeId;
    }

    public OverflowGoods() {
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

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Integer getOverflowListId() {
        return overflowListId;
    }

    public void setOverflowListId(Integer overflowListId) {
        this.overflowListId = overflowListId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}
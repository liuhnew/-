package com.jykj.entity;

public class GoodsUnit {
    private Integer id;

    private String name;

    public GoodsUnit(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public GoodsUnit() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}
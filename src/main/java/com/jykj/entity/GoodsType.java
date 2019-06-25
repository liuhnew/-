package com.jykj.entity;

public class GoodsType {
    private Integer id;

    private String icon;

    private String name;

    private Integer pId;

    private Integer state;

    public GoodsType(Integer id, String icon, String name, Integer pId, Integer state) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.pId = pId;
        this.state = state;
    }

    public GoodsType() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }


    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
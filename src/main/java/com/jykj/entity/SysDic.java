package com.jykj.entity;

public class SysDic {
    private Integer id;

    private String dicName;

    private String english;

    private Integer parentId;

    private String code;

    private Integer sortCode;

    private String remark;

    private Integer banDelete;

    public SysDic(Integer id, String dicName, String english, Integer parentId, String code, Integer sortCode, String remark, Integer banDelete) {
        this.id = id;
        this.dicName = dicName;
        this.english = english;
        this.parentId = parentId;
        this.code = code;
        this.sortCode = sortCode;
        this.remark = remark;
        this.banDelete = banDelete;
    }

    public SysDic() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName == null ? null : dicName.trim();
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english == null ? null : english.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getSortCode() {
        return sortCode;
    }

    public void setSortCode(Integer sortCode) {
        this.sortCode = sortCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getBanDelete() {
        return banDelete;
    }

    public void setBanDelete(Integer banDelete) {
        this.banDelete = banDelete;
    }
}
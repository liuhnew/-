package com.jykj.entity;

import com.mongodb.DBObject;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TenantStaff {

    private ObjectId _id;

    private int status;

    private ObjectId tenant;

    private String mobile;

    private int termType;

    private Date termStartOn;

    private Date termEndOn;

    private String name;

    private int gender;

    private Date birthday;

    private Date createdOn;

    private Date updatedOn;

    private String avatar;

    private Object roles;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ObjectId getTenant() {
        return tenant;
    }

    public void setTenant(ObjectId tenant) {
        this.tenant = tenant;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getTermType() {
        return termType;
    }

    public void setTermType(int termType) {
        this.termType = termType;
    }

    public Date getTermStartOn() {
        return termStartOn;
    }

    public void setTermStartOn(Date termStartOn) {
        this.termStartOn = termStartOn;
    }

    public Date getTermEndOn() {
        return termEndOn;
    }

    public void setTermEndOn(Date termEndOn) {
        this.termEndOn = termEndOn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Object getRoles() {
        return roles;
    }

    public void setRoles(Object roles) {
        this.roles = roles;
    }
}

package com.jykj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ProDefEntity {

    private String id;
    private String name;
    private String keyName;
    private int version;
    private String deploymentId;
    private String resourceName;
    private String dgrmResourceName;
    private String hasStartFormKey;
    private String hasGraphicalNotiation;
    private Integer suspensionState;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deployTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getDgrmResourceName() {
        return dgrmResourceName;
    }

    public void setDgrmResourceName(String dgrmResourceName) {
        this.dgrmResourceName = dgrmResourceName;
    }

    public String getHasStartFormKey() {
        return hasStartFormKey;
    }

    public void setHasStartFormKey(String hasStartFormKey) {
        this.hasStartFormKey = hasStartFormKey;
    }

    public String getHasGraphicalNotiation() {
        return hasGraphicalNotiation;
    }

    public void setHasGraphicalNotiation(String hasGraphicalNotiation) {
        this.hasGraphicalNotiation = hasGraphicalNotiation;
    }

    public Integer getSuspensionState() {
        return suspensionState;
    }

    public void setSuspensionState(Integer suspensionState) {
        this.suspensionState = suspensionState;
    }

    public Date getDeployTime() {
        return deployTime;
    }

    public void setDeployTime(Date deployTime) {
        this.deployTime = deployTime;
    }
}

package com.jykj.entity;

import java.util.Date;

public class OverTask {

    String processName;
    String processKey;
    String commitName;
    String assignee;
    String vehicleNum;
    String repairNum;
    String farm;
    String currentTaskName;
    String createTime;
    String taskDefKey;
    Date startTime;
    Date endTime;
    Long timeLong;
    String assigneeName;

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getCommitName() {
        return commitName;
    }

    public void setCommitName(String commitName) {
        this.commitName = commitName;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    public String getRepairNum() {
        return repairNum;
    }

    public void setRepairNum(String repairNum) {
        this.repairNum = repairNum;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getCurrentTaskName() {
        return currentTaskName;
    }

    public void setCurrentTaskName(String currentTaskName) {
        this.currentTaskName = currentTaskName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(Long timeLong) {
        this.timeLong = timeLong;
    }
}

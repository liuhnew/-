package com.jykj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class RepairOrder {
    private String repairId;

    private String repairNum;

    private String driverName;

    private String driverNum;

    private String commitName;

    private String checkerName;

    private String repairStatus;

    private String vehicleNum;

    private String tenantId;

    private String tenantName;

    private String repairMessage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appointmentTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date getTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private String repairProgress;

    private String contactName;

    private String contactTel;

    private String remark;

    private String pickGoodsId;

    private Integer orderType;

    private String farm;
    private String vin;

    private String engineNo;

    private String processInstanceId;

    private String taskId;

    private String dealerName;

    public RepairOrder(String repairId, String repairNum, String driverName, String driverNum, String commitName, String checkerName, String repairStatus, String vehicleNum, String tenantId, String tenantName, String repairMessage, Date createTime, Date appointmentTime, Date getTime, Date updateTime, Date endTime, String repairProgress, String contactName, String contactTel, String remark, String pickGoodsId, Integer orderType, String farm, String vin, String engineNo, String processInstanceId, String taskId, String dealerName) {
        this.repairId = repairId;
        this.repairNum = repairNum;
        this.driverName = driverName;
        this.driverNum = driverNum;
        this.commitName = commitName;
        this.checkerName = checkerName;
        this.repairStatus = repairStatus;
        this.vehicleNum = vehicleNum;
        this.tenantId = tenantId;
        this.tenantName = tenantName;
        this.repairMessage = repairMessage;
        this.createTime = createTime;
        this.appointmentTime = appointmentTime;
        this.getTime = getTime;
        this.updateTime = updateTime;
        this.endTime = endTime;
        this.repairProgress = repairProgress;
        this.contactName = contactName;
        this.contactTel = contactTel;
        this.remark = remark;
        this.pickGoodsId = pickGoodsId;
        this.orderType = orderType;
        this.farm = farm;
        this.vin = vin;
        this.engineNo = engineNo;
        this.processInstanceId = processInstanceId;
        this.taskId = taskId;
        this.dealerName = dealerName;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getCommitName() {
        return commitName;
    }

    public void setCommitName(String commitName) {
        this.commitName = commitName;
    }

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    public RepairOrder() {
        super();
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId == null ? null : repairId.trim();
    }

    public String getRepairNum() {
        return repairNum;
    }

    public void setRepairNum(String repairNum) {
        this.repairNum = repairNum == null ? null : repairNum.trim();
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName == null ? null : driverName.trim();
    }

    public String getDriverNum() {
        return driverNum;
    }

    public void setDriverNum(String driverNum) {
        this.driverNum = driverNum == null ? null : driverNum.trim();
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName == null ? null : checkerName.trim();
    }

    public String getRepairStatus() {
        return repairStatus;
    }

    public void setRepairStatus(String repairStatus) {
        this.repairStatus = repairStatus;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getRepairMessage() {
        return repairMessage;
    }

    public void setRepairMessage(String repairMessage) {
        this.repairMessage = repairMessage == null ? null : repairMessage.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRepairProgress() {
        return repairProgress;
    }

    public void setRepairProgress(String repairProgress) {
        this.repairProgress = repairProgress == null ? null : repairProgress.trim();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel == null ? null : contactTel.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getPickGoodsId() {
        return pickGoodsId;
    }

    public void setPickGoodsId(String pickGoodsId) {
        this.pickGoodsId = pickGoodsId == null ? null : pickGoodsId.trim();
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
package com.jykj.service;

import com.github.pagehelper.PageInfo;
import com.jykj.entity.RepairOrder;

import java.util.List;

public interface RepairOrderService {

    int deleteByPrimaryKey(String repairId);

    int insertSelective(RepairOrder record);

    RepairOrder selectByPrimaryKey(String repairId);

    int updateByPrimaryKeySelective(RepairOrder record);

    List<RepairOrder> list(String repairNum,
                           String vehicleNum,
                           String orderType,
                           String farm);

    PageInfo<RepairOrder> list(String repairNum,
                               String vehicleNum,
                               String orderType,
                               String farm,
                               Integer pageIndex,
                               Integer pageSize);
}

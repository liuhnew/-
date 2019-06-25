package com.jykj.dao;

import com.jykj.entity.RepairOrder;

import java.util.List;
import java.util.Map;

public interface RepairOrderMapper {
    int deleteByPrimaryKey(String repairId);

    int insert(RepairOrder record);

    int insertSelective(RepairOrder record);

    RepairOrder selectByPrimaryKey(String repairId);

    int updateByPrimaryKeySelective(RepairOrder record);

    int updateByPrimaryKey(RepairOrder record);

    List<RepairOrder> list(Map<String,Object> searchParams);
}
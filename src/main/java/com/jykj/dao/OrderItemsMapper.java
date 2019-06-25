package com.jykj.dao;

import com.jykj.entity.OrderItems;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemsMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrderItems record);

    int insertSelective(OrderItems record);

    OrderItems selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderItems record);

    int updateByPrimaryKey(OrderItems record);

    List<OrderItems> listDetailByRepairId(@Param("repairId")String repairId);
}
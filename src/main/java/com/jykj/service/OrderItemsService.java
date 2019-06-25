package com.jykj.service;

import com.jykj.entity.OrderItems;

import java.util.List;

public interface OrderItemsService {

    int deleteByPrimaryKey(String id);

    int save(OrderItems record);

    OrderItems selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderItems record);

    List<OrderItems> list(String repairId);

}

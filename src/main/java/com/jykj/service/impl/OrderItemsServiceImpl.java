package com.jykj.service.impl;

import com.jykj.dao.OrderItemsMapper;
import com.jykj.entity.OrderItems;
import com.jykj.service.OrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderItemsService")
public class OrderItemsServiceImpl implements OrderItemsService {

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return orderItemsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int save(OrderItems record) {
        return orderItemsMapper.insertSelective(record);
    }

    @Override
    public OrderItems selectByPrimaryKey(String id) {
        return orderItemsMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OrderItems record) {
        return orderItemsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<OrderItems> list(String repairId) {
        return orderItemsMapper.listDetailByRepairId(repairId);
    }
}

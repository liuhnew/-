package com.jykj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jykj.dao.RepairOrderMapper;
import com.jykj.entity.RepairOrder;
import com.jykj.service.RepairOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("repairOrderService")
public class RepairOrderServiceImpl implements RepairOrderService {

    @Autowired
    private RepairOrderMapper repairOrderMapper;

    @Override
    public int deleteByPrimaryKey(String repairId) {
        return repairOrderMapper.deleteByPrimaryKey(repairId);
    }

    @Override
    public int insertSelective(RepairOrder record) {
        return repairOrderMapper.insertSelective(record);
    }

    @Override
    public RepairOrder selectByPrimaryKey(String repairId) {
        return repairOrderMapper.selectByPrimaryKey(repairId);
    }

    @Override
    public int updateByPrimaryKeySelective(RepairOrder record) {
        return repairOrderMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<RepairOrder> list(String repairNum, String vehicleNum, String orderType, String farm) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("repairNum", repairNum);
        map.put("vehicleNum", vehicleNum);
        map.put("orderType", orderType);
        map.put("farm", farm);
        return repairOrderMapper.list(map);
    }

    @Override
    public PageInfo<RepairOrder> list(String repairNum, String vehicleNum, String orderType, String farm, Integer pageIndex, Integer pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("repairNum", repairNum);
        map.put("vehicleNum", vehicleNum);
        map.put("orderType", orderType);
        map.put("farm", farm);
        PageHelper.startPage(pageIndex, pageSize);
        List<RepairOrder> list = repairOrderMapper.list(map);
        PageInfo<RepairOrder> pageInfo = new PageInfo<RepairOrder>(list);
        return pageInfo;
    }
}

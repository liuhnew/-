package com.jykj.dao;

import com.jykj.entity.FixedWorkTime;

import java.util.List;
import java.util.Map;

public interface FixedWorkTimeMapper {
    int deleteByPrimaryKey(String id);

    int insert(FixedWorkTime record);

    int insertSelective(FixedWorkTime record);

    FixedWorkTime selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FixedWorkTime record);

    int updateByPrimaryKey(FixedWorkTime record);

    List<FixedWorkTime> list(Map<String,Object> searchParams);
}
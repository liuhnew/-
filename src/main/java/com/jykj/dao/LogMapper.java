package com.jykj.dao;

import com.jykj.entity.Log;

import java.util.List;
import java.util.Map;

public interface LogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Log record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);

    List<Log> list(Map<String,Object> searchParams);
}
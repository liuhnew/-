package com.jykj.dao;

import com.jykj.entity.SysDic;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysDicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysDic record);

    int insertSelective(SysDic record);

    SysDic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDic record);

    int updateByPrimaryKey(SysDic record);

    List<SysDic> list(@Param("keyWord") String keyWord);
}
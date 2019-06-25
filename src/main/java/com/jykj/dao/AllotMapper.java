package com.jykj.dao;

import com.jykj.entity.Allot;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AllotMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Allot record);

    int insertSelective(Allot record);

    Allot selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Allot record);

    int updateByPrimaryKey(Allot record);

    List<Allot> queryByAllot(@Param("orgId") String orgId);
}
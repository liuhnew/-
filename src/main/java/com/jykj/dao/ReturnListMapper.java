package com.jykj.dao;

import com.jykj.entity.ReturnList;

import java.util.List;

public interface ReturnListMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(ReturnList record);

    ReturnList selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(ReturnList record);

    String getTodayMaxReturnNumber();

    List<ReturnList> list(ReturnList returnList, String...properties);
}
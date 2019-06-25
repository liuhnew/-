package com.jykj.dao;

import com.jykj.entity.ReturnListGoods;

import java.util.List;

public interface ReturnListGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReturnListGoods record);

    ReturnListGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(ReturnListGoods record);

    List<ReturnListGoods> listByReturnListId(Integer returnListId);

    List<ReturnListGoods> list(ReturnListGoods returnListGoods);
}
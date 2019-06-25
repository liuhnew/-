package com.jykj.dao;

import com.jykj.entity.StockOddGoods;

import java.util.List;

public interface StockOddGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockOddGoods record);

    int insertSelective(StockOddGoods record);

    StockOddGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockOddGoods record);

    int updateByPrimaryKey(StockOddGoods record);

    List<StockOddGoods> queryByPurchaseNum(String purchaseNum);

    void deleteByPurchaseNum(String purchaseNum);
}
package com.jykj.service;

import com.jykj.entity.StockOddGoods;

import java.util.List;

public interface StockOddGoodsService {

    List<StockOddGoods> queryByPurchaseNum(String purchaseNum);

    void save(StockOddGoods stockOddGoods);

    void delete(String purchaseNum);

}

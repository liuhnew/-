package com.jykj.service.impl;

import com.jykj.dao.StockOddGoodsMapper;
import com.jykj.entity.StockOddGoods;
import com.jykj.service.StockOddGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("stockOddGoodsService")
public class StockOddGoodsServiceImpl implements StockOddGoodsService {

    @Autowired
    private StockOddGoodsMapper stockOddGoodsMapper;

    @Override
    public List<StockOddGoods> queryByPurchaseNum(String purchaseNum) {
        return stockOddGoodsMapper.queryByPurchaseNum(purchaseNum);
    }

    @Override
    public void delete(String purchaseNum) {
        stockOddGoodsMapper.deleteByPurchaseNum(purchaseNum);
    }

    @Override
    public void save(StockOddGoods stockOddGoods) {
        stockOddGoodsMapper.insert(stockOddGoods);
    }
}

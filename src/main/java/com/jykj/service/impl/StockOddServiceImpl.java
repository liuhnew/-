package com.jykj.service.impl;

import com.github.pagehelper.PageHelper;
import com.jykj.dao.StockOddGoodsMapper;
import com.jykj.dao.StockOddMapper;
import com.jykj.entity.StockOdd;
import com.jykj.entity.StockOddGoods;
import com.jykj.service.StockOddService;
import com.jykj.util.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("stockOddService")
public class StockOddServiceImpl implements StockOddService {

    @Autowired
    private StockOddMapper stockOddMapper;

    @Autowired
    private StockOddGoodsMapper stockOddGoodsMapper;

    @Override
    public List<StockOdd> list(String startTime,
                               String endTime,
                               String purchaseRevieceNum,
                               String purchaseNum,
                               String purchaseOrg,
                               String supplierName,
                               String docType,
                               Integer pageIndex,
                               Integer pageSize) {
        Map<String,Object> searchParams = new HashMap<String,Object>();
        searchParams.put("startTime", startTime);
        searchParams.put("endTime", endTime);
        searchParams.put("purchaseRevieceNum", purchaseRevieceNum);
        searchParams.put("purchaseNum", purchaseNum);
        searchParams.put("purchaseOrg", purchaseOrg);
        searchParams.put("supplierName", supplierName);
        searchParams.put("docType", docType);
        if (pageIndex!=null&&pageSize!=null){
            PageHelper.startPage(pageIndex, pageSize);
        }
        return stockOddMapper.list(searchParams);
    }

    @Override
    public StockOdd selectById(Integer id) {
        return stockOddMapper.selectById(id);
    }


    @Override
    public String getMaxPurChaseNums() {
        return stockOddMapper.getMaxPurChaseNums();
    }

    @Transactional
    @Override
    public void save(String requestBody) {
        Map<String,Object> map = GsonUtils.getObjectFromJson(requestBody, Map.class);
        //添加入库单
        Object stock = map.get("stockOdd");
        Map<String,String> stockData = GsonUtils.getObjectFromJson(GsonUtils.getJsonFromObject(stock), Map.class);
        StockOdd stockOdd = new StockOdd();
        stockOdd.setPurchaseNum(stockData.get("code"));
        stockOdd.setPurchaseGoodsNum(stockData.get("receiptNO"));
        stockOdd.setCreateTime(new Date());
        stockOdd.setDocType(stockData.get("orderType"));
        stockOdd.setPurchaseOrg(stockData.get("selectOrganizations"));
        stockOdd.setRepository(stockData.get("selectStore"));
        stockOddMapper.save(stockOdd);

        List<Map<String,String>> list = GsonUtils.getListFromJson(GsonUtils.getJsonFromObject(map.get("goodsList")), List.class);
        StockOddGoods oddGoods = null;
        if (!list.isEmpty()) {
            for (Map<String,String> obj : list){
                oddGoods = new StockOddGoods();
                oddGoods.setName(obj.get("name"));
                oddGoods.setAmount(obj.get("amount"));
                oddGoods.setModel(obj.get("model"));
                oddGoods.setRemarks(obj.get("remarks"));
                oddGoods.setCode(obj.get("code"));
                oddGoods.setPurchaseNum(stockData.get("code"));
                oddGoods.setSupplier(Integer.parseInt(obj.get("supplier")));
                oddGoods.setPrice(Float.valueOf(obj.get("price")));
                stockOddGoodsMapper.insert(oddGoods);
            }
        }
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        StockOdd stockOdd = stockOddMapper.selectById(id);
        stockOddGoodsMapper.deleteByPurchaseNum(stockOdd.getPurchaseNum());
        stockOddMapper.delete(id);
    }

    @Override
    public void update(StockOdd stockOdd) {
        stockOddMapper.save(stockOdd);
    }

}

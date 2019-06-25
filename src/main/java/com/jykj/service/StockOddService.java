package com.jykj.service;

import com.jykj.entity.StockOdd;
import java.util.List;

public interface StockOddService {

    String getMaxPurChaseNums();

    /**
     * 新增入库记录
     */
    void save(String requestBody);

    /**
     * 删除入库记录
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改入库记录
     * @param stockOdd
     */
    void update(StockOdd stockOdd);

    /**
     * 查询记录
     * @return
     */
    List<StockOdd> list(String startTime,
                        String endTime,
                        String purchaseRevieceNum,
                        String purchaseNum,
                        String purchaseOrg,
                        String supplierName,
                        String docType,
                        Integer pageIndex,
                        Integer pageSize);

    StockOdd selectById(Integer id);
}

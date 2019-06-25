package com.jykj.dao;

import com.jykj.entity.Purchase;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface PurchaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Purchase record);

    int insertSelective(Purchase record);

    Purchase selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Purchase record);

    int updateByPrimaryKey(Purchase record);

    /**
     * 获取当天最大进货单号
     * @return
     */
    @Select(value="SELECT MAX(purchase_number) FROM t_purchase WHERE TO_DAYS(purchase_date)=TO_DAYS(NOW())")
    String getTodayMaxPurchaseNumber();

    List<Purchase> list(Map<String,Object> searchParams);
}
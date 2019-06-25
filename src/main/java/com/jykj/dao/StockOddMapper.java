package com.jykj.dao;

import com.jykj.entity.StockOdd;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StockOddMapper {

    List<StockOdd> list(Map<String, Object> searchParams);

    void save(StockOdd stockOdd);

    void delete(Integer id);

    void update(StockOdd stockOdd);

    @Select("select max(purchase_num) from t_stock_odd where TO_DAYS(create_time)=TO_DAYS(NOW())")
    String getMaxPurChaseNums();

    StockOdd selectById(Integer id);

}

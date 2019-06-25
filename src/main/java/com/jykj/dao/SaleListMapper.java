package com.jykj.dao;

import com.jykj.entity.SaleList;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface SaleListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SaleList record);

    int insertSelective(SaleList record);

    SaleList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SaleList record);

    int updateByPrimaryKey(SaleList record);

    @Select("select max(sale_number) from t_sale_list ")
    String getTodayMaxSaleNumber();

    List<SaleList> list(Map<String,Object> searchParams);

    List<Object> countSaleByDay(String begin,String end);

    List<Object> countSaleByMonth(String begin, String end);
}
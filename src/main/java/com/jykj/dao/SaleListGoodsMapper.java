package com.jykj.dao;

import com.jykj.entity.SaleListGoods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SaleListGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SaleListGoods record);

    int insertSelective(SaleListGoods record);

    SaleListGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SaleListGoods record);

    int updateByPrimaryKey(SaleListGoods record);

    List<SaleListGoods> listBySaleListId(Integer saltListId);

    @Select("select count(*) from t_sale_list_goods ")
    Integer getTotalByGoodsId(Integer goodsId);

    List<SaleListGoods> list(@Param("name")String name);


    void deleteBySaleListId(Integer saltListId);
}
package com.jykj.dao;

import com.jykj.entity.GoodsUnit;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsUnitMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsUnit record);

    int insertSelective(GoodsUnit record);

    GoodsUnit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsUnit record);

    int updateByPrimaryKey(GoodsUnit record);

    @Select("select * from t_goods_unit ")
    List<GoodsUnit> list();
}
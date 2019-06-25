package com.jykj.dao;

import com.jykj.entity.OverflowGoods;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OverflowGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OverflowGoods record);

    int insertSelective(OverflowGoods record);

    OverflowGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OverflowGoods record);

    int updateByPrimaryKey(OverflowGoods record);

    /**
     * 根据商品报溢单id查询所有商品报溢单商品
     * @param overflowId
     * @return
     */
    @Select(value="SELECT * FROM t_overflow_goods WHERE overflow_list_id = #{overflowId}")
    List<OverflowGoods> listByOverflowListId(Integer overflowId);

    /**
     * 根据商品报溢单id删除所有商品报溢单商品
     * @param overflowId
     * @return
     */
    @Select(value="delete FROM t_overflow_goods WHERE overflow_list_id = #{overflowId}")
    void deleteByOverflowListId(Integer overflowId);
}
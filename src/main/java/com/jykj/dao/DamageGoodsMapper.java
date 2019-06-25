package com.jykj.dao;

import com.jykj.entity.DamageGoods;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DamageGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DamageGoods record);

    int insertSelective(DamageGoods record);

    DamageGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DamageGoods record);

    int updateByPrimaryKey(DamageGoods record);

    /**
     * 根据商品报损单id查询所有商品报损单商品
     * @param damageListId
     * @return
     */
    @Select(value="SELECT * FROM t_damage_goods WHERE damage_list_id= #{damageListId}")
    List<DamageGoods> listByDamageListId(Integer damageListId);

    /**
     * 根据商品报损单id删除所有商品报损单商品
     * @param damageListId
     * @return
     */
    @Select(value="delete FROM t_damage_goods WHERE damage_list_id= #{damageListId}")
    void deleteByDamageListId(Integer damageListId);
}
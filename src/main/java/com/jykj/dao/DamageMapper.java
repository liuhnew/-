package com.jykj.dao;

import com.jykj.entity.Damage;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DamageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Damage record);

    int insertSelective(Damage record);

    Damage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Damage record);

    int updateByPrimaryKey(Damage record);

    /**
     * 获取当天最大商品报损单号
     * @return
     */
    @Select(value="SELECT MAX(damage_number) FROM t_damage WHERE TO_DAYS(damage_date)=TO_DAYS(NOW())")
    String getTodayMaxDamageNumber();

    List<Damage> list();
}
package com.jykj.dao;

import com.jykj.entity.Overflow;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface OverflowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Overflow record);

    int insertSelective(Overflow record);

    Overflow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Overflow record);

    int updateByPrimaryKey(Overflow record);

    /**
     * 获取当天最大商品报溢单号
     * @return
     */
    @Select(value="SELECT MAX(overflow_number) FROM t_overflow WHERE TO_DAYS(overflow_date)=TO_DAYS(NOW())")
    String getTodayMaxOverflowNumber();

    List<Overflow> list(Map<String,Object> searchParams);
}
package com.jykj.dao;

import com.jykj.entity.Supplier;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface SupplierMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Supplier record);

    int insertSelective(Supplier record);

    Supplier selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Supplier record);

    int updateByPrimaryKey(Supplier record);

    @Select(value="select * from t_supplier where name like '%${name}%'")
    List<Supplier> findByName(@Param("name") String name);

    List<Supplier> list(Map<String,Object> searchParams);
}
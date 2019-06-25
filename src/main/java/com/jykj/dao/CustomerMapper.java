package com.jykj.dao;

import com.jykj.entity.Customer;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    List<Customer> list(@Param("name") String name);

    @Results({
        @Result(column = "id", property = "id", id = true),
        @Result(column = "name", property = "name")
    })
    @Select("SELECT id,name from t_customer where name like '%${name}%'")
    List<Customer> findByName(@Param("name")String name);
}
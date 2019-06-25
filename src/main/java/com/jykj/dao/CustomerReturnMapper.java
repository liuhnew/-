package com.jykj.dao;

import com.jykj.entity.CustomerReturn;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface CustomerReturnMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerReturn record);

    int insertSelective(CustomerReturn record);

    CustomerReturn selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerReturn record);

    int updateByPrimaryKey(CustomerReturn record);

    @Select(value="SELECT MAX(customer_return_number) FROM t_customer_return_list WHERE TO_DAYS(customer_return_date)=TO_DAYS(NOW())")
    public String getTodayMaxCustomerReturnNumber();

    List<CustomerReturn> list(Map<String,Object> searchParams);
}
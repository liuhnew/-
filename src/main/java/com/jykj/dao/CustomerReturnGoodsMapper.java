package com.jykj.dao;

import com.jykj.entity.CustomerReturnGoods;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CustomerReturnGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerReturnGoods record);

    int insertSelective(CustomerReturnGoods record);

    CustomerReturnGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerReturnGoods record);

    int updateByPrimaryKey(CustomerReturnGoods record);

    List<CustomerReturnGoods> list(CustomerReturnGoods customerReturnGoods);

    @Delete("DELETE FROM t_customer_return_goods WHERE customer_return_list_id = #{id}")
    void deleteByCustomerId(@Param("id") Integer customerId);

    @Select(value="SELECT * FROM t_customer_return_list_goods WHERE customer_return_list_id=#{customerReturnListId}")
    List<CustomerReturnGoods> listByCustomerReturnListId(Integer customerReturnListId);

    /**
     * 根据客户退货单id删除所有客户退货单商品
     * @param customerReturnListId
     * @return
     */
    @Delete(value="delete FROM t_customer_return_list_goods WHERE customer_return_list_id= #{customerReturnListId}")
    void deleteByCustomerReturnListId(Integer customerReturnListId);

    /**
     * 统计某个商品的退货总数
     * @param goodsId
     * @return
     */
    @Select(value="SELECT SUM(num) AS total FROM t_customer_return_goods WHERE goods_id=#{goodsId}")
    Integer getTotalByGoodsId(Integer goodsId);
}
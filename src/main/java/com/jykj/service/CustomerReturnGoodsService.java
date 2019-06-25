package com.jykj.service;

import com.jykj.entity.CustomerReturnGoods;

import java.util.List;

/**
 * 客户退货单商品Service接口
 * @author Administrator
 *
 */
public interface CustomerReturnGoodsService {

	/**
	 * 根据客户退货单id查询所有客户退货单商品
	 * @param customerReturnListId
	 * @return
	 */
	List<CustomerReturnGoods> listByCustomerReturnListId(Integer customerReturnListId,
																Integer pageIndex,
																Integer pageSize);
	
	/**
	 * 统计某个商品的退货总数
	 * @param goodsId
	 * @return
	 */
	Integer getTotalByGoodsId(Integer goodsId);
	
	/**
	 * 根据条件查询客户退货单商品
	 * @param customerReturnListGoods
	 * @return
	 */
	List<CustomerReturnGoods> list(CustomerReturnGoods customerReturnListGoods);
	
}

package com.jykj.service;

import java.util.List;

import com.jykj.entity.ReturnListGoods;

/**
 * 退货单商品Service接口
 * @author Administrator
 *
 */
public interface ReturnListGoodsService {

	/**
	 * 根据退货单id查询所有退货单商品
	 * @param returnListId
	 * @return
	 */
	List<ReturnListGoods> listByReturnListId(Integer returnListId);
	
	/**
	 * 根据条件查询退货单商品
	 * @param returnListGoods
	 * @return
	 */
	List<ReturnListGoods> list(ReturnListGoods returnListGoods);
}

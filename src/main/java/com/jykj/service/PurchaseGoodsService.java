package com.jykj.service;

import com.jykj.entity.PurchaseGoods;

import java.util.List;

/**
 * 进货单商品Service接口
 * @author Administrator
 *
 */
public interface PurchaseGoodsService {

	/**
	 * 根据进货单id查询所有进货单商品
	 * @param purchaseListId
	 * @return
	 */
	 List<PurchaseGoods> listByPurchaseListId(Integer purchaseListId);
	
	/**
	 * 根据条件查询进货单商品
	 * @param codeOrName
	 * @return
	 */
	 List<PurchaseGoods> list(Integer typeId,
									String codeOrName);
	
}

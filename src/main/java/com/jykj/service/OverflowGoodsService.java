package com.jykj.service;

import com.jykj.entity.OverflowGoods;

import java.util.List;

/**
 * 商品报溢单商品Service接口
 * @author Administrator
 *
 */
public interface OverflowGoodsService {

	/**
	 * 根据商品报溢单id查询所有商品报溢单商品
	 * @param overflowListId
	 * @return
	 */
	public List<OverflowGoods> listByOverflowListId(Integer overflowListId);
	
}

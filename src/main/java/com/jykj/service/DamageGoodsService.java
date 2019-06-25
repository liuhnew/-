package com.jykj.service;

import java.util.List;

import com.jykj.entity.DamageGoods;

/**
 * 商品报损单商品Service接口
 * @author Administrator
 *
 */
public interface DamageGoodsService {

	/**
	 * 根据商品报损单id查询所有商品报损单商品
	 * @param damageListId
	 * @return
	 */
	 List<DamageGoods> listByDamageListId(Integer damageListId,
												Integer pageIndex,
												Integer pageSize);
	
}

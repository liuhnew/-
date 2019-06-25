package com.jykj.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.jykj.dao.PurchaseGoodsMapper;
import com.jykj.entity.PurchaseGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jykj.service.PurchaseGoodsService;

/**
 * 进货单商品Service实现类
 * @author Administrator
 *
 */
@Service("purchaseGoodsService")
public class PurchaseGoodsServiceImpl implements PurchaseGoodsService {

	@Autowired
	private PurchaseGoodsMapper purchaseGoodsMapper;

	@Override
	public List<PurchaseGoods> listByPurchaseListId(Integer purchaseListId) {
		return purchaseGoodsMapper.listByPurchaseListId(purchaseListId);
	}

	@Override
	public List<PurchaseGoods> list(Integer typeId,
									String codeOrName) {
		return purchaseGoodsMapper.list(typeId, codeOrName);
	}

}

package com.jykj.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.jykj.dao.OverflowGoodsMapper;
import com.jykj.entity.OverflowGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jykj.service.OverflowGoodsService;

/**
 * 商品报溢单商品Service实现类
 * @author Administrator
 *
 */
@Service("overflowGoodsService")
public class OverflowGoodsServiceImpl implements OverflowGoodsService {

	@Autowired
	private OverflowGoodsMapper overflowGoodsMapper;

	@Override
	public List<OverflowGoods> listByOverflowListId(Integer overflowListId) {
		return overflowGoodsMapper.listByOverflowListId(overflowListId);
	}

}

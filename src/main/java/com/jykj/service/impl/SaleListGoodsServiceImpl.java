package com.jykj.service.impl;


import java.util.List;

import javax.annotation.Resource;
import com.github.pagehelper.PageHelper;
import com.jykj.dao.SaleListGoodsMapper;
import org.springframework.stereotype.Service;

import com.jykj.entity.SaleListGoods;
import com.jykj.service.SaleListGoodsService;

/**
 * 销售单商品Service实现类
 * @author Administrator
 *
 */
@Service("saleListGoodsService")
public class SaleListGoodsServiceImpl implements SaleListGoodsService{

	@Resource
	private SaleListGoodsMapper saleListGoodsMapper;

	@Override
	public List<SaleListGoods> listBySaleListId(Integer saleListId) {
		return saleListGoodsMapper.listBySaleListId(saleListId);
	}

	@Override
	public Integer getTotalByGoodsId(Integer goodsId) {
		return saleListGoodsMapper.getTotalByGoodsId(goodsId)==null?0:saleListGoodsMapper.getTotalByGoodsId(goodsId);
	}

	@Override
	public List<SaleListGoods> list(String name,
									Integer pageIndex,
									Integer pageSize) {
		if (pageIndex!=null&&pageSize!=null){
			PageHelper.startPage(pageIndex, pageSize);
		}
		return saleListGoodsMapper.list(name);
	}

	
	
}

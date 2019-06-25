package com.jykj.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.jykj.dao.CustomerReturnGoodsMapper;
import com.jykj.entity.CustomerReturnGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jykj.service.CustomerReturnGoodsService;

/**
 * 客户退货单商品Service实现类
 * @author Administrator
 *
 */
@Service("customerReturnGoodsService")
public class CustomerReturnGoodsServiceImpl implements CustomerReturnGoodsService {

	@Autowired
	private CustomerReturnGoodsMapper customerReturnGoodsMapper;

	@Override
	public List<CustomerReturnGoods> listByCustomerReturnListId(Integer customerReturnListId,
																Integer pageIndex,
																Integer pageSize) {
		if (pageIndex!=null&&pageSize!=null){
			PageHelper.startPage(pageIndex, pageSize);
		}
		return customerReturnGoodsMapper.listByCustomerReturnListId(customerReturnListId);
	}

	@Override
	public Integer getTotalByGoodsId(Integer goodsId) {
		return customerReturnGoodsMapper.getTotalByGoodsId(goodsId)==null ? 0 : customerReturnGoodsMapper.getTotalByGoodsId(goodsId);
	}

	@Override
	public List<CustomerReturnGoods> list(CustomerReturnGoods customerReturnListGoods) {
		return customerReturnGoodsMapper.list(customerReturnListGoods);
	}
	
}

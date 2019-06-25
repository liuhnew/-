package com.jykj.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.jykj.dao.CustomerReturnGoodsMapper;
import com.jykj.dao.CustomerReturnMapper;
import com.jykj.dao.GoodsMapper;
import com.jykj.entity.CustomerReturn;
import com.jykj.entity.CustomerReturnGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jykj.entity.Goods;
import com.jykj.service.CustomerReturnService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 客户退货单Service实现类
 * @author Administrator
 *
 */
@Service("customerReturnService")
public class CustomerReturnServiceImpl implements CustomerReturnService {

	@Autowired
	private CustomerReturnMapper customerReturnMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private CustomerReturnGoodsMapper customerReturnGoodsMapper;

	@Override
	public String getTodayMaxCustomerReturnNumber() {
		return customerReturnMapper.getTodayMaxCustomerReturnNumber();
	}

	@Transactional
	public void save(CustomerReturn customerReturn, List<CustomerReturnGoods> customerReturnListGoodsList) {
		for(CustomerReturnGoods customerReturnListGoods:customerReturnListGoodsList){
			customerReturnListGoods.setTypeId(customerReturnListGoods.getTypeId()); // 设置类别
			customerReturnListGoods.setCustomerReturnListId(customerReturn.getId()); // 设置客户退货单
			customerReturnGoodsMapper.insert(customerReturnListGoods);
			// 修改商品库存
			Goods goods=goodsMapper.selectByPrimaryKey(customerReturnListGoods.getGoodsId());
			goods.setInventoryQuantity(goods.getInventoryQuantity()+customerReturnListGoods.getNum());
			goods.setState(2);
			goodsMapper.insert(goods);
		}
		customerReturnMapper.insert(customerReturn); // 保存客户退货单
	}

	@Override
	public List<CustomerReturn> list(String customerReturnNumber,
										 Integer customerId,
										 Integer state,
										 String customerReturnDate,
										 Integer pageIndex,
										 Integer pageSize) {
		Map<String,Object> searchParams = new HashMap<>();
		searchParams.put("customerReturnNumber", customerReturnNumber);
		searchParams.put("customerId", customerId);
		searchParams.put("state", state);
		searchParams.put("customerReturnDate", customerReturnDate);

		if (pageIndex!=null&&pageSize!=null){
			PageHelper.startPage(pageIndex, pageSize);
		}

		return customerReturnMapper.list(searchParams);
	}

	@Override
	public CustomerReturn findById(Integer id) {
		return customerReturnMapper.selectByPrimaryKey(id);
	}

	@Transactional
	public void delete(Integer id) {
		customerReturnGoodsMapper.deleteByCustomerId(id);
		customerReturnMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(CustomerReturn customerReturn) {
		customerReturnMapper.updateByPrimaryKeySelective(customerReturn);
	}



}

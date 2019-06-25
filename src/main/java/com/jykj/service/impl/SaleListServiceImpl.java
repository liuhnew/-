package com.jykj.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.jykj.dao.GoodsMapper;
import com.jykj.dao.SaleListGoodsMapper;
import com.jykj.dao.SaleListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jykj.entity.Goods;
import com.jykj.entity.SaleList;
import com.jykj.entity.SaleListGoods;
import com.jykj.service.SaleListService;
import com.jykj.util.StringUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * 销售单Service实现类
 * @author Administrator
 *
 */
@Service("saleListService")
public class SaleListServiceImpl implements SaleListService{

	@Autowired
	private SaleListMapper saleListMapper;

	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private SaleListGoodsMapper saleListGoodsMapper;

	@Override
	public String getTodayMaxSaleNumber() {
		return saleListMapper.getTodayMaxSaleNumber();
	}

	@Transactional
	public void save(SaleList saleList, List<SaleListGoods> saleListGoodsList) {
		for(SaleListGoods saleListGoods:saleListGoodsList){
			saleListGoods.setSaleListId(saleList.getId()); // 设置销售单
			saleListGoodsMapper.insert(saleListGoods);
			// 修改商品库存
			Goods goods=goodsMapper.selectByPrimaryKey(saleListGoods.getGoodsId());
			goods.setInventoryQuantity(goods.getInventoryQuantity()-saleListGoods.getNum());
			goods.setState(2);
			goodsMapper.updateByPrimaryKey(goods);
		}
		saleListMapper.insert(saleList); // 保存销售单
	}

	@Override
	public List<SaleList> list(String saleNumber,
							   String customerId,
							   Integer state,
							   String startTime,
							   String endTime,
							   Integer pageIndex,
							   Integer pageSize) {
		Map<String,Object> map = new HashMap<>();
		map.put("saleNumber", saleNumber);
		map.put("customerId", customerId);
		map.put("state", state);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		if (pageIndex!=null&&pageSize!=null){
			PageHelper.startPage(pageIndex, pageSize);
		}
		return saleListMapper.list(map);
	}

	@Override
	public SaleList findById(Integer id) {
		return saleListMapper.selectByPrimaryKey(id);
	}

	@Transactional
	public void delete(Integer id) {
		saleListGoodsMapper.deleteBySaleListId(id);
		saleListMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(SaleList saleList) {
		saleListMapper.updateByPrimaryKey(saleList);
	}

	@Override
	public List<Object> countSaleByDay(String begin, String end) {
		return saleListMapper.countSaleByDay(begin, end);
	}

	@Override
	public List<Object> countSaleByMonth(String begin, String end) {
		return saleListMapper.countSaleByMonth(begin, end);
	}
}

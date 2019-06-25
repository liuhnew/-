package com.jykj.service.impl;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.jykj.dao.GoodsMapper;
import com.jykj.dao.OverflowGoodsMapper;
import com.jykj.dao.OverflowMapper;
import com.jykj.entity.Overflow;
import com.jykj.entity.OverflowGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jykj.entity.Goods;
import com.jykj.service.OverflowService;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品报溢单Service实现类
 * @author Administrator
 *
 */
@Service("overflowService")
public class OverflowServiceImpl implements OverflowService {

	@Autowired
	private OverflowMapper overflowMapper;

	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private OverflowGoodsMapper overflowGoodsMapper;

	@Override
	public String getTodayMaxOverflowNumber() {
		return overflowMapper.getTodayMaxOverflowNumber();
	}

	@Transactional
	public void save(Overflow overflow, List<OverflowGoods> overflowGoodsList) {
		for(OverflowGoods overflowListGoods:overflowGoodsList){
			overflowListGoods.setOverflowListId(overflow.getId()); // 设置商品报溢单
			overflowGoodsMapper.insert(overflowListGoods);
			// 修改商品库存
			Goods goods = goodsMapper.selectByPrimaryKey(overflowListGoods.getGoodsId());
			
			goods.setInventoryQuantity(goods.getInventoryQuantity()+overflowListGoods.getNum());
			goods.setState(2);
			goodsMapper.updateByPrimaryKey(goods);
		}
		overflowMapper.insert(overflow); // 保存商品报溢单
	}

	@Override
	public List<Overflow> list(String startTime,
							   String endTime,
							   Integer pageIndex,
							   Integer pageSize) {
		Map<String,Object> searchParams = new HashMap<String,Object>();
		searchParams.put("startTime", startTime);
		searchParams.put("endTime", endTime);
		if (pageIndex!=null&&pageSize!=null) {
			PageHelper.startPage(pageIndex, pageSize);
		}
		return overflowMapper.list(searchParams);
	}

	@Override
	public Overflow findById(Integer id) {
		return overflowMapper.selectByPrimaryKey(id);
	}


	

}

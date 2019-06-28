package com.jykj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jykj.dao.GoodsMapper;
import com.jykj.dao.ReturnListGoodsMapper;
import com.jykj.dao.ReturnListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jykj.entity.Goods;
import com.jykj.entity.ReturnList;
import com.jykj.entity.ReturnListGoods;
import com.jykj.service.ReturnListService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 退货单Service实现类
 * @author Administrator
 *
 */
@Service("returnListService")
public class ReturnListServiceImpl implements ReturnListService{

	@Autowired(required = false)
	private ReturnListMapper ReturnListMapper;

	@Autowired
	private ReturnListGoodsMapper returnListGoodsMapper;

	@Autowired
	private GoodsMapper goodsMapper;

	@Override
	public String getTodayMaxReturnNumber() {
		return ReturnListMapper.getTodayMaxReturnNumber();
	}

	@Transactional
	public void save(ReturnList returnList, List<ReturnListGoods> returnListGoodsList) {
		for(ReturnListGoods returnListGoods:returnListGoodsList){
			returnListGoods.setTypeId(returnListGoods.getTypeId()); // 设置类别
			returnListGoods.setReturnListId(returnList.getId()); // 设置退货单
			returnListGoodsMapper.insert(returnListGoods);
			// 修改商品库存 成本均价 以及上次进价
			Goods goods=goodsMapper.selectByPrimaryKey(returnListGoods.getGoodsId());
			
			goods.setInventoryQuantity(goods.getInventoryQuantity()-returnListGoods.getNum());
			goods.setState(2);
			goodsMapper.updateByPrimaryKey(goods);
		}
		ReturnListMapper.insertSelective(returnList); // 保存退货单
	}

	@Override
	public ReturnList findById(Integer id) {
		return ReturnListMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ReturnList> list(ReturnList returnList) {
		ReturnList returnLists = new ReturnList();
		returnLists.setAmountPaid(returnList.getAmountPaid());
		returnLists.setAmountPayable(returnList.getAmountPayable());
		returnLists.setId(returnList.getId());
		returnLists.setRemarks(returnList.getRemarks());
		returnLists.setReturnDate(returnList.getReturnDate());
		returnLists.setReturnNumber(returnList.getReturnNumber());
		returnLists.setState(returnList.getState());
		returnLists.setSupplierId(returnList.getSupplierId());
		returnLists.setUserId(returnList.getUserId());
		return  ReturnListMapper.list(returnLists);
	}

	@Transactional
	public void delete(Integer id) {
		returnListGoodsMapper.deleteByPrimaryKey(id);
		ReturnListMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(ReturnList returnList) {
		ReturnListMapper.updateByPrimaryKey(returnList);
	}
}

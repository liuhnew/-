package com.jykj.service.impl;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.jykj.dao.GoodsMapper;
import com.jykj.dao.PurchaseGoodsMapper;
import com.jykj.dao.PurchaseMapper;
import com.jykj.entity.Purchase;
import com.jykj.entity.PurchaseGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jykj.entity.Goods;
import com.jykj.service.PurchaseService;
import com.jykj.util.MathUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 进货单Service实现类
 * @author Administrator
 *
 */
@Service("purchaseListService")
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private PurchaseMapper purchaseMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private PurchaseGoodsMapper purchaseGoodsMapper;

	@Override
	public String getTodayMaxPurchaseNumber() {
		return purchaseMapper.getTodayMaxPurchaseNumber();
	}

	@Transactional
	public void save(Purchase purchaseList, List<PurchaseGoods> purchaseListGoodsList) {
		for(PurchaseGoods purchaseListGoods:purchaseListGoodsList){
			purchaseListGoods.setPurchaseListId(purchaseList.getId()); // 设置进货单
			purchaseGoodsMapper.insert(purchaseListGoods);
			// 修改商品库存 成本均价 以及上次进价
			Goods goods=goodsMapper.selectByPrimaryKey(purchaseListGoods.getGoodsId());
			
			float svePurchasePrice=(goods.getPurchasingPrice()*goods.getInventoryQuantity()+purchaseListGoods.getPrice()*purchaseListGoods.getNum())/(goods.getInventoryQuantity()+purchaseListGoods.getNum());
			goods.setPurchasingPrice(MathUtil.format2Bit(svePurchasePrice));
			Float quantity = goods.getInventoryQuantity() + purchaseListGoods.getNum();
			goods.setInventoryQuantity(quantity.intValue());
			goods.setLastPurchasingPrice(purchaseListGoods.getPrice());
			goods.setState(2);
			goodsMapper.updateByPrimaryKey(goods);
		}
		purchaseMapper.updateByPrimaryKey(purchaseList); // 保存进货单
	}

	@Override
	public List<Purchase> list(String purchaseNumber,
							   Integer supplierId,
							   Integer state,
							   String startTime,
							   String endTime,
							   Integer pageIndex,
							   Integer pageSize) {
		Map<String,Object> searchParams = new HashMap<String,Object>();
		searchParams.put("purchaseNumber", purchaseNumber);
		searchParams.put("supplierId", supplierId);
		searchParams.put("state", state);
		searchParams.put("startTime", startTime);
		searchParams.put("endTime", endTime);
		if(pageIndex!=null&&pageSize!=null){
			PageHelper.startPage(pageIndex, pageSize);
		}
		List<Purchase> list = purchaseMapper.list(searchParams);
		return list;
	}

	@Override
	public Purchase findById(Integer id) {
		return purchaseMapper.selectByPrimaryKey(id);
	}

	@Transactional
	public void delete(Integer id) {
		purchaseGoodsMapper.deleteByPurchaseListId(id);
		purchaseMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Purchase purchaseList) {
		purchaseMapper.updateByPrimaryKey(purchaseList);
	}

	

}

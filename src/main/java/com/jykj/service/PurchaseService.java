package com.jykj.service;

import java.util.List;

import com.jykj.entity.Purchase;
import com.jykj.entity.PurchaseGoods;
import org.springframework.data.domain.Sort.Direction;

/**
 * 进货单Service接口
 * @author Administrator
 *
 */
public interface PurchaseService {

	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public Purchase findById(Integer id);
	
	/**
	 * 获取当天最大进货单号
	 * @return
	 */
	public String getTodayMaxPurchaseNumber();
	
	/**
	 * 添加进货单 以及所有进货单商品  以及 修改商品成本价 库存数量 上次进价
	 * @param purchaseList
	 * @param purchaseListGoodsList
	 */
	public void save(Purchase purchaseList, List<PurchaseGoods> purchaseListGoodsList);
	
	/**
	 * 根据条件查询进货单信息
	 */
	public List<Purchase> list(String purchaseNumber,
							   Integer supplierId,
							   Integer state,
							   String startTime,
							   String endTime,
							   Integer pageIndex,
							   Integer pageSize);
	
	/**
	 * 根据id删除进货单信息 包括进货单里的所有商品
	 * @param id
	 */
	public void delete(Integer id);
	
	/**
	 * 更新进货单
	 * @param purchaseList
	 */
	public void update(Purchase purchaseList);
}

package com.jykj.service;

import java.util.List;

import com.jykj.entity.ReturnList;
import com.jykj.entity.ReturnListGoods;

/**
 * 退货单Service接口
 * @author Administrator
 *
 */
public interface ReturnListService {

	/**
	 * 获取当天最大退货单号
	 * @return
	 */
	 String getTodayMaxReturnNumber();
	
	/**
	 * 添加退货单 以及所有退货单商品  以及 修改 库存数量 
	 * @param returnList
	 * @param returnListGoodsList
	 */
	 void save(ReturnList returnList,List<ReturnListGoods> returnListGoodsList);

	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	 ReturnList findById(Integer id);
	
	/**
	 * 根据条件查询退货单信息
	 * @param returnList
	 * @return
	 */
	 List<ReturnList> list(ReturnList returnList);
	
	/**
	 * 根据id删除退货单信息 包括退货单里的所有商品
	 * @param id
	 */
	 void delete(Integer id);
	
	/**
	 * 更新退货单
	 * @param returnList
	 */
	 void update(ReturnList returnList);

}

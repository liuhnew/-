package com.jykj.service;

import java.util.List;

import com.jykj.entity.Damage;
import com.jykj.entity.DamageGoods;

/**
 * 商品报损单Service接口
 * @author Administrator
 *
 */
public interface DamageService {

	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	Damage findById(Integer id);
	
	/**
	 * 获取当天最大商品报损单号
	 * @return
	 */
	String getTodayMaxDamageNumber();
	
	/**
	 * 添加商品报损单 以及所有商品报损单商品  以及 修改商品成本价 库存数量 上次进价
	 * @param damage
	 * @param damageGoods
	 */
	void save(Damage damage,List<DamageGoods> damageGoods);
	
	/**
	 * 根据条件查询商品报损单信息
	 * @return
	 */
	List<Damage> list(Integer pageIndex,
					  Integer pageSize);
	
}

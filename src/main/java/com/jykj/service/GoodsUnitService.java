package com.jykj.service;

import java.util.List;


import com.jykj.entity.GoodsUnit;

/**
 * 商品单位Service接口
 * @author Administrator
 *
 */
public interface GoodsUnitService {

	/**
	 * 查询所有商品单位信息
	 * @return
	 */
	public List<GoodsUnit> list(Integer pageIndex,
                                Integer pageSize);
	
	/**
	 * 添加或者修改商品单位信息
	 */
	public void save(GoodsUnit goodsUnit);

	void update(GoodsUnit goodsUnit);
	
	/**
	 * 根据id删除商品单位
	 * @param id
	 */
	public void delete(Integer id);
	
	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public GoodsUnit findById(Integer id);
}

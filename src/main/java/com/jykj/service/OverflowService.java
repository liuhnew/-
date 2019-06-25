package com.jykj.service;

import java.util.List;

import com.jykj.entity.Overflow;
import com.jykj.entity.OverflowGoods;
import org.springframework.data.domain.Sort.Direction;

/**
 * 商品报溢单Service接口
 * @author Administrator
 *
 */
public interface OverflowService {

	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public Overflow findById(Integer id);
	
	/**
	 * 获取当天最大商品报溢单号
	 * @return
	 */
	public String getTodayMaxOverflowNumber();
	
	/**
	 * 添加商品报溢单 以及所有商品报溢单商品  以及 修改商品成本价 库存数量 上次进价
	 * @param overflow
	 * @param overflowGoodsList
	 */
	public void save(Overflow overflow,List<OverflowGoods> overflowGoodsList);
	
	/**
	 * 根据条件查询商品报溢单信息
	 * @param startTime
	 * @param endTime
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Overflow> list(String startTime,
							   String endTime,
							   Integer pageIndex,
							   Integer pageSize);
	
}

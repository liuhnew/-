package com.jykj.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.jykj.entity.Supplier;

/**
 * 供应商Service接口
 * @author Administrator
 *
 */
public interface SupplierService {

	/**
	 * 根据名称模糊查询供应商信息
	 * @param name
	 * @return
	 */
	public List<Supplier> findByName(String name);
	
	/**
	 * 根据条件分页查询供应商信息
	 * @return
	 */
	public List<Supplier> list(String name,
							   Integer pageIndex,
							   Integer pageSize);
	
	/**
	 * 添加或者修改供应商信息
	 * @param supplier
	 */
	public void save(Supplier supplier);

	void update(Supplier supplier);

	
	/**
	 * 根据id删除供应商
	 * @param id
	 */
	public void delete(Integer id);
	
	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public Supplier findById(Integer id);
}

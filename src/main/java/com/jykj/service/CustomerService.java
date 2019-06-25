package com.jykj.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.jykj.entity.Customer;

/**
 * 客户Service接口
 * @author Administrator
 *
 */
public interface CustomerService {

	List<Customer> findByName(String name);

	public List<Customer> list(String name,
							   Integer pageIndex,
							   Integer pageSize);
	/**
	 * 添加或者修改客户信息
	 * @param customer
	 */
	public void save(Customer customer);
	
	/**
	 * 根据id删除客户
	 * @param id
	 */
	public void delete(Integer id);
	
	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public Customer findById(Integer id);

	void update(Customer customer);
}

package com.jykj.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.jykj.dao.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jykj.entity.Customer;
import com.jykj.service.CustomerService;

/**
 * 客户Service实现类
 * @author Administrator
 *
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerMapper customerMapper;


	@Override
	public List<Customer> findByName(String name) {
		return customerMapper.findByName(name);
	}

	@Override
	public List<Customer> list(String name,
							   Integer pageIndex,
							   Integer pageSize) {
		if (pageIndex!=null && pageSize!=null) {
			PageHelper.startPage(pageIndex, pageSize);
		}
		List<Customer> list = customerMapper.list(name);
		return list;
	}

	@Override
	public void save(Customer customer) {
		customerMapper.insert(customer);
	}

	@Override
	public void delete(Integer id) {
		customerMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Customer findById(Integer id) {
		return customerMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(Customer customer) {
		customerMapper.updateByPrimaryKeySelective(customer);
	}

}

package com.jykj.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.jykj.dao.SupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jykj.entity.Supplier;
import com.jykj.service.SupplierService;

/**
 * 供应商Service实现类
 * @author Administrator
 *
 */
@Service("supplierService")
public class SupplierServiceImpl implements SupplierService{

	@Autowired
	private SupplierMapper supplierMapper;


	@Override
	public List<Supplier> list(String name,
							   Integer pageIndex,
							   Integer pageSize) {
		Map<String,Object> searchParams = new HashMap<String,Object>();
		searchParams.put("name", name);

		if (pageIndex!=null&&pageSize!=null){
			PageHelper.startPage(pageIndex, pageSize);
		}
		return supplierMapper.list(searchParams);
	}


	@Override
	public void save(Supplier supplier) {
		supplierMapper.insert(supplier);
	}

	@Override
	public void update(Supplier supplier) {
		supplierMapper.updateByPrimaryKey(supplier);
	}

	@Override
	public void delete(Integer id) {
		supplierMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Supplier findById(Integer id) {
		return supplierMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Supplier> findByName(String name) {
		return supplierMapper.findByName(name);
	}

}

package com.jykj.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.jykj.dao.GoodsTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jykj.entity.GoodsType;
import com.jykj.service.GoodsTypeService;

/**
 * 商品类别Service实现类
 * @author Administrator
 *
 */
@Service("goodsTypeService")
public class GoodsTypeServiceImpl implements GoodsTypeService{

	@Autowired
	private GoodsTypeMapper goodsTypeMapper;
	@Override
	public List<GoodsType> findByParentId(int parentId) {return goodsTypeMapper.findByParentId(parentId);}

	@Override
	public void save(GoodsType goodsType) {
		goodsTypeMapper.insert(goodsType);
	}

	@Override
	public void update(GoodsType goodsType) {
		goodsTypeMapper.updateByPrimaryKey(goodsType);
	}

	@Override
	public void delete(Integer id) {
		goodsTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public GoodsType findById(Integer id) {
		return goodsTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<GoodsType> select() {
		return goodsTypeMapper.list();
	}


}

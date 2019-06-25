package com.jykj.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.jykj.dao.GoodsUnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jykj.entity.GoodsUnit;
import com.jykj.service.GoodsUnitService;

/**
 * 商品单位Service实现类
 * @author Administrator
 *
 */
@Service("goodsUnitService")
public class GoodsUnitServiceImpl implements GoodsUnitService{

	@Autowired
	private GoodsUnitMapper goodsUnitMapper;

	@Override
	public void save(GoodsUnit goodsUnit) {
		goodsUnitMapper.insertSelective(goodsUnit);
	}

	@Override
	public void update(GoodsUnit goodsUnit) {
		goodsUnitMapper.updateByPrimaryKeySelective(goodsUnit);
	}

	@Override
	public void delete(Integer id) {
		goodsUnitMapper.deleteByPrimaryKey(id);
	}

	@Override
	public GoodsUnit findById(Integer id) {
		return goodsUnitMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<GoodsUnit> list(Integer pageIndex,
                                Integer pageSize) {
	    if (pageIndex!=null&&pageSize!=null){
            PageHelper.startPage(pageIndex, pageSize);
        }
		return goodsUnitMapper.list();
	}
}

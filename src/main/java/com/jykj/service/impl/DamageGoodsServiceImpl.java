package com.jykj.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.jykj.dao.DamageGoodsMapper;
import com.jykj.entity.DamageGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jykj.service.DamageGoodsService;

/**
 * 商品报损单商品Service实现类
 * @author Administrator
 *
 */
@Service("damageGoodsService")
public class DamageGoodsServiceImpl implements DamageGoodsService {

	@Autowired
	private DamageGoodsMapper damageGoodsMapper;

	@Override
	public List<DamageGoods> listByDamageListId(Integer damageListId,
												Integer pageIndex,
												Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		return damageGoodsMapper.listByDamageListId(damageListId);
	}
}

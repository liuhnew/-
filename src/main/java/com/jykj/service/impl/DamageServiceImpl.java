package com.jykj.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.jykj.dao.DamageGoodsMapper;
import com.jykj.dao.DamageMapper;
import com.jykj.dao.GoodsMapper;
import com.jykj.entity.*;
import com.jykj.service.DamageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品报损单Service实现类
 * @author Administrator
 *
 */
@Service("damageService")
public class DamageServiceImpl implements DamageService {

	@Autowired
	private DamageMapper damageMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private DamageGoodsMapper damageGoodsMapper;

	@Override
	public String getTodayMaxDamageNumber() {
		return damageMapper.getTodayMaxDamageNumber();
	}

	@Transactional
	public void save(Damage damage, List<DamageGoods> damageGoods) {
		for(DamageGoods damageGood:damageGoods){
			damageGood.setDamageListId(damage.getId()); // 设置商品报损单
			damageGoodsMapper.insert(damageGood);
			// 修改商品库存
			Goods goods = goodsMapper.selectByPrimaryKey(damageGood.getGoodsId());
			goods.setInventoryQuantity(goods.getInventoryQuantity()-damageGood.getNum());
			goods.setState(2);
			goodsMapper.updateByPrimaryKey(goods);
		}
		damageMapper.insert(damage); // 保存商品报损单
	}

	@Override
	public List<Damage> list(Integer pageIndex,
							 Integer pageSize) {
		if (pageIndex!=null&&pageSize!=null){
			PageHelper.startPage(pageIndex, pageSize);
		}
		return damageMapper.list();
	}

	@Override
	public Damage findById(Integer id) {
		return damageMapper.selectByPrimaryKey(id);
	}
}

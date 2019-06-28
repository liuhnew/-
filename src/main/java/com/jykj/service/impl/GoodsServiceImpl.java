package com.jykj.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.jykj.dao.GoodsMapper;
import com.jykj.mongo.MongoDBCollectionOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jykj.entity.Goods;
import com.jykj.service.GoodsService;

import javax.annotation.Resource;

/**
 * 商品Service实现类
 * @author Administrator
 *
 */
@Service("goodsService")
public class GoodsServiceImpl implements GoodsService{

	@Resource
	private GoodsMapper goodsMapper;

	@Autowired
	private MongoDBCollectionOperation tenantMainMongoDBCollection;

	@Override
	public List<Goods> findByTypeId(int typeId) {
		return goodsMapper.findByTypeId(typeId);
	}

	@Override
	public List<Goods> list(String goodsName,
							Integer inventoryQuantity,
							String goodsNum,
							Integer pageIndex,
							Integer pageSize,
							List<String> tenantId) {
		Map<String,Object> map = new HashMap<>();
		map.put("goodsName", goodsName);
		map.put("inventoryQuantity", inventoryQuantity);
		map.put("goodsNum", goodsNum);
		map.put("tenantId",tenantId);
		if(pageIndex!=null&&pageSize!=null){
			PageHelper.startPage(pageIndex, pageSize);
		}
		return goodsMapper.list(map);
	}

	@Override
	public String getMaxGoodsCode() {
		return goodsMapper.getMaxGoodsCode();
	}

	@Override
	public void delete(Integer id) {
		goodsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Goods findById(Integer id) {
		return goodsMapper.selectByPrimaryKey(id);
	}

	@Override
	public void save(Goods goods) {
		goodsMapper.insert(goods);
	}

	@Override
	public void update(Goods goods) {
/*		Map<String,Object> tenantId = tenantMainMongoDBCollection.findOne(goods.getTenantId());
		Map<String,Object> map = new HashMap<>();
		map.put("name", goods.getTenantName());
		tenantMainMongoDBCollection.find(map);*/
		goodsMapper.updateByPrimaryKey(goods);
	}

	@Override
	public List<Goods> listAlarm(String goodsName,
				Integer inventoryQuantity,
				String goodsNum,
				Integer pageIndex,
				Integer pageSize) {
			Map<String,Object> map = new HashMap<>();
			map.put("goodsName", goodsName);
			map.put("inventoryQuantity", inventoryQuantity);
			map.put("goodsNum", goodsNum);
			if(pageIndex!=null&&pageSize!=null){
				PageHelper.startPage(pageIndex, pageSize);
			}
			return goodsMapper.listAlarm(map);
	}
}

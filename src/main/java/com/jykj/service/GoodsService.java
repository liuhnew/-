package com.jykj.service;

import com.jykj.entity.Goods;

import java.util.List;
import java.util.Map;

/**
 * 商品Service接口
 *
 * @author Administrator
 */
public interface GoodsService {

    /**
     * 查询某个类别下的所有商品
     *
     * @param typeId
     * @return
     */
    List<Goods> findByTypeId(int typeId);


    List<Goods> list(String goodsName,
                     Integer inventoryQuantity,
                     String goodsNum,
                     Integer pageIndex,
                     Integer pageSize,
                     List<String> tenantId);


    /**
     * 获取最大的商品编码
     *
     * @return
     */
    String getMaxGoodsCode();

    /**
     * 根据id删除商品
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 根据id查询实体
     *
     * @param id
     * @return
     */
    Goods findById(Integer id);

    /**
     * 添加或者修改商品信息
     *
     * @param goods
     */
    void save(Goods goods);


    void update(Goods goods);

    /**
     * 查询库存报警商品，实际库存小于库存下限的商品
     *
     * @return
     */
    List<Goods> listAlarm(String goodsName,
                          Integer inventoryQuantity,
                          String goodsNum,
                          Integer pageIndex,
                          Integer pageSize);

}

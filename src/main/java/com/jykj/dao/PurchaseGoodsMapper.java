package com.jykj.dao;

import com.jykj.entity.PurchaseGoods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PurchaseGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PurchaseGoods record);

    int insertSelective(PurchaseGoods record);

    PurchaseGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PurchaseGoods record);

    int updateByPrimaryKey(PurchaseGoods record);

    /**
     * 根据进货单id查询所有进货单商品
     * @param purchaseId
     * @return
     */
    @Select(value="SELECT * FROM t_purchase_goods WHERE purchase_list_id=#{purchaseId} ")
    List<PurchaseGoods> listByPurchaseListId(Integer purchaseId);

    /**
     * 根据进货单id删除所有进货单商品
     * @param purchaseId
     * @return
     */
    @Select(value="delete FROM t_purchase_goods WHERE purchase_list_id= #{purchaseId} ")
    void deleteByPurchaseListId(Integer purchaseId);

    List<PurchaseGoods> list(@Param("typeId") Integer typeId, @Param("codeOrName")String codeOrName);
}
package com.jykj.dao;

import com.jykj.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface GoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods goods);

//    int insertSelective(Goods record);
    Goods selectByPrimaryKey(Integer id);

//    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods goods);

    /**
     * 查询某个类别下的所有商品
     *
     * @param typeId
     * @return
     */
    @Select(value = "select * from t_goods where type_id= #{typeId}")
    List<Goods> findByTypeId(@Param("typeId") int typeId);

    /**
     * 获取最大的商品编码
     *
     * @return
     */
    @Select(value = "SELECT MAX(CODE) FROM t_goods")
    String getMaxGoodsCode();

    /**
     * 查询库存报警商品，实际库存小于库存下限的商品
     *
     * @return
     */
    List<Goods> listAlarm(Map<String, Object> map);

    List<Goods> list(Map<String, Object> searchParam);
}
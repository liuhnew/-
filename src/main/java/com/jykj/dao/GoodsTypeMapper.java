package com.jykj.dao;

import com.jykj.entity.GoodsType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsType record);

    int insertSelective(GoodsType record);

    GoodsType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsType record);

    int updateByPrimaryKey(GoodsType record);

    /**
     * 根据父节点查找所有子节点
     * @param parentId
     * @return
     */
    @Select(value="select * from t_goods_type where p_id= #{pid}")
    List<GoodsType> findByParentId(@Param("pid")int parentId);

    List<GoodsType> list();

}
package com.jykj.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface RuprocdefMapper {

    void onoffTask(Map<String,Object> map);

    void onoffAllTask(@Param("id") String id, @Param("status") Integer status);
}

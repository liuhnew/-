package com.jykj.dao;

import com.jykj.entity.WaitTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;
import java.util.Map;

public interface RuprocdefMapper {

    void onoffTask(Map<String,Object> map);

    void onoffAllTask(@Param("id") String id, @Param("status") Integer status);

    Map<String,Object> listbyTaskId(@Param("id")String id);
}

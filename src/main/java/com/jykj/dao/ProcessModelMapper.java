package com.jykj.dao;

import com.jykj.entity.ProcessModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProcessModelMapper {

    List<ProcessModel> list(@Param("name") String name);

    List<Object> waitTaskList(Map<String, Object> map);
}

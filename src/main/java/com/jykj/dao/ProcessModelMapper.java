package com.jykj.dao;

import com.jykj.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProcessModelMapper {

    List<ProcessModel> list(@Param("name") String name);

    List<WaitTask> waitTaskList(Map<String, Object> map);

    List<OverTask> overTask(Map<String, Object> map);

    List<RunProcess> ruProcess(Map<String, Object> map);

    List<Object> runProcessDetail(@Param("proInsId") String processInstanceId);

    RepairOrder queryProcessDetail(String processInstanceId);

    List<HistoryProcess> hiProcess(Map<String,Object> map);

}

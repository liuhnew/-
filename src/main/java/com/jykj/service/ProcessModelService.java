package com.jykj.service;

import com.github.pagehelper.PageInfo;
import com.jykj.entity.*;

import java.util.List;
import java.util.Map;

public interface ProcessModelService {

    PageInfo<ProcessModel> list(String list,
                                Integer pageIndex,
                                Integer pageSize);

    List<WaitTask> waitTaskList(LoginInfo loginInfo,
                                String vehicleNum,
                                String farm,
                                String startTime,
                                String endTime,
                                Integer pageIndex,
                                Integer pageSize);

    List<OverTask> overTask(LoginInfo loginInfo,
                            String vehicleNum,
                            String farm,
                            String startTime,
                            String endTime,
                            Integer pageIndex,
                            Integer pageSize);

    List<RunProcess> ruProcess(LoginInfo loginInfo,
                               String processName,
                               String startTime,
                               String endTime,
                               Integer pageIndex,
                               Integer pageSize);

    List<Object> runProcessDetail(String processInstanceId);

    RepairOrder queryProcessDetail(String processInstanceId);

    List<HistoryProcess> hiProcess(String processName,
                                   String startTime,
                                   String endTime,
                                   Integer pageIndex,
                                   Integer pageSize);
}

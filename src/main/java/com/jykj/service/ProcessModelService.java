package com.jykj.service;

import com.github.pagehelper.PageInfo;
import com.jykj.entity.LoginInfo;
import com.jykj.entity.OverTask;
import com.jykj.entity.ProcessModel;
import com.jykj.entity.WaitTask;

import java.util.List;

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
}

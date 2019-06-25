package com.jykj.service;

import com.github.pagehelper.PageInfo;
import com.jykj.entity.ProcessModel;

import java.util.List;

public interface ProcessModelService {

    PageInfo<ProcessModel> list(String list,
                                Integer pageIndex,
                                Integer pageSize);

    List<Object> waitTaskList(String vehicleNum,
                              String farm,
                              String startTime,
                              String endTime,
                              Integer pageIndex,
                              Integer pageSize);
}

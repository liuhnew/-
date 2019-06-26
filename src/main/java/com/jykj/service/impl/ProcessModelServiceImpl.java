package com.jykj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jykj.dao.ProcessModelMapper;
import com.jykj.entity.LoginInfo;
import com.jykj.entity.OverTask;
import com.jykj.entity.ProcessModel;
import com.jykj.entity.WaitTask;
import com.jykj.service.ProcessModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("processModelService")
public class ProcessModelServiceImpl implements ProcessModelService {

    @Autowired
    private ProcessModelMapper processModelMapper;

    @Override
    public PageInfo<ProcessModel> list(String name,
                                   Integer pageIndex,
                                   Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<ProcessModel> list = processModelMapper.list(name);
        PageInfo<ProcessModel> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public List<WaitTask> waitTaskList(LoginInfo loginInfo,
                                       String vehicleNum,
                                       String farm,
                                       String startTime,
                                       String endTime,
                                       Integer pageIndex,
                                       Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", loginInfo.getSub());
        map.put("vehicleNum", vehicleNum);
        map.put("farm", farm);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        if(pageIndex!=null&&pageSize!=null){
            PageHelper.startPage(pageIndex, pageSize);
        }
        List<WaitTask> list = processModelMapper.waitTaskList(map);
        return list;
    }

    @Override
    public List<OverTask> overTask(LoginInfo loginInfo,
                                 String vehicleNum,
                                 String farm,
                                 String startTime,
                                 String endTime,
                                 Integer pageIndex,
                                 Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", loginInfo.getSub());
        map.put("vehicleNum", vehicleNum);
        map.put("farm", farm);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        if(pageIndex!=null&&pageSize!=null){
            PageHelper.startPage(pageIndex, pageSize);
        }
        List<OverTask> list = processModelMapper.overTask(map);
        return list;
    }
}

package com.jykj.service.impl;

import com.jykj.dao.RuprocdefMapper;
import com.jykj.service.RunTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("runTaskService")
public class RunTaskServiceImpl implements RunTaskService {

    @Autowired
    private RuprocdefMapper ruprocdefMapper;

    @Override
    public void onoffTask(String id, Integer status) {
        Map<String,Object> map = new HashMap<>();
        map.put("taskId", id);
        map.put("status", status);
        ruprocdefMapper.onoffTask(map);
    }

    @Override
    public void onoffAllTask(String id,
                             Integer status) {
        ruprocdefMapper.onoffAllTask(id, status);
    }
}

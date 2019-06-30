package com.jykj.service.impl;

import com.github.pagehelper.PageHelper;
import com.jykj.dao.ProcessManagerMapper;
import com.jykj.entity.ProDefEntity;
import com.jykj.service.ProcessManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("processManagerService")
public class ProcessManagerServiceImpl implements ProcessManagerService {

    @Autowired
    private ProcessManagerMapper processManagerMapper;

    @Override
    public List<ProDefEntity> list(String keyword, String startTime, String endTime, Integer pageIndex, Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("startTime", startTime);
        map.put("endTime", endTime);

        if (pageIndex!=null&&pageSize!=null){
            PageHelper.startPage(pageIndex, pageSize);
        }
        return processManagerMapper.list(map);
    }
}

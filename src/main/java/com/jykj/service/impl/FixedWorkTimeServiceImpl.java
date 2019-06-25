package com.jykj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jykj.dao.FixedWorkTimeMapper;
import com.jykj.entity.FixedWorkTime;
import com.jykj.service.FixedWorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("fixedWorkTimeService")
public class FixedWorkTimeServiceImpl implements FixedWorkTimeService {

    @Autowired
    private FixedWorkTimeMapper fixedWorkTimeMapper;

    @Override
    public void save(FixedWorkTime fixedWorkTime) {
        fixedWorkTimeMapper.insertSelective(fixedWorkTime);
    }

    @Override
    public void update(FixedWorkTime fixedWorkTime) {
        fixedWorkTimeMapper.updateByPrimaryKeySelective(fixedWorkTime);
    }

    @Override
    public List<FixedWorkTime> list(String fixedName,
                                    String fixedType,
                                    String vehicleType) {
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("fixedName", fixedName);
        searchParams.put("fixedType", fixedType);
        searchParams.put("vehicleType", vehicleType);
        return fixedWorkTimeMapper.list(searchParams);
    }

    @Override
    public PageInfo<FixedWorkTime> list(String fixedName,
                                        String fixedType,
                                        String vehicleType,
                                        Integer pageIndex,
                                        Integer pageSize) {
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("fixedName", fixedName);
        searchParams.put("fixedType", fixedType);
        searchParams.put("vehicleType", vehicleType);

        PageHelper.startPage(pageIndex, pageSize);
        List<FixedWorkTime> list = fixedWorkTimeMapper.list(searchParams);
        PageInfo<FixedWorkTime> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public FixedWorkTime queryById(String id) {
        return fixedWorkTimeMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(String id) {
        fixedWorkTimeMapper.deleteByPrimaryKey(id);
    }
}

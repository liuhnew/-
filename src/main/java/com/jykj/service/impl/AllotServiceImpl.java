package com.jykj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jykj.dao.AllotMapper;
import com.jykj.entity.Allot;
import com.jykj.service.AllotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("allotService")
public class AllotServiceImpl implements AllotService {

    @Override
    public PageInfo<Allot> list(String orgId,
                                Integer pageIndex,
                                Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Allot> list = allotMapper.queryByAllot(orgId);
        return new PageInfo<Allot>(list);
    }

    @Autowired
    private AllotMapper allotMapper;

    @Override
    public void save(Allot allot) {
        allotMapper.insertSelective(allot);
    }

    @Override
    public void delete(Integer id) {
        allotMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Allot allot) {
        allotMapper.updateByPrimaryKeySelective(allot);
    }

    @Override
    public List<Allot> queryByAllot(String orgId) {
        return allotMapper.queryByAllot(orgId);
    }

    @Override
    public Allot queryById(Integer id) {
        return allotMapper.selectByPrimaryKey(id);
    }
}

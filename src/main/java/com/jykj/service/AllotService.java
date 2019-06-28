package com.jykj.service;

import com.github.pagehelper.PageInfo;
import com.jykj.entity.Allot;

import java.util.List;
import java.util.Map;

public interface AllotService {

    void save(Allot allot);

    void delete(Integer id);

    void update(Allot allot);

    List<Allot> queryByAllot(String orgId);

    PageInfo<Allot> list(String orgId,Integer pageIndex,Integer pageSize);

    Allot queryById(Integer id);

    List<Allot> list(Map<String,Object> map);
}

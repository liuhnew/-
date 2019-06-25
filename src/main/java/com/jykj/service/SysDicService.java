package com.jykj.service;

import com.github.pagehelper.PageInfo;
import com.jykj.entity.SysDic;
import com.jykj.util.TreeBean;

import java.util.List;

public interface SysDicService {

    void save(SysDic sysDic);

    void delete(Integer id);

    void update(SysDic sysDic);

    PageInfo<SysDic> list(String keyWord, Integer pageIndex, Integer pageSize);

    SysDic selectById(Integer id);

    List<TreeBean<SysDic>> tree();

}

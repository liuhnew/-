package com.jykj.service;

import com.github.pagehelper.PageInfo;
import com.jykj.entity.FixedWorkTime;

import java.util.List;

public interface FixedWorkTimeService {

    void save(FixedWorkTime fixedWorkTime);

    void update(FixedWorkTime fixedWorkTime);

    List<FixedWorkTime> list(String fixedName,
                             String fixedType,
                             String vehicleType);

    PageInfo<FixedWorkTime> list(String fixedName,
                                 String fixedType,
                                 String vehicleType,
                                 Integer pageIndex,
                                 Integer pageSize);

    FixedWorkTime queryById(String id);

    void delete(String id);

}

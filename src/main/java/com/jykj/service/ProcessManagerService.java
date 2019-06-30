package com.jykj.service;

import com.jykj.entity.ProDefEntity;

import java.util.List;

public interface ProcessManagerService {

    List<ProDefEntity> list(String keyword,
                            String startTime,
                            String endTime,
                            Integer pageIndex,
                            Integer pageSize);
}

package com.jykj.dao;

import com.jykj.entity.ProDefEntity;

import java.util.List;
import java.util.Map;

public interface ProcessManagerMapper {

    List<ProDefEntity> list(Map<String, Object> map);

}

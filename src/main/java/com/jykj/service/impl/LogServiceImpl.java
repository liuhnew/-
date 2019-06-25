package com.jykj.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.github.pagehelper.PageHelper;
import com.jykj.dao.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jykj.entity.Log;
import com.jykj.service.LogService;

/**
 * 用户Service实现类
 * @author Administrator
 *
 */
@Service("logService")
public class LogServiceImpl implements LogService{

	@Autowired
	private LogMapper logMapper;

	@Override
	public void save(Log log) {
		log.setTime(new Date()); // 设置操作日期
		logMapper.insert(log);
	}

	@Override
	public List<Log> list(String type,
						  String startTime,
						  String endTime,
						  Integer pageIndex,
						  Integer pageSize) {
		Map<String,Object> searchParams = new HashMap<String,Object>();
		searchParams.put("type", type);
		searchParams.put("startTime", startTime);
		searchParams.put("endTime", endTime);
		if (pageIndex!=null&&pageSize!=null) {
			PageHelper.startPage(pageIndex, pageSize);
		}
		return logMapper.list(searchParams);
	}
}

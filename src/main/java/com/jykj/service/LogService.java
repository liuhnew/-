package com.jykj.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.jykj.entity.Log;

/**
 * 日志Service接口
 * @author Administrator
 *
 */
public interface LogService {

	/**
	 * 添加或者修改日志信息
	 * @param log
	 */
	public void save(Log log);
	
	/**
	 * 根据条件分页查询日志信息
	 * @return
	 */
	public List<Log> list(String type,
						  String startTime,
						  String endTime,
						  Integer pageIndex,
						  Integer pageSize);
}

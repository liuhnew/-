package com.jykj.controller.restapi;

import com.github.pagehelper.PageInfo;
import com.jykj.controller.BaseController;
import com.jykj.entity.*;
import com.jykj.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 后台管理用户Controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/service/log")
public class LogAPIController extends BaseController {

	@Autowired
	private LogService logService;

	/**
	 * 根据条件分页查询日志信息
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public Result list(String name,
					   String startTime,
					   String endTime,
					   @RequestParam(defaultValue = "1")Integer pageIndex,
					   @RequestParam(defaultValue = "15")Integer pageSize){
		List<Log> logList = logService.list(name, startTime, endTime, pageIndex, pageSize);
		PageInfo<Log> pageInfo=new PageInfo<Log>(logList);
		return Result.createSuccess("查询成功", pageInfo);
	}
	
	
}

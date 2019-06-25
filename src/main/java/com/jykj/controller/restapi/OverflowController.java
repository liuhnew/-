package com.jykj.controller.restapi;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jykj.controller.BaseController;
import com.jykj.entity.*;
import com.jykj.service.LogService;
import com.jykj.service.OverflowGoodsService;
import com.jykj.service.OverflowService;
import com.jykj.util.DateUtil;
import com.jykj.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理报溢单Controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/service/overflowList")
public class OverflowController extends BaseController {

	@Autowired
	private OverflowService overflowListService;
	
	@Autowired
	private OverflowGoodsService overflowListGoodsService;

	@Autowired
	private LogService logService;
	
	/**
	 * 获取报溢单号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/genCode", method = RequestMethod.GET)
	public Result genCode()throws Exception{
		StringBuffer code=new StringBuffer("BY");
		code.append(DateUtil.getCurrentDateStr());
		String overflowNumber=overflowListService.getTodayMaxOverflowNumber();
		if(overflowNumber!=null){
			code.append(StringUtil.formatCode(overflowNumber));
		}else{
			code.append("0001");
		}
		return new Result(true, StatusCode.OK,"查询成功",code.toString());
	}
	
	/**
	 * 添加报溢单 以及所有报溢单商品
	 * @param overflowList
	 * @param goodsJson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Result save(@RequestBody Overflow overflow, @RequestBody String goodsJson)throws Exception{
		Gson gson=new Gson();
		List<OverflowGoods> plgList=gson.fromJson(goodsJson,new TypeToken<List<OverflowGoods>>(){}.getType());
		overflowListService.save(overflow, plgList);
		logService.save(new Log(Log.ADD_ACTION,"添加报溢单"));
		return new Result(true, StatusCode.OK,"添加成功");
	}
	
	/**
	 * 根据条件查询所有报溢单信息
	 * @param overflowList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public Result list(String startTime,
					   String endTime,
					   Integer pageIndex,
					   Integer pageSize)throws Exception{
		List<Overflow> overflowList = overflowListService.list(startTime, endTime, pageIndex, pageSize);
		PageInfo<Overflow> pageInfo = new PageInfo<>(overflowList);
		return Result.createSuccess("查询成功", pageInfo);
	}
	
	/**
	 * 根据报溢单id查询所有报溢单商品
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listGoods", method = RequestMethod.GET)
	public Result listGoods(Integer id)throws Exception{
		List<OverflowGoods> overflowListGoods = overflowListGoodsService.listByOverflowListId(id);
		return new Result(true, StatusCode.OK,"查询成功", overflowListGoods);
	}
	
	
}

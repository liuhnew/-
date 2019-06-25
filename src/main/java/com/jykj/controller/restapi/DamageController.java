package com.jykj.controller.restapi;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jykj.controller.BaseController;
import com.jykj.entity.*;
import com.jykj.service.DamageGoodsService;
import com.jykj.service.DamageService;
import com.jykj.service.LogService;
import com.jykj.util.DateUtil;
import com.jykj.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台管理报损单Controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/service/damage")
public class DamageController extends BaseController {

	@Autowired
	private DamageService damageService;
	
	@Autowired
	private DamageGoodsService damageGoodsService;
	
	@Autowired
	private LogService logService;
	
	/**
	 * 获取报损单号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/genCode",method = RequestMethod.GET)
	public Result genCode()throws Exception{
		StringBuffer code=new StringBuffer("BS");
		code.append(DateUtil.getCurrentDateStr());
		String damageNumber = damageService.getTodayMaxDamageNumber();
		if(damageNumber!=null){
			code.append(StringUtil.formatCode(damageNumber));
		}else{
			code.append("0001");
		}
		return  new Result(true, StatusCode.OK,"获取报损单号成功",  code.toString());
	}
	
	/**
	 * 添加报损单 以及所有报损单商品
	 * @param damage
	 * @param goodsJson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result save(@RequestBody Damage damage,
					   @RequestBody String goodsJson)throws Exception{
		Gson gson=new Gson();
		List<DamageGoods> plgList=gson.fromJson(goodsJson,new TypeToken<List<DamageGoods>>(){}.getType());
		damageService.save(damage, plgList);
		logService.save(new Log(Log.ADD_ACTION,"添加报损单"));
		return Result.createSuccess("添加成功");
	}
	
	/**
	 * 根据条件查询所有报损单信息
	 * @param damageList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "list",method = RequestMethod.POST)
	public Result list(Integer pageIndex,
					   Integer pageSize)throws Exception{
		List<Damage> damage = damageService.list(pageIndex, pageSize);
		PageInfo<Damage> pageInfo = new PageInfo<>(damage);
		return Result.createSuccess("查询成功",  pageInfo);
	}
	
	/**
	 * 根据报损单id查询所有报损单商品
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryByDamageId", method = RequestMethod.POST)
	public Result listGoods(Integer id,
							Integer pageIndex,
							Integer pageSize)throws Exception{
		List<DamageGoods> damageGoods = damageGoodsService.listByDamageListId(id, pageIndex, pageSize);
		PageInfo<DamageGoods> pageInfo = new PageInfo<>(damageGoods);
		return Result.createSuccess("查询成功",  pageInfo);
	}
	
	
}

package com.jykj.controller.restapi;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.pagehelper.PageInfo;
import com.jykj.controller.BaseController;
import com.jykj.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jykj.service.LogService;
import com.jykj.service.PurchaseGoodsService;
import com.jykj.service.PurchaseService;
import com.jykj.util.DateUtil;
import com.jykj.util.StringUtil;

/**
 * 后台管理进货单Controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/service/purchaseList")
public class PurchaseListAPIController extends BaseController {

	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private PurchaseGoodsService purchaseGoodsService;
	
	@Autowired
	private LogService logService;

	
	/**
	 * 获取进货单号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/genCode", method = RequestMethod.GET)
	public Result genCode()throws Exception{
		StringBuffer code=new StringBuffer("JH");
		code.append(DateUtil.getCurrentDateStr());
		String purchaseNumber=purchaseService.getTodayMaxPurchaseNumber();
		if(purchaseNumber!=null){
			code.append(StringUtil.formatCode(purchaseNumber));
		}else{
			code.append("0001");
		}
		return new Result(true, StatusCode.OK,"获取进货单号",code.toString());
	}
	
	/**
	 * 添加进货单 以及所有进货单商品
	 * @param purchase
	 * @param goodsJson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Result save(Purchase purchase,String goodsJson)throws Exception{
		Gson gson=new Gson();
		List<PurchaseGoods> plgList=gson.fromJson(goodsJson,new TypeToken<List<PurchaseGoods>>(){}.getType());
		purchaseService.save(purchase, plgList);
		logService.save(new Log(Log.ADD_ACTION,"添加进货单"));
		return Result.createSuccess("添加成功");
	}
	
	/**
	 * 根据条件查询所有进货单信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Result list(String purchaseNumber,
								   Integer supplierId,
								   Integer state,
								   String startTime,
								   String endTime,
								   Integer pageIndex,
								   Integer pageSize) throws Exception{
		List<Purchase> purchaseListList=purchaseService.list(purchaseNumber, supplierId, state, startTime, endTime, pageIndex, pageSize);
		PageInfo<Purchase> pageInfo = new PageInfo<>(purchaseListList);
		return Result.createSuccess("查询成功", pageInfo);
	}
	
	/**
	 * 根据进货单id查询所有进货单商品
	 * @param purchaseListId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listGoods", method = RequestMethod.POST)
	public Result listGoods(Integer purchaseListId)throws Exception{
		return Result.createSuccess("查询成功", purchaseGoodsService.listByPurchaseListId(purchaseListId));
	}
	
	/**
	 * 删除进货单 以及进货单里的商品
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result delete(Integer id)throws Exception{
		purchaseService.delete(id);
		logService.save(new Log(Log.DELETE_ACTION,"删除进货单信息："+purchaseService.findById(id)));
		return  Result.createSuccess("删除成功");
	}
	
	/**
	 * 修改进货单的支付状态
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update" , method = RequestMethod.POST)
	public Result update(Integer id)throws Exception{
		Purchase purchaseList = purchaseService.findById(id);
		purchaseList.setState(1);
		purchaseService.update(purchaseList);
		return Result.createSuccess("修改成功");
	}
}

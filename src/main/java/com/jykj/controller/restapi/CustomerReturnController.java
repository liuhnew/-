package com.jykj.controller.restapi;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jykj.controller.BaseController;
import com.jykj.entity.*;
import com.jykj.service.CustomerReturnGoodsService;
import com.jykj.service.CustomerReturnService;
import com.jykj.service.LogService;
import com.jykj.util.DateUtil;
import com.jykj.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 后台管理客户退货单Controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/service/customerReturn")
public class CustomerReturnController extends BaseController {

	@Autowired
	private CustomerReturnService customerReturnService;
	
	@Autowired
	private CustomerReturnGoodsService customerReturnGoodsService;
	
	@Autowired
	private LogService logService;
	
	/**
	 * 获取客户退货单号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/genCode",method = RequestMethod.PUT)
	public Result genCode()throws Exception{
		StringBuffer code=new StringBuffer("XT");
		code.append(DateUtil.getCurrentDateStr());
		String customerReturnNumber=customerReturnService.getTodayMaxCustomerReturnNumber();
		if(customerReturnNumber!=null){
			code.append(StringUtil.formatCode(customerReturnNumber));
		}else{
			code.append("0001");
		}
		return  new Result(true, StatusCode.OK,"获取客户退货单号成功",  code.toString() );
	}
	
	/**
	 * 添加客户退货单 以及所有客户退货单商品
	 * @param customerReturnList
	 * @param goodsJson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.PUT)
	public Result save(@RequestBody(required = false) CustomerReturn customerReturnList, @RequestBody(required = false) String goodsJson)throws Exception{
		Gson gson=new Gson();
		List<CustomerReturnGoods> plgList=gson.fromJson(goodsJson,new TypeToken<List<CustomerReturnGoods>>(){}.getType());
		customerReturnService.save(customerReturnList, plgList);
		logService.save(new Log(Log.ADD_ACTION,"添加客户退货单"));
		return new Result(true, StatusCode.OK,"添加客户退货单成功");
	}
	
	/**
	 * 根据条件查询所有客户退货单信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public Result list(String customerReturnNumber,
					   Integer customerId,
					   Integer state,
					   String customerReturnDate,
					   Integer pageIndex,
					   Integer pageSize)throws Exception{
		List<CustomerReturn> list = customerReturnService.list(customerReturnNumber, customerId, state, customerReturnDate, pageIndex, pageSize);
		PageInfo<CustomerReturn> pageInfo = new PageInfo<CustomerReturn>(list);
		return Result.createSuccess("查询成功", pageInfo);
	}
	
	/**
	 * 根据条件获取商品销售信息
	 * @param customerReturnList
	 * @param customerReturnListGoods
	 * @r
	 * @throws Exception
	 */
	@RequestMapping(value = "/listCount",method = RequestMethod.POST)
	public Result listCount(@RequestBody CustomerReturn customerReturnList,@RequestBody CustomerReturnGoods customerReturnListGoods)throws Exception{
		return Result.createSuccess("");
	}
	
	/**
	 * 根据客户退货单id查询所有客户退货单商品
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryReturnGoods",method = RequestMethod.GET)
	public Result listGoods(Integer id,
							Integer pageIndex,
							Integer pageSize)throws Exception{
		List<CustomerReturnGoods> customerReturnGoods = customerReturnGoodsService.listByCustomerReturnListId(id, pageIndex, pageSize);
		PageInfo<CustomerReturnGoods> pageInfo = new PageInfo<CustomerReturnGoods>(customerReturnGoods);
		return Result.createSuccess("客户退货单商品查询成功", pageInfo);
	}
	
	/**
	 * 删除客户退货单 以及客户退货单里的商品
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result delete(Integer id)throws Exception{
		customerReturnService.delete(id);
		logService.save(new Log(Log.DELETE_ACTION,"删除客户退货单信息："+customerReturnService.findById(id)));
		return  new Result(true, StatusCode.OK,"删除客户退货单信息成功");
	}
	
	/**
	 * 修改客户退货单的支付状态
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/payState", method = RequestMethod.PUT)
	public Result update(@PathVariable Integer id)throws Exception{
		CustomerReturn customerReturn=customerReturnService.findById(id);
		customerReturn.setState(1);
		customerReturnService.update(customerReturn);
		logService.save(new Log(Log.DELETE_ACTION,"修改客户退货单的支付状态："+customerReturn));
		return Result.createSuccess("修改成功");
	}
}

package com.jykj.controller.restapi;

import com.github.pagehelper.PageInfo;
import com.jykj.entity.*;
import com.jykj.service.CustomerService;
import com.jykj.service.LogService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台管理客户Controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/service/customer")
public class CustomerAPIController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private LogService logService;
	
	/**
	 * 下拉框模糊查询
	 * @param q
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/comboList",method = RequestMethod.POST)
	public Result comboList(String name)throws Exception{
		List<Customer> customerList = customerService.findByName(name);
		return Result.createSuccess("查询成功",  customerList);
	}

	/**
	 * 分页查询客户信息
	 * @param name
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "name", value = "", required = false, dataType = "String", defaultValue = ""),
			@ApiImplicitParam(paramType = "query", name = "pageIndex", value = "", required = false, dataType = "String", defaultValue = ""),
			@ApiImplicitParam(paramType = "query", name = "pageSize", value = "", required = false, dataType = "String", defaultValue = ""),
	})
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public Result list(String name,
					   Integer pageIndex,
					   Integer pageSize)throws Exception{
		List<Customer> customerList=customerService.list(name, pageIndex, pageSize);
		PageInfo<Customer> pageInfo = new PageInfo<>(customerList);
		return Result.createSuccess("查询成功", pageInfo);
	}
	
	/**
	 * 添加客户信息
	 * @return
	 * @throws Exception
	 */
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "address", value = "", required = false, dataType = "String", defaultValue = ""),
			@ApiImplicitParam(paramType = "query", name = "contact", value = "", required = false, dataType = "String", defaultValue = ""),
			@ApiImplicitParam(paramType = "query", name = "name", value = "", required = false, dataType = "String", defaultValue = ""),
			@ApiImplicitParam(paramType = "query", name = "number", value = "", required = false, dataType = "String", defaultValue = ""),
			@ApiImplicitParam(paramType = "query", name = "remarks", value = "", required = false, dataType = "String", defaultValue = ""),
	})
	@ApiOperation(value = "添加客户信息", notes = "添加客户信息")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Result save(String address,
					   String contact,
					   String name,
					   String number,
					   String remarks)throws Exception{
		Customer customer = new Customer();
		customer.setAddress(address);
		customer.setContact(contact);
		customer.setName(name);
		customer.setNumber(number);
		customer.setRemarks(remarks);
		customerService.save(customer);
		return Result.createSuccess("保存成功");
	}

	/**
	 * 修改客户信息
	 * @param
	 * @return
	 * @throws Exception
	 */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "address", value = "", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "contact", value = "", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "name", value = "", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "number", value = "", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "remarks", value = "", required = false, dataType = "String", defaultValue = ""),
    })
	@ApiOperation(value = "修改客户信息", notes = "修改客户信息")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Result update(Integer id,
						 String address,
						 String contact,
						 String name,
						 String number,
						 String remarks)throws Exception{
		Customer customer = customerService.findById(id);
		if (customer==null){
			return Result.createFail("客戶不存在");
		}
		customer.setAddress(address);
		customer.setContact(contact);
		customer.setName(name);
		customer.setNumber(number);
		customer.setRemarks(remarks);
		customerService.update(customer);
		return Result.createSuccess("保存成功");
	}
	
	/**
	 * 删除客户信息
	 * @param  id
	 * @return
	 * @throws Exception
	 */
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "id", value = "", required = false, dataType = "String", defaultValue = "")
	})
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public Result delete(String id)throws Exception{
		String[] idsStr = id.split(",");
		for(int i = 0;i<idsStr.length;i++){
			logService.save(new Log(Log.DELETE_ACTION,"删除客户信息"+customerService.findById(Integer.parseInt(idsStr[i]))));
			customerService.delete(Integer.parseInt(idsStr[i]));
		}
		return  new Result(true, StatusCode.OK,"删除成功");
	}
	
	
}

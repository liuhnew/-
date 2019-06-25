package com.jykj.controller.restapi;

import com.github.pagehelper.PageInfo;
import com.jykj.entity.*;
import com.jykj.service.LogService;
import com.jykj.service.SupplierService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台管理供应商Controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/service/supplier")
public class SupplierAPIController {

	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private LogService logService;
	
	/**
	 * 下拉框模糊查询
	 * @param
	 * @return
	 * @throws Exception
	 */
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "string", paramType = "query", defaultValue = "", name = "name")
	})
	@RequestMapping(value = "/comboList", method = RequestMethod.POST)
	public Result comboList(String name)throws Exception{
		List<Supplier> suppliers =supplierService.findByName(name);
		return Result.createSuccess("查询成功", suppliers);
	}
	
	/**
	 * 分页查询供应商信息
	 * @param name
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "string", paramType = "query", defaultValue = "", name = "name"),
			@ApiImplicitParam(dataType = "string", paramType = "query", defaultValue = "", name = "pageIndex"),
			@ApiImplicitParam(dataType = "string", paramType = "query", defaultValue = "", name = "pageSize")
	})
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public Result list(String name,
					   Integer pageIndex,
					   Integer pageSize)throws Exception{
		List<Supplier> supplierList=supplierService.list(name, pageIndex, pageSize);
		PageInfo<Supplier> pageInfo = new PageInfo<>(supplierList);
		return Result.createSuccess( "查询成功", pageInfo);
	}

	/**
	 * 添加或者修改供应商信息
	 * @param id
	 * @param address
	 * @param contact
	 * @param name
	 * @param number
	 * @param remarks
	 * @return
	 * @throws Exception
	 */
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "string", paramType = "query", defaultValue = "", name = "name"),
			@ApiImplicitParam(dataType = "string", paramType = "query", defaultValue = "", name = "id"),
			@ApiImplicitParam(dataType = "string", paramType = "query", defaultValue = "", name = "address"),
			@ApiImplicitParam(dataType = "string", paramType = "query", defaultValue = "", name = "contact"),
			@ApiImplicitParam(dataType = "string", paramType = "query", defaultValue = "", name = "number"),
			@ApiImplicitParam(dataType = "string", paramType = "query", defaultValue = "", name = "remarks"),
	})
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Result save(Integer id,
					   String address,
					   String contact,
					   String name,
					   String number,
					   String remarks)throws Exception{
		Supplier supplier;
		if(id!=null){
			supplier = supplierService.findById(id);

			supplier.setName(name);
			supplier.setAddress(address);
			supplier.setContact(contact);
			supplier.setNumber(number);
			supplier.setRemarks(remarks);
			supplierService.update(supplier);
            logService.save(new Log(Log.UPDATE_ACTION,"更新供应商信息"+ name));
			return Result.createSuccess("更新成功", supplier);

		}else{
			supplier = new Supplier();
			supplier.setName(name);
			supplier.setAddress(address);
			supplier.setContact(contact);
			supplier.setNumber(number);
			supplier.setRemarks(remarks);
			supplierService.save(supplier);
            logService.save(new Log(Log.ADD_ACTION,"添加供应商信息"+ name));
			return Result.createSuccess("保存成功", supplier);
		}
    }
	
	/**
	 * 删除供应商信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "string", paramType = "query", defaultValue = "", name = "id"),
	})
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public Result delete(String id)throws Exception{
		String[] idsStr = id.split(",");
		for(int i=0;i<idsStr.length;i++){
			logService.save(new Log(Log.DELETE_ACTION,"删除供应商信息"+supplierService.findById(Integer.parseInt(idsStr[i]))));
			supplierService.delete(Integer.parseInt(idsStr[i]));
		}
		return Result.createSuccess( "删除成功");
	}
}

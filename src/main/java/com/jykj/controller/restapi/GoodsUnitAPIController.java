package com.jykj.controller.restapi;

import com.github.pagehelper.PageInfo;
import com.jykj.entity.*;
import com.jykj.service.GoodsUnitService;
import com.jykj.service.LogService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台管理商品单位Controller
 * @author Administrator
 *
 */
@Controller
@RestController
@RequestMapping("/service/goodsUnit")
public class GoodsUnitAPIController {

	@Autowired
	private GoodsUnitService goodsUnitService;

	@Autowired
	private LogService logService;

	/**
	 * 返回所有商品单位 下拉框用到
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/comboList", method = RequestMethod.POST)
	public Result comboList()throws Exception{
		return  Result.createSuccess("查询成功", goodsUnitService.list(null, null));
	}
	
	/**
	 * 查询所有商品单位信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public Result list(Integer pageIndex,
                       Integer pageSize)throws Exception{
        List<GoodsUnit> list = goodsUnitService.list(pageIndex, pageSize);
        PageInfo<GoodsUnit> pageInfo = new PageInfo<GoodsUnit>(list);
		return Result.createSuccess("查询成功", pageInfo);
	}
	
	/**
	 * 添加商品单位
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@ApiImplicitParams({
            @ApiImplicitParam(name = "name", dataType = "string", paramType = "query", defaultValue = "")
    })
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result save(String name)throws Exception{
		GoodsUnit goodsUnit = new GoodsUnit();
		goodsUnit.setName(name);
		goodsUnitService.save(goodsUnit);
		logService.save(new Log(Log.ADD_ACTION,"添加商品单位信息" + name));
		return Result.createSuccess("添加成功");
	}
	
	/**
	 * 商品单位删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", dataType = "string", paramType = "query", defaultValue = "")
    })
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Result delete(@RequestParam String id)throws Exception{
        String[] idStr = id.split(",");
        for (String str : idStr) {
            Integer a = Integer.parseInt(str);
            logService.save(new Log(Log.DELETE_ACTION,"删除商品单位信息："+goodsUnitService.findById(a).getName()));
            goodsUnitService.delete(a);
        }
		return Result.createSuccess("删除成功");
	}

}

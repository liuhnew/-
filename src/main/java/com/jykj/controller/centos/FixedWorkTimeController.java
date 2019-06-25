package com.jykj.controller.centos;

import com.github.pagehelper.PageInfo;
import com.jykj.entity.FixedWorkTime;
import com.jykj.entity.Result;
import com.jykj.service.FixedWorkTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@Api(value = "定时工额管理", tags = "定时工额管理")
@RestController
@RequestMapping("/service/cencus/fixwt")
public class FixedWorkTimeController {

    @Autowired
    private FixedWorkTimeService fixedWorkTimeService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "autoType", value = "车辆类型", required = false, dataType = "String", defaultValue = "纯电动车"),
            @ApiImplicitParam(paramType = "query", name = "num", value = "维修编号", required = false, dataType = "String", defaultValue = "0001"),
            @ApiImplicitParam(paramType = "query", name = "name", value = "维修事项名", required = false, dataType = "String", defaultValue = "更换挡风玻璃"),
            @ApiImplicitParam(paramType = "query", name = "serviceType", value = "维修服务类型", required = false, dataType = "String", defaultValue = "维修"),
            @ApiImplicitParam(paramType = "query", name = "itemsType", value = "作业部件类型", required = false, dataType = "String", defaultValue = "维修"),
            @ApiImplicitParam(paramType = "query", name = "repairTime", value = "工时", required = false, dataType = "String", defaultValue = "3"),
            @ApiImplicitParam(paramType = "query", name = "price", value = "金额", required = false, dataType = "String", defaultValue = "22"),
            @ApiImplicitParam(paramType = "query", name = "state", value = "是否启用", required = false, dataType = "String", defaultValue = "0"),
            @ApiImplicitParam(paramType = "query", name = "detail", value = "详情", required = false, dataType = "String", defaultValue = "1111"),
    })
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Result save(String autoType,
                       String num,
                       String name,
                       String serviceType,
                       String itemsType,
                       Float repairTime,
                       Float price,
                       Integer state,
                       String detail){
        FixedWorkTime fixedWorkTime = new FixedWorkTime();
        fixedWorkTime.setId(UUID.randomUUID().toString().replace("-",""));
        fixedWorkTime.setAutoType(autoType);
        fixedWorkTime.setNum(num);
        fixedWorkTime.setName(name);
        fixedWorkTime.setServiceType(serviceType);
        fixedWorkTime.setItemsType(itemsType);
        fixedWorkTime.setRepairItemsTime(repairTime);
        fixedWorkTime.setPrice(price);
        fixedWorkTime.setState(state);
        fixedWorkTime.setDetail(detail);
        fixedWorkTime.setCreateTime(new Date());
        fixedWorkTime.setUpdateTime(new Date());
        fixedWorkTimeService.save(fixedWorkTime);
        return Result.createSuccess("添加成功", fixedWorkTime);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "ID", required = false, dataType = "String", defaultValue = "纯电动车"),
            @ApiImplicitParam(paramType = "query", name = "autoType", value = "车辆类型", required = false, dataType = "String", defaultValue = "纯电动车"),
            @ApiImplicitParam(paramType = "query", name = "num", value = "维修编号", required = false, dataType = "String", defaultValue = "0001"),
            @ApiImplicitParam(paramType = "query", name = "name", value = "维修事项名", required = false, dataType = "String", defaultValue = "更换挡风玻璃"),
            @ApiImplicitParam(paramType = "query", name = "serviceType", value = "维修服务类型", required = false, dataType = "String", defaultValue = "维修"),
            @ApiImplicitParam(paramType = "query", name = "itemsType", value = "作业部件类型", required = false, dataType = "String", defaultValue = "维修"),
            @ApiImplicitParam(paramType = "query", name = "repairTime", value = "工时", required = false, dataType = "String", defaultValue = "3"),
            @ApiImplicitParam(paramType = "query", name = "price", value = "金额", required = false, dataType = "String", defaultValue = "22"),
            @ApiImplicitParam(paramType = "query", name = "state", value = "是否启用", required = false, dataType = "String", defaultValue = "0"),
            @ApiImplicitParam(paramType = "query", name = "detail", value = "详情", required = false, dataType = "String", defaultValue = "1111"),
    })
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Result save(String id,
                       String autoType,
                       String num,
                       String name,
                       String serviceType,
                       String itemsType,
                       Float repairTime,
                       Float price,
                       Integer state,
                       String detail){
        FixedWorkTime fwt = fixedWorkTimeService.queryById(id);
        fwt.setAutoType(autoType);
        fwt.setNum(num);
        fwt.setName(name);
        fwt.setServiceType(serviceType);
        fwt.setItemsType(itemsType);
        fwt.setRepairItemsTime(repairTime);
        fwt.setPrice(price);
        fwt.setState(state);
        fwt.setDetail(detail);
        fwt.setUpdateTime(new Date());
        fixedWorkTimeService.update(fwt);
        return Result.createSuccess("修改成功", fwt);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "维修名称", required = false, dataType = "String", defaultValue = "")
    })
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public Result delete(String id){
        String[] array = id.split(",");
        for (String str : array) {
            fixedWorkTimeService.delete(str);
        }
        return Result.createSuccess("删除成功");
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "fixedName", value = "维修名称", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "fixedType", value = "维修类型", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "vehicleType", value = "汽车类型", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "pageIndex", value = "分页", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "分页2", required = false, dataType = "String", defaultValue = "15"),
    })
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Result list(String fixedName,
                       String fixedType,
                       String vehicleType,
                       Integer pageIndex,
                       Integer pageSize){
        PageInfo<FixedWorkTime> pageInfo =  fixedWorkTimeService.list(fixedName,fixedType,vehicleType, pageIndex, pageSize);
        return Result.createSuccess("查询成功", pageInfo);
    }
}

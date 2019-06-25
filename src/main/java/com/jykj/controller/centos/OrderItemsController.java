package com.jykj.controller.centos;

import com.jykj.entity.OrderItems;
import com.jykj.entity.Result;
import com.jykj.service.OrderItemsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Api(value = "维修详情", tags = "维修详情")
@RestController
@RequestMapping("/service/cencus/orderDetail")
public class OrderItemsController {

    @Autowired
    private OrderItemsService orderItemsService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result save(String repairId,
                       String type,
                       String num,
                       String name,
                       String serviceType,
                       String itemsType,
                       Float repairItemsTime,
                       Float price,
                       String detail){
        OrderItems orderItems = new OrderItems();
        orderItems.setCreateTime(new Date());
        orderItems.setId(UUID.randomUUID().toString().replace("-", ""));
        orderItems.setRepairId(repairId);
        orderItems.setType(type);
        orderItems.setNum(num);
        orderItems.setName(name);
        orderItems.setServiceType(serviceType);
        orderItems.setItemType(itemsType);
        orderItems.setRepairItemsTime(repairItemsTime);
        orderItems.setPrice(price);
        orderItems.setDetail(detail);
        orderItemsService.save(orderItems);
        return Result.createSuccess("添加成功", orderItems);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(String id,
                         String repairId,
                         String type,
                         String num,
                         String name,
                         String serviceType,
                         String itemsType,
                         Float repairItemsTime,
                         Float price,
                         String detail){
        OrderItems orderItems = orderItemsService.selectByPrimaryKey(id);
        orderItems.setRepairId(repairId);
        orderItems.setType(type);
        orderItems.setNum(num);
        orderItems.setName(name);
        orderItems.setServiceType(serviceType);
        orderItems.setItemType(itemsType);
        orderItems.setRepairItemsTime(repairItemsTime);
        orderItems.setPrice(price);
        orderItems.setDetail(detail);
        orderItemsService.updateByPrimaryKeySelective(orderItems);
        return Result.createSuccess("添加成功", orderItems);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "repairId", value = "维修ID", required = false, dataType = "String", defaultValue = "cc16afdf2fec4ced961dedec85ddf44f")
    })
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public Result detail(String repairId){
        List<OrderItems> list = orderItemsService.list(repairId);
        return Result.createSuccess("查询成功", list);
    }

}

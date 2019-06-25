package com.jykj.controller.fix;


import com.jykj.entity.Result;
import com.jykj.service.VehicleService;
import com.mongodb.DBObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 车辆接口
 */

@Slf4j
@RestController
@RequestMapping("/service/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", defaultValue = "", dataType = "string", name = "vehicleNum"),
            @ApiImplicitParam(paramType = "query", defaultValue = "", dataType = "string", name = "pageIndex"),
            @ApiImplicitParam(paramType = "query", defaultValue = "", dataType = "string", name = "pageSize"),
    })
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Result list(String vehicleNum,
                       @RequestParam(defaultValue = "1") Integer pageIndex,
                       @RequestParam(defaultValue = "10")Integer pageSize) {
       List<DBObject> result = vehicleService.list(vehicleNum, pageIndex, pageSize);
       return Result.createSuccess("查询成功", result);
    }


}

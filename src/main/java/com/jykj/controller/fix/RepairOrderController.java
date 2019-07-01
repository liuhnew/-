package com.jykj.controller.fix;

import com.github.pagehelper.PageInfo;
import com.jykj.controller.BaseController;
import com.jykj.entity.LoginInfo;
import com.jykj.entity.RepairOrder;
import com.jykj.entity.Result;
import com.jykj.service.OrderItemsService;
import com.jykj.service.RepairOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Api(value = "维修工单", tags = "维修工单")
@RestController
@RequestMapping("/service/cencus/ro")
public class RepairOrderController extends BaseController {

    @Autowired
    private RepairOrderService repairOrderService;

    @Autowired
    private OrderItemsService orderItemsService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RuntimeService runtimeService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "repairId", value = "维修ID", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "repairNum", value = "维修代码", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "driverName", value = "驾驶员名称", required = false, dataType = "String", defaultValue = "张三"),
            @ApiImplicitParam(paramType = "query", name = "driverNum", value = "驾驶员编号", required = false, dataType = "String", defaultValue = "11234564"),
            @ApiImplicitParam(paramType = "query", name = "commitName", value = "报修人", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "checkerName", value = "质检员", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "repairStatus", value = "维修状态", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "vehicleNum", value = "车牌号", required = false, dataType = "String", defaultValue = "闽C88888"),
            @ApiImplicitParam(paramType = "query", name = "repairMessage", value = "维修说明", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "appointmentTime", value = "预约时间", required = false, dataType = "Date", defaultValue = "2019-06-30 09:00:00"),
            @ApiImplicitParam(paramType = "query", name = "repairProgress", value = "维修进度", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "contactName", value = "联系人名称", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "contactTel", value = "联系人电话", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "pickGoodsId", value = "领料单ID", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "orderType", value = "工单类型", required = false, dataType = "int", defaultValue = "0"),
            @ApiImplicitParam(paramType = "query", name = "farm", value = "车间", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "vin", value = "vin", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "engineNo", value = "发动机号", required = false, dataType = "String", defaultValue = "15"),
    })
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result save(RepairOrder repairOrder,
                       HttpServletRequest request) {
        //添加工单并启动流程
        LoginInfo loginInfo = getUserInfo(request);
        Date date = new Date();
        repairOrder.setRepairId(UUID.randomUUID().toString().replace("-", ""));
        repairOrder.setRepairNum("WX" + date.getSeconds() + date.getTime());
        repairOrder.setCommitName(loginInfo.getSub());
        repairOrder.setCreateTime(date);
//        repairOrderService.insertSelective(repairOrder);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("USERNAME", loginInfo.getSub());
        map.put("commitName", repairOrder.getCommitName());
        map.put("repairNum", repairOrder.getRepairNum());
        map.put("repairId", repairOrder.getRepairId());
        map.put("repairType", repairOrder.getOrderType());
        map.put("vehicleNum", repairOrder.getVehicleNum());
        map.put("vin", repairOrder.getVin());
        map.put("engineNo", repairOrder.getEngineNo());
        map.put("appointTime", repairOrder.getAppointmentTime());
        map.put("contact", repairOrder.getContactName());
        map.put("contactPhone", repairOrder.getContactTel());
        map.put("checkerName", repairOrder.getCheckerName());
        map.put("repairStatus", "新建");

//        map.put("contactPhone", repairOrder.getContactTel());//所属组织
//        map.put("contactPhone", repairOrder.getContactTel());//所属线路

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("KEY_weixiu", map);

        repairOrder.setProcessInstanceId(processInstance.getId());
        repairOrder.setTaskId(processInstance.getId());

        repairOrderService.insertSelective(repairOrder);
        return Result.createSuccess("添加成功", repairOrder);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "repairId", value = "维修ID", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "repairNum", value = "维修代码", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "driverName", value = "驾驶员名称", required = false, dataType = "String", defaultValue = "张三"),
            @ApiImplicitParam(paramType = "query", name = "driverNum", value = "驾驶员编号", required = false, dataType = "String", defaultValue = "11234564"),
            @ApiImplicitParam(paramType = "query", name = "commitName", value = "报修人", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "checkerName", value = "质检员", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "repairStatus", value = "维修状态", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "vehicleNum", value = "车牌号", required = false, dataType = "String", defaultValue = "闽C88888"),
            @ApiImplicitParam(paramType = "query", name = "orgId", value = "组织机构ID", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "repairMessage", value = "维修说明", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "appointmentTime", value = "预约时间", required = false, dataType = "Date", defaultValue = "2019-06-30 09:00:00"),
            @ApiImplicitParam(paramType = "query", name = "repairProgress", value = "维修进度", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "contactName", value = "联系人名称", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "contactTel", value = "联系人电话", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "pickGoodsId", value = "领料单ID", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "orderType", value = "工单类型", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "farm", value = "车间", required = false, dataType = "String", defaultValue = "15"),
    })
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(RepairOrder repairOrder) {
        repairOrderService.updateByPrimaryKeySelective(repairOrder);
        return Result.createSuccess("修改成功", repairOrder);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result delete(@RequestParam String id) {
        String[] array = id.split(",");
        for (String str : array) {
            repairOrderService.deleteByPrimaryKey(str);
        }
        return Result.createSuccess("删除成功");
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "repairNum", value = "维修编码", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "vehicleNum", value = "车牌号", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "orderType", value = "工单类型", required = false, dataType = "String", defaultValue = "张三"),
            @ApiImplicitParam(paramType = "query", name = "farm", value = "车间", required = false, dataType = "String", defaultValue = "11234564"),
            @ApiImplicitParam(paramType = "query", name = "pageIndex", value = "分页", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "分页", required = false, dataType = "String", defaultValue = "15"),
    })
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Result list(String repairNum,
                       String vehicleNum,
                       String orderType,
                       String farm,
                       @RequestParam(defaultValue = "1") Integer pageIndex,
                       @RequestParam(defaultValue = "15")Integer pageSize) {
        PageInfo<RepairOrder> pageInfo = repairOrderService.list(repairNum, vehicleNum, orderType, farm, pageIndex, pageSize);
        return Result.createSuccess("查询成功", pageInfo);
    }

}

package com.jykj.controller.fix;

import com.github.pagehelper.PageInfo;
import com.jykj.controller.BaseController;
import com.jykj.entity.*;
import com.jykj.mongo.MongoDBCollectionOperation;
import com.jykj.service.ProcessModelService;
import com.jykj.service.RepairOrderService;
import com.jykj.service.RunTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "待办任务与已办任务", tags = "待办任务与已办任务")
@RestController
@RequestMapping("/service/fix/")
public class WaitTaskController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(WaitTaskController.class);

    @Autowired
    private ProcessModelService processModelService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private MongoDBCollectionOperation tenantStaffMongoDBCollection;

    @Autowired
    private RunTaskService runTaskService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepairOrderService repairOrderService;

    @RequestMapping(value = "/waitTask", method = RequestMethod.POST)
    public Result waitTaskList(String vehicleNum,
                               String farm,
                               String startTime,
                               String endTime,
                               @RequestParam(defaultValue = "1")Integer pageIndex,
                               @RequestParam(defaultValue = "15")Integer pageSize,
                               HttpServletRequest request) {
        LoginInfo loginInfo = getUserInfo(request);
        List<WaitTask> list = processModelService.waitTaskList(loginInfo, vehicleNum, farm, startTime, endTime, pageIndex, pageSize);
        for (WaitTask waitTask: list) {
            Map<String,Object> map = tenantStaffMongoDBCollection.findById(waitTask.getAssignee()).toMap();
            if (map.get("name")!=null){
                waitTask.setAssigneeName(map.get("name").toString());
            }
        }
        PageInfo<WaitTask> pageInfo = new PageInfo<>(list);
        return Result.createSuccess("查询成功", pageInfo);
    }

    @RequestMapping(value = "/overTask", method = RequestMethod.POST)
    public Result overTask(String vehicleNum,
                           String farm,
                           String startTime,
                           String endTime,
                           @RequestParam(defaultValue = "1")Integer pageIndex,
                           @RequestParam(defaultValue = "15")Integer pageSize,
                           HttpServletRequest request) {
        LoginInfo loginInfo = getUserInfo(request);
        List<OverTask> list = processModelService.overTask(loginInfo, vehicleNum, farm, startTime, endTime, pageIndex, pageSize);
        for (OverTask overTask: list) {
            Map<String,Object> map = tenantStaffMongoDBCollection.findById(overTask.getAssignee()).toMap();
            if (map.get("name")!=null){
                overTask.setAssigneeName(map.get("name").toString());
            }
            overTask.setTimeLong(overTask.getEndTime().getTime() - overTask.getStartTime().getTime());
        }
        PageInfo<OverTask> pageInfo = new PageInfo<>(list);
        return Result.createSuccess("查询成功", pageInfo);
    }

    @ApiOperation(value = "办理委派任务", notes = "办理委派任务")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", defaultValue = "", name = "taskId", value = "任务ID", paramType = "query"),
            @ApiImplicitParam(dataType = "string", defaultValue = "", name = "remark", paramType = "query"),
    })
    @RequestMapping(value = "handleWX2", method = RequestMethod.POST)
    public Result handleWX2(String taskId,
                            HttpSession session,
                            HttpServletRequest request) {
        LoginInfo loginInfo = getUserInfo(request);
        logger.info("+++++++++" + loginInfo.getName() + "办理委派任务");
        String repairId = (String)taskService.getVariable(taskId, "repairId");
        RepairOrder repairOrder = repairOrderService.selectByPrimaryKey(repairId);

        return Result.createSuccess("");
    }


}

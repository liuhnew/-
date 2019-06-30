package com.jykj.controller.fix;

import com.github.pagehelper.PageInfo;
import com.jykj.controller.BaseController;
import com.jykj.entity.*;
import com.jykj.service.ProcessModelService;
import com.jykj.service.RunTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "运行流程与历史流程", tags = "运行流程历史流程")
@RestController
@RequestMapping("/service/process/")
public class ProcessController extends BaseController {

    @Autowired
    private ProcessModelService processModelService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RunTaskService runTaskService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProcessEngine processEngine;

    @ApiImplicitParams({
            @ApiImplicitParam(value = "" ,dataType = "string", name = "processName", paramType = "query"),
            @ApiImplicitParam(value = "" ,dataType = "string", name = "startTime", paramType = "query"),
            @ApiImplicitParam(value = "" ,dataType = "string", name = "endTime", paramType = "query"),
            @ApiImplicitParam(value = "" ,dataType = "int", name = "pageIndex", paramType = "query"),
            @ApiImplicitParam(value = "" ,dataType = "int", name = "pageSize", paramType = "query"),
    })
    @RequestMapping(value = "ruProcess", method = RequestMethod.POST)
    public Result ruProcess(String processName,
                            String startTime,
                            String endTime,
                            Integer pageIndex,
                            Integer pageSize,
                            HttpServletRequest request){
        LoginInfo loginInfo = getUserInfo(request);
        List<RunProcess> list =  processModelService.ruProcess(loginInfo, processName, startTime, endTime, pageIndex, pageSize);
        PageInfo<RunProcess> pageInfo = new PageInfo<RunProcess>(list);
        return Result.createSuccess("查询成功", pageInfo);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(value = "" ,dataType = "string", name = "processInstanceId", paramType = "query"),
    })

    @RequestMapping(value = "processDetail", method = RequestMethod.POST)
    public Result runProcessDetail(String processInstanceId) {
        List<Object> list = processModelService.runProcessDetail(processInstanceId);
        RepairOrder repairOrder = processModelService.queryProcessDetail(processInstanceId);
        Map<String,Object> map = new HashMap<>();
        map.put("list", list);
        map.put("repairOrder", repairOrder);
        return Result.createSuccess("查询成功", map);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(value = "" ,dataType = "string", name = "processName", paramType = "query"),
            @ApiImplicitParam(value = "" ,dataType = "string", name = "startTime", paramType = "query"),
            @ApiImplicitParam(value = "" ,dataType = "string", name = "endTime", paramType = "query"),
            @ApiImplicitParam(value = "" ,dataType = "int", name = "pageIndex", paramType = "query"),
            @ApiImplicitParam(value = "" ,dataType = "int", name = "pageSize", paramType = "query"),
    })

    @RequestMapping(value = "hiProcess", method = RequestMethod.POST)
    public Result hisProcess(String processName,
                             String startTime,
                             String endTime,
                             Integer pageIndex,
                             Integer pageSize,
                             HttpServletRequest request) {
        List<HistoryProcess> list = processModelService.hiProcess(processName, startTime, endTime, pageIndex, pageSize);
        PageInfo<HistoryProcess> pageInfo = new PageInfo<>(list);
        return Result.createSuccess("查询成功", pageInfo);
    }

    @ApiOperation(value = "激活或挂起任务", notes = "激活或挂起任务")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", paramType = "query", defaultValue = "", name = "任务ID"),
            @ApiImplicitParam(dataType = "int", paramType = "query", defaultValue = "", name = "激活或挂起任务(0:挂起，1:激活)")
    })
    @RequestMapping(value = "onoffTask", method = RequestMethod.POST)
    public Result onoffTask(String taskId,
                            Integer status) {
        runTaskService.onoffTask(taskId, status);
        if (status == 0){
            return Result.createSuccess("激活成功");
        }
        return Result.createSuccess("挂起成功");
    }

    @ApiOperation(value = "delegate", notes = "委派")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", defaultValue = "", name = "taskId", paramType = "query"),
            @ApiImplicitParam(dataType = "string", defaultValue = "", name = "assignee", paramType = "query"),
    })
    @RequestMapping(value = "delegate", method = RequestMethod.POST)
    public Result delegate(String taskId,
                           String assignee,
                           HttpServletRequest request) {
        LoginInfo loginInfo = getUserInfo(request);
        Map<String,Object> map = new HashMap<>();
        map.put("审批结果", "任务由【" + loginInfo.getName() + "】委派");
        taskService.setVariablesLocal(taskId, map);
        taskService.setAssignee(taskId, assignee);
        return Result.createSuccess("委派成功");
    }

    @ApiOperation(value = "delete", notes = "作废")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", defaultValue = "", name = "proInstanceId", paramType = "query"),
            @ApiImplicitParam(dataType = "string", defaultValue = "", name = "remark", paramType = "query"),
    })
    @RequestMapping(value = "delete" ,method = RequestMethod.POST)
    public Result delete(String proInstanceId,
                         String remark,
                         HttpServletRequest request) {
        LoginInfo loginInfo = getUserInfo(request);
        String reason = "【作废】" + loginInfo.getName() + "：" + remark; // 作废原因
        //任务结束时发站内信通知审批结束
        runtimeService.deleteProcessInstance(proInstanceId, reason);
        return Result.createSuccess("流程已作废");
    }
}

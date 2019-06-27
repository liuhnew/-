package com.jykj.controller.fix;

import com.github.pagehelper.PageInfo;
import com.jykj.controller.BaseController;
import com.jykj.entity.*;
import com.jykj.service.ProcessModelService;
import io.swagger.annotations.Api;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricProcessInstance;
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
    private HistoryService historyService;

    @Autowired
    private ProcessEngine processEngine;

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

    @RequestMapping(value = "processDetail", method = RequestMethod.POST)
    public Result runProcessDetail(String processInstanceId) {
        List<Object> list = processModelService.runProcessDetail(processInstanceId);
        RepairOrder repairOrder = processModelService.queryProcessDetail(processInstanceId);
        Map<String,Object> map = new HashMap<>();
        map.put("list", list);
        map.put("repairOrder", repairOrder);
        return Result.createSuccess("查询成功", map);
    }

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
}

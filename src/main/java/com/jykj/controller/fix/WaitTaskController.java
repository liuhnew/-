package com.jykj.controller.fix;

import com.github.pagehelper.PageInfo;
import com.jykj.controller.BaseController;
import com.jykj.entity.LoginInfo;
import com.jykj.entity.OverTask;
import com.jykj.entity.Result;
import com.jykj.entity.WaitTask;
import com.jykj.mongo.MongoDBCollectionOperation;
import com.jykj.service.ProcessModelService;
import io.swagger.annotations.Api;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "待办任务与已办任务", tags = "待办任务与已办任务")
@RestController
@RequestMapping("/service/fix/")
public class WaitTaskController extends BaseController {

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
            Map<String,Object> map = tenantStaffMongoDBCollection.findBySelfId(waitTask.getAssignee());
            waitTask.setAssigneeName(map.get("name").toString());
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
            Map<String,Object> map = tenantStaffMongoDBCollection.findBySelfId(overTask.getAssignee());
            overTask.setAssigneeName(map.get("name").toString());
            overTask.setTimeLong(overTask.getEndTime().getTime() - overTask.getStartTime().getTime());
        }
        PageInfo<OverTask> pageInfo = new PageInfo<>(list);
        return Result.createSuccess("查询成功", pageInfo);
    }

}

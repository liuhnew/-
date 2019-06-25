package com.jykj.controller.fix;

import com.github.pagehelper.PageInfo;
import com.jykj.controller.BaseController;
import com.jykj.entity.LoginInfo;
import com.jykj.entity.Result;
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

    @RequestMapping(value = "/waitTask", method = RequestMethod.POST)
    public Result waitTaskList(String vehicleNum,
                               String farm,
                               String startTime,
                               String endTime,
                               @RequestParam(defaultValue = "1")Integer pageIndex,
                               @RequestParam(defaultValue = "15")Integer pageSize,
                               HttpServletRequest request) {
//        List<Object> list = processModelService.waitTaskList(vehicleNum, farm, startTime, endTime, pageIndex, pageSize);
//        PageInfo<Object> pageInfo = new PageInfo<>(list);
        TaskService taskService = processEngine.getTaskService();
        LoginInfo loginInfo = getUserInfo(request);
        //任务负责人
        String assignee = loginInfo.getSub();

        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.taskAssignee(assignee);

        String processDefinitionKey = "KEY_weixiu";
        List<Task> list = taskQuery.processDefinitionKey(processDefinitionKey).list();
        for (Task task : list) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService
                    .createProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult();
            //从流程实例对象获取bussinesskey
            String businessKey = processInstance.getBusinessKey();
            //根据businessKey查询业务系统，获取相关的业务信息
            System.out.println("流程实例id：" + task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务标识：" + task.getTaskDefinitionKey());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
            System.out.println("任务创建时间：" + task.getCreateTime());
            System.out.println("++++++++++++++++++++++++++");
        }
        return Result.createSuccess("查询成功");
    }

}

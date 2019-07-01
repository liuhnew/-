package com.jykj.controller.fix;

import com.github.pagehelper.PageInfo;
import com.jykj.config.Jurisdiction;
import com.jykj.controller.BaseController;
import com.jykj.entity.*;
import com.jykj.mongo.MongoDBCollectionOperation;
import com.jykj.service.*;
import com.jykj.util.StringUtil;
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
import org.apache.regexp.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private OrderItemsService orderItemsService;

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

    @ApiOperation(value = "WX1", notes = "WX1")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", defaultValue = "", name = "taskId", value = "任务ID", paramType = "query"),
    })
    @RequestMapping(value = "/handleWX1",method = RequestMethod.POST)
    public Result handleWX1(String taskId,
                            String msg,
                            String option,
                            HttpServletRequest request){
        LoginInfo loginInfo = getUserInfo(request);
        String sfrom = "";
        Object ofrom = taskService.getVariable(taskId, "repairId");
        if (null != ofrom) {
            sfrom = ofrom.toString();
        }
        Map<String, Object> map = new LinkedHashMap<>();
        String assignee = "";
        Map<String, Object> task = runTaskService.listbyTaskId(taskId);
        String currentTaskKey = task.get("TASK_DEF_KEY_").toString();
        //获取工单信息
        String repairId = (String) taskService.getVariable(taskId, "repairId");
        RepairOrder repairOrder = repairOrderService.selectByPrimaryKey(repairId);
        if ("WX1".equals(currentTaskKey)) {
            repairOrder.setRepairStatus("申请工单");
            repairOrder.setUpdateTime(new Date());
            String userList =  findCJAdmin(repairOrder.getCommitName());
            assignee = userList;
        }
        if (StringUtil.isNotEmpty(assignee)) {
            System.out.println(Jurisdiction.getSession().getAttribute("TASKID"));
            String TASKID = Jurisdiction.getSession().getAttribute("TASKID").toString();
            taskService.setAssignee(TASKID, assignee);

//            taskService.complete(taskId);
        }else {
            Object os = request.getSession().getAttribute("YAssignee");
            if(null != os && !"".equals(os.toString())){
                assignee = os.toString();										//没有指定就是默认流程的待办人
            }
        }
        repairOrderService.updateByPrimaryKeySelective(repairOrder);
        return Result.createSuccess("提交成功");
    }


    @ApiOperation(value = "办理委派任务", notes = "办理委派任务")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", defaultValue = "", name = "taskId", value = "任务ID", paramType = "query"),
            @ApiImplicitParam(dataType = "string", defaultValue = "", name = "assignee", paramType = "query"),
            @ApiImplicitParam(dataType = "string", defaultValue = "", name = "advice", paramType = "query"),
            @ApiImplicitParam(dataType = "string", defaultValue = "", name = "msg",value = "yes?no", paramType = "query"),
    })
    @RequestMapping(value = "handleWX2", method = RequestMethod.POST)
    public Result handleWX2(String taskId,
                            String assignee,
                            String advice,
                            String msg,
                            HttpSession session,
                            HttpServletRequest request) {
        LoginInfo loginInfo = getUserInfo(request);
        logger.info("+++++++++" + loginInfo.getName() + "办理委派任务");
        String repairId = (String)taskService.getVariable(taskId, "repairId");
        RepairOrder repairOrder = repairOrderService.selectByPrimaryKey(repairId);
        //////////////
        Map<String, Object> task = runTaskService.listbyTaskId(taskId);
        String currentTaskKey = task.get("TASK_DEF_KEY_").toString();
        Map<String,Object> map = new HashMap<>();
        if ("WX2".equals(currentTaskKey)){
            taskService.setVariable(taskId, "checkerName", assignee);
            repairOrder.setCheckerName(assignee);
            repairOrder.setRepairStatus("指定质检员");
            repairOrder.setUpdateTime(new Date());
        }
        String sfrom = "";
        Object ofrom = taskService.getVariable(taskId, "处理结果");
        if (null != ofrom) {
            sfrom = ofrom.toString();
        }
        String option = sfrom + loginInfo.getName() + ",fh," + advice;
//        String roleName = loginInfo.getRoleName();
        if ("WXSJ".equals(currentTaskKey)){
            if ("yes".equals(msg)) {
                map.put("处理结果", option);
                map.put("车间管理员", loginInfo.getName());
                taskService.setVariables(taskId, map);
                taskService.complete(taskId);
            }else{												//驳回
                map.put("处理结果", option);		//审批结果
                taskService.setVariables(taskId, map);
                taskService.complete(taskId);
            }
            assignee = findCJAdmin(loginInfo.getName());
        }
        if ("WX2".equals(currentTaskKey)) {
            if ("yes".equals(msg)) {
                map.put("处理结果", option);
                map.put("车间管理员", loginInfo.getName());
//                repairOrder.setc需添加当前处理人
                taskService.setVariablesLocal(taskId, map);
                taskService.complete(taskId);
            }else {
                map.put("处理结果", option);
                taskService.setVariablesLocal(taskId, map);
                taskService.complete(taskId);
            }
            assignee = findCJAdmin(findCJAdmin(loginInfo.getSub()));
        }
        if ("WXJD".equals(currentTaskKey)){
            if ("yes".equals(msg)) {
                repairOrder.setRepairStatus("接单");
                repairOrder.setUpdateTime(new Date());
//                repairOrder.set
                map.put("处理结果", "接单");	//审批结果
                map.put("车间管理员", loginInfo.getName());	//审批结果
                taskService.setVariables(taskId,map);			//设置流程变量
                taskService.complete(taskId);
                assignee = findCJAdmin(loginInfo.getName());
            }else {
                assignee = findCJAdmin(loginInfo.getSub());
            }
        }
        if ("RBJD".equals(currentTaskKey)){
            if ("yes".equals(msg)) {
                repairOrder.setRepairStatus("回退接单");
                repairOrder.setUpdateTime(new Date());
                //保存当前处理人
                map.put("处理结果", "接单");	//审批结果
                taskService.setVariables(taskId,map);			//设置流程变量
                taskService.complete(taskId);
                assignee = findCJAdmin(loginInfo.getSub());
            }
        }
        repairOrderService.updateByPrimaryKeySelective(repairOrder);

        if (StringUtil.isNotEmpty(assignee)){
            taskService.setAssignee(session.getAttribute("TASKID").toString(), assignee);
        }else {

        }
        return Result.createSuccess("处理完成");
    }

    @ApiOperation(value = "办理派工任务WX3", notes = "办理派工任务WX3")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", defaultValue = "", name = "taskId", value = "任务ID", paramType = "query"),
    })
    @RequestMapping(value = "handleWX3", method = RequestMethod.POST)
    public Result handleWX3(String taskId,
                            String option,
                            String msg,
                            String proInstanceId,
                            String remark,
                            HttpSession session,
                            HttpServletRequest request) {
        LoginInfo loginInfo = getUserInfo(request);
        String sfrom = "";
        Object ofrom = taskService.getVariable(taskId, "结果");
        if (null!=ofrom) {
            sfrom = ofrom.toString();
        }
        Map<String,Object> map = new LinkedHashMap<>();
        option = sfrom + loginInfo.getName() + ",fh," + option;
        String assignee = "";
        Map<String, Object> task = runTaskService.listbyTaskId(taskId);
        String currentTaskKey = task.get("TASK_DEF_KEY_").toString();
        //获取工单信息
        String repairId = (String)taskService.getVariable(taskId, "repairId");
        RepairOrder repairOrder = repairOrderService.selectByPrimaryKey(repairId);
        //判断维修事项是否为空
        List<OrderItems> foundItems = orderItemsService.list(repairId);
        if (foundItems.size()==0) {
            return Result.createFail("请填写维修事项");
        }else {
            if ("yes".equals(msg)) {
                if ("WXXG".equals(currentTaskKey)) {
                    repairOrder.setRepairStatus("修改作业内容");
                }else if ("WXQR".equals(currentTaskKey)) {
                    repairOrder.setRepairStatus("确认作业内容");
                }else {
                    repairOrder.setRepairStatus("填写作业内容");
                }
                taskService.setVariables(taskId, map);
                taskService.complete(taskId);
            }
        }
        runtimeService.removeVariable(proInstanceId, "RESULT");
        repairOrder.setUpdateTime(new Date());
        repairOrderService.updateByPrimaryKeySelective(repairOrder);

        if ("WXXG".equals(currentTaskKey)) {
            assignee = findBXY(repairOrder.getCommitName());
        }else if ("WX3".equals(currentTaskKey)||"EXQR".equals(currentTaskKey)) {
            assignee = findCJAdmin(repairOrder.getDealerName());
        }else {
            assignee = findCJAdmin(repairOrder.getDealerName());
        }

        if (StringUtil.isNotEmpty(assignee)) {
            taskService.setAssignee(session.getAttribute("TASKID").toString(), assignee);
        }else {

        }
        return Result.createSuccess("提交成功");
    }

    @ApiOperation(value = "办理领料任务", notes = "办理领料任务")
    @RequestMapping(value = "handleWX10", method = RequestMethod.POST)
    public Result handleWX10(String taskId,
                             String msg,
                             String option,
                             String proInstanceId,
                             HttpSession session,
                             HttpServletRequest request){
        LoginInfo loginInfo = getUserInfo(request);
        String sfrom = "";
        Object ofrom = taskService.getVariable(taskId, "结果");
        if (null!=ofrom) {
            sfrom = ofrom.toString();
        }
        Map<String,Object> map = new LinkedHashMap<>();
        option = sfrom + loginInfo.getName() + ",fh," + option;
        String assignee = "";
        Map<String, Object> task = runTaskService.listbyTaskId(taskId);
        String currentTaskKey = task.get("TASK_DEF_KEY_").toString();
        //获取工单信息
        String repairId = (String)taskService.getVariable(taskId, "repairId");
        RepairOrder repairOrder = repairOrderService.selectByPrimaryKey(repairId);
        List<OrderItems> foundItems = orderItemsService.list(repairId);
//        if (foundItems.size()>0){
//            for (int i = 0; i<foundItems.size();i++) {
//
//            }
//        }
        if ("yes".equals(msg)) {
            repairOrder.setRepairStatus("申请汽配");
            map.put("处理结果", "【领料】" + option);		//审批结果
            taskService.setVariablesLocal(taskId,map);			//设置流程变量
            taskService.setVariable(taskId,"result","领料");
            taskService.complete(taskId);
        }else {
            if ("WX10".equals(currentTaskKey)) {
                taskService.getVariable(taskId, "维修员");
                repairOrder.setRepairStatus("维修工领单");
                map.put("处理结果", "【无领料】" + option);
                taskService.setVariables(taskId, map);
                taskService.setVariableLocal(taskId, "RESULT", "无领料");
            }else {
                repairOrder.setRepairStatus("等待管理员确认");
                map.put("处理结果", "退单分配维修员" + option);
                taskService.setVariables(taskId, map);
            }
            taskService.complete(taskId);
        }
        runtimeService.removeVariable(proInstanceId, "RESULT");
        repairOrder.setUpdateTime(new Date());
        repairOrderService.updateByPrimaryKeySelective(repairOrder);
        if ("RBFG".equals(currentTaskKey)) {
            assignee = "";
        }else {
            assignee = assignee = findCJAdmin(loginInfo.getSub());
        }
        if (StringUtil.isNotEmpty(assignee)) {
            taskService.setAssignee(session.getAttribute("TASKID").toString(), assignee);
        }else {

        }
        return Result.createSuccess("提交成功");
    }

    @ApiOperation(value = "申请非易耗品", notes = "申请非易耗品")
    @RequestMapping(value = "handlewx11", method = RequestMethod.POST)
    public Result handlewx11(String taskId,
                             String msg,
                             String option,
                             String proInstanceId,
                             HttpSession session,
                             HttpServletRequest request) {
        LoginInfo loginInfo = getUserInfo(request);
        String sfrom = "";
        Object ofrom = taskService.getVariable(taskId, "结果");
        if (null != ofrom) {
            sfrom = ofrom.toString();
        }
        Map<String, Object> map = new LinkedHashMap<>();
        option = sfrom + loginInfo.getName() + ",fh," + option;
        String assignee = "";
        Map<String, Object> task = runTaskService.listbyTaskId(taskId);
        String currentTaskKey = task.get("TASK_DEF_KEY_").toString();
        //获取工单信息
        String repairId = (String) taskService.getVariable(taskId, "repairId");
        RepairOrder repairOrder = repairOrderService.selectByPrimaryKey(repairId);
        if (StringUtil.isNotEmpty(repairOrder.getPickGoodsId())) {
            String pickGoodId = repairOrder.getPickGoodsId();
        }
        if (StringUtil.isNotEmpty(repairOrder.getPickGoodsId())) {
            repairOrder.setRepairStatus("报信息员申请非易耗品");
            map.put("处理结果", "【申请非易耗品】" + option);        //审批结果
            taskService.setVariables(taskId, map);
            taskService.setVariableLocal(taskId, "RESULT", "非易耗品");
            assignee = findBXY(repairOrder.getCommitName());
            taskService.complete(taskId);
        } else {

        }
        return Result.createSuccess("提交代码");
    }

    @ApiOperation(value = "办理审核任务", notes = "办理审核任务")
    @RequestMapping(value = "handlewx4", method = RequestMethod.POST)
    public Result handlewx4(String taskId,
                            String option,
                            String proInstanceId,
                            String msg,
                            String remark,
                            HttpServletRequest request,
                            HttpSession session){
        LoginInfo loginInfo = getUserInfo(request);
        String sfrom = "";
        Object ofrom = taskService.getVariable(taskId, "结果");
        if (null != ofrom) {
            sfrom = ofrom.toString();
        }
        Map<String, Object> map = new LinkedHashMap<>();
        option = sfrom + loginInfo.getName() + ",fh," + option;
        String assignee = "";
        Map<String, Object> task = runTaskService.listbyTaskId(taskId);
        String currentTaskKey = task.get("TASK_DEF_KEY_").toString();
        //获取工单信息
        String repairId = (String) taskService.getVariable(taskId, "repairId");
        RepairOrder repairOrder = repairOrderService.selectByPrimaryKey(repairId);
        if ("RBQR".equals(currentTaskKey)) {
            if ("yes".equals(msg)) {
                map.put("是否通过", "【通过】" + option);
                taskService.setVariables(taskId, map);
                taskService.setVariableLocal(taskId, "RESULT", "通过");
                repairOrder.setRepairStatus("通过退单");
                taskService.complete(taskId);
                List<OrderItems> foundItems = orderItemsService.list(repairId);

//                for (int i=0;i<foundItems.size();i++) {
//
//                }
            }else {
                map.put("是否通过", "【驳回】" + option);		//审批结果
                taskService.setVariables(taskId,map);			//设置流程变量
                taskService.setVariableLocal(taskId,"RESULT","驳回");
                repairOrder.setRepairStatus("驳回退单");
                taskService.complete(taskId);
                //不合格返回到车间管理员
                //先查询是否已绑定车间管理员
                try{
                    assignee = findCJAdmin(repairOrder.getDealerName());
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }else {
            if("yes".equals(msg)){								//批准
                map.put("是否合格", "【合格】" + option);		//审批结果
                taskService.setVariables(taskId,map);			//设置流程变量
                taskService.setVariableLocal(taskId,"RESULT","合格");
                repairOrder.setRepairStatus("合格");
                taskService.complete(taskId);
                assignee = findBXY(repairOrder.getCommitName());
            }else{												//驳回
                map.put("是否合格", "【不合格】" + option);		//审批结果
                assignee = findCJAdmin(repairOrder.getDealerName());
                taskService.setVariables(taskId,map);		//设置流程变量
                taskService.setVariableLocal(taskId,"RESULT","不合格");
                repairOrder.setRepairStatus("返修");
                taskService.complete(taskId);
                //不合格返回到车间管理员
                //先查询是否已绑定车间管理员
                try{
                    assignee = findCJAdmin(loginInfo.getSub());
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            try{
                runtimeService.deleteProcessInstance(proInstanceId,"RESULT");			//移除流程变量(从正在运行中)
            }catch(Exception e){
                /*此流程变量在历史中**/
            }
        }
        repairOrder.setUpdateTime(new Date());
        repairOrderService.updateByPrimaryKeySelective(repairOrder);
        if (StringUtil.isNotEmpty(assignee)) {
            taskService.setAssignee(session.getAttribute("TASKID").toString(), assignee);
        }
        return Result.createSuccess("提交成功");
    }

    /**办理领料审核任务
     * @param
     * @throws Exception
     */
    @ApiOperation(value = "办理领料审核任务", notes = "办理领料审核任务")
    @RequestMapping(value="/handlewx9")
    public Result handlewx9(String taskId,
                            String option,
                            String proInstanceId,
                            String msg,
                            String remark,
                            HttpServletRequest request,
                            HttpSession session) throws Exception{
        LoginInfo loginInfo = getUserInfo(request);
        String sfrom = "";
        Object ofrom = taskService.getVariable(taskId, "结果");
        if (null != ofrom) {
            sfrom = ofrom.toString();
        }
        Map<String, Object> map = new LinkedHashMap<>();
        option = sfrom + loginInfo.getName() + ",fh," + option;
        String assignee = "";
        Map<String, Object> task = runTaskService.listbyTaskId(taskId);
        String currentTaskKey = task.get("TASK_DEF_KEY_").toString();
        //获取工单信息
        String repairId = (String) taskService.getVariable(taskId, "repairId");
        RepairOrder repairOrder = repairOrderService.selectByPrimaryKey(repairId);
        ////////////////////
        if("yes".equals(msg)){								//批准
            map.put("处理结果", "【已发放】" + option);		//审批结果
            repairOrder.setRepairStatus("已领料");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = df.format(new Date());
            try{
                String picking_id = repairOrder.getPickGoodsId();
//                pickingstatusPd.put("STATUS", "已领料");
//                pickingstatusPd.put("PICKING_ID", picking_id);
//                pickingstatusPd.put("STORAGER_ID", Jurisdiction.getUsername());
//                pickingstatusPd.put("END_TIME", date);
//                pickingService.updatePickingStatus(pickingstatusPd);
            }catch(Exception e){
                e.printStackTrace();
            }
            try{
//                String PICKINGFORCONSUME_ID = OrderPd.getString("PICKINGFORCONSUME_ID");
//                PageData pickingstatusPd2 = new PageData();
//                //设置相应领料单状态
//                pickingstatusPd2.put("STATUS", "已领料");
//                pickingstatusPd2.put("PICKING_ID", PICKINGFORCONSUME_ID);
//                pickingstatusPd2.put("STORAGER_ID", Jurisdiction.getUsername());
//                pickingstatusPd2.put("END_TIME", date);
//                pickingService.updatePickingStatus(pickingstatusPd2);
            }catch(Exception e){
                e.printStackTrace();
            }
            taskService.setVariables(taskId,map);			//设置流程变量
            taskService.setVariableLocal(taskId,"RESULT","已发放");
            taskService.complete(taskId);
            //指定下一流程代办人-车间管理员
            //先查询是否已绑定车间管理员
            try{
                Object userObj = taskService.getVariable(taskId,"车间管理员");
                assignee = repairOrder.getDealerName();
            }catch(Exception e){
                e.printStackTrace();
            }
            assignee = findCJAdmin(loginInfo.getSub());
        }else{												//驳回
            Object userObj = taskService.getVariable(taskId,"USERNAME");
            assignee = userObj.toString();
            map.put("处理结果", "【驳回】" + option);		//审批结果
            repairOrder.setRepairStatus("驳回申请");
            taskService.setVariables(taskId,map);			//设置流程变量
            taskService.setVariableLocal(taskId,"RESULT","驳回");
            taskService.complete(taskId);
        }
        try{
            runtimeService.deleteProcessInstance(proInstanceId,"RESULT");			//移除流程变量(从正在运行中)
        }catch(Exception e){
            /*此流程变量在历史中**/
        }
        //填入当前仓管员信息
//        repairOrder.set
        repairOrder.setUpdateTime(new Date());
        //更新工单信息
        repairOrderService.updateByPrimaryKeySelective(repairOrder);
        //////////////////
//		String ASSIGNEE_ = pd.getString("ASSIGNEE_");							//下一待办对象
        if (StringUtil.isNotEmpty(assignee)) {
               taskService.setAssignee(session.getAttribute("TASKID").toString(), assignee);
        }
//      mv.addObject("ASSIGNEE_",ASSIGNEE_);	//用于给待办人发送新任务消息/
        return Result.createSuccess("提交成功");
    }
}

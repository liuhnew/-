package com.jykj.activiti.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;
import com.jykj.entity.ProcessModel;
import com.jykj.entity.Result;
import com.jykj.service.ProcessModelService;
import com.jykj.util.GsonUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.exceptions.XMLException;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/service")
public class MyController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessModelService processModelService;

    @Autowired
    private ProcessEngine processEngine;

    @RequestMapping(value = "createModel", method = RequestMethod.POST)
    public Result createModel(String name,
                              String description,
                              HttpServletRequest request,
                              HttpServletResponse response){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            Model modelData = repositoryService.newModel();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(name);
            modelData.setKey(name);

            repositoryService.saveModel(modelData);
            System.out.println(modelData.getId());
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
            Map<String,Object> map = new HashMap<>();
            map.put("id", modelData.getId());

            return Result.createSuccess("创建模型成功", map);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation(value = "查询入库单", notes = "查询入库单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "模型名称", required = false, dataType = "String", defaultValue = "hello"),
            @ApiImplicitParam(paramType = "query", name = "pageIndex", value = "", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "", required = false, dataType = "String", defaultValue = "15"),
    })
    @RequestMapping(value = "modelList", method = RequestMethod.POST)
    public Result modelList(String name,
                            Integer pageIndex,
                            Integer pageSize){
        PageInfo<ProcessModel> pageInfo = processModelService.list(name, pageIndex, pageSize);
        return Result.createSuccess("查询成功", pageInfo);
    }

    @RequestMapping(value = "deploy", method = RequestMethod.POST)
    public Result deploy(String id){
        Deployment deployment = null;
        try {
            Model modelData = repositoryService.getModel(id);
            byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

            if (bytes==null) {
                return Result.createFail("模型数据为空,请先设计流程并成功保存，再进行发布");
            }
            JsonNode modelNode = new ObjectMapper().readTree(bytes);
            System.out.println(" ++++++++++modeNode"  + modelNode);
            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            if (model.getProcesses().size()==0){
                return Result.createFail("数据模型不符要求，请至少设计一条主线流程");
            }
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

            //发布流程
            String processName = modelData.getName() + ".bpmn20.xml";
            deployment = repositoryService.createDeployment()
                    .name(modelData.getName())
                    .addString(processName, new String(bpmnBytes, "UTF-8"))
                    .deploy();
            modelData.setDeploymentId(deployment.getId());
            repositoryService.saveModel(modelData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLException e1) {
            return Result.createFail("绘制的流程图不正确");
        }
        return Result.createSuccess("发布成功", deployment);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "模型名称", required = false, dataType = "String", defaultValue = ""),
    })
    @RequestMapping(value = "showImage", method = RequestMethod.POST)
    public void showImage(String id,
                          HttpServletResponse response) throws Exception {
        response.setContentType("image/png;charset=utf8");
        Model modelData = repositoryService.getModel(id);
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(modelData.getDeploymentId()).list();
        String imageName = list.get(0).getDiagramResourceName();
        InputStream inputStream = repositoryService.getResourceAsStream(modelData.getDeploymentId(),  imageName);
        BufferedInputStream reader = new BufferedInputStream(inputStream);
        BufferedOutputStream writer = new BufferedOutputStream(response.getOutputStream());
        byte[] bytes = new byte[1024 * 1024];
        int length = reader.read(bytes);
        while ((length > 0)) {
            writer.write(bytes, 0, length);
            length = reader.read(bytes);
        }
        reader.close();
        writer.close();
    }

    @ApiOperation(value = "启动流程并分配任务", notes = "启动流程并分配任务")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "modelId", value = "模型名称", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "username", value = "报修员", required = false, dataType = "String", defaultValue = ""),
    })
    @RequestMapping(value = "startProcess",method = RequestMethod.POST)
    public Result startProcess(String modelId,
                               String username) {
        Model modelData = repositoryService.getModel(modelId);
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(modelData.getDeploymentId()).list();
        if (list.size()<=0){
            return Result.createSuccess("该流程定义还未部署");
        }
        String key = list.get(0).getKey();

        Map<String,Object> variables = new HashMap<String,Object>();
        variables.put("USERNAME",username);
        RuntimeService runtimeService = processEngine.getRuntimeService();
        String pdi = runtimeService.startProcessInstanceByKey(key, variables).getProcessDefinitionId();
        return Result.createSuccess("success");
    }

    @ApiOperation(value = "查询需完成任务", notes = "查询需完成任务")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "报修员", required = false, dataType = "String", defaultValue = ""),
    })
    @RequestMapping(value = "taskList",method = RequestMethod.POST)
    public Result taskList(String username) {
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(username).list();
        return Result.createSuccess("success", tasks);
    }

    @ApiOperation(value = "完成任务", notes = "完成任务")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "taskId", value = "待完成任务ID", required = false, dataType = "String", defaultValue = ""),
    })
    @RequestMapping(value = "finishTask",method = RequestMethod.POST)
    public Result finishTask(String taskId) {
        TaskService taskService = processEngine.getTaskService();
        taskService.complete(taskId);
        return Result.createSuccess("任务完成");
    }


    @ApiOperation(value = "历史流程",  notes = "历史流程")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "taskId", value = "查询所有", required = false, dataType = "String", defaultValue = "")
    })
    @RequestMapping(value = "hiProcess", method = RequestMethod.POST)
    public Result hiProcess() {
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery()
                .orderByProcessInstanceStartTime().asc().list();
        for (HistoricProcessInstance processInstance: list) {
            System.out.println("----id---" + processInstance.getId());
            System.out.println("----name---" + processInstance.getName());
            System.out.println("----businessKey---" + processInstance.getBusinessKey());
            System.out.println("----description---" + processInstance.getDescription());
            System.out.println("-------" + processInstance.getStartTime());
        }
        return Result.createSuccess("查询成功");
    }

}

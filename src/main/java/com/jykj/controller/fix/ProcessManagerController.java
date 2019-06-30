package com.jykj.controller.fix;

import com.github.pagehelper.PageInfo;
import com.jykj.entity.ProDefEntity;
import com.jykj.entity.Result;
import com.jykj.service.ProcessManagerService;
import com.jykj.service.RunTaskService;
import com.jykj.util.DelAllFile;
import com.jykj.util.FileUpload;
import com.jykj.util.PathUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Api(value = "流程管理", tags = "流程管理")
@RestController
@RequestMapping("/service/proManager/")
public class ProcessManagerController {

    @Autowired
    private ProcessManagerService processManagerService;
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RunTaskService runTaskService;


    @ApiOperation(value = "流程管理列表", notes = "流程管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", paramType = "query", name = "keyword", value = "流程名", defaultValue = ""),
            @ApiImplicitParam(dataType = "string", paramType = "query", name = "startTime", value = "开始时间", defaultValue = ""),
            @ApiImplicitParam(dataType = "string", paramType = "query", name = "endTime", value = "结束时间", defaultValue = ""),
            @ApiImplicitParam(dataType = "int", paramType = "query", name = "pageIndex", value = "分页iNdex", defaultValue = "1"),
            @ApiImplicitParam(dataType = "int", paramType = "query", name = "pageSize", value = "size", defaultValue = "15"),
    })
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Result list(String keyword,
                       String startTime,
                       String endTime,
                       Integer pageIndex,
                       Integer pageSize) {
        List<ProDefEntity> list = processManagerService.list(keyword, startTime, endTime, pageIndex, pageSize);
        PageInfo<ProDefEntity> pageInfo = new PageInfo<>(list);
        return Result.createSuccess("查询成功", pageInfo);
    }

    @ApiOperation(value = "激活或挂起流程", notes = "激活或挂起")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", paramType = "query", name = "id", value = "流程定义的ID", defaultValue = ""),
            @ApiImplicitParam(dataType = "string", paramType = "query", name = "status", value = "状态（1激活2挂起）", defaultValue = "")
    })
    @RequestMapping(value = "onoffPro", method = RequestMethod.POST)
    public Result onoffPro(String id,
                           Integer status) {
        if (status==2) {
            runTaskService.onoffAllTask(id, 1);
            repositoryService.suspendProcessDefinitionById(id, true, null);
        }else {
            runTaskService.onoffAllTask(id, 2);
            repositoryService.activateProcessDefinitionById(id, true, null);
        }
        return Result.createSuccess("提交成功");
    }

    @ApiOperation(value = "删除流程", notes = "删除流程")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", paramType = "query", name = "deploymentId", value = "流程部署的ID", defaultValue = ""),
            @ApiImplicitParam(dataType = "string", paramType = "query", name = "status", value = "状态（1激活2挂起）", defaultValue = "")
    })
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result delete(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId);
        return Result.createSuccess("删除成功");
    }

    @ApiOperation(value = "打包下载", notes = "打包下载")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", paramType = "query", name = "deploymentId", value = "流程部署的ID", defaultValue = ""),
    })
    @RequestMapping(value = "download", method = RequestMethod.GET)
    public void download(String deploymentId,
                         HttpServletResponse response)throws Exception{
        InputStream in = null;
        try {
            DelAllFile.delFolder(PathUtil.getClasspath() + "uploadFiles/activitiFile");
            List<String> names = repositoryService.getDeploymentResourceNames(deploymentId);
            for (String name : names){
                if (name.indexOf("zip")!=-1) continue;
                in = repositoryService.getResourceAsStream(deploymentId, name);
                FileUpload.copyFile(in, PathUtil.getClasspath() + "uploadFiles/activitiFile/" , name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
    }
}

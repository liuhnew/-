package com.jykj.controller.sys;

import com.github.pagehelper.PageInfo;
import com.jykj.entity.Result;
import com.jykj.entity.SysDic;
import com.jykj.service.SysDicService;
import com.jykj.util.TreeBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "字典管理", tags = "字典管理")
@RestController
@RequestMapping("/service/sys/dic")
public class SysDicController {

    @Autowired
    private SysDicService sysDicService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "dicName", value = "字典名称", required = false, dataType = "String", defaultValue = "采购入库"),
            @ApiImplicitParam(paramType = "query", name = "english", value = "英文", required = false, dataType = "String", defaultValue = "stock_type"),
            @ApiImplicitParam(paramType = "query", name = "code", value = "编码", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "sortCode", value = "排序吗", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "banDelete", value = "禁止删除", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "parentId", value = "上级父ID", required = false, dataType = "String", defaultValue = "1"),
    })
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Result save(String dicName,
                       String english,
                       String code,
                       Integer sortCode,
                       String remark,
                       Integer banDelete,
                       Integer parentId){
        SysDic sysDic = new SysDic();
        sysDic.setDicName(dicName);
        sysDic.setEnglish(english);
        sysDic.setCode(code);
        sysDic.setSortCode(sortCode);
        sysDic.setRemark(remark);
        sysDic.setBanDelete(banDelete);
        sysDic.setParentId(parentId);
        sysDicService.save(sysDic);
        return Result.createSuccess("保存成功", sysDic);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "dicName", value = "字典名称", required = false, dataType = "String", defaultValue = "采购入库"),
            @ApiImplicitParam(paramType = "query", name = "english", value = "英文", required = false, dataType = "String", defaultValue = "stock_type"),
            @ApiImplicitParam(paramType = "query", name = "code", value = "编码", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "sortCode", value = "排序吗", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "banDelete", value = "禁止删除", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "parentId", value = "上级父ID", required = false, dataType = "String", defaultValue = "1"),
    })
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(Integer id,
                         String dicName,
                         String english,
                         String code,
                         Integer sortCode,
                         String remark,
                         Integer banDelete,
                         Integer parentId){
        SysDic sysDic = sysDicService.selectById(id);
        sysDic.setDicName(dicName);
        sysDic.setEnglish(english);
        sysDic.setCode(code);
        sysDic.setSortCode(sortCode);
        sysDic.setRemark(remark);
        sysDic.setBanDelete(banDelete);
        sysDic.setParentId(parentId);
        sysDicService.update(sysDic);
        return Result.createSuccess("修改成功", sysDic);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result delete(@RequestParam("id") String id){
        String[] array = id.split(",");
        for (String str : array) {
            sysDicService.delete(Integer.parseInt(str));
        }
        return Result.createSuccess("删除成功");
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "keyWord", value = "关键字", required = false, dataType = "String", defaultValue = "采购入库"),
            @ApiImplicitParam(paramType = "query", name = "pageIndex", value = "分页", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "分页大小", required = false, dataType = "String", defaultValue = "15"),
    })
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Result list(String keyWord,
                       Integer pageIndex,
                       Integer pageSize) {
        PageInfo<SysDic> list =  sysDicService.list(keyWord, pageIndex, pageSize);
        return Result.createSuccess("查询成功", list);
    }


    @RequestMapping(value = "tree", method = RequestMethod.POST)
    public Result tree() {
        List<TreeBean<SysDic>> treeBeans = sysDicService.tree();
        return Result.createSuccess("查询成功", treeBeans);
    }


}

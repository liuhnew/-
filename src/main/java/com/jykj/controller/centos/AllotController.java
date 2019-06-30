package com.jykj.controller.centos;

import com.github.pagehelper.PageInfo;
import com.jykj.entity.Allot;
import com.jykj.entity.Goods;
import com.jykj.entity.Result;
import com.jykj.mongo.MongoDBCollectionOperation;
import com.jykj.service.AllotService;
import com.jykj.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/service/cencus/allot")
public class AllotController {

    @Autowired
    private AllotService allotService;

    @Autowired
    private GoodsService goodsService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品主键ID", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "num", value = "申请数量", required = false, dataType = "String", defaultValue = "20"),
    })
    @ApiOperation(value = "申请汽配" , notes = "申请汽配")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result save(Integer goodsId,
                       Integer num) {
        Goods goods = goodsService.findById(goodsId);
        Allot allot = null;
        if (goods!=null){
            allot = new Allot();
            allot.setCreateTime(new Date());
            allot.setCreateUserId(1);
            allot.setGoodsName(goods.getName());
            allot.setGoodsModel(goods.getModel());
            allot.setNum(num);
            allot.setPrice(goods.getSellingPrice());
            allot.setState(0);
            allot.setOrgId("0001");
            allotService.save(allot);
            return Result.createSuccess("申请调拨成功", null);
        }
        return Result.createFail("申请失败");
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "调拨记录ID", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "orgName", value = "调出仓库名称", required = false, dataType = "String", defaultValue = "第一车间"),
            @ApiImplicitParam(paramType = "query", name = "state", value = "调拨状态", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", required = false, dataType = "String", defaultValue = "没有库存"),
    })
    @ApiOperation(value = "审核" , notes = "审核")
    @RequestMapping(value = "verify", method = RequestMethod.POST)
    public Result verify(Integer id,
                         String orgName,
                         Integer state,
                         String remark) {
        Allot allot = allotService.queryById(id);
        if (allot!=null){
           allot.setState(state);
           allot.setOutRepo(orgName);
           allot.setRemark(remark);
           allot.setConfirmDate(new Date());
           allotService.update(allot);
           return Result.createSuccess("提交成功");
        }
        return Result.createFail("审核接口错误");
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageIndex", value = "index", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "size", required = false, dataType = "String", defaultValue = "20")
    })
    @ApiOperation(value = "查询" , notes = "查询")
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Result list(Integer pageIndex,
                       Integer pageSize){
        PageInfo<Allot> pageInfo = allotService.list("0001",pageIndex, pageSize);
        return Result.createSuccess("查询成功", pageInfo);
    }

    /*@RequestMapping(value = "allotList", method = RequestMethod.POST)
    public Result allotList(Integer pageIndex,Integer pageSize,String orgId,Date beginTime,Date endTime){

    }*/
}

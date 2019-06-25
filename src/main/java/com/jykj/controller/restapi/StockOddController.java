package com.jykj.controller.restapi;

import com.github.pagehelper.PageInfo;
import com.jykj.controller.BaseController;
import com.jykj.entity.Result;
import com.jykj.entity.StockOdd;
import com.jykj.entity.StockOddGoods;
import com.jykj.service.StockOddGoodsService;
import com.jykj.service.StockOddService;
import com.jykj.util.DateUtil;
import com.jykj.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台管理入库单
 * @author 程照
 */
@Api(value = "入库单管理", tags = "入库单管理")
@RestController
@RequestMapping("/service/stockOdd")
public class StockOddController extends BaseController {

    private Logger log = LoggerFactory.getLogger(StockOddController.class);

    @Autowired
    private StockOddService stockOddService;

    @Autowired
    private StockOddGoodsService stockOddGoodsService;

    @ApiOperation(value = "生成入库单号", notes = "生成入库单号")
    @RequestMapping(value = "genCode", method = RequestMethod.POST)
    public Result genCode(){
        StringBuffer sb = new StringBuffer("RK");
        try {
            sb.append(DateUtil.getCurrentDateStr());
            String maxPurChaseNum = stockOddService.getMaxPurChaseNums();
            if (maxPurChaseNum!=null){
                sb.append(StringUtil.formatCode(maxPurChaseNum));
            }else {
                sb.append("0001");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Result.createSuccess("查询成功", sb.toString());
    }

    @ApiOperation(value = "添加入库单", notes = "添加入库单")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result save(@RequestBody String requestBody){
        try {
            stockOddService.save(requestBody);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.createFail("添加失败");
        }
        return Result.createSuccess("添加成功");
    }

    @ApiOperation(value = "查询入库单", notes = "查询入库单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "制单日期：开始日期", required = false, dataType = "String", defaultValue = "2019-05-24"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "制单日期：结束日期", required = false, dataType = "String", defaultValue = "2020-05-24"),
            @ApiImplicitParam(paramType = "query", name = "purchaseRevieceNum", value = "采购收货单号", required = false, dataType = "String", defaultValue = "RK20180524"),
            @ApiImplicitParam(paramType = "query", name = "purchaseNum", value = "采购单号", required = false, dataType = "String", defaultValue = "RK20180524"),
            @ApiImplicitParam(paramType = "query", name = "purchaseOrg", value = "采购组织", required = false, dataType = "String", defaultValue = "第一车间"),
            @ApiImplicitParam(paramType = "query", name = "supplierName", value = "供应商", required = false, dataType = "String", defaultValue = "哇哈哈"),
            @ApiImplicitParam(paramType = "query", name = "docType", value = "单据类型", required = false, dataType = "String", defaultValue = "统采"),
    })
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Result list(String startTime,
                       String endTime,
                       String purchaseRevieceNum,
                       String purchaseNum,
                       String purchaseOrg,
                       String supplierName,
                       String docType,
                       @RequestParam(defaultValue = "1") Integer pageIndex,
                       @RequestParam(defaultValue = "15")Integer pageSize){
        List<StockOdd> list = stockOddService.list(startTime, endTime, purchaseRevieceNum, purchaseNum, purchaseOrg, supplierName, docType, pageIndex, pageSize);
        PageInfo<StockOdd> pageInfo = new PageInfo<StockOdd>(list);
        return Result.createSuccess("查询成功", pageInfo);
    }

    @ApiOperation(value = "查看商品详情", notes = "查看商品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "purchaseNum", value = "入库单号", required = false, dataType = "String", defaultValue = "RK201906040001"),
    })
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public Result detail(String purchaseNum){
        List<StockOddGoods> list = stockOddGoodsService.queryByPurchaseNum(purchaseNum);
        return Result.createSuccess("查询成功", list);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "入库单号", required = false, dataType = "String", defaultValue = ""),
    })
    @ApiOperation(value = "删除入库单", notes = "删除入库单")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result delete(String id) {
        String[] array = id.split(",");
        for (String str : array) {
            StockOdd stockOdd = stockOddService.selectById(Integer.parseInt(str));
            if (stockOdd!=null){
                stockOddService.delete(Integer.parseInt(str));
                stockOddGoodsService.delete(stockOdd.getPurchaseNum());
            }
        }
        return Result.createSuccess("删除成功");
    }

}

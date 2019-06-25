package com.jykj.controller.restapi;

import com.github.pagehelper.PageInfo;
import com.jykj.controller.BaseController;
import com.jykj.entity.*;
import com.jykj.service.*;
import com.jykj.util.DateUtil;
import com.jykj.util.GsonUtils;
import com.jykj.util.MathUtil;
import com.jykj.util.StringUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * 后台管理销售单Controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/service/saleList")
public class SaleListController extends BaseController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SaleListService saleListService;

    @Autowired
    private SaleListGoodsService saleListGoodsService;

    @Autowired
    private GoodsTypeService goodsTypeService;

    @Autowired
    private LogService logService;

    /**
     * 获取销售单号
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/genCode", method = RequestMethod.POST)
    public Result genCode()throws Exception{
        StringBuffer code=new StringBuffer("XS");
        code.append(DateUtil.getCurrentDateStr());
        String saleNumber=saleListService.getTodayMaxSaleNumber();
        if(saleNumber!=null){
            code.append(StringUtil.formatCode(saleNumber));
        }else{
            code.append("0001");
        }
        return Result.createSuccess("查询成功",code.toString());
    }

    /**
     * 添加销售单 以及所有销售单商品

     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "hasPaid", dataType = "float", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "saleRemarks", dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "customer", dataType = "int", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "goodsJson", dataType = "string", paramType = "query", defaultValue = ""),
    })
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result save(String num,
                       Float hasPaid,
                       String saleRemarks,
                       Integer customer,
                       String goodsJson)throws Exception{
        SaleList saleList = new SaleList();
        saleList.setState(0);
        saleList.setSaleNumber(num);
        saleList.setRemarks(saleRemarks);
        saleList.setAmountPaid(hasPaid);
        saleList.setCustomerId(customer);

        goodsJson = "[{\"index\":\"1\",\"code\":\"0001\",\"name\":\"陶华碧老干妈香辣脆油辣椒\",\"model\":\"红色装\",\"unit\":\"瓶\",\"amount\":\"1\",\"sellingPrice\":8.5,\"total\":8.5,\"id\":1,\"inventoryQuantity\":11,\"lastPurchasingPrice\":20,\"minNum\":1000,\"producer\":\"贵州省贵阳南明老干妈风味食品有限公司\",\"purchasingPrice\":20,\"remarks\":\"好吃1\",\"saleTotal\":null,\"state\":2,\"typeId\":10,\"orgId\":\"\",\"url\":null}]";

        List<Map<String,Object>> list = GsonUtils.getListFromJson(goodsJson, List.class);
        List<SaleListGoods> goodsSaleList = new ArrayList<SaleListGoods>();
        SaleListGoods saleListGoods = null;
        for (Map<String,Object> map1 : list) {
            saleListGoods = new SaleListGoods();
            saleListGoods.setCode(map1.get("code").toString());
            saleListGoods.setName(map1.get("name").toString());
            saleListGoods.setModel(map1.get("model").toString());
            saleListGoods.setUnit(map1.get("unit").toString());
            saleListGoods.setNum(Integer.parseInt(map1.get("amount").toString()));
            saleListGoods.setTotal(Float.parseFloat(map1.get("total").toString()));
            saleListGoods.setTypeId(Double.valueOf(map1.get("typeId").toString()).intValue());
            saleListGoods.setGoodsId(Double.valueOf(map1.get("id").toString()).intValue());
            saleListGoods.setPrice(Float.valueOf(map1.get("purchasingPrice").toString()));
            goodsSaleList.add(saleListGoods);
        }
        saleListService.save(saleList, goodsSaleList);
        logService.save(new Log(Log.ADD_ACTION,"添加销售单"));
        return Result.createSuccess("添加成功");
    }

    /**
     * 根据条件查询所有销售单信息
     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "saleNumber", dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "customerId", dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "state", dataType = "int", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "startTime", dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "endTime", dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "pageIndex", dataType = "int", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "pageSize", dataType = "int", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Result list(String saleNumber,
                       String customerId,
                       Integer state,
                       String startTime,
                       String endTime,
                       Integer pageIndex,
                       Integer pageSize){
        List<SaleList> saleListList = saleListService.list(saleNumber, customerId, state, startTime, endTime, pageIndex, pageSize);
        PageInfo<SaleList> pageInfo = new PageInfo<SaleList>(saleListList);
        return Result.createSuccess("查询成功", pageInfo);
    }

    /**
     * 根据条件获取商品销售信息
     * @param saleList
     * @param saleListGoods
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Result listCount(@RequestBody(required = false) SaleList saleList,
                            @RequestBody(required = false) SaleListGoods saleListGoods)throws Exception{
       return Result.createSuccess("");
    }

    /**
     * 根据销售单id查询所有销售单商品
     * @param id
     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "id", paramType = "query", defaultValue = ""),
    })
    @RequestMapping(value = "/allGoods",method = RequestMethod.POST)
    public Result listGoods(@RequestParam Integer id)throws Exception{
        List<SaleListGoods> saleListGoods = saleListGoodsService.listBySaleListId(id);
        PageInfo<SaleListGoods> pageInfo = new PageInfo<>(saleListGoods);
        return Result.createSuccess("查询成功", pageInfo);
    }

    /**
     * 删除销售单 以及销售单里的商品
     * @param id
     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "string", paramType = "query", defaultValue = ""),
    })
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(@RequestParam String id)throws Exception{
        String[] idStr = id.split(",");
        for (String id1 : idStr) {
            saleListService.delete(Integer.parseInt(id1));
            logService.save(new Log(Log.DELETE_ACTION,"删除销售单信息："+saleListService.findById(Integer.parseInt(id1)).getSaleNumber()));
        }
        return Result.createSuccess( "删除成功");
    }

    /**
     * 修改销售单的支付状态
     * @param id
     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "string", paramType = "query", defaultValue = ""),
    })
    @RequestMapping(value = "/payState",method = RequestMethod.POST)
    public Result update(@RequestParam Integer id)throws Exception{
        SaleList saleList=saleListService.findById(id);
        saleList.setState(1);
        saleListService.update(saleList);
        return Result.createSuccess( "修改成功", saleList);
    }

    /**
     * 按日统计分析
     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginTime", dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "endTime", dataType = "string", paramType = "query", defaultValue = ""),
    })
    @RequestMapping(value = "/countSaleByDay", method = RequestMethod.POST)
    public Result countSaleByDay(@RequestParam String beginTime,
                                 @RequestParam String endTime)throws Exception{
        List<SaleCount> scList=new ArrayList<>();
        List<String> dates=DateUtil.getRangeDates(beginTime, endTime);
        List<Object> ll=saleListService.countSaleByDay(beginTime, endTime);
        for(String date:dates){
            SaleCount sc=new SaleCount();
            sc.setDate(date);
            boolean flag=false;
            for(Object o:ll){
                Object []oo=(Object[]) o;
                String dd=oo[2].toString().substring(0,10);
                if(dd.equals(date)){ // 存在
                    sc.setAmountCost(MathUtil.format2Bit(Float.parseFloat(oo[0].toString()))); // 成本总金额
                    sc.setAmountSale(MathUtil.format2Bit(Float.parseFloat(oo[1].toString()))); // 销售总金额
                    sc.setAmountProfit(MathUtil.format2Bit(sc.getAmountSale()-sc.getAmountCost())); // 销售利润
                    flag=true;
                }
            }
            if(!flag){
                sc.setAmountCost(0);
                sc.setAmountSale(0);
                sc.setAmountProfit(0);
            }
            scList.add(sc);
        }
        return Result.createSuccess("查询成功", scList);
    }

    /**
     * 按月统计分析
     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginTime", dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "endTime", dataType = "string", paramType = "query", defaultValue = ""),
    })
    @RequestMapping(value = "/countSaleByMonth", method = RequestMethod.POST)
    public Result countSaleByMonth(@RequestParam String beginTime,
                                   @RequestParam String endTime)throws Exception{
        List<SaleCount> scList=new ArrayList<>();
        List<String> dates=DateUtil.getRangeMonths(beginTime, endTime);
        List<Object> ll=saleListService.countSaleByMonth(beginTime, endTime);
        for(String date:dates){
            SaleCount sc=new SaleCount();
            sc.setDate(date);
            boolean flag=false;
            for(Object o:ll){
                Object []oo=(Object[]) o;
                String dd=oo[2].toString().substring(0,7);
                if(dd.equals(date)){ // 存在
                    sc.setAmountCost(MathUtil.format2Bit(Float.parseFloat(oo[0].toString()))); // 成本总金额
                    sc.setAmountSale(MathUtil.format2Bit(Float.parseFloat(oo[1].toString()))); // 销售总金额
                    sc.setAmountProfit(MathUtil.format2Bit(sc.getAmountSale()-sc.getAmountCost())); // 销售利润
                    flag=true;
                }
            }
            if(!flag){
                sc.setAmountCost(0);
                sc.setAmountSale(0);
                sc.setAmountProfit(0);
            }
            scList.add(sc);
        }
        return new Result(true, StatusCode.OK, "查询成功",new PageResult<SaleCount>(scList.size()+0l,scList));
    }
}

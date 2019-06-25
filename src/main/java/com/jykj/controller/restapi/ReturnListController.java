package com.jykj.controller.restapi;

import com.github.pagehelper.PageInfo;
import com.jykj.entity.*;
import com.jykj.service.LogService;
import com.jykj.service.ReturnListGoodsService;
import com.jykj.service.ReturnListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service/returnList")
public class ReturnListController {

    @Autowired
    private ReturnListService returnListService;

    @Autowired
    private ReturnListGoodsService returnListGoodsService;

    @Autowired
    private LogService logService;

    /**
     * 获取当天最大退货单号
     * @return
     */
    @RequestMapping(value = "/getTodayMaxReturnNumber", method = RequestMethod.POST)
    public Result getTodayMaxReturnNumber() {
        String odayMaxReturnNumber = returnListService.getTodayMaxReturnNumber();
        return new Result(true, StatusCode.OK,"获取当天最大退货单号",odayMaxReturnNumber);
    }

    /**
     * 添加退货单 以及所有退货单商品  以及 修改 库存数量
     * @param returnList
     * @param returnListGoodsList
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(ReturnList returnList, List<ReturnListGoods> returnListGoodsList)throws Exception{
        ReturnList returnLists = new ReturnList();
        returnLists.setUserId(returnList.getUserId());
        returnLists.setSupplierId(returnList.getSupplierId());
        returnLists.setState(returnList.getState());
        returnLists.setReturnNumber(returnList.getReturnNumber());
        returnLists.setReturnDate(returnList.getReturnDate());
        returnLists.setRemarks(returnList.getRemarks());
        returnLists.setId(returnList.getId());
        returnLists.setAmountPayable(returnList.getAmountPayable());
        returnLists.setAmountPaid(returnList.getAmountPaid());
        returnListService.save(returnLists, returnListGoodsList);
        logService.save(new Log(Log.ADD_ACTION,"添加退货单"));
        return Result.createSuccess("添加成功");
    }

    /**
     * 根据id查询实体
     * @param id
     */
    @RequestMapping(value = "/findById", method = RequestMethod.POST)
    public Result findById(Integer id){
        return Result.createSuccess("查询成功", returnListService.findById(id));
    }

    /**
     * 根据条件查询退货单信息
     * @param returnLists
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result list(ReturnList returnLists){
        List<ReturnList> returnList=returnListService.list(returnLists);
        PageInfo<ReturnList> pageInfo = new PageInfo<>(returnList);
        return Result.createSuccess("查询成功", pageInfo);
    }

    /**
     * 根据id删除退货单信息 包括退货单里的所有商品
     * @param id
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(Integer id){
        returnListService.delete(id);
        return  Result.createSuccess("删除成功");
    }

    /**
     * 更新退货单
     * @param returnList
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(ReturnList returnList){
        returnListService.update(returnList);
        return Result.createSuccess("更新成功");
    }

    /**
     * 根据退货单id查询所有退货单商品
     */
    @RequestMapping(value = "/listByReturnListId", method = RequestMethod.POST)
    public Result listByReturnListId(Integer id){
        return Result.createSuccess("查询成功", returnListGoodsService.listByReturnListId(id));
    }

    /**
     * 根据条件查询退货单商品
     * @param returnListGoods
     * @return
     */
    @RequestMapping(value = "/goodsList", method = RequestMethod.POST)
    public Result goodsList(ReturnListGoods returnListGoods){
        List<ReturnListGoods> returnListGoods1=returnListGoodsService.list(returnListGoods);
        PageInfo<ReturnListGoods> pageInfo = new PageInfo<>(returnListGoods1);
        return Result.createSuccess("查询成功", pageInfo);
    }
}

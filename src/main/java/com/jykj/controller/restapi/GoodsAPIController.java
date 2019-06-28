package com.jykj.controller.restapi;

import com.github.pagehelper.PageInfo;
import com.jykj.controller.BaseController;
import com.jykj.entity.*;
import com.jykj.service.CustomerReturnGoodsService;
import com.jykj.service.GoodsService;
import com.jykj.service.LogService;
import com.jykj.service.SaleListGoodsService;
import com.jykj.util.OssClientUtil;
import com.jykj.util.StringUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 后台管理商品Controller
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/service/goods")
public class GoodsAPIController extends BaseController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SaleListGoodsService saleListGoodsService;

    @Autowired
    private CustomerReturnGoodsService customerReturnListGoodsService;

    @Autowired
    private LogService logService;

    /**
     * 分页查询商品信息
     *
     * @throws Exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "goodsName", value = "", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "inventoryQuantity", value = "", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "goodsNum", value = "", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "pageIndex", value = "", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "", required = false, dataType = "String", defaultValue = "15"),
    })
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result list(String goodsName,
                       Integer inventoryQuantity,
                       String goodsNum,
                       HttpServletRequest request,
                       @RequestParam(defaultValue = "1") Integer pageIndex,
                       @RequestParam(defaultValue = "15") Integer pageSize) throws Exception {
        List<String> tenantId = super.tenantList(request);
        List<Goods> goodsList = goodsService.list(goodsName, inventoryQuantity, goodsNum, pageIndex, pageSize,tenantId);
        PageInfo<Goods> pageInfo = new PageInfo<Goods>(goodsList);
        return Result.createSuccess("查询成功", pageInfo);
    }

    /**
     * 查询库存报警商品
     *
     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "goodsName", value = "", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "inventoryQuantity", value = "", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "goodsNum", value = "", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "pageIndex", value = "", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "", required = false, dataType = "String", defaultValue = "15"),
    })
    @RequestMapping(value = "/listAlarm", method = RequestMethod.POST)
    public Result listAlarm(String goodsName,
                            Integer inventoryQuantity,
                            String goodsNum,
                            Integer pageIndex,
                            Integer pageSize) throws Exception {
        List<Goods> list = goodsService.listAlarm(goodsName, inventoryQuantity, goodsNum, pageIndex, pageSize);
        PageInfo<Goods> pageInfo = new PageInfo<>(list);
        return Result.createSuccess("查询成功", pageInfo);
    }

    /**
     * 根据条件分页查询商品库存信息
     *
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "goodsName", value = "", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "inventoryQuantity", value = "", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "goodsNum", value = "", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "pageIndex", value = "", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "", required = false, dataType = "String", defaultValue = "15"),
    })
    @RequestMapping(value = "/listInventory", method = RequestMethod.POST)
    public Result listInventory(String goodsName,
                                Integer inventoryQuantity,
                                String goodsNum,
                                HttpServletRequest request,
                                @RequestParam(defaultValue = "1") Integer pageIndex,
                                @RequestParam(defaultValue = "15") Integer pageSize) throws Exception {
        List<String> list = super.tenantList(request);
        List<Goods> goodsList = goodsService.list(goodsName, inventoryQuantity, goodsNum, pageIndex, pageSize,list);
        for (Goods g : goodsList) {
            g.setSaleTotal(saleListGoodsService.getTotalByGoodsId(g.getId()) - customerReturnListGoodsService.getTotalByGoodsId(g.getId())); // 设置销售总量

        }
        PageInfo<Goods> pageInfo = new PageInfo<Goods>(goodsList);
        return Result.createSuccess("查询成功", pageInfo);
    }

    /**
     * 生成商品编码
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/genGoodsCode", method = RequestMethod.POST)
    public Result genGoodsCode() throws Exception {
        String maxGoodsCode = goodsService.getMaxGoodsCode();
        String codes = "";
        if (StringUtil.isNotEmpty(maxGoodsCode)) {
            Integer code = Integer.parseInt(maxGoodsCode) + 1;
            codes = code.toString();
            int length = codes.length();
            for (int i = 4; i > length; i--) {
                codes = "0" + codes;
            }
        } else {
            codes = "0001";
        }
        return Result.createSuccess("生成商品编码成功", codes);
    }

    /**
     * 添加或者修改商品信息
     *
     * @param
     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "code", value = "", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "minNum", value = "", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "model", value = "", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "name", value = "", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "producer", value = "", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "purchasingPrice", value = "", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "remarks", value = "", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "sellingPrice", value = "", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "typeId", value = "", required = false, dataType = "String", defaultValue = "15"),
            @ApiImplicitParam(paramType = "query", name = "imgUrl", value = "", required = false, dataType = "String", defaultValue = "15"),
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(Integer id,
                       String code,
                       String minNum,
                       String model,
                       String name,
                       String producer,
                       String purchasingPrice,
                       String remarks,
                       String sellingPrice,
                       Integer typeId,
                       String imgUrl,
                       HttpServletRequest request) throws Exception {
        LoginInfo tenant = super.getUserInfo(request);
        Goods goods = null;
        if (id != null) {
            goods = goodsService.findById(id);
            goods.setCode(code);
            goods.setMinNum(Integer.parseInt(minNum));
            goods.setModel(model);
            goods.setName(name);
            goods.setProducer(producer);
            goods.setPurchasingPrice(Float.parseFloat(purchasingPrice));
            goods.setRemarks(remarks);
            goods.setSellingPrice(Float.parseFloat(sellingPrice));
            goods.setTypeId(typeId);
            goods.setUrl(imgUrl);
            goods.setTenantId(tenant.getTenant());
            goodsService.update(goods);
            logService.save(new Log(Log.UPDATE_ACTION, "更新商品信息" + goods));
        } else {
            goods = new Goods();
            goods.setCode(code);
            goods.setMinNum(Integer.parseInt(minNum));
            goods.setModel(model);
            goods.setName(name);
            goods.setProducer(producer);
            goods.setPurchasingPrice(Float.parseFloat(purchasingPrice));
            goods.setRemarks(remarks);
            goods.setState(0);
            goods.setSellingPrice(Float.parseFloat(sellingPrice));
            goods.setTypeId(typeId);
            goods.setUrl(imgUrl);
            goods.setLastPurchasingPrice(goods.getPurchasingPrice()); // 设置上次进价为当前价格
            goods.setTenantId(tenant.getTenant());
            goodsService.save(goods);
            logService.save(new Log(Log.ADD_ACTION, "添加商品信息" + goods));
        }
        return new Result(true, StatusCode.OK, "增加或修改成功");
    }

    /**
     * 上传商品图片
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "uploadImg", method = RequestMethod.POST)
    public Result uploadImg(@RequestParam("file") MultipartFile file) {
        String imgUrl = OssClientUtil.uploadFile(file);
        if (StringUtil.isEmpty(imgUrl) || imgUrl.length() <= 2) {
            return Result.createFail("上传图片失败", imgUrl);
        }
        return Result.createFail("上传图片成功", imgUrl);
    }

    /**
     * 删除商品信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "", required = false, dataType = "String", defaultValue = ""),
    })
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(String id) throws Exception {
        String[] array = id.split(",");
        for (String str : array) {
            Goods goods = goodsService.findById(Integer.parseInt(str));
            if (goods.getState() == 1) {
                return Result.createFail("该商品已经期初入库，不能删除");
            } else if (goods.getState() == 2) {
                return Result.createFail("该商品已经发生单据，不能删除");
            } else {
                logService.save(new Log(Log.DELETE_ACTION, "删除商品信息" + goods));
                goodsService.delete(Integer.parseInt(str));
            }
        }
        return Result.createSuccess("删除商品信息");
    }

    /**
     * 添加商品到仓库 修改库存以及价格信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "num", value = "", required = false, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "price", value = "", required = false, dataType = "String", defaultValue = ""),
    })
    @RequestMapping(value = "/saveStore", method = RequestMethod.POST)
    public Result saveStore(Integer id,
                            Integer num,
                            Float price) throws Exception {
        Goods goods = goodsService.findById(id);
        goods.setInventoryQuantity(num);
        goods.setPurchasingPrice(price);
        goods.setLastPurchasingPrice(price);
        goodsService.save(goods);
        logService.save(new Log(Log.UPDATE_ACTION, "修改商品信息：" + goods + "，价格=" + price + ",库存=" + num));
        return new Result(true, StatusCode.OK, "修改商品信息(修改库存或价格)");
    }

    /**
     * 删除库存，把商品的库存设置为0
     *
     * @param id
     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "", required = false, dataType = "String", defaultValue = ""),
    })
    @RequestMapping(value = "/deleteStock", method = RequestMethod.POST)
    public Result deleteStock(String id) throws Exception {
        String[] array = id.split(",");
        for (String str : array) {
            Goods goods = goodsService.findById(Integer.parseInt(str));
            if (goods.getState() == 2) {
                return Result.createFail("该商品已经发生单据，不能删除");
            } else {
                goods.setInventoryQuantity(0);
                goodsService.update(goods);
                logService.save(new Log(Log.UPDATE_ACTION, "修改商品库存" + goods));
            }
        }
        return Result.createSuccess("已删除库存");
    }

}

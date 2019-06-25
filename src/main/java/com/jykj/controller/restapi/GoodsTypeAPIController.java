package com.jykj.controller.restapi;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jykj.entity.GoodsType;
import com.jykj.entity.Log;
import com.jykj.entity.Result;
import com.jykj.entity.StatusCode;
import com.jykj.service.GoodsService;
import com.jykj.service.GoodsTypeService;
import com.jykj.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台商品类别Controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/service/goodsType")
public class GoodsTypeAPIController {

	
	@Autowired
	private GoodsTypeService goodsTypeService;

	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private LogService logService;

	@RequestMapping(value = "select", method = RequestMethod.POST)
	public Result select() throws Exception {
		List<GoodsType> list = goodsTypeService.select();
		return Result.createSuccess("查询成功", list);
	}

	/**
	 * 添加商品类别
	 * @param name
	 * @Param pid
	 * @Param state
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Result save(String name,
					   Integer pid,
					   Integer state)throws Exception{
		GoodsType goodsType=new GoodsType();
		goodsType.setName(name);
		goodsType.setpId(pid);
		goodsType.setIcon("icon-folder");
		goodsType.setState(state);
		goodsTypeService.save(goodsType);
		GoodsType parentGoodsType=goodsTypeService.findById(pid);
		parentGoodsType.setState(1);
		goodsTypeService.save(parentGoodsType);
		logService.save(new Log(Log.ADD_ACTION,"添加商品类别信息"));
		return new Result(true, StatusCode.OK,"增加成功");
	}
	
	/**
	 * 商品类别删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Result delete(Integer id)throws Exception{
		if(goodsService.findByTypeId(id).size()==0){
			GoodsType goodsType=goodsTypeService.findById(id);
			if(goodsTypeService.findByParentId(goodsType.getpId()).size()==1){
				GoodsType parentGoodsType=goodsTypeService.findById(goodsType.getpId());
				parentGoodsType.setState(0);
				goodsTypeService.update(parentGoodsType);
			}
			logService.save(new Log(Log.DELETE_ACTION,"删除商品类别信息"+goodsType));
			goodsTypeService.delete(id);
			return Result.createSuccess("操作成功");
		}else{
			return Result.createFail("该类别下含有商品，不能删除");
		}

	}

	/**
	 * 获取所有类别菜单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loadTreeInfo",method = RequestMethod.POST)
	public Result loadTreeInfo()throws Exception{
		return Result.createSuccess("查询所有商品类别信息成功",getAllByParentId(-1).toString());
	}

	/**
	 * 根据父节点id和权限菜单id集合获取所有复选框菜单集合
	 * @param parentId
	 * @return
	 */
	public JsonArray getAllByParentId(Integer parentId){
		JsonArray jsonArray=this.getByParentId(parentId);
		for(int i=0;i<jsonArray.size();i++){
			JsonObject jsonObject=(JsonObject) jsonArray.get(i);
			if("open".equals(jsonObject.get("state").getAsString())){
				continue;
			}else{
				jsonObject.add("children", getAllByParentId(jsonObject.get("id").getAsInt()));
			}
		}
		return jsonArray;
	}
	
	/**
	 * 根据父节点查询所有子节点
	 * @param parentId
	 * @return
	 */
	public JsonArray getByParentId(Integer parentId){
		List<GoodsType> goodsTypeList=goodsTypeService.findByParentId(parentId);
		JsonArray jsonArray=new JsonArray();
		for(GoodsType goodsType:goodsTypeList){
			JsonObject jsonObject=new JsonObject();
			jsonObject.addProperty("id", goodsType.getId()); // 节点Id
			jsonObject.addProperty("text", goodsType.getName()); // 节点名称
			if(goodsType.getState()==1){
				jsonObject.addProperty("state", "closed"); // 根节点
			}else{
				jsonObject.addProperty("state", "open"); // 叶子节点
			}
			jsonObject.addProperty("iconCls", goodsType.getIcon()); // 节点图标
			JsonObject attributeObject=new JsonObject(); // 扩展属性
			attributeObject.addProperty("state", goodsType.getState()); // 节点状态
			jsonObject.add("attributes", attributeObject); 
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}

}

package com.jykj.controller;
import javax.servlet.http.HttpServletRequest;

import com.jykj.entity.*;

import com.jykj.mongo.MongoDBCollectionOperation;
import com.jykj.service.UserService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户Controller
 * @author Administrator
 *
 */
@Slf4j
@RestController
@RequestMapping("/service/user")
public class UserController extends BaseController{


	@Autowired
	private UserService userService;

	@Autowired
	private MongoDBCollectionOperation tenantMainMongoDBCollection;

	/**
	 * 加载当前用户信息
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("menuInfo")
	public Result loadLoginUser(HttpServletRequest request) throws Exception {
		LoginInfo loginInfo = getUserInfo(request);
		//使用mongoClient查询用户信息
		Map<String,Object> list = userService.listPermission(loginInfo);
		if (list == null) {
			return Result.createFail("您还没有此系统的权限");
		}
		return Result.createSuccess("查询", list);
	}

	@RequestMapping(value = "userInfo", method = RequestMethod.POST)
	public Result userInfo(HttpServletRequest request) throws Exception {
		LoginInfo loginInfo = getUserInfo(request);
		return Result.createSuccess("查询", loginInfo);
	}

	@RequestMapping(value = "testMongo", method = RequestMethod.POST)
	public Result testMongo(HttpServletRequest request) throws Exception {
		List<String> result = tenantList(request);
		return Result.createSuccess("查询",result);
	}

	@ApiOperation(value = "查询质检员", notes = "查询质检员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户手机号", dataType = "string", paramType = "query"),
    })
    @RequestMapping(value = "queryZJY", method = RequestMethod.POST)
    public Result queryZJY(String userName,
                           HttpServletRequest request) throws Exception {
	    LoginInfo loginInfo = getUserInfo(request);
        List<Map<String,Object>> result = userService.queryZJY(loginInfo, userName);
        return Result.createSuccess("查询",result);
    }

}
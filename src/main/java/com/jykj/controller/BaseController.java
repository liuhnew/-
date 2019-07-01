package com.jykj.controller;

import com.jykj.config.SpringApplicationContextHolder;
import com.jykj.entity.LoginInfo;
import com.jykj.mongo.MongoDBCollectionOperation;
import com.jykj.service.UserService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class BaseController {

    @Autowired
    private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

    public LoginInfo getUserInfo(HttpServletRequest request){
        MongoDBCollectionOperation tenantRoleMongoDBCollection =
                (MongoDBCollectionOperation) SpringApplicationContextHolder.getSpringBean("tenantRoleMongoDBCollection");
        LoginInfo loginInfo = null;
        try {
            loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");
            List<String> roles =  loginInfo.getRol();
            Map<String,Object> map = tenantRoleMongoDBCollection.findById(roles.get(0)).toMap();
            loginInfo.setRoleName(map.get("name").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginInfo;
   }

    public List<String> tenantList(HttpServletRequest request) {
        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");
        List<String> list = userService.tenantList(loginInfo.getTenant());
        return list;
    }

    public String findCJAdmin(String userName) {
        MongoDBCollectionOperation tenantStaffMongoDBCollection =
                (MongoDBCollectionOperation) SpringApplicationContextHolder.getSpringBean("tenantStaffMongoDBCollection");
        MongoDBCollectionOperation tenantRoleMongoDBCollection =
                (MongoDBCollectionOperation) SpringApplicationContextHolder.getSpringBean("tenantRoleMongoDBCollection");
        Map<String,Object> mePd = tenantStaffMongoDBCollection.findById(userName).toMap();
        String tenantId = mePd.get("tenant").toString();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("tenant", new ObjectId(tenantId));
        basicDBObject.put("name", "车间管理员");
        Map<String,Object> role = tenantRoleMongoDBCollection.findOne(basicDBObject).toMap();
        Map<String,Object> query = new HashMap<>();
        query.put("roles.5d06159fd5ca6ee6ca4baf2a", role.get("_id"));
        List<DBObject> list = tenantStaffMongoDBCollection.find(query);
        List<String> usrNameList = new ArrayList<>();
        if (list!=null && list.size()>0) {
            for (DBObject object : list) {
                usrNameList.add(object.toMap().get("mobile").toString());
            }
        }
        return StringUtils.join(usrNameList, ",");
    }

    public String findBXY(String userName) {
        MongoDBCollectionOperation tenantStaffMongoDBCollection =
                (MongoDBCollectionOperation) SpringApplicationContextHolder.getSpringBean("tenantStaffMongoDBCollection");
        MongoDBCollectionOperation tenantRoleMongoDBCollection =
                (MongoDBCollectionOperation) SpringApplicationContextHolder.getSpringBean("tenantRoleMongoDBCollection");
        Map<String,Object> mePd = tenantStaffMongoDBCollection.findById(userName).toMap();
        String tenantId = mePd.get("tenant").toString();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("tenant", new ObjectId(tenantId));
        basicDBObject.put("name", "报修员");
        Map<String,Object> role = tenantRoleMongoDBCollection.findOne(basicDBObject).toMap();
        Map<String,Object> query = new HashMap<>();
        query.put("roles.5d06159fd5ca6ee6ca4baf2a", role.get("_id"));
        List<DBObject> list = tenantStaffMongoDBCollection.find(query);
        List<String> usrNameList = new ArrayList<>();
        if (list!=null && list.size()>0) {
            for (DBObject object : list) {
                usrNameList.add(object.toMap().get("mobile").toString());
            }
        }
        return StringUtils.join(usrNameList, ",");
    }
}

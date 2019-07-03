package com.jykj.service.impl;

import com.jykj.entity.LoginInfo;
import com.jykj.mongo.MongoDBCollectionOperation;
import com.jykj.mongo.PageInfo;
import com.jykj.service.UserService;
import com.jykj.util.GsonUtils;
import com.jykj.util.StringUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.regex.Pattern;

@Service("userService")
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private MongoDBCollectionOperation tenantRoleMongoDBCollection;

    @Autowired
    private MongoDBCollectionOperation appAuthorityMongoDBCollection;

    @Autowired
    private MongoDBCollectionOperation appMainMongoDBCollection;

    @Autowired
    private MongoDBCollectionOperation tenantMainMongoDBCollection;

    @Autowired
    private MongoDBCollectionOperation tenantStaffMongoDBCollection;


    @Override
    public Map<String,Object> listPermission(LoginInfo loginInfo) {
        List<String> btnPerm = new ArrayList<>();
        Map<String,Object> appId = new HashMap<>();
        appId.put("id", loginInfo.getAud());
        Map<String,Object> app = appMainMongoDBCollection.findOne(appId).toMap();
        List<String> roleIds =  loginInfo.getRol();
        List<Map<String,Object>> list = tenantRoleMongoDBCollection.queryMenuIdByRoleIds(roleIds, app.get("_id").toString());
        Set<ObjectId> set = new HashSet<>();
        for (Map<String,Object> map : list) {
            List<ObjectId> list1 = (List<ObjectId>)map.get("authorities");
            for (ObjectId objId : list1) {
                set.add(objId);
            }
        }
        List<Map<String,Object>> menu = appAuthorityMongoDBCollection.queryMenuIdByMenuIds(set);
        if (menu==null||menu.size()==0) {
            return null;
        }
        for (Map<String,Object> obj : menu) {
            btnPerm.add(obj.get("uri").toString());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("perm", btnPerm);
        return map;
    }


    /**
     * 查詢用戶的父节点
     * @param tenant
     * @return
     */
    @Override
    public List<String> tenantList(String tenant) {
        List<DBObject> list = tenantMainMongoDBCollection.findAll();
        List<String> result = new ArrayList<>();
        //存放id parent
        Map<String,Object> idAndParent = new HashMap<>();
        for (DBObject object: list) {
            Map<String, Object> map = object.toMap();
            idAndParent.put(map.get("_id").toString(), map.get("parent"));
        }
        result = genaten(tenant, idAndParent, result);
        logger.info(" ++++++ " + GsonUtils.getJsonFromObject(result));
        return result;
    }

    @Override
    public List<Map<String, Object>> queryZJY(LoginInfo loginInfo,
                                              String userName) {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("tenant", new ObjectId(loginInfo.getTenant()));
        basicDBObject.put("name", "质检员");
        Map<String,Object> role = tenantRoleMongoDBCollection.findOne(basicDBObject).toMap();
        Map<String,Object> query = new HashMap<>();
        query.put("roles.5d06159fd5ca6ee6ca4baf2a", role.get("_id"));
        if (StringUtil.isNotEmpty(userName)){
            Pattern pattern = Pattern.compile("^.*"+ userName+".*$", Pattern.CASE_INSENSITIVE);
            query.put("mobile", new BasicDBObject("$regex", pattern));
        }
        System.out.println(GsonUtils.getJsonFromObject(query));
        List<DBObject> list = tenantStaffMongoDBCollection.find(query);
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String,Object> temp = null;
        for (DBObject object : list) {
            Map<String,Object> map = object.toMap();
            temp = new HashMap<>();
            temp.put("id", map.get("_id").toString());
            temp.put("status", map.get("status").toString());
            temp.put("tenant", map.get("tenant").toString());
            temp.put("mobile", map.get("mobile").toString());
            temp.put("termType", map.get("termType").toString());
            temp.put("name", map.get("name").toString());
            temp.put("createdOn", map.get("createdOn").toString());
            temp.put("updatedOn", map.get("updatedOn").toString());
            resultList.add(temp);
        }
        return resultList;
    }

    private List<String> genaten(String tenant, Map<String,Object> parent, List<String> result) {
        Object obj = parent.get(tenant);
        result.add(tenant);
        if (obj != null) {
            return genaten(obj.toString(), parent, result);
        }
        return result;
    }



}

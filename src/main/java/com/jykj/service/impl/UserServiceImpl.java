package com.jykj.service.impl;

import com.jykj.entity.LoginInfo;
import com.jykj.mongo.MongoDBCollectionOperation;
import com.jykj.service.UserService;
import com.jykj.util.GsonUtils;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

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

    private List<String> genaten(String tenant, Map<String,Object> parent, List<String> result) {
        Object obj = parent.get(tenant);
        result.add(tenant);
        if (obj != null) {
            return genaten(obj.toString(), parent, result);
        }
        return result;
    }

}

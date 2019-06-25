package com.jykj.service.impl;

import com.jykj.mongo.MongoDBCollectionOperation;
import com.jykj.service.VehicleService;
import com.jykj.util.StringUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


@Service("vehicleService")
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private MongoDBCollectionOperation vehicleMainMongoDBCollection;

    @Override
    public List<DBObject> list(String vehicleNum,
                                    Integer pageIndex,
                                    Integer pageSize) {
        Map<String,Object> query = new HashMap<>();
        if (StringUtil.isNotEmpty(vehicleNum)) {
           query.put("plate.number",  vehicleNum);
        }
        List<DBObject> result = vehicleMainMongoDBCollection.findInPageAndSort(query, new BasicDBObject("createdOn", 1), (pageIndex-1)*pageSize, pageSize);
        return result;
    }
}

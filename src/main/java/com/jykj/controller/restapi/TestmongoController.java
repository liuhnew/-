package com.jykj.controller.restapi;

import com.jykj.entity.Result;
import com.jykj.mongo.MongoDBCollectionOperation;
import com.jykj.util.GsonUtils;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mongo")
public class TestmongoController {

    @Autowired
    private MongoDBCollectionOperation tenantStaffMongoDBCollection;

    @RequestMapping(value = "/mongo", method = RequestMethod.POST)
    public Result getMongDB(){
        List<DBObject> list = tenantStaffMongoDBCollection.findAll();
        System.out.println("-------" + GsonUtils.getJsonFromObject(list));
        return Result.createSuccess("success");
    }
}

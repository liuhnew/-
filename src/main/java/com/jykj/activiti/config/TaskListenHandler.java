package com.jykj.activiti.config;

import com.jykj.mongo.MongoDBCollectionOperation;
import com.mongodb.DBObject;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskListenHandler implements TaskListener {

    @Autowired
    private MongoDBCollectionOperation tenantRoleMongoDBCollection;

    private static final Logger log = LoggerFactory.getLogger(TaskListenHandler.class);

    @Override
    public void notify(DelegateTask delegateTask) {
        String eventName = delegateTask.getEventName();
        if ("create".equals(eventName)){
            String assignee = delegateTask.getAssignee();
            if (assignee.startsWith("roles")) {
                Map<String, Object> query = new HashMap<>();
                query.put("roles.5d06159ad5ca6ee6ca4baf29", new ObjectId(delegateTask.getAssignee()));
                List<DBObject> list = tenantRoleMongoDBCollection.find(query);
                List<String> users = new ArrayList<>();
                for (DBObject object : list) {
                    Map<String,Object> map = object.toMap();
                    users.add(map.get("_id").toString());
                }
                delegateTask.setVariable(delegateTask.getAssignee(), users);
            }else {
                delegateTask.getVariable("");
            }
            delegateTask.setDueDate(DateTime.now().plusDays(3).toDate());
        }else if ("complete".equals(eventName)) {
            log.info("----task complete");
        }
     }
}

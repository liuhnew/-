package com.jykj.activiti.config;

import com.jykj.config.Jurisdiction;
import com.jykj.mongo.MongoDBCollectionOperation;
import com.mongodb.DBObject;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class TaskListenHandler implements TaskListener {

    @Autowired
    private MongoDBCollectionOperation tenantRoleMongoDBCollection;

    private static final Logger log = LoggerFactory.getLogger(TaskListenHandler.class);

    @Override
    public void notify(DelegateTask delegateTask) {
        ConcurrentMap<String,String> concurrentMap = Jurisdiction.concurrentMap;
        concurrentMap.put("TASKID", delegateTask.getId());			//任务ID
        concurrentMap.put("YAssignee", delegateTask.getAssignee());	//默认待办人
     }
}

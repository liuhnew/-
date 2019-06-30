package com.jykj.service;

import java.util.Map;

public interface RunTaskService {

    void onoffTask(String id,
                   Integer status);

    void onoffAllTask(String id,
                      Integer status);

    Map<String,Object> listbyTaskId(String id);

}

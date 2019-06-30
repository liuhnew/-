package com.jykj.service;

public interface RunTaskService {

    void onoffTask(String id,
                   Integer status);

    void onoffAllTask(String id,
                      Integer status);

}

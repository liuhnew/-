package com.jykj.service;

import com.mongodb.DBObject;

import java.util.List;

public interface VehicleService {

    List<DBObject> list(String vehicleNum,
                        Integer pageIndex,
                        Integer pageSize);

}

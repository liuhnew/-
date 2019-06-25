package com.jykj.service;

import com.jykj.entity.LoginInfo;

import java.util.List;
import java.util.Map;

public interface UserService {

    Map<String,Object> listPermission(LoginInfo loginInfo);

    List<String> tenantList(String tenant);
}

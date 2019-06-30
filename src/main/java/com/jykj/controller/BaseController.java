package com.jykj.controller;

import com.jykj.config.SpringApplicationContextHolder;
import com.jykj.entity.LoginInfo;
import com.jykj.mongo.MongoDBCollectionOperation;
import com.jykj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BaseController {

    @Autowired
    private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

    public LoginInfo getUserInfo(HttpServletRequest request){
        MongoDBCollectionOperation tenantRoleMongoDBCollection =
                (MongoDBCollectionOperation) SpringApplicationContextHolder.getSpringBean("tenantRoleMongoDBCollection");
        LoginInfo loginInfo = null;
        try {
            loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");
            List<String> roles =  loginInfo.getRol();
            Map<String,Object> map = tenantRoleMongoDBCollection.findById(roles.get(0)).toMap();
            loginInfo.setRoleName(map.get("name").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginInfo;
   }

    public List<String> tenantList(HttpServletRequest request) {
        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");
        List<String> list = userService.tenantList(loginInfo.getTenant());
        return list;
    }
}

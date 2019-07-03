package com.jykj.util;

import com.jykj.config.Jurisdiction;
import com.jykj.config.SpringApplicationContextHolder;
import com.jykj.entity.LoginInfo;
import com.jykj.mongo.MongoDBCollectionOperation;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;
import java.util.Map;

@Component
public class JWTUtils{

    private static String publicKey;

    @Value("${public.key}")
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public static LoginInfo getClaimsFormToken(HttpSession session,
                                               String token,
                                               LoginInfo loginInfo) {
        JWTAuthOptions config = new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions().setAlgorithm("HS256").setPublicKey(publicKey).setSymmetric(true));
        JWTAuth provider = JWTAuth.create(Vertx.vertx(), config);
        provider.authenticate(new JsonObject().put("jwt", token), res ->  {
            if (res.succeeded()) {
                JsonObject object = res.result().principal();
                loginInfo.setAud(object.getString("aud"));
                loginInfo.setSub(object.getString("sub"));
                loginInfo.setIat(object.getInteger("iat"));
                JsonArray array = object.getJsonArray("rol");
                loginInfo.setRol(array.getList());
                System.out.println(GsonUtils.getJsonFromObject(loginInfo));
                MongoDBCollectionOperation tenantStaffMongoDBCollection =
                        (MongoDBCollectionOperation)SpringApplicationContextHolder.getSpringBean("tenantStaffMongoDBCollection");

                Map<String,Object> map = tenantStaffMongoDBCollection.findOne(loginInfo.getSub());
                loginInfo.setName(map.get("name").toString());
                loginInfo.setTenant(map.get("tenant").toString());
            }
        });
        return loginInfo;
    }

    public static void main(String[] args) {

    }
}

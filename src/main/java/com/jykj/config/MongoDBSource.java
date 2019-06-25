package com.jykj.config;

import com.jykj.mongo.MongoDBCollectionOperation;
import com.mongodb.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoDBSource {

    @Value("${db.mongodb.url}")
    private String mongoDURL;

    @Value("${db.mongodb.port}")
    private int port;

    @Value("${db.mongodb.username}")
    private String username;

    @Value("${db.mongodb.password}")
    private String password;

    @Bean("mongodbClient")
    public MongoClient mongodbClient() throws Exception{
        MongoClientOptions.Builder options = new MongoClientOptions.Builder();
        options.connectionsPerHost(10);
        options.connectTimeout(15000);
        options.maxWaitTime(5000);
        options.socketTimeout(0);
        options.threadsAllowedToBlockForConnectionMultiplier(10);
        options.writeConcern(WriteConcern.SAFE);

        List<MongoCredential> credentialsList = new ArrayList<>();
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(username, "admin", password.toCharArray());
        credentialsList.add(mongoCredential);

        MongoClientOptions mongoClientOptions = options.build();
        ServerAddress serverAddress = new ServerAddress(mongoDURL, port);
        MongoClient mongoClient = new MongoClient(serverAddress, credentialsList, mongoClientOptions);
        return mongoClient;
    }

    @Bean(name="tenantStaffMongoDBCollection")
    public MongoDBCollectionOperation tenantStaffMongoDBCollection(@Qualifier("mongodbClient") MongoClient mongoClient) {
        MongoDBCollectionOperation mongoDBCollectionOperation =
                new MongoDBCollectionOperation(mongoClient, "iss", "tenant.staff");
        return mongoDBCollectionOperation;
    }

    @Bean(name = "tenantRoleMongoDBCollection")
    public MongoDBCollectionOperation tenantRoleMongoDBCollection(@Qualifier("mongodbClient") MongoClient mongoClient) {
        MongoDBCollectionOperation mongoDBCollectionOperation =
                new MongoDBCollectionOperation(mongoClient, "iss", "tenant.role");
        return mongoDBCollectionOperation;
    }

    @Bean(name = "appAuthorityMongoDBCollection")
    public MongoDBCollectionOperation appAuthorityMongoDBCollection(@Qualifier("mongodbClient") MongoClient mongoClient) {
        MongoDBCollectionOperation mongoDBCollectionOperation =
                new MongoDBCollectionOperation(mongoClient, "iss", "app.authority");
        return mongoDBCollectionOperation;
    }

    @Bean(name = "appMainMongoDBCollection")
    public MongoDBCollectionOperation appMainMongoDBCollection(@Qualifier("mongodbClient") MongoClient mongoClient) {
        MongoDBCollectionOperation mongoDBCollectionOperation =
                new MongoDBCollectionOperation(mongoClient, "iss", "app.main");
        return mongoDBCollectionOperation;
    }

    @Bean(name = "tenantMainMongoDBCollection")
    public MongoDBCollectionOperation tenantMainMongoDBCollection(@Qualifier("mongodbClient") MongoClient mongoClient) {
        MongoDBCollectionOperation mongoDBCollectionOperation =
                new MongoDBCollectionOperation(mongoClient, "iss", "tenant.main");
        return mongoDBCollectionOperation;
    }

    @Bean(name = "vehicleMainMongoDBCollection")
    public MongoDBCollectionOperation vehicleMainMongoDBCollection(@Qualifier("mongodbClient") MongoClient mongoClient) {
        MongoDBCollectionOperation mongoDBCollectionOperation =
                new MongoDBCollectionOperation(mongoClient, "iss", "vehicle.main");
        return mongoDBCollectionOperation;
    }

}

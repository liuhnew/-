package com.jykj.config;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Jurisdiction {

    public static ConcurrentMap<String,String> concurrentMap = new ConcurrentHashMap<>();

    public static String getConcurrentMap(String key) {
        return concurrentMap.get(key);
    }

}

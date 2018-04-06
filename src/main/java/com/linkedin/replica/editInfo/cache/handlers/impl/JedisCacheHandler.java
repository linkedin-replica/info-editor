package com.linkedin.replica.editInfo.cache.handlers.impl;

import com.google.gson.Gson;
import com.linkedin.replica.editInfo.cache.CacheConnection;
import com.linkedin.replica.editInfo.config.Configuration;
import com.linkedin.replica.editInfo.cache.handlers.CacheHandler;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class JedisCacheHandler implements EditInfoHandler {

    private static JedisPool cachePool;
    private Configuration configuration = Configuration.getInstance();
    private int databaseIndexusers = Integer.parseInt(configuration.getRedisConfigProp("cache.users.index"));
    private int databaseIndexcompanies = Integer.parseInt(configuration.getRedisConfigProp("cache.companies.index"));
    private Gson gson;

    public JedisCacheHandler() throws IOException, IOException {
        cachePool = CacheConnection.getInstance().getRedisPool();
        gson = CacheConnection.getGson();
    }



}

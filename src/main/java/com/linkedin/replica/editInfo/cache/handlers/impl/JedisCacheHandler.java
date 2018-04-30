package com.linkedin.replica.editInfo.cache.handlers.impl;

import com.google.gson.Gson;
import com.linkedin.replica.editInfo.cache.CacheConnection;
import com.linkedin.replica.editInfo.cache.handlers.CacheEditInfoHandler;
import com.linkedin.replica.editInfo.config.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class JedisCacheHandler implements CacheEditInfoHandler {

    private static JedisPool cachePool;
    private Configuration configuration = Configuration.getInstance();
    private int databaseIndexusers = Integer.parseInt(configuration.getRedisConfigProp("cache.users.index"));
    private int databaseIndexcompanies = Integer.parseInt(configuration.getRedisConfigProp("cache.companies.index"));
    private Gson gson;

    public JedisCacheHandler() throws IOException, IOException {
        cachePool = CacheConnection.getInstance().getRedisPool();
        gson = CacheConnection.getGson();
    }

    @Override
    public void saveUsersInCache(String userId, Object user) throws IOException {

        try {
            Jedis cacheInstance = cachePool.getResource();
            cacheInstance.select(databaseIndexusers);
            Pipeline pipeline = cacheInstance.pipelined();
            Class userClass = user.getClass();
            Field [] fields = userClass.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                Object fieldValue;
                try {
                    field.setAccessible(true);
                    fieldValue = field.get(user);
                    pipeline.hset(userId, fieldName, gson.toJson(fieldValue));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            pipeline.sync();
            pipeline.close();
            cacheInstance.close();
        } catch (JedisException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public Object getUserFromCache(String key, Class<?> tClass)  {

        try {
            Jedis cacheInstance = cachePool.getResource();
            cacheInstance.select(Integer.parseInt(configuration.getRedisConfigProp("cache.users.index")));
            if(!cacheInstance.exists(key))
                return null;
            Class userClass = tClass;
            Object user = null;
            try {
                user = userClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Field [] fields = userClass.getDeclaredFields();
            for(int i = 0; i < fields.length; ++i) {
                String fieldName = fields[i].getName();
                String jsonValue = cacheInstance.hget(key, fieldName);
                Object objectValue = gson.fromJson(jsonValue, fields[i].getType());
                try {
                    userClass.getField(fieldName).set(user, objectValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
            cacheInstance.close();
            return user;
        } catch(JedisException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteUserFromCache(String key) {
        try {
            Jedis cacheInstance = cachePool.getResource();
            cacheInstance.select(databaseIndexusers);
            if(!cacheInstance.exists(key))
                return;
            String [] fieldNames = cacheInstance.hgetAll(key).keySet().toArray(new String[cacheInstance.hgetAll(key).keySet().size()]);
            cacheInstance.hdel(key, fieldNames);
        } catch(JedisException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void editUserCache(String key, LinkedHashMap<String, String> args) {
        try {
            Jedis cacheInstance = cachePool.getResource();
            cacheInstance.select(databaseIndexusers);
            if(!cacheInstance.exists(key)) {
                return;
            }
            for(Map.Entry<String, String> entry : args.entrySet()) {
                cacheInstance.hset(key, entry.getKey(), entry.getValue());
            }
        } catch(JedisException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void saveCompanyInCache(String companyId, Object company) throws IOException {

        try (Jedis cacheInstance = cachePool.getResource()){

            cacheInstance.select(databaseIndexcompanies);
            Pipeline pipeline = cacheInstance.pipelined();
            Class companyClass = company.getClass();
            Field [] fields = companyClass.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                Object fieldValue;
                try {
                    field.setAccessible(true);
                    fieldValue = field.get(company);
                    pipeline.hset(companyId, fieldName, gson.toJson(fieldValue));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            pipeline.sync();
            pipeline.close();
        } catch (JedisException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public Object getCompanyFromCache(String key, Class<?> tClass) {

        try {
            Jedis cacheInstance = cachePool.getResource();
            cacheInstance.select(Integer.parseInt(configuration.getRedisConfigProp("cache.companies.index")));
            if(!cacheInstance.exists(key))
                return null;
            Class companyClass = tClass;
            Object company = null;
            try {
                company = companyClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Field [] fields = companyClass.getDeclaredFields();
            for(int i = 0; i < fields.length; ++i) {
                String fieldName = fields[i].getName();
                String jsonValue = cacheInstance.hget(key, fieldName);
                Object objectValue = gson.fromJson(jsonValue, fields[i].getType());
                try {
                    companyClass.getField(fieldName).set(company, objectValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
            cacheInstance.close();
            return company;
        } catch(JedisException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteCompanies(String key) {
        try {
            Jedis cacheInstance = cachePool.getResource();
            cacheInstance.select(databaseIndexcompanies);
            if(!cacheInstance.exists(key))
                return;
            String [] fieldNames = cacheInstance.hgetAll(key).keySet().toArray(new String[cacheInstance.hgetAll(key).keySet().size()]);
            cacheInstance.hdel(key, fieldNames);
        } catch(JedisException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void editcompanyFromCache(String key, LinkedHashMap<String, String> args) {
        try {
            Jedis cacheInstance = cachePool.getResource();
            cacheInstance.select(databaseIndexcompanies);
            if(!cacheInstance.exists(key)) {
                return;
            }
            for(Map.Entry<String, String> entry : args.entrySet()) {
                cacheInstance.hset(key, entry.getKey(), entry.getValue());
            }
        } catch(JedisException e) {
            e.printStackTrace();
        }
    }


}

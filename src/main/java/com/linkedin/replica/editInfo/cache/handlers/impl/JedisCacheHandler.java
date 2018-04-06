package com.linkedin.replica.editInfo.cache.handlers.impl;

import com.google.gson.Gson;
import com.linkedin.replica.editInfo.cache.CacheConnection;
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
    public void saveUsersInCache(String[] usersIds, Object users) throws IOException {

        try {
            Jedis cacheInstance = cachePool.getResource();
            cacheInstance.select(databaseIndexusers);
            Pipeline pipeline = cacheInstance.pipelined();
            ArrayList<Object> usersList = (ArrayList<Object>) users;
            for(int j = 0; j < usersIds.length; ++j) {
                String key = usersIds[j];
                Object user = usersList.get(j);
                Class userClass = user.getClass();
                Field [] fields = userClass.getDeclaredFields();
                for(int i = 0; i < fields.length; ++i) {
                    String fieldName = fields[i].getName();
                    Object fieldValue;
                    try {
                        fieldValue = fields[i].get(user);
                        pipeline.hset(key, fieldName, gson.toJson(fieldValue));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
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
    public Object getUserFromCache(String key, Class<?> tClass) throws IOException {

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
    public void editUserFromCache(String key, LinkedHashMap<String, String> args) {
        try {
            Jedis cacheInstance = cachePool.getResource();
            cacheInstance.select(databaseIndexusers);
            if(!cacheInstance.exists(key))
                return;
            for(Map.Entry<String, String> entry : args.entrySet()) {
                cacheInstance.hset(key, entry.getKey(), entry.getValue());
            }
        } catch(JedisException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveCompanyInCache(String[] companiesIds, Object companies) throws IOException {

        try {
            Jedis cacheInstance = cachePool.getResource();
            cacheInstance.select(databaseIndexcompanies);
            Pipeline pipeline = cacheInstance.pipelined();
            ArrayList<Object> company = (ArrayList<Object>) companies;
            for(int j = 0; j < companiesIds.length; ++j) {
                String key = companiesIds[j];
                Object job = company.get(j);
                Class companyClass = company.getClass();
                Field [] fields = companyClass.getDeclaredFields();
                for(int i = 0; i < fields.length; ++i) {
                    String fieldName = fields[i].getName();
                    Object fieldValue;
                    try {
                        fieldValue = fields[i].get(job);
                        pipeline.hset(key, fieldName, gson.toJson(fieldValue));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
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
    public Object getCompanyFromCache(String key, Class<?> tClass) throws IOException {

        try {
            Jedis cacheInstance = cachePool.getResource();
            cacheInstance.select(Integer.parseInt(configuration.getRedisConfigProp("cache.companies.index")));
            if(!cacheInstance.exists(key))
                return null;
            Class companyClass = tClass;
            Object user = null;
            try {
                user = companyClass.newInstance();
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
                    companyClass.getField(fieldName).set(user, objectValue);
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
            if(!cacheInstance.exists(key))
                return;
            for(Map.Entry<String, String> entry : args.entrySet()) {
                cacheInstance.hset(key, entry.getKey(), entry.getValue());
            }
        } catch(JedisException e) {
            e.printStackTrace();
        }
    }


}

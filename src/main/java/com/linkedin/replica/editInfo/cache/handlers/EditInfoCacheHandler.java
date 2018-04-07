package com.linkedin.replica.editInfo.cache.handlers;
import com.linkedin.replica.editInfo.cache.handlers.CacheHandler;
import com.linkedin.replica.editInfo.models.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public interface EditInfoCacheHandler extends CacheHandler{


    void saveUsersInCache(String[] usersIds, Object users) throws IOException;

    Object getUserFromCache(String key, Class<?> tClass) throws IOException;

    void deleteUserFromCache(String key);



    void editUserCache(String key, LinkedHashMap<String, String> args);

    void saveCompanyInCache(String[] companiesIds, Object companies) throws IOException;

    Object getCompanyFromCache(String key, Class<?> tClass) throws IOException;

    void deleteCompany(String key);

    void editCompanyFromCache(String key, LinkedHashMap<String, String> args);
}

package com.linkedin.replica.editInfo.cache.handlers;
import com.linkedin.replica.editInfo.cache.handlers.CacheHandler;
import com.linkedin.replica.editInfo.models.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public interface CacheEditInfoHandler extends CacheHandler{


    void saveUsersInCache(String[] usersIds, Object users) throws IOException;

    Object getUserFromCache(String key, Class<?> tClass) throws IOException;

    void deleteUserFromCache(String key);



    void editUserCache(String key, LinkedHashMap<String, String> args);

    void saveCompanyInCache(String[] companiesIds, Object companies) throws IOException;

    Object getCompanyFromCache(String key, Class<?> tClass) throws IOException;

    void deleteCompanies(String key);

    void editcompanyFromCache(String key, LinkedHashMap<String, String> args);
}

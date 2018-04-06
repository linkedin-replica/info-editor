package com.linkedin.replica.editInfo.cache.handlers.impl;
import com.linkedin.replica.editInfo.cache.handlers.CacheHandler;
import com.linkedin.replica.editInfo.models.*;

import java.io.IOException;
import java.util.List;

public interface EditInfoHandler extends CacheHandler{


    void saveUsersInCache(String[] usersIds, Object users) throws IOException;

    Object getUserFromCache(String key, Class<?> tClass) throws IOException;

    void deleteUserFromCache(String key);

    void saveCompanyInCache(String[] companiesIds, Object companies) throws IOException;

    Object getCompanyFromCache(String key, Class<?> tClass) throws IOException;

    void deleteCompanies(String key);
}

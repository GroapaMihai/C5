package client_app.api.em;

import java.util.List;
import java.util.Map;

public interface EntityManager {
    <T> T findById(Class<T> entityClass, Integer id);
    Integer getNextIdVal(String tableName, String columnIdName);
    <T> Object insert(T entity);
    <T> List<T> findAll(Class<T> entityClass);
    <T> T update(T entity);
    void delete(Object entity);
    <T> List<T> findMatchingEntities(Class<T> entityClass, T patternEntity);
}

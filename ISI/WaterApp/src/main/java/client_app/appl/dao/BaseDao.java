package client_app.appl.dao;

import java.util.List;

public interface BaseDao<T> {
    T findById(Integer id);
    Integer getNextIdVal(String columnIdName);
    T insert(T entity);
    List<T> findAll();
    T update(T entity);
    void delete(T entity);
    List<T> findMatchingEntities(T patternEntity);
}

package client_app.appl.dao;

import client_app.api.em.EntityManager;
import client_app.api.em.EntityManagerImpl;
import client_app.api.em.EntityUtils;
import client_app.appl.domain.entities.User;

import java.util.List;

public class UserDao implements BaseDao<User> {
    private static UserDao instance = null;
    private EntityManager em;

    private UserDao() {
        em = new EntityManagerImpl();
    }

    public static UserDao getInstance() {
        if (instance == null) {
        	instance = new UserDao();
        }

        return instance;
    }

    @Override
    public User findById(Integer id) {
        return em.findById(User.class, id);
    }

    @Override
    public Integer getNextIdVal(String columnIdName) {
        return em.getNextIdVal(EntityUtils.getTableName(User.class), columnIdName);
    }

    @Override
    public User insert(User user) {
        return (User) em.insert(user);
    }

    @Override
    public List<User> findAll() {
        return em.findAll(User.class);
    }

    @Override
    public User update(User user) {
        return em.update(user);
    }

    @Override
    public void delete(User user) {
    	  em.delete(user);
    }
    
    @Override
    public List<User> findMatchingEntities(User patternUser) {
    	return em.findMatchingEntities(User.class, patternUser);
    }
}

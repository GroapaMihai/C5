package client_app.appl.dao;

import java.util.List;

import client_app.api.em.EntityManager;
import client_app.api.em.EntityManagerImpl;
import client_app.appl.domain.entities.SeverityLevel;
import client_app.api.em.EntityUtils;

public class SeverityLevelDao implements BaseDao<SeverityLevel> {

	private static SeverityLevelDao instance = null;
    private EntityManager em;

    private SeverityLevelDao() {
        em = new EntityManagerImpl();
    }

    public static SeverityLevelDao getInstance() {
        if (instance == null) {
        	instance = new SeverityLevelDao();
        }

        return instance;
    }

	@Override
	public SeverityLevel findById(Integer id) {
		return em.findById(SeverityLevel.class, id);
	}

	@Override
	public Integer getNextIdVal(String columnIdName) {
		return em.getNextIdVal(EntityUtils.getTableName(SeverityLevel.class), columnIdName);
	}

	@Override
	public SeverityLevel insert(SeverityLevel entity) {
		return (SeverityLevel) em.insert(entity);
	}

	@Override
	public List<SeverityLevel> findAll() {
		 return em.findAll(SeverityLevel.class);
	}

	@Override
	public SeverityLevel update(SeverityLevel entity) {
		return em.update(entity);
	}

	@Override
	public void delete(SeverityLevel entity) {
		  em.delete(entity);
	}

	@Override
	public List<SeverityLevel> findMatchingEntities(SeverityLevel patternEntity) {
		return em.findMatchingEntities(SeverityLevel.class, patternEntity);
	}

}

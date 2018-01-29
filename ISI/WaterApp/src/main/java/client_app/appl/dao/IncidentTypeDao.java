package client_app.appl.dao;

import java.util.List;

import client_app.api.em.EntityManager;
import client_app.api.em.EntityManagerImpl;
import client_app.api.em.EntityUtils;
import client_app.appl.domain.entities.IncidentType;

public class IncidentTypeDao implements BaseDao<IncidentType> {
	private static IncidentTypeDao instance = null;
    private EntityManager em;

	private IncidentTypeDao() {
        em = new EntityManagerImpl();
	}

	public static IncidentTypeDao getInstance() {
		if (instance == null) {
			instance = new IncidentTypeDao();
		}
		
		return instance;
	}

	@Override
	public IncidentType findById(Integer id) {
		return em.findById(IncidentType.class, id);
	}

	@Override
	public Integer getNextIdVal(String columnIdName) {
		return em.getNextIdVal(EntityUtils.getTableName(IncidentType.class), columnIdName);
	}

	@Override
	public IncidentType insert(IncidentType entity) {
		return (IncidentType) em.insert(entity);
	}

	@Override
	public List<IncidentType> findAll() {
		return em.findAll(IncidentType.class);
	}

	@Override
	public IncidentType update(IncidentType entity) {
		return em.update(entity);
	}

	@Override
	public void delete(IncidentType entity) {
		em.delete(entity);
	}

	@Override
	public List<IncidentType> findMatchingEntities(IncidentType patternEntity) {
		return em.findMatchingEntities(IncidentType.class, patternEntity);
	}
}

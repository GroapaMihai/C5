package client_app.appl.dao;

import java.util.List;

import client_app.api.em.EntityManager;
import client_app.api.em.EntityManagerImpl;
import client_app.api.em.EntityUtils;
import client_app.appl.domain.entities.Incident;

public class IncidentDao implements BaseDao<Incident> {

	private static IncidentDao instance = null;
    private EntityManager em;

    private IncidentDao() {
        em = new EntityManagerImpl();
    }

    public static IncidentDao getInstance() {
        if (instance == null) {
        	instance = new IncidentDao();
        }

        return instance;
    }

	@Override
	public Incident findById(Integer id) {
		return em.findById(Incident.class, id);
	}

	@Override
	public Integer getNextIdVal(String columnIdName) {
		return em.getNextIdVal(EntityUtils.getTableName(Incident.class), columnIdName);
	}

	@Override
	public Incident insert(Incident entity) {
		return (Incident) em.insert(entity);
	}

	@Override
	public List<Incident> findAll() {
		 return em.findAll(Incident.class);
	}

	@Override
	public Incident update(Incident entity) {
		return em.update(entity);
	}

	@Override
	public void delete(Incident entity) {
		  em.delete(entity);
	}

	@Override
	public List<Incident> findMatchingEntities(Incident patternEntity) {
		return em.findMatchingEntities(Incident.class, patternEntity);
	}
}
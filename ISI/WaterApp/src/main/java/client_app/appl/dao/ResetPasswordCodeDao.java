package client_app.appl.dao;

import java.util.List;

import client_app.api.em.EntityManager;
import client_app.api.em.EntityManagerImpl;
import client_app.api.em.EntityUtils;
import client_app.appl.domain.entities.ResetPasswordCode;

public class ResetPasswordCodeDao implements BaseDao<ResetPasswordCode> {
    private static ResetPasswordCodeDao instance = null;
    private EntityManager em;

    private ResetPasswordCodeDao() {
        em = new EntityManagerImpl();
    }

    public static ResetPasswordCodeDao getInstance() {
        if (instance == null) {
        	instance = new ResetPasswordCodeDao();
        }

        return instance;
    }

	@Override
	public ResetPasswordCode findById(Integer id) {
		return em.findById(ResetPasswordCode.class, id);
	}

	@Override
	public Integer getNextIdVal(String columnIdName) {
        return em.getNextIdVal(EntityUtils.getTableName(ResetPasswordCode.class), columnIdName);
	}

	@Override
	public ResetPasswordCode insert(ResetPasswordCode entity) {
		return (ResetPasswordCode) em.insert(entity);
	}

	@Override
	public List<ResetPasswordCode> findAll() {
		return em.findAll(ResetPasswordCode.class);
	}

	@Override
	public ResetPasswordCode update(ResetPasswordCode entity) {
		return em.update(entity);
	}

	@Override
	public void delete(ResetPasswordCode entity) {
		em.delete(entity);
	}

	@Override
	public List<ResetPasswordCode> findMatchingEntities(ResetPasswordCode patternEntity) {
		return em.findMatchingEntities(ResetPasswordCode.class, patternEntity);
	}

}

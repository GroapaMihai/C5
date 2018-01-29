package client_app.appl.dao;

import java.util.List;

import client_app.api.em.EntityManager;
import client_app.api.em.EntityManagerImpl;
import client_app.api.em.EntityUtils;
import client_app.appl.domain.entities.AccountType;

public class AccountTypeDao implements BaseDao<AccountType> {
	private static AccountTypeDao instance = null;
    private EntityManager em;
	
	private AccountTypeDao() {
        em = new EntityManagerImpl();
	}

	public static AccountTypeDao getInstance() {
		if (instance == null) {
			instance = new AccountTypeDao();
		}
		
		return instance;
	}

	@Override
	public AccountType findById(Integer id) {
		return em.findById(AccountType.class, id);
	}

	@Override
	public Integer getNextIdVal(String columnIdName) {
		return em.getNextIdVal(EntityUtils.getTableName(AccountType.class), columnIdName);
	}

	@Override
	public AccountType insert(AccountType entity) {
		return (AccountType) em.insert(entity);
	}

	@Override
	public List<AccountType> findAll() {
		return em.findAll(AccountType.class);
	}

	@Override
	public AccountType update(AccountType entity) {
		return em.update(entity);
	}

	@Override
	public void delete(AccountType entity) {
		em.delete(entity);
	}

	@Override
	public List<AccountType> findMatchingEntities(AccountType patternEntity) {
		return em.findMatchingEntities(AccountType.class, patternEntity);
	}

}

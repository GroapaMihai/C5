package ro.transport.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.transport.demo.dao.AccountTypeDao;
import ro.transport.demo.domain.AccountType;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountTypeServiceImpl implements AccountTypeService {
    @Autowired
    private AccountTypeDao accountTypeDao;

    @Override
    public AccountType find(Long id) {
        return accountTypeDao.find(id);
    }

    @Override
    public List<AccountType> findAll() {
        return accountTypeDao.findAll();
    }

    @Override
    public AccountType findByName(String name) {
        return accountTypeDao.findByName(name);
    }
}

package ro.transport.demo.services;

import ro.transport.demo.domain.AccountType;

import java.util.List;

public interface AccountTypeService {
    AccountType find(Long id);

    List<AccountType> findAll();

    AccountType findByName(String name);
}

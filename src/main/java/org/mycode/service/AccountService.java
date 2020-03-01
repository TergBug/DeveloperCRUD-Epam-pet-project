package org.mycode.service;

import org.apache.log4j.Logger;
import org.mycode.model.Account;
import org.mycode.repository.AccountRepository;
import org.mycode.service.visitors.ServiceVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("accountService")
public class AccountService implements Serviceable {
    private static final Logger log = Logger.getLogger(AccountService.class);
    private AccountRepository currentRepo;

    @Autowired
    public AccountService(AccountRepository currentRepo) {
        this.currentRepo = currentRepo;
    }

    public void create(Account model) {
        currentRepo.create(model);
        log.debug("Service->Create");
    }

    public Account getById(Long readID) {
        Account account = currentRepo.getById(readID);
        log.debug("Service->Read");
        return account;
    }

    public void update(Account updatedModel) {
        currentRepo.update(updatedModel);
        log.debug("Service->Update");
    }

    public void delete(Long deletedEntry) {
        currentRepo.delete(deletedEntry);
        log.debug("Service->Delete");
    }

    public List<Account> getAll() {
        List<Account> accounts = currentRepo.getAll();
        log.debug("Service->Get all");
        return accounts;
    }

    @Override
    public void doService(ServiceVisitor visitor) {
        visitor.visitAccountService(this);
    }
}

package org.mycode.service;

import org.apache.log4j.Logger;
import org.mycode.model.Account;
import org.mycode.repository.AccountRepository;
import org.mycode.service.visitors.ServiceVisitor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements Serviceable {
    private static final Logger log = Logger.getLogger(AccountService.class);
    private AccountRepository currentRepo;
    public AccountService(AccountRepository currentRepo) {
        this.currentRepo = currentRepo;
    }
    public void create(Account model) throws RuntimeException{
        currentRepo.create(model);
        log.debug("Service->Create");
    }
    public Account getById(Long readID) throws RuntimeException{
        Account account = currentRepo.getById(readID);
        log.debug("Service->Read");
        return account;
    }
    public void update(Account updatedModel) throws RuntimeException{
        currentRepo.update(updatedModel);
        log.debug("Service->Update");
    }
    public void delete(Long deletedEntry) throws RuntimeException{
        currentRepo.delete(deletedEntry);
        log.debug("Service->Delete");
    }
    public List<Account> getAll() throws RuntimeException{
        List<Account> accounts = currentRepo.getAll();
        log.debug("Service->Get all");
        return accounts;
    }
    @Override
    public void doService(ServiceVisitor visitor) {
        visitor.visitAccountService(this);
    }
}

package org.mycode.service;

import org.apache.log4j.Logger;
import org.mycode.exceptions.RepoStorageException;
import org.mycode.model.Account;
import org.mycode.repository.AccountRepository;
import org.mycode.repository.jdbc.JDBCAccountRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class AccountService {
    private static final Logger log = Logger.getLogger(AccountService.class);
    private AccountRepository currentRepo;
    public AccountService() throws RepoStorageException {
        this.currentRepo = new JDBCAccountRepositoryImpl();
    }
    public void create(Account model) throws Exception{
        currentRepo.create(model);
        log.debug("Service->Create");
    }
    public Account getById(Long readID) throws Exception{
        Account account = currentRepo.getById(readID);
        log.debug("Service->Read");
        return account;
    }
    public void update(Account updatedModel) throws Exception{
        currentRepo.update(updatedModel);
        log.debug("Service->Update");
    }
    public void delete(Long deletedEntry) throws Exception{
        currentRepo.delete(deletedEntry);
        log.debug("Service->Delete");
    }
    public List<Account> getAll() throws Exception{
        List<Account> accounts = currentRepo.getAll();
        log.debug("Service->Get all");
        return accounts;
    }
}

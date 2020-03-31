package org.mycode.service.impl;

import org.apache.log4j.Logger;
import org.mycode.assembler.AccountAssembler;
import org.mycode.dto.AccountDto;
import org.mycode.repository.AccountRepository;
import org.mycode.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    private static final Logger LOG = Logger.getLogger(AccountServiceImpl.class);
    private AccountRepository currentRepo;
    private AccountAssembler accountAssembler;

    @Autowired
    public AccountServiceImpl(AccountRepository currentRepo, AccountAssembler accountAssembler) {
        this.currentRepo = currentRepo;
        this.accountAssembler = accountAssembler;
    }

    public void create(AccountDto model) {
        currentRepo.create(accountAssembler.assemble(model));
        LOG.debug("Service->Create");
    }

    public AccountDto getById(UUID readID) {
        AccountDto account = accountAssembler.assemble(currentRepo.getById(readID));
        LOG.debug("Service->Read");
        return account;
    }

    public void update(AccountDto updatedModel) {
        currentRepo.update(accountAssembler.assemble(updatedModel));
        LOG.debug("Service->Update");
    }

    public void delete(UUID deletedEntry) {
        currentRepo.delete(deletedEntry);
        LOG.debug("Service->Delete");
    }

    public List<AccountDto> getAll() {
        List<AccountDto> accounts = currentRepo.getAll().stream().map(el -> accountAssembler.assemble(el))
                .collect(Collectors.toList());
        LOG.debug("Service->Get all");
        return accounts;
    }
}

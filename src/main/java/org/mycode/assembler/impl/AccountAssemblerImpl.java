package org.mycode.assembler.impl;

import org.mycode.assembler.AccountAssembler;
import org.mycode.dto.AccountDto;
import org.mycode.model.Account;
import org.mycode.model.enums.AccountStatus;
import org.springframework.stereotype.Component;

@Component
public class AccountAssemblerImpl implements AccountAssembler {
    @Override
    public AccountDto assemble(Account accountEntity) {
        return new AccountDto(accountEntity.getId(), accountEntity.getName(), accountEntity.getStatus().name());
    }

    @Override
    public Account assemble(AccountDto account) {
        return new Account(account.getId(), account.getName(), AccountStatus.valueOf(account.getStatus()));
    }
}

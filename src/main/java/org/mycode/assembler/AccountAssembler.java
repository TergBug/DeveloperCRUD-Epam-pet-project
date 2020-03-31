package org.mycode.assembler;

import org.mycode.dto.AccountDto;
import org.mycode.model.Account;

public interface AccountAssembler {
    AccountDto assemble(Account accountEntity);

    Account assemble(AccountDto account);
}

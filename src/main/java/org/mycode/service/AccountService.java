package org.mycode.service;

import org.mycode.dto.AccountDto;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    void create(AccountDto model);

    AccountDto getById(UUID readID);

    void update(AccountDto updatedModel);

    void delete(UUID deletedEntry);

    List<AccountDto> getAll();
}

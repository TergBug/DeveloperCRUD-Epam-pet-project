package org.mycode.service;

import org.junit.Test;
import org.mycode.model.Account;
import org.mycode.model.AccountStatus;
import org.mycode.repository.AccountRepository;

import static org.mockito.Mockito.*;

public class AccountServiceTest {
    private static final Account CREATE_ACCOUNT = new Account(5L, "Jog", AccountStatus.ACTIVE);
    private static final Account UPDATE_ACCOUNT = new Account(5L, "Pof", AccountStatus.BANNED);
    private AccountRepository mockedRepo = mock(AccountRepository.class);
    private AccountService sut = new AccountService(mockedRepo);

    @Test
    public void shouldInvokeCreateInRepo() {
        sut.create(CREATE_ACCOUNT);
        verify(mockedRepo, times(1)).create(CREATE_ACCOUNT);
    }

    @Test
    public void shouldInvokeGetByIdInRepo() {
        sut.getById(1L);
        verify(mockedRepo, times(1)).getById(1L);
    }

    @Test
    public void shouldInvokeUpdateInRepo() {
        sut.update(UPDATE_ACCOUNT);
        verify(mockedRepo, times(1)).update(UPDATE_ACCOUNT);
    }

    @Test
    public void shouldInvokeDeleteInRepo() {
        sut.delete(2L);
        verify(mockedRepo, times(1)).delete(2L);
    }

    @Test
    public void shouldInvokeGetAllInRepo() {
        sut.getAll();
        verify(mockedRepo, times(1)).getAll();
    }
}
package org.mycode.service;

import org.junit.Test;
import org.mycode.assembler.AccountAssembler;
import org.mycode.dto.AccountDto;
import org.mycode.repository.AccountRepository;
import org.mycode.service.impl.AccountServiceImpl;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class AccountServiceTest {
    private static final UUID ID_FOR_CREATE = UUID.randomUUID();
    private static final UUID ID_FOR_GET = UUID.fromString("77845a88-c171-4cd0-a73a-3045bdbe4f30");
    private static final UUID ID_FOR_DELETE = UUID.fromString("9743c5ef-4e76-4fc1-9b1b-a88f352cb22e");
    private static final AccountDto CREATE_ACCOUNT = new AccountDto(ID_FOR_CREATE, "Jog", "ACTIVE");
    private static final AccountDto UPDATE_ACCOUNT = new AccountDto(ID_FOR_CREATE, "Pof", "BANNED");
    private AccountRepository mockedRepo = mock(AccountRepository.class);
    private AccountAssembler accountAssembler = mock(AccountAssembler.class);
    private AccountServiceImpl sut = new AccountServiceImpl(mockedRepo, accountAssembler);

    @Test
    public void shouldInvokeCreateInRepo() {
        sut.create(CREATE_ACCOUNT);
        verify(mockedRepo, times(1)).create(accountAssembler.assemble(CREATE_ACCOUNT));
    }

    @Test
    public void shouldInvokeGetByIdInRepo() {
        sut.getById(ID_FOR_GET);
        verify(mockedRepo, times(1)).getById(ID_FOR_GET);
    }

    @Test
    public void shouldInvokeUpdateInRepo() {
        sut.update(UPDATE_ACCOUNT);
        verify(mockedRepo, times(1)).update(accountAssembler.assemble(UPDATE_ACCOUNT));
    }

    @Test
    public void shouldInvokeDeleteInRepo() {
        sut.delete(ID_FOR_DELETE);
        verify(mockedRepo, times(1)).delete(ID_FOR_DELETE);
    }

    @Test
    public void shouldInvokeGetAllInRepo() {
        sut.getAll();
        verify(mockedRepo, times(1)).getAll();
    }
}
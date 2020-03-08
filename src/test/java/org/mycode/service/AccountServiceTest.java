package org.mycode.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mycode.model.Account;
import org.mycode.model.AccountStatus;
import org.mycode.repository.AccountRepository;
import org.mycode.testutil.TestUtils;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @InjectMocks
    private static AccountService testedAccountService;
    @Mock
    private AccountRepository currentRepo;
    private Account createAccount = new Account(5L, "Jog", AccountStatus.ACTIVE);
    private Account updateAccount = new Account(5L, "Pof", AccountStatus.BANNED);

    @BeforeClass
    public static void connect() {
        TestUtils.switchConfigToTestMode();
        testedAccountService = TestUtils.getApplicationContext().getBean(AccountService.class);
    }

    @AfterClass
    public static void backProperty() {
        TestUtils.switchConfigToWorkMode();
    }

    @Test
    public void shouldInvokeCreateInRepo() {
        testedAccountService.create(createAccount);
        verify(currentRepo, times(1)).create(createAccount);
    }

    @Test
    public void shouldInvokeGetByIdInRepo() {
        testedAccountService.getById(1L);
        verify(currentRepo, times(1)).getById(1L);
    }

    @Test
    public void shouldInvokeUpdateInRepo() {
        testedAccountService.update(updateAccount);
        verify(currentRepo, times(1)).update(updateAccount);
    }

    @Test
    public void shouldInvokeDeleteInRepo() {
        testedAccountService.delete(2L);
        verify(currentRepo, times(1)).delete(2L);
    }

    @Test
    public void shouldInvokeGetAllInRepo() {
        testedAccountService.getAll();
        verify(currentRepo, times(1)).getAll();
    }
}
package org.mycode.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mycode.exceptions.RepoStorageException;
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
        try {
            testedAccountService = TestUtils.getApplicationContext().getBean(AccountService.class);
        } catch (RepoStorageException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void backProperty() {
        TestUtils.switchConfigToWorkMode();
    }

    @Test
    public void shouldInvokeCreateInRepo() {
        try {
            testedAccountService.create(createAccount);
            verify(currentRepo, times(1)).create(createAccount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldInvokeGetByIdInRepo() {
        try {
            testedAccountService.getById(1L);
            verify(currentRepo, times(1)).getById(1L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldInvokeUpdateInRepo() {
        try {
            testedAccountService.update(updateAccount);
            verify(currentRepo, times(1)).update(updateAccount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldInvokeDeleteInRepo() {
        try {
            testedAccountService.delete(2L);
            verify(currentRepo, times(1)).delete(2L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldInvokeGetAllInRepo() {
        try {
            testedAccountService.getAll();
            verify(currentRepo, times(1)).getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
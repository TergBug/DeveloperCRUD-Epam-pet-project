package org.mycode.service;

import org.junit.Test;
import org.mycode.model.Account;
import org.mycode.model.Developer;
import org.mycode.repository.DeveloperRepository;

import java.util.HashSet;

import static org.mockito.Mockito.*;

public class DeveloperServiceTest {
    private static final Developer CREATE_DEVELOPER = new Developer(5L, "Joe", "Tred", new HashSet<>(), new Account(2L));
    private static final Developer UPDATE_DEVELOPER = new Developer(5L, "Jony", "Fedorov", new HashSet<>(), new Account(1L));
    private DeveloperRepository mockedRepo = mock(DeveloperRepository.class);
    private DeveloperService sut = new DeveloperService(mockedRepo);

    @Test
    public void shouldInvokeCreateInRepo() {
        sut.create(CREATE_DEVELOPER);
        verify(mockedRepo, times(1)).create(CREATE_DEVELOPER);
    }

    @Test
    public void shouldInvokeGetByIdInRepo() {
        sut.getById(1L);
        verify(mockedRepo, times(1)).getById(1L);
    }

    @Test
    public void shouldInvokeUpdateInRepo() {
        sut.update(UPDATE_DEVELOPER);
        verify(mockedRepo, times(1)).update(UPDATE_DEVELOPER);
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
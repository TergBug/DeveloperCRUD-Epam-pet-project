package org.mycode.service;

import org.junit.Test;
import org.mycode.assembler.DeveloperAssembler;
import org.mycode.dto.DeveloperDto;
import org.mycode.repository.DeveloperRepository;
import org.mycode.service.impl.DeveloperServiceImpl;

import java.util.HashSet;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class DeveloperServiceTest {
    private static final UUID ID_FOR_CREATE = UUID.randomUUID();
    private static final UUID ID_FOR_GET = UUID.fromString("673c347a-ba46-4c5f-9f13-6d1a1aa29b81");
    private static final UUID ID_FOR_DELETE = UUID.fromString("9a4f7c3f-fe35-4b45-82f1-128b65cb3a92");
    private static final DeveloperDto CREATE_DEVELOPER = new DeveloperDto(ID_FOR_CREATE, "Joe",
            "Tred", new HashSet<>(), "");
    private static final DeveloperDto UPDATE_DEVELOPER = new DeveloperDto(ID_FOR_CREATE, "Jony",
            "Fedorov", new HashSet<>(), "");
    private DeveloperRepository mockedRepo = mock(DeveloperRepository.class);
    private DeveloperAssembler developerAssembler = mock(DeveloperAssembler.class);
    private DeveloperServiceImpl sut = new DeveloperServiceImpl(mockedRepo, developerAssembler);

    @Test
    public void shouldInvokeCreateInRepo() {
        sut.create(CREATE_DEVELOPER);
        verify(mockedRepo, times(1)).create(developerAssembler.assemble(CREATE_DEVELOPER));
    }

    @Test
    public void shouldInvokeGetByIdInRepo() {
        sut.getById(ID_FOR_GET);
        verify(mockedRepo, times(1)).getById(ID_FOR_GET);
    }

    @Test
    public void shouldInvokeUpdateInRepo() {
        sut.update(UPDATE_DEVELOPER);
        verify(mockedRepo, times(1)).update(developerAssembler.assemble(UPDATE_DEVELOPER));
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
package org.mycode.service;

import org.junit.Test;
import org.mycode.assembler.ProjectAssembler;
import org.mycode.dto.ProjectDto;
import org.mycode.repository.ProjectRepository;
import org.mycode.service.impl.ProjectServiceImpl;

import java.util.HashSet;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class ProjectServiceTest {
    private static final UUID ID_FOR_CREATE = UUID.randomUUID();
    private static final UUID ID_FOR_GET = UUID.fromString("e07748c4-88ba-4c69-a994-8bad8890b2b8");
    private static final UUID ID_FOR_DELETE = UUID.fromString("958c58da-dfae-4fcc-85bd-8d4a7454cfb4");
    private static final ProjectDto CREATE_PROJECT = new ProjectDto(ID_FOR_CREATE, "Dinot", new HashSet<>(),
            null, "DESIGN");
    private static final ProjectDto UPDATE_PROJECT = new ProjectDto(ID_FOR_CREATE, "Pejh", new HashSet<>(),
            null, "TESTING");
    private ProjectRepository mockedRepo = mock(ProjectRepository.class);
    private ProjectAssembler projectAssembler = mock(ProjectAssembler.class);
    private ProjectServiceImpl sut = new ProjectServiceImpl(mockedRepo, projectAssembler);

    @Test
    public void shouldInvokeCreateInRepo() {
        sut.create(CREATE_PROJECT);
        verify(mockedRepo, times(1)).create(projectAssembler.assemble(CREATE_PROJECT));
    }

    @Test
    public void shouldInvokeGetByIdInRepo() {
        sut.getById(ID_FOR_GET);
        verify(mockedRepo, times(1)).getById(ID_FOR_GET);
    }

    @Test
    public void shouldInvokeUpdateInRepo() {
        sut.update(UPDATE_PROJECT);
        verify(mockedRepo, times(1)).update(projectAssembler.assemble(UPDATE_PROJECT));
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

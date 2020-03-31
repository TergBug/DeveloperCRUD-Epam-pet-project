package org.mycode.service;

import org.junit.Test;
import org.mycode.assembler.SkillAssembler;
import org.mycode.dto.SkillDto;
import org.mycode.repository.SkillRepository;
import org.mycode.service.impl.SkillServiceImpl;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class SkillServiceTest {
    private static final UUID ID_FOR_CREATE = UUID.randomUUID();
    private static final UUID ID_FOR_GET = UUID.fromString("77351156-9d20-405e-a0c5-24e17b4b0ad0");
    private static final UUID ID_FOR_DELETE = UUID.fromString("e893a876-a583-42d4-99c1-a99ef99723db");
    private static final SkillDto CREATE_SKILL = new SkillDto(ID_FOR_CREATE, "Java");
    private static final SkillDto UPDATE_SKILL = new SkillDto(ID_FOR_CREATE, "JDBC");
    private SkillRepository mockedRepo = mock(SkillRepository.class);
    private SkillAssembler skillAssembler = mock(SkillAssembler.class);
    private SkillServiceImpl sut = new SkillServiceImpl(mockedRepo, skillAssembler);

    @Test
    public void shouldInvokeCreateInRepo() {
        sut.create(CREATE_SKILL);
        verify(mockedRepo, times(1)).create(skillAssembler.assemble(CREATE_SKILL));
    }

    @Test
    public void shouldInvokeGetByIdInRepo() {
        sut.getById(ID_FOR_GET);
        verify(mockedRepo, times(1)).getById(ID_FOR_GET);
    }

    @Test
    public void shouldInvokeUpdateInRepo() {
        sut.update(UPDATE_SKILL);
        verify(mockedRepo, times(1)).update(skillAssembler.assemble(UPDATE_SKILL));
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
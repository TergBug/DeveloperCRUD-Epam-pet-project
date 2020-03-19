package org.mycode.service;

import org.junit.Test;
import org.mycode.model.Skill;
import org.mycode.repository.SkillRepository;

import static org.mockito.Mockito.*;

public class SkillServiceTest {
    private static final Skill CREATE_SKILL = new Skill(5L, "Java");
    private static final Skill UPDATE_SKILL = new Skill(5L, "JDBC");
    private SkillRepository mockedRepo = mock(SkillRepository.class);
    private SkillService sut = new SkillService(mockedRepo);

    @Test
    public void shouldInvokeCreateInRepo() {
        sut.create(CREATE_SKILL);
        verify(mockedRepo, times(1)).create(CREATE_SKILL);
    }

    @Test
    public void shouldInvokeGetByIdInRepo() {
        sut.getById(1L);
        verify(mockedRepo, times(1)).getById(1L);
    }

    @Test
    public void shouldInvokeUpdateInRepo() {
        sut.update(UPDATE_SKILL);
        verify(mockedRepo, times(1)).update(UPDATE_SKILL);
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
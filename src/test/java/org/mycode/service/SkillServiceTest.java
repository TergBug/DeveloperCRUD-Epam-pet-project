package org.mycode.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mycode.model.Skill;
import org.mycode.repository.SkillRepository;
import org.mycode.testutil.TestUtils;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SkillServiceTest {
    @InjectMocks
    private static SkillService testedSkillService;
    @Mock
    private SkillRepository currentRepo;
    private Skill createSkill = new Skill(5L, "Java");
    private Skill updateSkill = new Skill(5L, "JDBC");

    @BeforeClass
    public static void connect() {
        TestUtils.switchConfigToTestMode();
        testedSkillService = TestUtils.getApplicationContext().getBean(SkillService.class);
    }

    @AfterClass
    public static void backProperty() {
        TestUtils.switchConfigToWorkMode();
    }

    @Test
    public void shouldInvokeCreateInRepo() {
        testedSkillService.create(createSkill);
        verify(currentRepo, times(1)).create(createSkill);
    }

    @Test
    public void shouldInvokeGetByIdInRepo() {
        testedSkillService.getById(1L);
        verify(currentRepo, times(1)).getById(1L);
    }

    @Test
    public void shouldInvokeUpdateInRepo() {
        testedSkillService.update(updateSkill);
        verify(currentRepo, times(1)).update(updateSkill);
    }

    @Test
    public void shouldInvokeDeleteInRepo() {
        testedSkillService.delete(2L);
        verify(currentRepo, times(1)).delete(2L);
    }

    @Test
    public void shouldInvokeGetAllInRepo() {
        testedSkillService.getAll();
        verify(currentRepo, times(1)).getAll();
    }
}
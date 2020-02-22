package org.mycode.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mycode.exceptions.RepoStorageException;
import org.mycode.model.Skill;
import org.mycode.repository.SkillRepository;
import org.mycode.testutil.TestUtils;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SkillServiceTest {
    @InjectMocks
    private static SkillService testedSkillService;
    @Mock
    private SkillRepository currentRepo;
    private Skill createSkill = new Skill(5L, "Java");
    private Skill updateSkill = new Skill(5L, "JDBC");
    @BeforeClass
    public static void connect(){
        TestUtils.switchConfigToTestMode();
        try {
            testedSkillService = TestUtils.getApplicationContext().getBean(SkillService.class);
        } catch (RepoStorageException e) {
            e.printStackTrace();
        }
    }
    @AfterClass
    public static void backProperty(){
        TestUtils.switchConfigToWorkMode();
    }
    @Test
    public void shouldInvokeCreateInRepo() {
        try {
            testedSkillService.create(createSkill);
            verify(currentRepo, times(1)).create(createSkill);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void shouldInvokeGetByIdInRepo() {
        try {
            testedSkillService.getById(1L);
            verify(currentRepo, times(1)).getById(1L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void shouldInvokeUpdateInRepo() {
        try {
            testedSkillService.update(updateSkill);
            verify(currentRepo, times(1)).update(updateSkill);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void shouldInvokeDeleteInRepo() {
        try {
            testedSkillService.delete(2L);
            verify(currentRepo, times(1)).delete(2L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void shouldInvokeGetAllInRepo() {
        try {
            testedSkillService.getAll();
            verify(currentRepo, times(1)).getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
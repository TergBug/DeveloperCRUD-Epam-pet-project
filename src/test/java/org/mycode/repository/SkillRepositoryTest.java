package org.mycode.repository;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mycode.controller.it.BaseIT;
import org.mycode.exceptions.NoSuchEntryException;
import org.mycode.model.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class SkillRepositoryTest extends BaseIT {
    private static final Logger LOG = Logger.getLogger(SkillRepositoryTest.class);
    private static final String HQL_QUERY_GET_BY_ID = "select distinct s from Skill s " +
            "where s.id=unhex(replace('?', '-', ''))";
    private static final UUID ID_FOR_GET = UUID.fromString("e893a876-a583-42d4-99c1-a99ef99723db");
    private static final UUID ID_SECOND = UUID.fromString("77351156-9d20-405e-a0c5-24e17b4b0ad0");
    private static final UUID ID_THIRD = UUID.fromString("ad34f1da-43cd-4742-948c-f2e59a975839");
    private static final UUID ID_FOR_DELETE = UUID.fromString("a8f6eab7-f1c9-4e3a-85fb-e4a1e0504de3");
    private static final Skill CREATED_SKILL = new Skill("HTML");
    private static final Skill READ_SKILL = new Skill(ID_FOR_GET, "Java");
    private static final Skill UPDATED_SKILL = new Skill(ID_FOR_GET, "JavaScript");
    private static final List<Skill> SKILLS_LIST = new ArrayList<>();
    @Autowired
    private SkillRepository sut;

    @Test
    @Transactional
    @Rollback
    public void shouldCreate() {
        sut.create(CREATED_SKILL);
        assertNotNull(sessionFactory.getCurrentSession().get(Skill.class, CREATED_SKILL.getId()));
        LOG.debug("Create");
    }

    @Test
    @Transactional
    @Rollback
    public void shouldGetById() {
        try {
            assertEquals(READ_SKILL, sut.getById(ID_FOR_GET));
            LOG.debug("Read");
        } catch (NoSuchEntryException e) {
            fail("Test is failed");
        }
    }

    @Test
    @Transactional
    @Rollback
    public void shouldUpdate() {
        try {
            sut.update(UPDATED_SKILL);
            assertEquals(UPDATED_SKILL, sessionFactory.getCurrentSession().get(Skill.class, UPDATED_SKILL.getId()));
            LOG.debug("Update");
        } catch (NoSuchEntryException e) {
            fail("Test is failed");
        }
    }

    @Test
    @Transactional
    @Rollback
    public void shouldDelete() {
        try {
            sut.delete(ID_FOR_DELETE);
            assertEquals(0, sessionFactory.getCurrentSession()
                    .createQuery(HQL_QUERY_GET_BY_ID.replace("?", ID_FOR_DELETE.toString()), Skill.class)
                    .list().size());
            LOG.debug("Delete");
        } catch (NoSuchEntryException e) {
            fail("Test is failed");
        }
    }

    @Test
    @Transactional
    @Rollback
    public void shouldGetAll() {
        Collections.addAll(SKILLS_LIST, new Skill(ID_FOR_GET, "Java"),
                new Skill(ID_SECOND, "C#"),
                new Skill(ID_THIRD, "JDBC"),
                new Skill(ID_FOR_DELETE, "JSON"));
        assertEquals(SKILLS_LIST, sut.getAll());
        LOG.debug("GetAll");
    }
}
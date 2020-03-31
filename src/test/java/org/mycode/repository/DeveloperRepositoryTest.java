package org.mycode.repository;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mycode.controller.it.BaseIT;
import org.mycode.exceptions.NoSuchEntryException;
import org.mycode.model.Account;
import org.mycode.model.Developer;
import org.mycode.model.Skill;
import org.mycode.model.enums.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class DeveloperRepositoryTest extends BaseIT {
    private static final Logger LOG = Logger.getLogger(DeveloperRepositoryTest.class);
    private static final String HQL_QUERY_GET_BY_ID = "select distinct d from Developer d left join fetch d.skills " +
            "where d.id=unhex(replace('?', '-', ''))";
    private static final UUID ID_FOR_GET = UUID.fromString("9a4f7c3f-fe35-4b45-82f1-128b65cb3a92");
    private static final UUID ID_SECOND = UUID.fromString("cff6549f-b728-4ed5-857e-3027e7e6a370");
    private static final UUID ID_THIRD = UUID.fromString("673c347a-ba46-4c5f-9f13-6d1a1aa29b81");
    private static final UUID ID_FOR_DELETE = UUID.fromString("6bc3db84-1930-438a-b230-63379ac93ded");

    private static final Account FIRST_ACCOUNT = new Account(
            UUID.fromString("77845a88-c171-4cd0-a73a-3045bdbe4f30"), "LiXiao", AccountStatus.ACTIVE);
    private static final Account SECOND_ACCOUNT = new Account(
            UUID.fromString("e86937cd-1535-4c0e-9026-e6ae27adc992"), "LiXiao", AccountStatus.ACTIVE);
    private static final Account THIRD_ACCOUNT = new Account(
            UUID.fromString("9743c5ef-4e76-4fc1-9b1b-a88f352cb22e"), "LiXiao", AccountStatus.ACTIVE);
    private static final Account FOURTH_ACCOUNT = new Account(
            UUID.fromString("effaa7cc-373e-47f8-8ff6-7d227f950cfe"), "LiXiao", AccountStatus.ACTIVE);

    private static final Skill FIRST_SKILL = new Skill(
            UUID.fromString("e893a876-a583-42d4-99c1-a99ef99723db"), "Java");
    private static final Skill SECOND_SKILL = new Skill(
            UUID.fromString("77351156-9d20-405e-a0c5-24e17b4b0ad0"), "C#");
    private static final Skill THIRD_SKILL = new Skill(
            UUID.fromString("ad34f1da-43cd-4742-948c-f2e59a975839"), "JDBC");
    private static final Skill FOURTH_SKILL = new Skill(
            UUID.fromString("a8f6eab7-f1c9-4e3a-85fb-e4a1e0504de3"), "JSON");

    private static final Developer CREATED_DEVELOPER = new Developer("Lord", "Dog",
            Arrays.stream(new Skill[]{SECOND_SKILL}).collect(Collectors.toSet()), THIRD_ACCOUNT);

    private static final Developer READ_DEVELOPER = new Developer(ID_FOR_GET, "Din", "Ford",
            Arrays.stream(new Skill[]{FIRST_SKILL, THIRD_SKILL}).collect(Collectors.toSet()), FIRST_ACCOUNT);

    private static final Developer UPDATED_DEVELOPER = new Developer(ID_FOR_GET, "Dinis", "Wong",
            Arrays.stream(new Skill[]{THIRD_SKILL}).collect(Collectors.toSet()), SECOND_ACCOUNT);

    private static final List<Developer> DEVELOPERS_LIST = new ArrayList<>();
    @Autowired
    private DeveloperRepository sut;

    @Test
    @Transactional
    @Rollback
    public void shouldCreate() {
        sut.create(CREATED_DEVELOPER);
        assertNotNull(sessionFactory.getCurrentSession().get(Developer.class, CREATED_DEVELOPER.getId()));
        LOG.debug("Create");
    }

    @Test
    @Transactional
    @Rollback
    public void shouldGetById() {
        try {
            assertEquals(READ_DEVELOPER, sut.getById(ID_FOR_GET));
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
            sut.update(UPDATED_DEVELOPER);
            assertEquals(UPDATED_DEVELOPER, sessionFactory.getCurrentSession()
                    .get(Developer.class, UPDATED_DEVELOPER.getId()));
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
                    .createQuery(HQL_QUERY_GET_BY_ID.replace("?", ID_FOR_DELETE.toString()), Developer.class)
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
        Collections.addAll(DEVELOPERS_LIST,
                new Developer(ID_THIRD, "Gird", "Long",
                        Arrays.stream(new Skill[]{FIRST_SKILL, SECOND_SKILL}).collect(Collectors.toSet()),
                        THIRD_ACCOUNT),
                new Developer(ID_FOR_DELETE, "Nord", "Cir", null, null),
                READ_DEVELOPER,
                new Developer(ID_SECOND, "Xiaoming", "Li",
                        Arrays.stream(new Skill[]{SECOND_SKILL}).collect(Collectors.toSet()),
                        FIRST_ACCOUNT));

        assertEquals(DEVELOPERS_LIST, sut.getAll());
        LOG.debug("GetAll");
    }
}
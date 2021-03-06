package org.mycode.repository;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mycode.BaseIT;
import org.mycode.exceptions.NoSuchEntryException;
import org.mycode.model.Account;
import org.mycode.testutil.TestedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

public class AccountRepositoryTest extends BaseIT {
    private static final Logger LOG = Logger.getLogger(AccountRepositoryTest.class);
    private static final String HQL_QUERY_GET_BY_ID = "select distinct a from Account a " +
            "where a.id=unhex(replace('?', '-', ''))";
    @Autowired
    private AccountRepository sut;
    @Autowired
    private TestedEntities util;

    @Test
    @Transactional
    @Rollback
    public void shouldCreate() {
        sut.create(util.getEntity(Account.class, TestedEntities.ENTITY_TO_CREATE));
        assertNotNull(sessionFactory.getCurrentSession().get(Account.class,
                util.getEntity(Account.class, TestedEntities.ENTITY_TO_CREATE).getId()));
        LOG.debug("Create");
    }

    @Test
    @Transactional
    @Rollback
    public void shouldGetById() {
        try {
            assertEquals(util.getEntity(Account.class, TestedEntities.ENTITY_TO_GET),
                    sut.getById(util.getEntity(Account.class, TestedEntities.ENTITY_TO_GET).getId()));
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
            sut.update(util.getEntity(Account.class, TestedEntities.ENTITY_TO_UPDATE));
            assertEquals(util.getEntity(Account.class, TestedEntities.ENTITY_TO_UPDATE),
                    sessionFactory.getCurrentSession().get(Account.class,
                            util.getEntity(Account.class, TestedEntities.ENTITY_TO_UPDATE).getId()));
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
            sut.delete(util.getEntity(Account.class, TestedEntities.ENTITY_TO_DELETE).getId());
            assertEquals(0, sessionFactory.getCurrentSession()
                    .createQuery(HQL_QUERY_GET_BY_ID.replace("?",
                            util.getEntity(Account.class, TestedEntities.ENTITY_TO_DELETE).getId().toString()),
                            Account.class)
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
        assertEquals(util.getEntityList(Account.class), sut.getAll());
        LOG.debug("GetAll");
    }
}
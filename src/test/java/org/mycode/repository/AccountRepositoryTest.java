package org.mycode.repository;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mycode.controller.it.BaseIT;
import org.mycode.exceptions.NoSuchEntryException;
import org.mycode.model.Account;
import org.mycode.model.enums.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class AccountRepositoryTest extends BaseIT {
    private static final Logger LOG = Logger.getLogger(AccountRepositoryTest.class);
    private static final String HQL_QUERY_GET_BY_ID = "select distinct a from Account a " +
            "where a.id=unhex(replace('?', '-', ''))";
    private static final UUID ID_FOR_GET = UUID.fromString("77845a88-c171-4cd0-a73a-3045bdbe4f30");
    private static final UUID ID_SECOND = UUID.fromString("e86937cd-1535-4c0e-9026-e6ae27adc992");
    private static final UUID ID_THIRD = UUID.fromString("9743c5ef-4e76-4fc1-9b1b-a88f352cb22e");
    private static final UUID ID_FOR_DELETE = UUID.fromString("effaa7cc-373e-47f8-8ff6-7d227f950cfe");
    private static final Account CREATED_ACCOUNT = new Account("Lord", AccountStatus.ACTIVE);
    private static final Account READ_ACCOUNT = new Account(ID_FOR_GET, "LiXiao", AccountStatus.ACTIVE);
    private static final Account UPDATED_ACCOUNT = new Account(ID_FOR_GET, "Ming", AccountStatus.BANNED);
    private static final List<Account> ACCOUNTS_LIST = new ArrayList<>();
    @Autowired
    private AccountRepository sut;

    @Test
    @Transactional
    @Rollback
    public void shouldCreate() {
        sut.create(CREATED_ACCOUNT);
        assertNotNull(sessionFactory.getCurrentSession().get(Account.class, CREATED_ACCOUNT.getId()));
        LOG.debug("Create");
    }

    @Test
    @Transactional
    @Rollback
    public void shouldGetById() {
        try {
            assertEquals(READ_ACCOUNT, sut.getById(ID_FOR_GET));
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
            sut.update(UPDATED_ACCOUNT);
            assertEquals(UPDATED_ACCOUNT, sessionFactory.getCurrentSession()
                    .get(Account.class, UPDATED_ACCOUNT.getId()));
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
                    .createQuery(HQL_QUERY_GET_BY_ID.replace("?", ID_FOR_DELETE.toString()), Account.class)
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
        Collections.addAll(ACCOUNTS_LIST, new Account(ID_FOR_GET, "LiXiao", AccountStatus.ACTIVE),
                new Account(ID_SECOND, "Din", AccountStatus.DELETED),
                new Account(ID_THIRD, "Geek", AccountStatus.BANNED),
                new Account(ID_FOR_DELETE, "Ford", AccountStatus.ACTIVE));
        assertEquals(ACCOUNTS_LIST, sut.getAll());
        LOG.debug("GetAll");
    }
}
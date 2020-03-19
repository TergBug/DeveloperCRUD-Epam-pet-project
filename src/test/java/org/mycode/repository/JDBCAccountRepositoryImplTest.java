package org.mycode.repository;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mycode.controller.it.BaseIT;
import org.mycode.exceptions.NoSuchEntryException;
import org.mycode.exceptions.NotUniqueEntryException;
import org.mycode.exceptions.RepoStorageException;
import org.mycode.mapping.JDBCAccountMapper;
import org.mycode.model.Account;
import org.mycode.model.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class JDBCAccountRepositoryImplTest extends BaseIT {
    private static final Logger LOG = Logger.getLogger(JDBCAccountRepositoryImplTest.class);
    private static final String SELECT_QUERY_FOR_CREATE = "select * from accounts group by id having max(id);";
    private static final String SELECT_QUERY = "select * from accounts where id=?;";
    private static final Account CREATED_ACCOUNT = new Account(5L, "Lord", AccountStatus.ACTIVE);
    private static final Account READ_ACCOUNT = new Account(2L, "Din", AccountStatus.DELETED);
    private static final Account UPDATED_ACCOUNT = new Account(1L, "Ming", AccountStatus.BANNED);
    private static final List<Account> ACCOUNTS_LIST = new ArrayList<>();
    @Autowired
    private AccountRepository sut;

    @Before
    public void connectAndInitDB() {
        connectionAndInitDB.reInitDB();
    }

    @Test
    public void shouldCreate() {
        try (Statement statement = connectionAndInitDB.getConnection()
                .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            sut.create(CREATED_ACCOUNT);
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY_FOR_CREATE);
            assertEquals(CREATED_ACCOUNT, new JDBCAccountMapper().map(resultSet, 5L));
            LOG.debug("Create");
        } catch (RepoStorageException | SQLException | NoSuchEntryException e) {
            fail();
        }
    }

    @Test
    public void shouldGetById() {
        try {
            assertEquals(READ_ACCOUNT, sut.getById(2L));
            LOG.debug("Read");
        } catch (RepoStorageException | NoSuchEntryException | NotUniqueEntryException e) {
            fail();
        }
    }

    @Test
    public void shouldUpdate() {
        try (PreparedStatement statement = connectionAndInitDB.getConnection()
                .prepareStatement(SELECT_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            sut.update(UPDATED_ACCOUNT);
            statement.setLong(1, 1);
            ResultSet resultSet = statement.executeQuery();
            assertEquals(UPDATED_ACCOUNT, new JDBCAccountMapper().map(resultSet, 1L));
            LOG.debug("Update");
        } catch (RepoStorageException | SQLException | NoSuchEntryException e) {
            fail();
        }
    }

    @Test
    public void shouldDelete() {
        try (PreparedStatement statement = connectionAndInitDB.getConnection()
                .prepareStatement(SELECT_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            sut.delete(4L);
            statement.setLong(1, 4);
            assertFalse(statement.executeQuery().next());
            LOG.debug("Delete");
        } catch (RepoStorageException | SQLException | NoSuchEntryException e) {
            fail();
        }
    }

    @Test
    public void shouldGetAll() {
        try {
            Collections.addAll(ACCOUNTS_LIST, new Account(1L, "LiXiao", AccountStatus.ACTIVE),
                    new Account(2L, "Din", AccountStatus.DELETED),
                    new Account(3L, "Geek", AccountStatus.BANNED),
                    new Account(4L, "Ford", AccountStatus.ACTIVE));
            assertEquals(ACCOUNTS_LIST, sut.getAll());
            LOG.debug("GetAll");
        } catch (RepoStorageException | NoSuchEntryException e) {
            fail();
        }
    }
}
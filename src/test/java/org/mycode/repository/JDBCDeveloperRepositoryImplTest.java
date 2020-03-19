package org.mycode.repository;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mycode.controller.it.BaseIT;
import org.mycode.exceptions.NoSuchEntryException;
import org.mycode.exceptions.RepoStorageException;
import org.mycode.mapping.JDBCDeveloperMapper;
import org.mycode.model.Account;
import org.mycode.model.AccountStatus;
import org.mycode.model.Developer;
import org.mycode.model.Skill;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class JDBCDeveloperRepositoryImplTest extends BaseIT {
    private static final Logger LOG = Logger.getLogger(JDBCDeveloperRepositoryImplTest.class);
    private static final String SELECT_QUERY_FOR_CREATE = "select max(d.id), d.first_name, d.last_name, s.id, s.name, a.id, a.name, a.status " +
            "from developers d " +
            "left join (" +
            "select ds.developer_id, s.id, s.name " +
            "from developer_skill ds " +
            "inner join skills s " +
            "on ds.skill_id = s.id) s " +
            "on d.id = s.developer_id " +
            "left join accounts a " +
            "on d.account_id = a.id " +
            "group by s.id, d.last_name, d.first_name, s.name, a.id, a.name, a.status;";

    private static final String SELECT_QUERY = "select d.id, d.first_name, d.last_name, s.id, s.name, a.id, a.name, a.status " +
            "from developers d " +
            "left join (" +
            "select ds.developer_id, s.id, s.name " +
            "from developer_skill ds " +
            "inner join skills s " +
            "on ds.skill_id = s.id) s " +
            "on d.id = s.developer_id " +
            "left join accounts a " +
            "on d.account_id = a.id " +
            "where d.id=?;";

    private static final Developer CREATED_DEVELOPER = new Developer(5L, "Lord", "Dog",
            Arrays.stream(new Skill[]{new Skill(2L, "C#")}).collect(Collectors.toSet()),
            new Account(3L, "Geek", AccountStatus.BANNED));

    private static final Developer READ_DEVELOPER = new Developer(2L, "Xiaoming", "Li",
            Arrays.stream(new Skill[]{new Skill(2L, "C#")}).collect(Collectors.toSet()),
            new Account(1L, "LiXiao", AccountStatus.ACTIVE));

    private static final Developer UPDATED_DEVELOPER = new Developer(1L, "Dinis", "Wong",
            Arrays.stream(new Skill[]{new Skill(3L, "JDBC")}).collect(Collectors.toSet()),
            new Account(2L, "Din", AccountStatus.DELETED));

    private static final List<Developer> DEVELOPERS_LIST = new ArrayList<>();
    @Autowired
    private DeveloperRepository sut;

    @Before
    public void connectAndInitDB() {
        connectionAndInitDB.reInitDB();
    }

    @Test
    public void shouldCreate() {
        try (Statement statement = connectionAndInitDB.getConnection()
                .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            sut.create(CREATED_DEVELOPER);
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY_FOR_CREATE);
            assertEquals(CREATED_DEVELOPER, new JDBCDeveloperMapper().map(resultSet, 5L));
            LOG.debug("Create");
        } catch (RepoStorageException | SQLException | NoSuchEntryException e) {
            fail();
        }
    }

    @Test
    public void shouldGetById() {
        try {
            assertEquals(READ_DEVELOPER, sut.getById(2L));
            LOG.debug("Read");
        } catch (RepoStorageException | NoSuchEntryException e) {
            fail();
        }
    }

    @Test
    public void shouldUpdate() {
        try (PreparedStatement statement = connectionAndInitDB.getConnection()
                .prepareStatement(SELECT_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            sut.update(UPDATED_DEVELOPER);
            statement.setLong(1, 1);
            ResultSet resultSet = statement.executeQuery();
            assertEquals(UPDATED_DEVELOPER, new JDBCDeveloperMapper().map(resultSet, 1L));
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
            Collections.addAll(DEVELOPERS_LIST,
                    new Developer(1L, "Din", "Ford",
                            Arrays.stream(new Skill[]{new Skill(1L, "Java"),
                                    new Skill(3L, "JDBC")}).collect(Collectors.toSet()),
                            new Account(2L, "Din", AccountStatus.DELETED)),

                    new Developer(2L, "Xiaoming", "Li",
                            Arrays.stream(new Skill[]{new Skill(2L, "C#")}).collect(Collectors.toSet()),
                            new Account(1L, "LiXiao", AccountStatus.ACTIVE)),

                    new Developer(3L, "Gird", "Long",
                            Arrays.stream(new Skill[]{new Skill(1L, "Java"),
                                    new Skill(2L, "C#")}).collect(Collectors.toSet()),
                            new Account(3L, "Geek", AccountStatus.BANNED)),

                    new Developer(4L, "Gordon", "Fong",
                            new HashSet<>(),
                            new Account(1L, "LiXiao", AccountStatus.ACTIVE)));

            assertEquals(DEVELOPERS_LIST, sut.getAll());
            LOG.debug("GetAll");
        } catch (RepoStorageException | NoSuchEntryException e) {
            fail();
        }
    }
}
package org.mycode.repository;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mycode.controller.it.BaseIT;
import org.mycode.exceptions.NoSuchEntryException;
import org.mycode.exceptions.NotUniqueEntryException;
import org.mycode.exceptions.RepoStorageException;
import org.mycode.mapping.JDBCSkillMapper;
import org.mycode.model.Skill;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class JDBCSkillRepositoryImplTest extends BaseIT {
    private static final Logger LOG = Logger.getLogger(JDBCSkillRepositoryImplTest.class);
    private static final String SELECT_QUERY_FOR_CREATE = "select * from skills group by id having max(id);";
    private static final String SELECT_QUERY = "select * from skills where id=?;";
    private static final Skill CREATED_SKILL = new Skill(5L, "HTML");
    private static final Skill READ_SKILL = new Skill(2L, "C#");
    private static final Skill UPDATED_SKILL = new Skill(1L, "JavaScript");
    private static final List<Skill> SKILLS_LIST = new ArrayList<>();
    @Autowired
    private SkillRepository sut;

    @Before
    public void connectAndInitDB() {
        connectionAndInitDB.reInitDB();
    }

    @Test
    public void shouldCreate() {
        try (Statement statement = connectionAndInitDB.getConnection()
                .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            sut.create(CREATED_SKILL);
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY_FOR_CREATE);
            assertEquals(CREATED_SKILL, new JDBCSkillMapper().map(resultSet, 5L));
            LOG.debug("Create");
        } catch (RepoStorageException | SQLException | NoSuchEntryException e) {
            fail("Test is failed");
        }
    }

    @Test
    public void shouldGetById() {
        try {
            assertEquals(READ_SKILL, sut.getById(2L));
            LOG.debug("Read");
        } catch (RepoStorageException | NoSuchEntryException | NotUniqueEntryException e) {
            fail("Test is failed");
        }
    }

    @Test
    public void shouldUpdate() {
        try (PreparedStatement statement = connectionAndInitDB.getConnection()
                .prepareStatement(SELECT_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            sut.update(UPDATED_SKILL);
            statement.setLong(1, 1);
            ResultSet resultSet = statement.executeQuery();
            assertEquals(UPDATED_SKILL, new JDBCSkillMapper().map(resultSet, 1L));
            LOG.debug("Update");
        } catch (RepoStorageException | SQLException | NoSuchEntryException e) {
            fail("Test is failed");
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
            fail("Test is failed");
        }
    }

    @Test
    public void shouldGetAll() {
        try {
            Collections.addAll(SKILLS_LIST, new Skill(1L, "Java"),
                    new Skill(2L, "C#"),
                    new Skill(3L, "JDBC"),
                    new Skill(4L, "JSON"));
            assertEquals(SKILLS_LIST, sut.getAll());
            LOG.debug("GetAll");
        } catch (RepoStorageException | NoSuchEntryException e) {
            fail("Test is failed");
        }
    }
}
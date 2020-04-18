package org.mycode.repository;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mycode.BaseIT;
import org.mycode.exceptions.NoSuchEntryException;
import org.mycode.model.Project;
import org.mycode.testutil.TestedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

public class ProjectRepositoryTest extends BaseIT {
    private static final Logger LOG = Logger.getLogger(ProjectRepositoryTest.class);
    private static final String HQL_QUERY_GET_BY_ID = "select distinct p from Project p left join fetch p.developers " +
            "where p.id=unhex(replace('?', '-', ''))";
    @Autowired
    private ProjectRepository sut;

    @Test
    @Transactional
    @Rollback
    public void shouldCreate() {
        sut.create(util.getEntity(Project.class, TestedEntities.ENTITY_TO_CREATE));
        assertNotNull(sessionFactory.getCurrentSession().get(Project.class,
                util.getEntity(Project.class, TestedEntities.ENTITY_TO_CREATE).getId()));
        LOG.debug("Create");
    }

    @Test
    @Transactional
    @Rollback
    public void shouldGetById() {
        try {
            assertEquals(util.getEntity(Project.class, TestedEntities.ENTITY_TO_GET),
                    sut.getById(util.getEntity(Project.class, TestedEntities.ENTITY_TO_GET).getId()));
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
            sut.update(util.getEntity(Project.class, TestedEntities.ENTITY_TO_UPDATE));
            assertEquals(util.getEntity(Project.class, TestedEntities.ENTITY_TO_UPDATE),
                    sessionFactory.getCurrentSession().get(Project.class,
                            util.getEntity(Project.class, TestedEntities.ENTITY_TO_UPDATE).getId()));
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
            sut.delete(util.getEntity(Project.class, TestedEntities.ENTITY_TO_DELETE).getId());
            assertEquals(0, sessionFactory.getCurrentSession()
                    .createQuery(HQL_QUERY_GET_BY_ID.replace("?",
                            util.getEntity(Project.class, TestedEntities.ENTITY_TO_DELETE).getId().toString()),
                            Project.class)
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
        assertEquals(util.getEntityList(Project.class), sut.getAll());
        LOG.debug("GetAll");
    }
}

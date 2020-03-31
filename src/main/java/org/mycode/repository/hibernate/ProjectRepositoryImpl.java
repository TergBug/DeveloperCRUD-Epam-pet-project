package org.mycode.repository.hibernate;

import org.hibernate.SessionFactory;
import org.mycode.exceptions.NoSuchEntryException;
import org.mycode.model.Project;
import org.mycode.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@EnableTransactionManagement
public class ProjectRepositoryImpl implements ProjectRepository {
    private static final String HQL_QUERY_GET_BY_ID = "from Project p left join fetch p.developers " +
            "where p.id=unhex(replace('?', '-', ''))";
    private static final String HQL_QUERY_GET_ALL = "from Project p left join fetch p.developers";
    private SessionFactory sessionFactory;

    @Autowired
    public ProjectRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void create(Project model) {
        sessionFactory.getCurrentSession().save(model);
    }

    @Override
    @Transactional(readOnly = true)
    public Project getById(UUID readID) {
        List<Project> readProjects = sessionFactory.getCurrentSession()
                .createQuery(HQL_QUERY_GET_BY_ID.replace("?", readID.toString()), Project.class)
                .getResultList();
        if (readProjects == null || readProjects.size() != 1) {
            throw new NoSuchEntryException("Entity for read not found");
        }
        return readProjects.get(0);
    }

    @Override
    @Transactional
    public void update(Project updatedModel) {
        Project updatedProject = sessionFactory.getCurrentSession().get(Project.class, updatedModel.getId());
        if (updatedProject == null) {
            throw new NoSuchEntryException("Entity for update not found");
        }
        sessionFactory.getCurrentSession().detach(updatedProject);
        sessionFactory.getCurrentSession().update(updatedModel);
    }

    @Override
    @Transactional
    public void delete(UUID deletedEntryId) {
        Project deletedProject = sessionFactory.getCurrentSession().get(Project.class, deletedEntryId);
        if (deletedProject == null) {
            throw new NoSuchEntryException("Entity for delete not found");
        }
        sessionFactory.getCurrentSession().delete(deletedProject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getAll() {
        return sessionFactory.getCurrentSession()
                .createQuery(HQL_QUERY_GET_ALL, Project.class).list();
    }
}

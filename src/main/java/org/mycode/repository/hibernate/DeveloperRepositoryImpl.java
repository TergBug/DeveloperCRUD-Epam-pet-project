package org.mycode.repository.hibernate;

import org.hibernate.SessionFactory;
import org.mycode.exceptions.NoSuchEntryException;
import org.mycode.model.Developer;
import org.mycode.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@EnableTransactionManagement
public class DeveloperRepositoryImpl implements DeveloperRepository {
    private static final String HQL_QUERY_GET_BY_ID = "select distinct d from Developer d left join fetch d.skills " +
            "where d.id=unhex(replace('?', '-', ''))";
    private static final String HQL_QUERY_GET_ALL = "select distinct d from Developer d left join fetch d.skills";
    private SessionFactory sessionFactory;

    @Autowired
    public DeveloperRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void create(Developer model) {
        sessionFactory.getCurrentSession().save(model);
    }

    @Override
    @Transactional(readOnly = true)
    public Developer getById(UUID readID) {
        List<Developer> readDevelopers = sessionFactory.getCurrentSession()
                .createQuery(HQL_QUERY_GET_BY_ID.replace("?", readID.toString()), Developer.class)
                .getResultList();
        if (readDevelopers == null || readDevelopers.size() != 1) {
            throw new NoSuchEntryException("Entity for read not found");
        }
        return readDevelopers.get(0);
    }

    @Override
    @Transactional
    public void update(Developer updatedModel) {
        Developer updatedDeveloper = sessionFactory.getCurrentSession().get(Developer.class, updatedModel.getId());
        if (updatedDeveloper == null) {
            throw new NoSuchEntryException("Entity for update not found");
        }
        sessionFactory.getCurrentSession().detach(updatedDeveloper);
        sessionFactory.getCurrentSession().update(updatedModel);
    }

    @Override
    @Transactional
    public void delete(UUID deletedEntryId) {
        Developer deletedDeveloper = sessionFactory.getCurrentSession().get(Developer.class, deletedEntryId);
        if (deletedDeveloper == null) {
            throw new NoSuchEntryException("Entity for delete not found");
        }
        sessionFactory.getCurrentSession().delete(deletedDeveloper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Developer> getAll() {
        return sessionFactory.getCurrentSession()
                .createQuery(HQL_QUERY_GET_ALL, Developer.class).list();
    }
}

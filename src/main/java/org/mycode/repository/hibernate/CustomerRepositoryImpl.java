package org.mycode.repository.hibernate;

import org.hibernate.SessionFactory;
import org.mycode.exceptions.NoSuchEntryException;
import org.mycode.model.Customer;
import org.mycode.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.UUID;

@Repository
@EnableTransactionManagement
public class CustomerRepositoryImpl implements CustomerRepository {
    private static final String HQL_QUERY_GET_BY_ID = "select distinct c from Customer c left join fetch c.projects " +
            "where c.id=unhex(replace('?', '-', ''))";
    private static final String HQL_QUERY_GET_ALL = "select distinct c from Customer c left join fetch c.projects";
    private SessionFactory sessionFactory;

    @Autowired
    public CustomerRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void create(Customer model) {
        sessionFactory.getCurrentSession().save(model);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getById(UUID readID) {
        Customer outputCustomer;
        try {
            List<Customer> readCustomers = sessionFactory.getCurrentSession()
                    .createQuery(HQL_QUERY_GET_BY_ID.replace("?", readID.toString()), Customer.class)
                    .getResultList();
            if (readCustomers == null || readCustomers.size() != 1) {
                throw new NoSuchEntryException("Entity for read not found");
            }
            outputCustomer = readCustomers.get(0);
        } catch (EntityExistsException e) {
            outputCustomer = sessionFactory.getCurrentSession().get(Customer.class, readID);
        }
        return outputCustomer;
    }

    @Override
    @Transactional
    public void update(Customer updatedModel) {
        Customer updatedCustomer = sessionFactory.getCurrentSession().get(Customer.class, updatedModel.getId());
        if (updatedCustomer == null) {
            throw new NoSuchEntryException("Entity for update not found");
        }
        sessionFactory.getCurrentSession().detach(updatedCustomer);
        sessionFactory.getCurrentSession().update(updatedModel);
    }

    @Override
    @Transactional
    public void delete(UUID deletedEntryId) {
        Customer deletedCustomer = sessionFactory.getCurrentSession().get(Customer.class, deletedEntryId);
        if (deletedCustomer == null) {
            throw new NoSuchEntryException("Entity for delete not found");
        }
        sessionFactory.getCurrentSession().delete(deletedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getAll() {
        return sessionFactory.getCurrentSession()
                .createQuery(HQL_QUERY_GET_ALL, Customer.class).list();
    }
}
